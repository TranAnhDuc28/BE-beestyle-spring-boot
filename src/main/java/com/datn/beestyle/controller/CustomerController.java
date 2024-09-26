package com.datn.beestyle.controller;

import com.datn.beestyle.dto.ApiResponse;
import com.datn.beestyle.dto.customer.CreateCustomerRequest;
import com.datn.beestyle.dto.customer.UpdateCustomerRequest;
import com.datn.beestyle.dto.product.attributes.color.UpdateColorRequest;
import com.datn.beestyle.service.customer.ICustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
    @PutMapping("/update/{id}")
    public ApiResponse<?> updateCustomer(@Min(1) @PathVariable int id, @Valid @RequestBody UpdateCustomerRequest request) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Color updated successfully",
                iCustomerService.update(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<?> deleteColor(@Min(1) @PathVariable int id) {
        iCustomerService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Color deleted successfully.");
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getColor(@Min(1) @PathVariable int id) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Color", iCustomerService.getDtoById(id));
    }
}
