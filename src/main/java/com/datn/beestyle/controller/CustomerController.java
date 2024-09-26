package com.datn.beestyle.controller;

import com.datn.beestyle.dto.ApiResponse;
import com.datn.beestyle.dto.customer.CreateCustomerRequest;
import com.datn.beestyle.service.customer.ICustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/admin/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final ICustomerService iCustomerService;

    @GetMapping
    public ApiResponse<?> getCustomers(Pageable pageable,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false, defaultValue = "false") boolean deleted) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Customer",
                iCustomerService.getAllByNameAndDeleted(pageable,name,deleted));
    }

    @PostMapping("/create")
    public ApiResponse<?> createCustomer(@Valid @RequestBody CreateCustomerRequest request){
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Customer add successfully",
                iCustomerService.create(request));
    }
}
