package com.datn.beestyle.controller;

import com.datn.beestyle.dto.ApiResponse;
import com.datn.beestyle.dto.product.attributes.color.CreateColorRequest;
import com.datn.beestyle.dto.product.attributes.color.UpdateColorRequest;
import com.datn.beestyle.dto.promotion.CreatePromotionRequest;
import com.datn.beestyle.dto.promotion.UpdatePromotionRequest;
import com.datn.beestyle.service.user.promotion.IPromotionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/admin/promotion")
@RequiredArgsConstructor
public class PromotionController {
    private final IPromotionService promotionService;

    @GetMapping
    public ApiResponse<?> getPromotions(Pageable pageable,
                                        @RequestParam(required = false) String name,
                                        @RequestParam(required = false, defaultValue = "false") boolean deleted) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Promotions",
                promotionService.getAllByNameAndDeleted(pageable, name, deleted));
    }

    @PostMapping("/create")
    public ApiResponse<?> createCPromotion(@Valid @RequestBody CreatePromotionRequest request) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Promotion added successfully",
                promotionService.create(request));
    }

    @PostMapping("/creates")
    public ApiResponse<?> createPromotions(@RequestBody List<@Valid CreatePromotionRequest> requestList) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Promotion added successfully",
                promotionService.createEntities(requestList));
    }

    @PutMapping("/update/{id}")
    public ApiResponse<?> updatePromotion(@Min(1) @PathVariable int id, @Valid @RequestBody UpdatePromotionRequest request) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Promotion updated successfully",
                promotionService.update(id, request));
    }

    @PatchMapping("/updates")
    public ApiResponse<?> updatePromotions(@RequestBody List<@Valid UpdatePromotionRequest> requestList) {
        promotionService.updateEntities(requestList);
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Promotion updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<?> deletePromotion(@Min(1) @PathVariable int id) {
        promotionService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Promotion deleted successfully.");
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getPromotion(@Min(1) @PathVariable int id) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Promotion", promotionService.getDtoById(id));
    }

}
