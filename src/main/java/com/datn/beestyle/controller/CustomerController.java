package com.datn.beestyle.controller;

import com.datn.beestyle.dto.ApiResponse;
import com.datn.beestyle.dto.customer.CreateCustomerRequest;
import com.datn.beestyle.dto.customer.UpdateCustomerRequest;
import com.datn.beestyle.entity.Address;
import com.datn.beestyle.service.customer.ICustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@Validated
@RestController
@RequestMapping("/admin/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final ICustomerService customerService;

    @GetMapping
    public ApiResponse<?> getCustomers(Pageable pageable,@RequestParam(required = false) String status,@RequestParam(required = false) String gender
            ,@RequestParam(required = false) String keyword) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Customer",
                customerService.getAllByKeywordAndStatusAndGender(pageable,status,gender,keyword));
    }



    @PostMapping("/create")
    public ApiResponse<?> createCustomer(@Valid @RequestBody CreateCustomerRequest request){
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Thêm khách hàng thành công",
                customerService.create(request));
    }
    @PutMapping("/update/{id}")
    public ApiResponse<?> updateCustomer(@Min(1) @PathVariable int id, @Valid @RequestBody UpdateCustomerRequest request) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Cập nhật khách hàng thành công",
                customerService.update(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<?> deleteCustomer(@Min(1) @PathVariable int id) {
        customerService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Xóa khách hàng thành công");
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getCustomer(@Min(1) @PathVariable int id) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Customer", customerService.getDtoById(id));
    }
}
