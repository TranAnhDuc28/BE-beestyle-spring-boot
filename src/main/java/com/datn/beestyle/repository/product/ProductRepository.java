package com.datn.beestyle.repository.product;

import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.product.ProductResponse;
import com.datn.beestyle.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends IGenericRepository<Product, Long>, ProductRepositoryCustom {

    @Query(value = """
            select new com.datn.beestyle.dto.product.ProductResponse(
                p.id, p.productCode, p.productName, pi.imageUrl, min(pv.salePrice), sum(pv.quantityInStock))
            from Product p
                left join ProductImage pi on p.id = pi.product.id and pi.isDefault = true
                left join ProductVariant pv on p.id = pv.product.id
            where 
                (:keyword is null or 
                    p.productName like concat('%', :keyword, '%') or
                    p.productCode like concat('%', :keyword, '%')) and
                (:status is null or p.status = :status)
            group by p.id, p.productCode, p.productName, pi.imageUrl
            """)
    Page<ProductResponse> searchProduct(Pageable pageable,
                                        @Param("keyword") String keyword,
                                        @Param("status") Integer status);

    @Query(value = """
            select new com.datn.beestyle.dto.product.ProductResponse(
                p.id, p.productCode, p.productName, pi.imageUrl, p.gender, b.id, b.brandName, m.id, 
                m.materialName, c.id, c.categoryName,p.description, p.status, p.createdAt,
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

    @Query(value = """
            select new com.datn.beestyle.dto.product.ProductResponse(
                p.id,
                p.productName,
                pi.imageUrl,
                max(pv.salePrice)
                ) from Product p
                inner join ProductImage pi on p.id = pi.product.id and pi.isDefault = true
                inner join ProductVariant pv on p.id = pv.product.id
            where (:q is null or p.gender = :q)
            group by p.id, p.productName, pi.imageUrl
            """)
    Page<ProductResponse> getFeaturedProducts(Pageable pageable, @Param("q") Integer q);

    @Query(value = """
            select new com.datn.beestyle.dto.product.ProductResponse(
                    p.id,
                    p.productName,
                    max(pi2.imageUrl),
                    max(pv.salePrice)
            )
            from OrderItem oi
                inner join ProductVariant pv on oi.productVariant.id = pv.id
                inner join Product p on p.id = pv.product.id
                inner join ProductImage pi2 on pi2.product.id = p.id
            group by p.id, p.productName
            order by count(p.id) desc
            """)
    Page<ProductResponse> getSellingProducts(Pageable pageable);

    @Query(value = """
            select new com.datn.beestyle.dto.product.ProductResponse(
                    p.id,
                    p.productName,
                    min(pi2.imageUrl),
                    min(pv.salePrice)
            )
            from Product p
                inner join ProductVariant pv on p.id = pv.product.id
                inner join ProductImage pi2 on pi2.product.id = p.id
            group by p.id, p.productName
            order by min(pv.salePrice) asc
            """)
    Page<ProductResponse> getOfferingProducts(Pageable pageable);

    boolean existsByProductName(String name);

    boolean existsByProductCode(String code);

    Optional<Product> findByProductNameAndIdNot(String productName, Long id);

}
