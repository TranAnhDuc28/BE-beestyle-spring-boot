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
    )
    Page<OrderResponse> findAllByFields(Pageable pageable,
                                        @Param("keyword") String keyword,
                                        @Param("orderChannel") Integer orderChannel,
                                        @Param("orderStatus") Integer orderStatus);

    @Query(value = """
                select new com.datn.beestyle.dto.order.OrderResponse(
                    o.id, o.orderTrackingNumber, c.id, o.orderChannel, o.orderStatus
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

}
