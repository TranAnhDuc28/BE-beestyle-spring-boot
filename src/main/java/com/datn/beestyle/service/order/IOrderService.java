package com.datn.beestyle.service.order;

import com.datn.beestyle.common.IGenericService;
import com.datn.beestyle.dto.order.CreateOrderRequest;
import com.datn.beestyle.dto.order.OrderResponse;
import com.datn.beestyle.dto.order.UpdateOrderRequest;
import com.datn.beestyle.entity.order.Order;

public interface IOrderService
        extends IGenericService<Order, Long, CreateOrderRequest, UpdateOrderRequest, OrderResponse> {

}
