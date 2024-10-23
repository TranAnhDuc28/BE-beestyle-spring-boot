package com.datn.beestyle.repository;

import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.product.ProductResponse;
import com.datn.beestyle.entity.Category;
import com.datn.beestyle.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends IGenericRepository<Product, Long> {

    @Query(value = """
            select p.id, p.productName, p.imageUrl, p.gender, p.brand.id, p.brand.brandName,
                p.material.id, p.material.materialName, p.description
            from Product p
            where p.status in (1, 2) and p.category.id = :categoryId
            """)
    Page<Product> findAllForUserByCategoryId(Pageable pageable, @Param("categoryId") int categoryId);

    @Query(value = """
            select p from Product p
            where 
                (:keyword is null or p.productName like concat('%', :keyword, '%')) and
                (:categoryId is null or p.category.id = :categoryId) and
                (:gender is null or p.gender = :gender) and
                (:brandIds is null or p.brand.id in (:brandIds)) and
                (:materialIds is null or p.material.id in (:materialIds)) and
                (:status is null or p.status = :status)
            """)
    Page<Product> findAllByFields(Pageable pageable, @Param("keyword") String keyword,
                                          @Param("categoryId") Integer categoryId,
                                          @Param("gender") Integer gender,
                                          @Param("brandIds") List<Integer> brandIds,
                                          @Param("materialIds") List<Integer> materialIds,
                                          @Param("status") Integer status);

    boolean existsByProductName(String name);

    Optional<Product> findByProductName(String name);
}
