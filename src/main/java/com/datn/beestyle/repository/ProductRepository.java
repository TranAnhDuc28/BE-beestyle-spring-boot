package com.datn.beestyle.repository;

import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends IGenericRepository<Product, Long> {

    @Query(value = """
            select p.id, p.productName, p.imageUrl, p.gender, p.brand.id, p.brand.brandName,
                p.material.id, p.material.materialName, p.description
            from Product p
            where p.status in (1, 2) and p.category.id = :categoryId
            """)
    Page<Product> findAllForUserByCategoryId(Pageable pageable, @Param("categoryId") int categoryId);
}
