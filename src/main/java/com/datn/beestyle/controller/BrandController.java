package com.datn.beestyle.controller;

import com.datn.beestyle.dto.ApiResponse;
import com.datn.beestyle.dto.product.attributes.brand.CreateBrandRequest;
import com.datn.beestyle.dto.product.attributes.brand.UpdateBrandRequest;
import com.datn.beestyle.service.product.attributes.brand.IBrandService;
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
@RequestMapping("/admin/brand")
@RequiredArgsConstructor
public class BrandController {

    private final IBrandService brandService;

    @GetMapping
    public ApiResponse<?> getBrands(Pageable pageable,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false, defaultValue = "false") boolean deleted) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Brands",
                brandService.getAllByNameAndDeleted(pageable, name, deleted));
    }

    @PostMapping("/create")
    public ApiResponse<?> createBrand(@Valid @RequestBody CreateBrandRequest request) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Brand added successfully",
                brandService.create(request));
    }

    @PostMapping("/creates")
    public ApiResponse<?> createBrands(@RequestBody List<@Valid CreateBrandRequest> requestList) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Brands added successfully",
                brandService.createEntities(requestList));
    }

    @PutMapping("/update/{id}")
    public ApiResponse<?> updateBrand(@Min(1) @PathVariable int id,
                                         @Valid @RequestBody UpdateBrandRequest request) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Brand updated successfully",
                brandService.update(id, request));
    }

    @PatchMapping("/updates")
    public ApiResponse<?> updateBrands(@RequestBody List<@Valid UpdateBrandRequest> requestList) {
        brandService.updateEntities(requestList);
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Brands updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<?> deleteBrand(@Min(1) @PathVariable int id) {
        brandService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Brand deleted successfully.");
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getBrand(@Min(1) @PathVariable int id) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Brand", brandService.getDtoById(id));
    }
}
