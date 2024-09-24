package com.datn.beestyle.controller;

import com.datn.beestyle.dto.ApiResponse;
import com.datn.beestyle.dto.voucher.CreateVoucherRequest;
import com.datn.beestyle.dto.voucher.UpdateVoucherRequest;
import com.datn.beestyle.service.material.MaterialService;
import com.datn.beestyle.service.voucher.VoucherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/voucher")
@RequiredArgsConstructor
public class VoucherController {
    private final VoucherService voucherService;


    @GetMapping
    public ApiResponse<?> getVouchers(Pageable pageable, @RequestParam(required = false) String code) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Vouchers",
                voucherService.searchByName(pageable, code));
    }

    @PostMapping("/create")
    public ApiResponse<?> createVoucher(@Valid @RequestBody CreateVoucherRequest request) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Voucher thêm thành công",
                voucherService.create(request));
    }

    @PostMapping("/creates")
    public ApiResponse<?> createVouchers(@Valid @RequestBody List<CreateVoucherRequest> requestList) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), " Nhiều voucher thêm thành công",
                voucherService.createVoucher(requestList));
    }

    @PutMapping("/update/{id}")
    public ApiResponse<?> updateVoucher(@PathVariable Integer id, @RequestBody UpdateVoucherRequest request) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Voucher sửa thành công",
                voucherService.update(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<?> deleteVoucher(@PathVariable Integer id) {
        voucherService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Xóa voucher thành công");
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getVoucher(@PathVariable Integer id) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Voucher", voucherService.getDtoById(id));
    }
}
