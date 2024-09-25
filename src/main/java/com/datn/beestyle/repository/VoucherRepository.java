package com.datn.beestyle.repository;

import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.entity.Voucher;
import com.datn.beestyle.entity.product.attributes.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoucherRepository extends IGenericRepository<Voucher, Integer> {
    @Query("""
                select v from Voucher v where (:code is null or v.voucherCode like concat('%', :code, '%')) and v.deleted = :deleted
            """)
    Page<Voucher> findByNameContainingAndDeleted(Pageable pageable, String code, boolean deleted);

}
