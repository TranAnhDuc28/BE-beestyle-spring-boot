package com.datn.beestyle.service.voucher;

import com.datn.beestyle.dto.PageResponse;

import com.datn.beestyle.dto.voucher.CreateVoucherRequest;
import com.datn.beestyle.dto.voucher.VoucherResponse;
import com.datn.beestyle.enums.DiscountType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IVoucherService {
    PageResponse<?> getAllByNameAndStatus(Pageable pageable, String name, String status, String discountType);
    List<VoucherResponse> createVoucher(List<CreateVoucherRequest> requestList);
}
