package com.datn.beestyle.controller;

import com.datn.beestyle.dto.ApiResponse;
<<<<<<< HEAD
import com.datn.beestyle.dto.order.UpdateOrderRequest;
import com.datn.beestyle.entity.order.Order;
import com.datn.beestyle.service.order.OrderService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
=======
import com.datn.beestyle.dto.order.CreateOrderRequest;
import com.datn.beestyle.service.order.OrderService;
import jakarta.validation.Valid;
>>>>>>> ee23191801e6c6287e495bb989978a11a4ae2e84
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

    @PostMapping("/create")
    public ApiResponse<?> createOrder(@Valid  @RequestBody CreateOrderRequest request) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Order added successfully", orderService.create(request));
    }

    @PostMapping("/add")
    public ApiResponse<?> create() {
        this.orderService.createBill();
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "CreateOrder"
        );
    }

    @PostMapping("/update/{id}")
    public ApiResponse<?> update(
            @PathVariable Long id,
            @RequestBody UpdateOrderRequest order
    ) {

//        this.orderService.updateBill(order, id);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "UpdateOrder"
        );
    }
}
