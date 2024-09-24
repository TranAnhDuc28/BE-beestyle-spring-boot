package com.datn.beestyle.controller;

import com.datn.beestyle.dto.ApiResponse;
import com.datn.beestyle.dto.material.CreateMaterialRequest;
import com.datn.beestyle.dto.material.UpdateMaterialRequest;
import com.datn.beestyle.service.material.MaterialService;
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

    private final MaterialService materialService;

    @GetMapping
    public ApiResponse<?> getCommunes(Pageable pageable,
                                      @RequestParam(required = false) String name,
                                      @RequestParam(required = false, defaultValue = "false") boolean deleted) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Materials",
                materialService.searchByName(pageable, name, deleted));
    }

    @PostMapping("/create")
    public ApiResponse<?> createMaterial(@Valid @RequestBody CreateMaterialRequest request) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Material added successfully",
                materialService.create(request));
    }

    @PostMapping("/creates")
    public ApiResponse<?> createMaterials(@RequestBody List<@Valid CreateMaterialRequest> requestList) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Materials added successfully",
                materialService.createMaterials(requestList));
    }

    @PutMapping("/update/{id}")
    public ApiResponse<?> updateMaterial(@Min(1) @PathVariable int id,
                                         @Valid @RequestBody UpdateMaterialRequest request) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Material updated successfully",
                materialService.update(id, request));
    }

    @PatchMapping("/updates")
    public ApiResponse<?> updateMaterials(@Valid @RequestBody List<UpdateMaterialRequest> requestList) {
        materialService.updateMaterials(requestList);
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Materials updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<?> deleteMaterial(@Min(1) @PathVariable int id) {
        materialService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Material deleted successfully.");
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getMaterial(@PathVariable int id) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Material", materialService.getDtoById(id));
    }
}
