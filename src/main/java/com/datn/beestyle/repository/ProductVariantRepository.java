package com.datn.beestyle.repository;

import com.datn.beestyle.common.IGenericRepository;

import com.datn.beestyle.dto.product.variant.ProductVariantResponse;
import com.datn.beestyle.entity.product.ProductVariant;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.Optional;



public interface ProductVariantRepository extends IGenericRepository<ProductVariant, Long> {

    @Query(value = """
            select new com.datn.beestyle.dto.product.variant.ProductVariantResponse(
                pv.id, pv.sku, p.id, p.productName, c.id, c.colorName, s.id, s.sizeName, pv.salePrice, pv.quantityInStock,
                pv.originalPrice, pv.status, pv.createdAt, pv.updatedAt, pv.createdBy, pv.updatedBy)
            from ProductVariant pv
                join Product p on pv.product.id = p.id
                left join Color c on pv.color.id = c.id
                left join Size s on pv.size.id = s.id
            where
                pv.product.id = :productId and
                (:keyword is null or pv.sku like concat('%', :keyword, '%')) and
                (:colorIds is null or pv.color.id in (:colorIds)) and
                (:sizeIds is null or pv.size.id in (:sizeIds)) and
                (:status is null or pv.status = :status)
            """)
    Page<ProductVariantResponse> findAllByFieldsByProductId(Pageable pageable,
                                                            @Param("productId") Integer productId,
                                                            @Param("keyword") String keyword,
                                                            @Param("colorIds") List<Integer> colorIds,
                                                            @Param("sizeIds") List<Integer> sizeIds,
                                                            @Param("status") Integer status);
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
    @Query("update ProductVariant pv set pv.promotion.id = :promotionId where pv.id in :ids")
    int updatePromotionForVariants(@Param("promotionId") Integer promotionId, @Param("ids") List<Integer> ids);

}
