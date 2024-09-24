package com.datn.beestyle.repository;

import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface VoucherRepository extends IGenericRepository<Voucher, Integer> {
    @Query("""
        select v from Voucher v where :code is null or v.voucherCode like concat('%', :code, '%')
        """)
    Page<Voucher> findAllByVoucherCodeContaining(Pageable pageable, String code);

}
