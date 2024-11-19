package com.datn.beestyle.service.order.item;

import com.datn.beestyle.common.IGenericService;
import com.datn.beestyle.dto.order.item.CreateOrderItemRequest;
import com.datn.beestyle.dto.order.item.OrderItemResponse;
import com.datn.beestyle.dto.order.item.UpdateOrderItemRequest;
import com.datn.beestyle.entity.order.OrderItem;

import java.util.List;

public interface IOrderItemService
        extends IGenericService<OrderItem, Long, CreateOrderItemRequest, UpdateOrderItemRequest, OrderItemResponse> {

    List<OrderItemResponse> getAllByOrderId(Long orderId);
}
