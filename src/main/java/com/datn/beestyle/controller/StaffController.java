package com.datn.beestyle.controller;

import com.datn.beestyle.dto.ApiResponse;
import com.datn.beestyle.dto.staff.CreateStaffRequest;
import com.datn.beestyle.dto.staff.UpdateStaffRequest;
import com.datn.beestyle.service.staff.IStaffService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/admin/staff")
@RequiredArgsConstructor
public class StaffController {
    private final IStaffService staffService;

    @GetMapping
    public ApiResponse<?> getCustomers(Pageable pageable,
                                       @RequestParam(required = false) String name,
                                       @RequestParam(required = false, defaultValue = "false") boolean deleted) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Staff",
                staffService.getAllByNameAndDeleted(pageable,name,deleted));
    }

    @PostMapping("/create")
    public ApiResponse<?> createCustomer(@Valid @RequestBody CreateStaffRequest request){
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Customer add successfully",
                staffService.create(request));
    }
    @PutMapping("/update/{id}")
    public ApiResponse<?> updateCustomer(@Min(1) @PathVariable int id, @Valid @RequestBody UpdateStaffRequest request) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Customer updated successfully",
                staffService.update(id, request));
    }
}
