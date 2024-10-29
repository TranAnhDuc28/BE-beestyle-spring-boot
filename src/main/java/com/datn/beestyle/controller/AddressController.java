package com.datn.beestyle.controller;

import com.datn.beestyle.dto.ApiResponse;
import com.datn.beestyle.dto.address.CreateAddressRequest;
import com.datn.beestyle.service.address.IAddressService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/create")
    public ApiResponse<?> createAddress(@RequestBody CreateAddressRequest request) {

        return new ApiResponse<>(HttpStatus.CREATED.value(), "Address add successfully",
                addressService.create(request));
    }
}
