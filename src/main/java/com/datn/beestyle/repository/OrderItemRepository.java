package com.datn.beestyle.repository;

import com.datn.beestyle.common.IGenericRepository;
<<<<<<< HEAD
import com.datn.beestyle.entity.order.OrderItem;
import com.datn.beestyle.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
=======
import com.datn.beestyle.dto.order.item.OrderItemResponse;
import com.datn.beestyle.entity.order.OrderItem;
>>>>>>> ee23191801e6c6287e495bb989978a11a4ae2e84
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends IGenericRepository<OrderItem, Long> {
<<<<<<< HEAD
    @Query(
            value = """
                    SELECT oi FROM OrderItem oi
                    WHERE oi.order.id = :id
                    """
    )
    Page<OrderItem> findAllById(
            @Param("id") Long id,
            Pageable pageable
    );

    @Query(
            value = """
                    SELECT p FROM Product p
                    WHERE p.id = :id
                    """
    )
    List<Product> findProductById(
            @Param("id") Long id
    );

    @Query(
            value = """
                    SELECT * FROM order_item WHERE order_id = ?1
                     """
            , nativeQuery = true
    )
    List<OrderItem> findOrderItemById(Long id);
=======

    @Query("""
        select new com.datn.beestyle.dto.order.item.OrderItemResponse(
            oi.id, oi.order.id, pv.id, pv.sku, p.id, p.productName, c.id, c.colorCode, c.colorName, s.id, s.sizeName,
            oi.quantity, oi.salePrice, oi.discountedPrice, oi.note
        )
        from OrderItem oi
            left join ProductVariant pv on oi.productVariant.id = pv.id
            join Product p on pv.product.id = p.id
            left join Color c on pv.color.id = c.id
            left join Size s on pv.size.id = s.id
        where oi.order.id = :orderId
    """)
    List<OrderItemResponse> findAllByOrderId(@Param("orderId") Long orderId);
>>>>>>> ee23191801e6c6287e495bb989978a11a4ae2e84
}
