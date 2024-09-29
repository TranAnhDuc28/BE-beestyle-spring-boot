package com.datn.beestyle.repository;

import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.entity.product.attributes.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BrandRepository extends IGenericRepository<Brand, Integer> {

    @Query("""
            select b from Brand b 
            where 
                (:name is null or b.brandName like concat('%', :name, '%')) and
                (:status is null or b.status = :status)
            """)
    Page<Brand> findByNameContainingAndStatus(Pageable pageable,
                                              @Param("name") String name,
                                              @Param("status") Integer status);
}
