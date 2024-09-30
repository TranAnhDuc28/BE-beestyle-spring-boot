package com.datn.beestyle.controller;

import com.datn.beestyle.dto.ApiResponse;
import com.datn.beestyle.dto.voucher.CreateVoucherRequest;
import com.datn.beestyle.dto.voucher.UpdateVoucherRequest;
import com.datn.beestyle.dto.voucher.VoucherResponse;
import com.datn.beestyle.service.voucher.VoucherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin/voucher")
@RequiredArgsConstructor
public class VoucherController {
    private final VoucherService voucherService;

    @GetMapping
    public ApiResponse<?> getVouchers(Pageable pageable,
                                      @RequestParam(required = false) String name,
                                      @RequestParam(required = false) String status) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Vouchers",
                voucherService.getAllByNameAndStatus(pageable, name, status));
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

    @PatchMapping("/updates")
    public ApiResponse<?> updateMaterials(@Valid @RequestBody List<UpdateVoucherRequest> requestList) {
        voucherService.updateEntities(requestList);
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Materials updated successfully");
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

//    @GetMapping("/voucherName/{voucherName}")
//    public ApiResponse<?> getVoucherByCode(@PathVariable String voucherName) {
//        return new ApiResponse<>(HttpStatus.OK.value(), "Voucher found", voucherService.getVoucherByName(voucherName));
//    }
    @GetMapping("/voucherName/{voucherName}")
    public ApiResponse<?> getVoucherByName(@PathVariable("voucherName") String voucherName) {
        List<VoucherResponse> vouchers = voucherService.getVoucherByName(voucherName);
        if (vouchers.isEmpty()) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Không có voucher", null);
        }

        return new ApiResponse<>(HttpStatus.OK.value(), "Vouchers found", vouchers);
    }
    @GetMapping("/findbydate")
    public ApiResponse<?> findByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Timestamp startTimestamp = Timestamp.valueOf(startDate.atStartOfDay());
        Timestamp endTimestamp = Timestamp.valueOf(endDate.atStartOfDay());

        return new ApiResponse<>(HttpStatus.OK.value(), "Vouchers found", voucherService.getVoucherByDateRange(startTimestamp, endTimestamp));
    }


}
