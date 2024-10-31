package com.datn.beestyle.repository;

import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.entity.order.OrderItem;
import com.datn.beestyle.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends IGenericRepository<OrderItem, Long> {
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
}
