package com.datn.beestyle.repository;

import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.order.OrderResponse;
import com.datn.beestyle.entity.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends IGenericRepository<Order, Long> {

    @Query(value = """
                select new com.datn.beestyle.dto.order.OrderResponse(
                    o.id, o.orderTrackingNumber, c.id, c.fullName, o.phoneNumber, o.totalAmount, o.paymentDate, 
                    o.paymentMethod, o.orderChannel, o.orderType, o.orderStatus, o.createdAt, o.updatedAt, o.createdBy, 
                    o.updatedBy
                )
                from Order o
                    left join Customer c on o.customer.id = c.id
                where
                    (:keyword is null or 
                        o.orderTrackingNumber like concat('%', :keyword, '%') or
                        c.fullName like concat('%', :keyword, '%') or
                        o.phoneNumber like concat('%', :keyword, '%')) and
                    (:startDate is null or o.createdAt >= :startDate) and
                    (:endDate is null or o.createdAt <= :endDate) and
                    (:month is null or function('MONTH', o.createdAt) = :month) and
                    (:year is null or function('YEAR', o.createdAt) = :year) and     
                    (:orderChannel is null or o.orderChannel = :orderChannel) and 
                    (:orderStatus is null or o.orderStatus in (:orderStatus))    
            """
    )
    Page<OrderResponse> findAllByFields(Pageable pageable,
                                        @Param("keyword") String keyword,
                                        @Param("startDate") LocalDateTime startDate,
                                        @Param("endDate") LocalDateTime endDate,
                                        @Param("month") Integer month,
                                        @Param("year") Integer year,
                                        @Param("orderChannel") Integer orderChannel,
                                        @Param("orderStatus") List<Integer> orderStatus);


    @Query(value = """
                select new com.datn.beestyle.dto.order.OrderResponse(
                    o.id, o.orderTrackingNumber, c.id, c.fullName, o.phoneNumber, o.totalAmount, o.paymentDate, 
                    o.paymentMethod, o.orderChannel, o.orderStatus, o.createdAt, o.updatedAt, o.createdBy, o.updatedBy
                )
                from Order o
                    left join Customer c on o.customer.id = c.id
                where o.id = :orderId   
            """
    )
    Optional<OrderResponse> findOrderById(@Param("orderId") Long orderId);

    @Query(value = """
                select new com.datn.beestyle.dto.order.OrderResponse(
                    o.id, o.orderTrackingNumber, c.id, o.orderChannel, o.orderType, o.orderStatus
                )
                from Order o
                    left join Customer c on o.customer.id = c.id
                where
                    (:orderChannel is null or o.orderChannel = :orderChannel) and 
                    (:orderStatus is null or o.orderStatus = :orderStatus)
            """
    )
    List<OrderResponse> findOrdersByOrderChannelAndOrderStatus(@Param("orderChannel") Integer orderChannel,
                                             @Param("orderStatus") Integer orderStatus);

    int countByCreatedByAndAndOrderStatus(Long staffId, Integer orderStatus);


    @Query(value = """
                select new com.datn.beestyle.dto.order.OrderResponse(
                    o.id, o.orderTrackingNumber, c.id, c.fullName, o.phoneNumber, o.totalAmount, o.paymentDate, 
                    o.paymentMethod, o.orderChannel, o.orderType, o.orderStatus, o.createdAt, o.updatedAt, o.createdBy, 
                    o.updatedBy, o.shippingAddress.id, o.shippingFee
                )
                from Order o
                    left join Customer c on o.customer.id = c.id
                where
                    (:id is null or o.id = :id)
            """
    )
    List<OrderResponse> findOrdersById(@Param("id") Long id);


}
