package com.datn.beestyle.repository;

import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.entity.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface PromotionRepository extends IGenericRepository<Promotion, Integer> {
    @Query("""
            select p from Promotion p where :name is null or p.promotionName like concat('%', :name, '%')
            """)
    Page<Promotion> findAllByNameContaining(Pageable pageable, String name);
}
