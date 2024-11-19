package com.datn.beestyle.repository;

import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.order.OrderResponse;
import com.datn.beestyle.entity.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends IGenericRepository<Order, Long> {

    @Query(value = """
<<<<<<< HEAD
                WITH RECURSIVE order_hierarchy AS (
                SELECT * FROM `order`\s
                WHERE status = 1 AND order_id IS NULL\s
                UNION ALL\s
                SELECT *\s
                FROM `order` AS o 
                INNER JOIN order_hierarchy oh ON o.order_id = o.id 
                WHERE o.status = 1 AND o.level <= 3 
                )
                SELECT * FROM order_hierarchy 
                ORDER BY level, priority; 
            """,
            nativeQuery = true
=======
                select new com.datn.beestyle.dto.order.OrderResponse(
                    o.id, o.orderTrackingNumber, c.id, c.fullName, o.phoneNumber, o.totalAmount, o.paymentDate, 
                    o.paymentMethod, o.orderChannel, o.orderStatus, o.createdAt, o.updatedAt, o.createdBy, o.updatedBy
                )
                from Order o
                    left join Customer c on o.customer.id = c.id
                where
                    (:keyword is null or 
                        o.orderTrackingNumber like concat('%', :keyword, '%') or
                        c.fullName like concat('%', :keyword, '%') or
                        o.phoneNumber like concat('%', :keyword, '%')) and 
                    (:orderChannel is null or o.orderChannel = :orderChannel) and 
                    (:orderStatus is null or o.orderStatus = :orderStatus)    
            """
>>>>>>> ee23191801e6c6287e495bb989978a11a4ae2e84
    )
    Page<OrderResponse> findAllByFields(Pageable pageable,
                                        @Param("keyword") String keyword,
                                        @Param("orderChannel") Integer orderChannel,
                                        @Param("orderStatus") Integer orderStatus);

<<<<<<< HEAD
    @Query(
            value = """
                    SELECT o FROM Order o \s
                    WHERE :keyword IS NULL OR  o.customer.fullName LIKE CONCAT('%', :keyword, '%') OR \s
                            o.customer.phoneNumber LIKE CONCAT('%', :keyword, '%') AND \s
                        (:status IS NULL OR o.status = :status)
                    """
=======
    @Query(value = """
                select new com.datn.beestyle.dto.order.OrderResponse(
                    o.id, o.orderTrackingNumber, c.id, o.orderChannel, o.orderStatus
                )
                from Order o
                    left join Customer c on o.customer.id = c.id
                where
                    (:orderChannel is null or o.orderChannel = :orderChannel) and 
                    (:orderStatus is null or o.orderStatus = :orderStatus)
                order by o.createdAt desc   
            """
>>>>>>> ee23191801e6c6287e495bb989978a11a4ae2e84
    )
    List<OrderResponse> findOrdersByOrderChannelAndOrderStatus(@Param("orderChannel") Integer orderChannel,
                                             @Param("orderStatus") Integer orderStatus);

<<<<<<< HEAD

    @Query(
            value = """
                    SELECT o.id, o.customer.fullName FROM Order o WHERE o.id IN (:ids)
                    """
    )
    List<Object[]> findCustomerNameById(@Param("ids") Iterable<Long> ids);
=======
>>>>>>> ee23191801e6c6287e495bb989978a11a4ae2e84
}
