package com.datn.beestyle.repository;

import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.entity.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends IGenericRepository<Order, Long> {
    @Query(value = """
                WITH RECURSIVE order_hierarchy AS (
                SELECT * FROM `order` 
                WHERE status = 1 AND order_id IS NULL
                UNION ALL
                SELECT *
                FROM `order` AS o 
                INNER JOIN order_hierarchy oh ON o.order_id = o.id 
                WHERE o.status = 1 AND o.level <= 3
                )
                SELECT * FROM order_hierarchy 
                ORDER BY level, priority;
            """,
            nativeQuery = true
    )
//    ORs
//    DATE(o.createdAt) between DATE(:dateStart) AND DATE(:dateEnd)
    List<Object[]> getOrders();

    @Query(
            value = """
                    SELECT o FROM Order o \s
                    WHERE :keyword IS NULL OR  o.customer.fullName LIKE CONCAT('%', :keyword, '%') OR \s
                            o.customer.phoneNumber LIKE CONCAT('%', :keyword, '%') AND \s
                        (:status IS NULL OR o.status = :status)
                    """
    )
    Page<Order> findAllByKeywordAndStatus(
            @Param("keyword") String keyword,
            @Param("status") String status,
            Pageable pageable
    );


    @Query(
            value = """
                    SELECT o.id, o.customer.fullName FROM Order o WHERE o.id IN (:ids)
                    """
    )
    List<Object[]> findCustomerNameById(@Param("ids") Iterable<Long> ids);
}
