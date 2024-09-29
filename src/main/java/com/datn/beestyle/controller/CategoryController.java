package com.datn.beestyle.controller;

import com.datn.beestyle.dto.ApiResponse;
import com.datn.beestyle.enums.Status;
import com.datn.beestyle.repository.CategoryRepository;
import com.datn.beestyle.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;

    @GetMapping
    public ApiResponse<?> getCategories(Pageable pageable,
                                        @RequestParam(required = false) String name,
                                        @RequestParam(required = false) Short status) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Categories",
                categoryService.getAllForAdmin(pageable, name, status));

    }
}
