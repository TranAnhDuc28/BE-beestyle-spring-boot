package com.datn.beestyle.repository;

import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.voucher.VoucherResponse;
import com.datn.beestyle.entity.Voucher;
import com.datn.beestyle.entity.product.attributes.Material;
import com.datn.beestyle.entity.product.attributes.Size;
import com.datn.beestyle.enums.DiscountType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public interface VoucherRepository extends IGenericRepository<Voucher, Integer> {

    @Query("""
                select v from Voucher v 
                where 
                    (:name is null or v.voucherName like concat('%', :name, '%')) and
                    (:status is null or v.status = :status) and
                    (:discountType is null or v.discountType = :discountType)
            """)
    Page<Voucher> findByNameContainingAndStatus(Pageable pageable,
                                                @Param("name") String name,
                                                @Param("status") Integer status,
                                                @Param("discountType") Integer discountType);

    @Query("""
            select v from Voucher v 
            """)
    Page<Voucher> findAll(Pageable pageable);

    @Query(""" 
    SELECT v FROM Voucher v
    WHERE (:startDate IS NULL OR v.startDate >= :startDate)
    AND (:endDate IS NULL OR v.endDate <= :endDate)
    """)
    Page<Voucher> findByDateRange(@Param("startDate") Timestamp startDate,
                                          @Param("endDate") Timestamp endDate,
                                          Pageable pageable);


    @Query("SELECT v FROM Voucher v WHERE v.status = :status AND v.minOrderValue <= :totalAmount")
    List<Voucher> findValidVouchers(@Param("status") int status, @Param("totalAmount") BigDecimal totalAmount);

}
