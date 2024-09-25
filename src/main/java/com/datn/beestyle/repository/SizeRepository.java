package com.datn.beestyle.repository;

import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.entity.product.attributes.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends IGenericRepository<Size, Integer> {

    @Query("""
            select s from Size s 
            where 
                :name is null or s.sizeName like concat('%', :name, '%')
                 and s.deleted = :deleted
            """)
    Page<Size> findByNameContainingAndDeleted(Pageable pageable,
                                               @Param("name") String name,
                                               @Param("deleted") boolean deleted);
}
