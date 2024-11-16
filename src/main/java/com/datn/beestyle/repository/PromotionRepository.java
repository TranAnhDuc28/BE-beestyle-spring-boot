package com.datn.beestyle.repository;

import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.entity.Promotion;
import com.datn.beestyle.entity.Voucher;
import com.datn.beestyle.entity.product.attributes.Brand;
import com.datn.beestyle.enums.DiscountType;
import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface PromotionRepository extends IGenericRepository<Promotion, Integer> {
    //    @Query("""
//            select p from Promotion p
//            where
//                :name is null or p.promotionName like concat('%', :name, '%')
//                 and p.deleted = :deleted
//            """)
//    Page<Promotion> findByNameContainingAndDeleted(Pageable pageable,
//                                               @Param("name") String name,
//                                               @Param("deleted") boolean deleted);

    @Query("""
                select p from Promotion p 
                where 
                    (:name is null or p.promotionName like concat('%', :name, '%')) and
                    (:status is null or p.status = :status) and
                    (:discountType is null or p.discountType = :discountType)
            """)
    Page<Promotion> findByNameContainingAndStatus(Pageable pageable,
                                                @Param("name") String name,
                                                @Param("status") Integer status,
                                                @Param("discountType") Integer discountType);


    @Query(""" 
    SELECT p FROM Promotion p
    WHERE (:startDate IS NULL OR p.startDate >= :startDate)
    AND (:endDate IS NULL OR p.endDate <= :endDate)
    """)
    Page<Promotion> findByDateRange(@Param("startDate") Timestamp startDate,
                                  @Param("endDate") Timestamp endDate,
                                  Pageable pageable);

    @Query("SELECT p FROM Promotion p WHERE p.endDate < :currentDate")
    List<Promotion> findEndedPromotions(@Param("currentDate") Timestamp currentDate);

}
