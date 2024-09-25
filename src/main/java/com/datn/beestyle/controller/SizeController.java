package com.datn.beestyle.controller;

import com.datn.beestyle.dto.ApiResponse;
import com.datn.beestyle.dto.product.attributes.size.CreateSizeRequest;
import com.datn.beestyle.dto.product.attributes.size.UpdateSizeRequest;
import com.datn.beestyle.service.product.attributes.size.ISizeService;
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
@RequestMapping("/admin/size")
@RequiredArgsConstructor
public class SizeController {
    
    private final ISizeService sizeService;
    
    @GetMapping
    public ApiResponse<?> getSizes(Pageable pageable,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false, defaultValue = "false") boolean deleted) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Sizes",
                sizeService.getAllByNameAndDeleted(pageable, name, deleted));
    }

    @PostMapping("/create")
    public ApiResponse<?> createSize(@Valid @RequestBody CreateSizeRequest request) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Size added successfully",
                sizeService.create(request));
    }

    @PostMapping("/creates")
    public ApiResponse<?> createSizes(@RequestBody List<@Valid CreateSizeRequest> requestList) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Sizes added successfully",
                sizeService.createEntities(requestList));
    }

    @PutMapping("/update/{id}")
    public ApiResponse<?> updateSize(@Min(1) @PathVariable int id, @Valid @RequestBody UpdateSizeRequest request) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Size updated successfully",
                sizeService.update(id, request));
    }

    @PatchMapping("/updates")
    public ApiResponse<?> updateSizes(@RequestBody List<@Valid UpdateSizeRequest> requestList) {
        sizeService.updateEntities(requestList);
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Sizes updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<?> deleteSize(@Min(1) @PathVariable int id) {
        sizeService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Size deleted successfully.");
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getSize(@Min(1) @PathVariable int id) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Size", sizeService.getDtoById(id));
    }
}
