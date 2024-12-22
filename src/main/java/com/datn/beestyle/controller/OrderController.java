package com.datn.beestyle.controller;

import com.datn.beestyle.dto.ApiResponse;
import com.datn.beestyle.dto.order.CreateOrderRequest;
import com.datn.beestyle.service.order.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Validated
@RestController
@RequestMapping(path = "/admin/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ApiResponse<?> getOrders(Pageable pageable, @RequestParam(required = false) Map<String, String> filters) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Orders",
                this.orderService.getOrdersFilterByFields(pageable, filters));
    }

    @GetMapping("/order-pending")
    public ApiResponse<?> getOrdersPending() {
        return new ApiResponse<>(HttpStatus.OK.value(), "Orders Pending",
                this.orderService.getOrdersPending());
    }

    @GetMapping("/{orderId}")
    public ApiResponse<?> getOrderDetail(@PathVariable("orderId") Long orderId) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Order detail", orderService.getOrderDetailById(orderId));
    }

    @PostMapping("/create")
    public ApiResponse<?> createOrder(@Valid  @RequestBody CreateOrderRequest request) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Order pending added successfully.", orderService.create(request));
    }
}
