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
    @Query("""
            SELECT o FROM Order o INNER JOIN o.customer c WHERE (
                :keyword IS NULL OR :keyword LIKE '' OR\s
                c.fullName LIKE :keyword OR\s
                c.phoneNumber LIKE :keyword
            )
            """)
//    ORs
//    DATE(o.createdAt) between DATE(:dateStart) AND DATE(:dateEnd)
    Page<Order> getOrders(
            @Param("keyword") String keyword,
//            @Param("dateStart") LocalDate dateStart,
//            @Param("dateEnd") LocalDate dateEnd,
            Pageable pageable
    );
}
