package com.datn.beestyle.repository;

import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.product.ProductResponse;
import com.datn.beestyle.dto.product.variant.ProductVariantResponse;
import com.datn.beestyle.entity.Category;
import com.datn.beestyle.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends IGenericRepository<Product, Long> {

    @Query(value = """
            select p.id, p.productCode, p.productName, pi.imageUrl, p.gender, p.brand.id, p.brand.brandName,
                p.material.id, p.material.materialName, p.description
            from Product p
                left join ProductImage pi on p.id = pi.product.id and pi.isDefault = true
            where p.status in (1, 2) and p.category.id = :categoryId
            """)
    Page<Product> findAllForUserByCategoryId(Pageable pageable, @Param("categoryId") int categoryId);

    @Query(value = """
            select new com.datn.beestyle.dto.product.ProductResponse(
                p.id, p.productCode, p.productName, pi.imageUrl, p.gender, b.id, b.brandName, m.id, 
                m.materialName, p.description, c.id, c.categoryName, p.status, p.createdAt,
                p.updatedAt, p.createdBy, p.updatedBy)
            from Product p
                left join Category c on p.category.id = c.id
                left join Brand b on p.brand.id = b.id
                left join Material m on p.material.id = m.id 
                left join ProductImage pi on p.id = pi.product.id and pi.isDefault = true
            where 
                (:keyword is null or 
                    p.productName like concat('%', :keyword, '%') or
                    p.productCode like concat('%', :keyword, '%')) and
                (:categoryId is null or p.category.id = :categoryId) and
                (:gender is null or p.gender = :gender) and
                (:brandIds is null or p.brand.id in (:brandIds)) and
                (:materialIds is null or p.material.id in (:materialIds)) and
                (:status is null or p.status = :status)
            """)
    Page<ProductResponse> findAllByFields(Pageable pageable, @Param("keyword") String keyword,
                                          @Param("categoryId") Integer categoryId,
                                          @Param("gender") Integer gender,
                                          @Param("brandIds") List<Integer> brandIds,
                                          @Param("materialIds") List<Integer> materialIds,
                                          @Param("status") Integer status);

    boolean existsByProductName(String name);

    Optional<Product> findByProductName(String name);

}
