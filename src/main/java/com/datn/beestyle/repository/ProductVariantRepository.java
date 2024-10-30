package com.datn.beestyle.repository;

import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.entity.product.Product;
import com.datn.beestyle.entity.product.ProductVariant;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductVariantRepository extends IGenericRepository<ProductVariant, Long> {
    @Query(value = """
            SELECT 
                p.id AS productId, 
                p.productName AS productName, 
                b.brandName AS brandName, 
                m.materialName AS materialName, 
                pv.id AS productVariantId, 
                pv.sku AS sku, 
                c.colorName AS colorName, 
                s.sizeName AS sizeName, 
                pv.originalPrice AS originalPrice, 
                pv.quantityInStock AS quantityInStock,
                pi.imageUrl AS imageUrl
            FROM Product p
            LEFT JOIN p.brand b
            LEFT JOIN p.material m
            LEFT JOIN p.productVariants pv
            LEFT JOIN pv.color c
            LEFT JOIN pv.size s
            LEFT JOIN p.productImages pi
            WHERE p.id IN :productIds
            """)
    Optional<Object[]> findAllProductsWithDetails(@Param("productIds") List<Long> productIds);

    @Modifying
    @Transactional
    @Query("UPDATE ProductVariant pv SET pv.promotion.id = :promotionId WHERE pv.id IN :ids")
    int updatePromotionForVariants(@Param("promotionId") Integer promotionId, @Param("ids") List<Integer> ids);



}
