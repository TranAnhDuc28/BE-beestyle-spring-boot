package com.datn.beestyle.service.order;

import com.datn.beestyle.common.IGenericService;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.order.CreateOrderRequest;
import com.datn.beestyle.dto.order.OrderResponse;
import com.datn.beestyle.dto.order.UpdateOrderRequest;
import com.datn.beestyle.entity.order.Order;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface IOrderService
        extends IGenericService<Order, Long, CreateOrderRequest, UpdateOrderRequest, OrderResponse> {
    PageResponse<List<OrderResponse>> getOrdersFilterByFields(Pageable pageable, Map<String, String> filters);
    List<OrderResponse> getOrdersPending();
    OrderResponse getOrderDetailById(Long id);
    String changeOrderStatus(Long id, String status);
}
