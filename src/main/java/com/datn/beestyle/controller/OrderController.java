package com.datn.beestyle.controller;

import com.datn.beestyle.dto.ApiResponse;
import com.datn.beestyle.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping(path = "/admin/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ApiResponse<?> getOrders(Pageable pageable,
                                    @RequestParam(required = false) String keyword,
                                    @RequestParam(required = false) String orderChannel,
                                    @RequestParam(required = false) String orderStatus) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Orders",
                this.orderService.getOrdersFilterByFields(pageable, keyword, orderChannel, orderStatus));
    }

    @GetMapping("/sale/order-pending")
    public ApiResponse<?> getOrdersPending() {
        return new ApiResponse<>(HttpStatus.OK.value(), "Orders Pending",
                this.orderService.getOrdersPending());
    }

    @GetMapping("/{orderId}")
    public ApiResponse<?> getOrderDetail(@PathVariable("orderId") Long orderId) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Order detail");
    }

    @GetMapping("/create")
    public ApiResponse<?> createOrder() {
        return new ApiResponse<>(HttpStatus.OK.value(), "Orders");
    }
}
