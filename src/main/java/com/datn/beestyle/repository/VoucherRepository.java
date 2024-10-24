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

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public interface VoucherRepository extends IGenericRepository<Voucher, Integer> {
    //    @Query("""
//            select v from Voucher v
//            where
//                (:name is null or v.voucherName like concat('%', :name, '%')) and
//                (:status is null or v.status = :status)and
//                (:discountType is null or v.discountType = :discountType)
//            """)
//    Page<Voucher> findByNameContainingAndStatus(Pageable pageable,
//                                                @Param("name") String name,
//                                                @Param("status") Integer status),
//                                                @Param("discountType") DiscountType discountType);
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
                                                @Param("discountType") DiscountType discountType);

    @Query("""
            select v from Voucher v 
            """)
    Page<Voucher> findAll(Pageable pageable);

    @Query("SELECT v FROM Voucher v WHERE v.discountType = :discountType")
    Page<Voucher> findVouchersByDiscountType(@Param("discountType") DiscountType discountType, Pageable pageable);

    //    @Query("""
//            SELECT new com.datn.beestyle.dto.voucher.VoucherResponse(v.id,v.voucherName, v.voucherCode, v.discountType, v.discountValue,
//                                                                     v.maxDiscount, v.minOrderValue, v.startDate, v.endDate,
//                                                                     v.usageLimit, v.usagePerUser, v.status)
//            FROM Voucher v
//            WHERE (:name IS NULL OR v.voucherName LIKE CONCAT('%', :name, '%'))
//            """)
//    List<VoucherResponse> findByVoucherName(@Param("name") String name);
    @Query("""
            SELECT new com.datn.beestyle.dto.voucher.VoucherResponse(v.id, v.voucherName, v.voucherCode, v.discountType, 
                                                                   v.discountValue, v.maxDiscount, v.minOrderValue, 
                                                                   v.startDate, v.endDate, v.usageLimit, v.usagePerUser, 
                                                                   v.status)
            FROM Voucher v
            WHERE (:searchTerm IS NULL OR :searchTerm = '' OR 
                  v.voucherName LIKE CONCAT('%', :searchTerm, '%') 
                  OR v.voucherCode LIKE CONCAT('%', :searchTerm, '%'))
            """)
    Page<VoucherResponse> findByVoucherNameOrCode(@Param("searchTerm") String searchTerm, Pageable pageable);


    @Query(""" 
            SELECT new com.datn.beestyle.dto.voucher.VoucherResponse(v.id, v.voucherName, v.voucherCode, v.discountType, v.discountValue,
                                                                     v.maxDiscount, v.minOrderValue, v.startDate, v.endDate,
                                                                     v.usageLimit, v.usagePerUser, v.status)
            FROM Voucher v
            WHERE (DATE(:startDate) IS NULL OR DATE(v.startDate) >= DATE(:startDate))
            AND (DATE(:endDate) IS NULL OR DATE(v.endDate) <= DATE(:endDate))
            """)
    Page<VoucherResponse> findByDateRange(@Param("startDate") Timestamp startDate,
                                          @Param("endDate") Timestamp endDate,
                                          Pageable pageable);
}
