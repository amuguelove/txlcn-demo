package com.flygopher.order;

import com.flygopher.common.base.feign.domain.ProductModel;
import com.flygopher.common.base.feign.inventory.InventoryClient;
import com.flygopher.common.base.feign.payment.PaymentClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final InventoryClient inventoryClient;

    private final PaymentClient paymentClient;

    @Autowired
    public OrderService(
            OrderRepository orderRepository,
            InventoryClient inventoryClient,
            PaymentClient paymentClient) {
        this.orderRepository = orderRepository;
        this.inventoryClient = inventoryClient;
        this.paymentClient = paymentClient;
    }

//    @Transactional
    public Order order(String username, String productName, Integer productUnit) {
        ProductModel productModel = inventoryClient.getProductByName(productName);
        int totalAmount = productModel.getPrice() * productUnit;

        Order order = new Order();
        order.setUsername(username);
        order.setProductName(productName);
        order.setProductUnit(productUnit);
        order.setAmount(totalAmount);
        order.setStatus(OrderStatus.UNPAID);
        orderRepository.save(order);

        boolean hasStock = inventoryClient.updateProductStock(productName, productUnit);
        if (hasStock) {
            boolean hasBalance = paymentClient.updateBalance(username, totalAmount);
            if (hasBalance) {
                order.setStatus(OrderStatus.PAID);
            } else {
                order.setStatus(OrderStatus.FAILED);
                order.setReason("账户余额不足");
                orderRepository.save(order);

                throw new RuntimeException("账户余额不足");
            }
        } else {
            order.setStatus(OrderStatus.FAILED);
            order.setReason("商品库存不足");
            orderRepository.save(order);

            throw new RuntimeException("商品库存不足");
        }
        return orderRepository.save(order);
    }
}
