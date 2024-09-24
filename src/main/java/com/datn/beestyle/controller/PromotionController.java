package com.datn.beestyle.controller;

import com.datn.beestyle.dto.ApiResponse;
import com.datn.beestyle.dto.material.CreateMaterialRequest;
import com.datn.beestyle.dto.material.UpdateMaterialRequest;
import com.datn.beestyle.dto.promotion.CreatePromotionRequest;
import com.datn.beestyle.dto.promotion.UpdatePromotionRequest;
import com.datn.beestyle.service.Promotion.PromotionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/promotion")
@RequiredArgsConstructor
public class PromotionController {
    private final PromotionService promotionService;
    @GetMapping
    public ApiResponse<?> getCommunes(Pageable pageable, @RequestParam(required = false) String name) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Promotion",
                promotionService.searchByName(pageable, name));
    }

    @PostMapping("/create")
    public ApiResponse<?> createPromotion(@Valid @RequestBody CreatePromotionRequest request) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Promotion added successfully",
                promotionService.create(request));
    }

    @PostMapping("/creates")
    public ApiResponse<?> createPromotion(@Valid @RequestBody List<CreatePromotionRequest> requestList) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Promotion added successfully",
                promotionService.createPromotions(requestList));
    }

    @PutMapping("/update/{id}")
    public ApiResponse<?> updatePromotion(@PathVariable int id, @RequestBody UpdatePromotionRequest request) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Promotion updated successfully",
                promotionService.update(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<?> deletePromotion(@PathVariable int id) {
        promotionService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Promotion deleted successfully.");
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getPromotion(@PathVariable int id) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Promotion", promotionService.getDtoById(id));
    }
}
