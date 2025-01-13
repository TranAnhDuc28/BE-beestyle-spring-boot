package com.datn.beestyle.repository;

import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.cart.ShoppingCartResponse;
import com.datn.beestyle.entity.cart.ShoppingCart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends IGenericRepository<ShoppingCart, Long> {
    @Query("""
            select new com.datn.beestyle.dto.cart.ShoppingCartResponse(
                sc.id,
                sc.productVariant.id,
                sc.customer.id,
                sc.cartCode,
                sc.quantity,
                sc.salePrice,
                sc.discountedPrice
            )
            from ShoppingCart sc 
            where sc.productVariant.id in :productVariantIds and sc.customer.id = :customerId
            """)
    List<ShoppingCartResponse> findByProductVariantIdInAndCustomerId(
            @Param("productVariantIds") List<Long> productVariantIds,
            @Param("customerId") Long customerId
    );
}
