package com.datn.beestyle.repository;

import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.entity.Promotion;
import com.datn.beestyle.entity.product.attributes.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PromotionRepository extends IGenericRepository<Promotion, Integer> {
    @Query("""
            select p from Promotion p 
            where 
                :name is null or p.promotionName like concat('%', :name, '%')
                 and p.deleted = :deleted
            """)
    @Override
    Page<Promotion> findByNameContainingAndDeleted(Pageable pageable,
                                                  @Param("name") String name,
                                                  @Param("deleted") boolean deleted);
}
