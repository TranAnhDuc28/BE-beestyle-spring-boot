package com.datn.beestyle.controller;

import com.datn.beestyle.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(path = "/admin/order")
@RequiredArgsConstructor
public class OrderItemController {


    @GetMapping("/{orderId}/order-items")
    public ApiResponse<?> getOrderItemsByOrderId(@PathVariable("orderId") Integer orderId) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Orders");
    }
}
