package com.datn.beestyle.controller;

import com.datn.beestyle.dto.ApiResponse;
import com.datn.beestyle.dto.product.variant.ProductVariantResponse;
import com.datn.beestyle.service.statistical.StatisticalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;


@Validated
@RestController
@RequestMapping("/admin/statistical")
@RequiredArgsConstructor
public class StatisticalController {
    private final StatisticalService statisticalService;
    @GetMapping("/filterByStock")
    public ApiResponse<?> getProductVariants(
            @RequestParam(required = false, defaultValue = "desc") String orderByStock,
            Pageable pageable) {

        return new ApiResponse<>(HttpStatus.OK.value(), "",
                statisticalService.getProductVariantsByStock(pageable, orderByStock));
    }
}
