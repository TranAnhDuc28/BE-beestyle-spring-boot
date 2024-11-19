package com.datn.beestyle.controller;

import com.datn.beestyle.dto.ApiResponse;
import com.datn.beestyle.service.order.item.IOrderItemService;
import com.datn.beestyle.service.order.item.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
public class OrderItemController {

    private final IOrderItemService orderItemService;

    @GetMapping("/order/{orderId}/order-items")
    public ApiResponse<?> getOrderItemsByOrderId(@PathVariable("orderId") Long orderId) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Order items", orderItemService.getAllByOrderId(orderId));
    }

    @PostMapping("/order/{orderId}/order-item/create")
    public ApiResponse<?> createOrderItemByOrderId(@PathVariable("orderId") Long orderId) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Create Order item", orderItemService.getAllByOrderId(orderId));
    }

    @PutMapping("/order/{orderId}/order-item/update")
    public ApiResponse<?> updateOrderItemByOrderId(@PathVariable("orderId") Long orderId) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Update Order item", orderItemService.getAllByOrderId(orderId));
    }

    @DeleteMapping("/order/{orderId}/order-item/delete/{orderItemId}")
    public ApiResponse<?> deleteOrderItemByOrderId(@PathVariable("orderId") Long orderId, @PathVariable("orderItemId") Long orderItemId) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Update Order item", orderItemService.getAllByOrderId(orderId));
    }
}
