package com.flygopher.lcn;

import com.codingapi.txlcn.tc.annotation.TccTransaction;
import com.flygopher.common.base.feign.domain.ProductModel;
import com.flygopher.common.base.feign.inventory.InventoryClient;
import com.flygopher.common.base.feign.payment.PaymentClient;
import com.flygopher.order.Order;
import com.flygopher.order.OrderRepository;
import com.flygopher.order.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LcnOrderService {

    private final OrderRepository orderRepository;

    private final InventoryClient inventoryClient;

    private final PaymentClient paymentClient;

    @Autowired
    public LcnOrderService(
            OrderRepository orderRepository,
            InventoryClient inventoryClient,
            PaymentClient paymentClient) {
        this.orderRepository = orderRepository;
        this.inventoryClient = inventoryClient;
        this.paymentClient = paymentClient;
    }

//    @Transactional
    @TccTransaction(confirmMethod = "confirmOrder", cancelMethod = "cancelOrder")
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

        boolean hasStock = inventoryClient.updateProductStockByLcn(productName, productUnit);
        if (hasStock) {
            boolean hasBalance = paymentClient.updateBalanceByLcn(username, totalAmount);
            if (hasBalance) {
                order.setStatus(OrderStatus.PAID);
            } else {
                order.setStatus(OrderStatus.FAILED);
                order.setReason("账户余额不足");
                orderRepository.save(order);

                throw new IllegalStateException("账户余额不足");
            }
        } else {
            order.setStatus(OrderStatus.FAILED);
            order.setReason("商品库存不足");
            orderRepository.save(order);

            throw new IllegalStateException("商品库存不足");
        }
        return orderRepository.save(order);
    }

    public void confirmOrder(String username, String productName, Integer productUnit) {

    }

    public void cancelOrder(String username, String productName, Integer productUnit) {
        inventoryClient.cancelUpdateProductStockByLcn(productName, productUnit);
    }

}
