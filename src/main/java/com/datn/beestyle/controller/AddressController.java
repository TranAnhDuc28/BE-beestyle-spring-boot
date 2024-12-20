package com.datn.beestyle.controller;

import com.datn.beestyle.dto.ApiResponse;
import com.datn.beestyle.dto.address.CreateAddressRequest;
import com.datn.beestyle.dto.address.UpdateAddressRequest;
import com.datn.beestyle.service.address.IAddressService;
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
@RequestMapping("/admin/address")
@RequiredArgsConstructor
public class AddressController {
    private final IAddressService addressService;

    @GetMapping()
    public ApiResponse<?> getAllByCutomerId(Pageable pageable,
                                    @RequestParam(required = false) Long id) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Addresses",
                addressService.getAllByCustomerId(pageable, id));
    }
    @PostMapping("/create")
    public ApiResponse<?> createAddress(@RequestBody CreateAddressRequest request) {

        return new ApiResponse<>(HttpStatus.CREATED.value(), "Address add successfully",
                addressService.create(request));
    }
    @PutMapping("/{id}")
    public ApiResponse<?> setIsDefault(@PathVariable("id") Long id, @RequestBody UpdateAddressRequest request) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Address updated to isDefault successfully",
                addressService.setUpdateIsDefault(id,request));
    }
    @PutMapping("/update/{id}")
    public ApiResponse<?> updateAddress(@Min(1) @PathVariable Long id,
                                      @Valid @RequestBody UpdateAddressRequest request) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Address updated successfully",
                addressService.update(id, request));
    }
    @GetMapping("/{id}")
    public ApiResponse<?> getAddress (@Min(1) @PathVariable Long id) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Address", addressService.getDtoById(id));
    }

}
