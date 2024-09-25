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
                :name is null or b.brandName like concat('%', :name, '%')
                 and b.deleted = :deleted
            """)
    Page<Brand> findByNameContainingAndDeleted(Pageable pageable,
                                               @Param("name") String name,
                                               @Param("deleted") boolean deleted);
}
