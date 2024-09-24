package com.datn.beestyle.service.voucher;

import com.datn.beestyle.dto.PageResponse;

import com.datn.beestyle.dto.voucher.CreateVoucherRequest;
import com.datn.beestyle.dto.voucher.VoucherResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IVoucherService {
    PageResponse<?> searchByName(Pageable pageable, String name);
    List<VoucherResponse> createVoucher(List<CreateVoucherRequest> requestList);
}
