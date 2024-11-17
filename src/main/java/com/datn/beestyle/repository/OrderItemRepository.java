package com.datn.beestyle.repository;

import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.entity.order.OrderItem;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends IGenericRepository<OrderItem, Long> {
}
