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
public interface ProductRepository extends IGenericRepository<Product, Long>, ProductRepositoryCustom{

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



    boolean existsByProductName(String name);
    boolean existsByProductCode(String code);
    Optional<Product> findByProductNameAndIdNot(String productName, Long id);

}
