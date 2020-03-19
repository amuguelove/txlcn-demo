package com.flygopher.order;

import com.flygopher.common.base.feign.domain.OrderModel;
import com.flygopher.common.base.utils.Mapper;
import com.flygopher.lcn.LcnOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    private final LcnOrderService lcnOrderService;

    @Autowired
    public OrderController(
            OrderService orderService,
            LcnOrderService lcnOrderService) {
        this.orderService = orderService;
        this.lcnOrderService = lcnOrderService;
    }

    @PostMapping("/user/{username}/product/{productName}/{productUnit}")
    public OrderModel order(
            @PathVariable("username") String username,
            @PathVariable("productName") String productName,
            @PathVariable("productUnit") Integer productUnit) {
        return Mapper.map(orderService.order(username, productName, productUnit), OrderModel.class);
    }

    @PostMapping("/user/{username}/product/{productName}/{productUnit}/lcn")
    public OrderModel orderByLcn(
            @PathVariable("username") String username,
            @PathVariable("productName") String productName,
            @PathVariable("productUnit") Integer productUnit) {
        return Mapper.map(lcnOrderService.order(username, productName, productUnit), OrderModel.class);
    }
}
