package com.datn.beestyle.repository;

import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.material.UpdateMaterialRequest;
import com.datn.beestyle.entity.product.properties.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends IGenericRepository<Material, Integer> {
    @Query("""
            select m from Material m 
            where 
                :name is null or m.materialName like concat('%', :name, '%')
                 and m.deleted = :deleted
            """)
    Page<Material> findByNameContainingAndDeleted(Pageable pageable,
                                                  @Param("name") String name,
                                                  @Param("deleted") boolean deleted);

}
