package com.datn.beestyle.controller;

import com.datn.beestyle.dto.ApiResponse;
import com.datn.beestyle.entity.order.Order;
import com.datn.beestyle.repository.OrderRepository;
import com.datn.beestyle.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Validated
@RestController
@RequestMapping(path = "/admin/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ApiResponse<?> getOrders(
            Pageable pageable,
            @RequestParam(name = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "size", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "q", defaultValue = "") String search
//            @RequestParam(name = "dateS", defaultValue = "") String dateStart,
//            @RequestParam(name = "dateE", defaultValue = "") String dateEnd
    ) {
        pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").ascending());
        return new ApiResponse<>(HttpStatus.OK.value(), "Order",
                this.orderService.getOrdersDTO(search, pageable)
        );
    }
}
