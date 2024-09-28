package com.datn.beestyle.repository;

import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.entity.product.attributes.Color;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends IGenericRepository<Color, Integer> {

    @Query("""
            select c from Color c 
            where 
                :name is null or c.colorName like concat('%', :name, '%')
                 and c.status = :status
            """)
    Page<Color> findByNameContainingAndStatus(Pageable pageable,
                                               @Param("name") String name,
                                               @Param("status") short status);
}
