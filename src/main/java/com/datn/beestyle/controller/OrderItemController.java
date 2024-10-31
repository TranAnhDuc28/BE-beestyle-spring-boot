package com.datn.beestyle.controller;

import com.datn.beestyle.dto.ApiResponse;
import com.datn.beestyle.repository.OrderItemRepository;
import com.datn.beestyle.service.order.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping(path = "/admin/order-item")
@RequiredArgsConstructor
public class OrderItemController {
    private final OrderItemService orderItemService;

    private final OrderItemRepository orderItemRepository;

    @GetMapping
    public ApiResponse<?> getOrderItems(
            Pageable pageable,
            @RequestParam(name = "id", required = false) Long id
    ) {
        return new ApiResponse<>(HttpStatus.OK.value(), "OrderItem",
                this.orderItemService.getOrderItemsDTO(pageable, id)
        );
    }
}
