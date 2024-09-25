package com.datn.beestyle.controller;

import com.datn.beestyle.dto.ApiResponse;
import com.datn.beestyle.dto.product.attributes.material.CreateBrandRequest;
import com.datn.beestyle.dto.product.attributes.material.UpdateBrandRequest;
import com.datn.beestyle.service.product.attributes.material.IMaterialService;
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
@RequestMapping("/admin/material")
@RequiredArgsConstructor
public class MaterialController {

    private final IMaterialService materialService;

    @GetMapping
    public ApiResponse<?> getMaterials(Pageable pageable,
                                      @RequestParam(required = false) String name,
                                      @RequestParam(required = false, defaultValue = "false") boolean deleted) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Materials",
                materialService.getAllByNameAndDeleted(pageable, name, deleted));
    }

    @PostMapping("/create")
    public ApiResponse<?> createMaterial(@Valid @RequestBody CreateBrandRequest request) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Material added successfully",
                materialService.create(request));
    }

    @PostMapping("/creates")
    public ApiResponse<?> createMaterials(@RequestBody List<@Valid CreateBrandRequest> requestList) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Materials added successfully",
                materialService.createEntities(requestList));
    }

    @PutMapping("/update/{id}")
    public ApiResponse<?> updateMaterial(@Min(1) @PathVariable int id,
                                         @Valid @RequestBody UpdateBrandRequest request) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Material updated successfully",
                materialService.update(id, request));
    }

    @PatchMapping("/updates")
    public ApiResponse<?> updateMaterials(@Valid @RequestBody List<UpdateBrandRequest> requestList) {
        materialService.updateEntities(requestList);
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Materials updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<?> deleteMaterial(@Min(1) @PathVariable int id) {
        materialService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Material deleted successfully.");
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getMaterial(@Min(1) @PathVariable int id) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Material", materialService.getDtoById(id));
    }
}
