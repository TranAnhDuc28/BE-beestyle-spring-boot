package com.datn.beestyle.repository;

import com.datn.beestyle.common.IGenericRepository;

import com.datn.beestyle.dto.product.variant.ProductVariantResponse;
import com.datn.beestyle.entity.product.ProductVariant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import java.util.Optional;


public interface ProductVariantRepository extends IGenericRepository<ProductVariant, Long> {

    @Query(value = """
                select new com.datn.beestyle.dto.product.variant.ProductVariantResponse(
                    pv.id, pv.sku, p.id, p.productName, c.id, c.colorCode, c.colorName, s.id, s.sizeName, pv.salePrice, pv.quantityInStock)
                from ProductVariant pv
                    join Product p on pv.product.id = p.id
                    left join Color c on pv.color.id = c.id
                    left join Size s on pv.size.id = s.id
                where
                    pv.product.id = :productId and
                    (:colorIds is null or pv.color.id in (:colorIds)) and
                    (:sizeIds is null or pv.size.id in (:sizeIds)) and
                    (:minPrice is null or pv.salePrice >= :minPrice) and    
                    (:maxPrice is null or pv.salePrice <= :maxPrice) and    
                    (:status is null or pv.status = :status)    
            """)
    Page<ProductVariantResponse> filterProductVariantByProductId(Pageable pageable,
                                                                 @Param("productId") Integer productId,
                                                                 @Param("colorIds") List<Integer> colorIds,
                                                                 @Param("sizeIds") List<Integer> sizeIds,
                                                                 @Param("minPrice") BigDecimal minPrice,
                                                                 @Param("maxPrice") BigDecimal maxPrice,
                                                                 @Param("status") Integer status);

    @Query(value = """
            select new com.datn.beestyle.dto.product.variant.ProductVariantResponse(
                pv.id, pv.sku, p.id, p.productName, c.id, c.colorCode, c.colorName, s.id, s.sizeName, pv.salePrice, pv.quantityInStock,
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


    @Transactional
    @Modifying
    @Query(value = """
            update ProductVariant pv set pv.quantityInStock = :quantity, pv.updatedAt = CURRENT_TIMESTAMP where pv.id = :productVariantId
            """)
    int updateQuantityProductVariant(@Param("productVariantId") long productVariantId, @Param("quantity") int quantity);

    @Query("SELECT NEW com.datn.beestyle.dto.product.variant.ProductVariantResponse(" +
            "p.id, p.productName, b.brandName, m.materialName, pv.id, pv.sku, c.colorName, s.sizeName, pv.originalPrice, " +
            "pv.quantityInStock, promo.promotionName) " +
            "FROM ProductVariant pv " +
            "JOIN pv.product p " +
            "LEFT JOIN p.brand b " +
            "LEFT JOIN p.material m " +
            "LEFT JOIN pv.color c " +
            "LEFT JOIN pv.size s " +
            "LEFT JOIN pv.promotion promo " +
            "WHERE pv.product.id in :productIds")
    List<ProductVariantResponse> findAllProductsWithDetails(@Param("productIds") List<Long> productIds);


    @Modifying
    @Transactional
    @Query("update ProductVariant pv set pv.promotion.id = :promotionId where pv.id in :ids")
    int updatePromotionForVariants(@Param("promotionId") Integer promotionId, @Param("ids") List<Integer> ids);

    @Modifying
    @Transactional
    @Query("UPDATE ProductVariant pv SET pv.promotion.id = null WHERE pv.promotion.id = :promotionId")
    void updateProductVariantToNullByPromotionId(@Param("promotionId") Integer promotionId);

    @Modifying
    @Transactional
    @Query("UPDATE ProductVariant pv SET pv.promotion.id = null WHERE pv.promotion.id = :promotionId AND pv.id IN :ids")
    void updatePromotionToNullForNonSelectedIds(@Param("promotionId") Integer promotionId, @Param("ids") Integer ids);

    @Query("SELECT pv.product.id AS productId, pv.id AS productDetailId " +
            "FROM ProductVariant pv " +
            "JOIN pv.promotion p " +
            "WHERE p.id = :promotionId " +
            "AND pv.promotion.id = :promotionId")
    List<Object[]> findProductAndDetailIdsByPromotionId(Long promotionId);

    @Query(
            value = """
                        select distinct
                            p.id as id, p.product_code as productCode,
                        	p.product_name as productName, pv.sale_price as salePrice, 
                        	pv.sale_price - (pv.sale_price * COALESCE(pm.discount_value, 0) / 100) as discountedPrice,
                        	pm.discount_value as discountValue, 
                        	pv.sku as sku, c.category_name as categoryName, 
                        	b.brand_name, pv.quantity_in_stock as quantityInStock,
                        	cl.color_code as colorCode, cl.color_name as colorName,
                        	s.size_name as sizeName, p.description as description
                        from product_variant pv
                        inner join product p on p.id = pv.product_id
                        inner join category c on c.id = p.category_id
                        inner join brand b on p.brand_id = b.id 
                        inner join color cl on cl.id = pv.color_id 
                        inner join size s on s.id = pv.size_id
                        left join promotion pm on pv.promotion_id = pm.id
                        where pv.product_id = :productId
                        	and (:colorCode is null or cl.color_code like :colorCode)
                        	and (:sizeId is null or s.id = :sizeId)
                        limit 1
                    """,
            nativeQuery = true
    )
    List<Object[]> getProductVariantData(
            @Param("productId") Long productId,
            @Param("colorCode") String colorCode,
            @Param("sizeId") Long sizeId
    );







    @Query(
            value = """
                        select distinct
                            pv.id as id, p.id as productId, p.product_code as productCode,
                        	p.product_name as productName, pv.sale_price as salePrice,
                        	pv.sale_price - (pv.sale_price * COALESCE(pm.discount_value, 0) / 100) as discountedPrice,
                        	pm.discount_value as discountValue,
                        	pv.sku as sku, c.category_name as categoryName,
                        	b.brand_name, pv.quantity_in_stock as quantityInStock,
                        	cl.color_code as colorCode, cl.color_name as colorName,
                        	s.size_name as sizeName, p.description as description
                        from product_variant pv
                        inner join product p on p.id = pv.product_id
                        inner join category c on c.id = p.category_id
                        inner join brand b on p.brand_id = b.id
                        inner join color cl on cl.id = pv.color_id
                        inner join size s on s.id = pv.size_id
                        left join promotion pm on pv.promotion_id = pm.id
                        where pv.id in (:productVariantIds)
                    """,
            nativeQuery = true
    )
    List<Object[]> getProductVariantDataByIds(@Param("productVariantIds") List<Long> productVariantIds);

}
