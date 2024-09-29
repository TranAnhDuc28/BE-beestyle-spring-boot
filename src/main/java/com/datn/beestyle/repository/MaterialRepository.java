package com.datn.beestyle.repository;

import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.entity.product.attributes.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends IGenericRepository<Material, Integer> {
    @Query("""
            select m from Material m 
            where 
                (:name is null or m.materialName like concat('%', :name, '%')) and 
                (:status is null or m.status = :status)
            """)
    Page<Material> findByNameContainingAndStatus(Pageable pageable,
                                                 @Param("name") String name,
                                                 @Param("status") Integer status);

}
