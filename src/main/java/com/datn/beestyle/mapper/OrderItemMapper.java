package com.datn.beestyle.mapper;

import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.dto.order.item.CreateOrderItemRequest;
import com.datn.beestyle.dto.order.item.OrderItemResponse;
import com.datn.beestyle.dto.order.item.UpdateOrderItemRequest;
import com.datn.beestyle.entity.order.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderItemMapper extends IGenericMapper<OrderItem, CreateOrderItemRequest, UpdateOrderItemRequest, OrderItemResponse> {
    @Override
    OrderItemResponse toEntityDto(OrderItem entity);

    @Mapping(target = "id", ignore = true)
    @Override
    OrderItem toCreateEntity(CreateOrderItemRequest orderItemRequest);

    @Mapping(target = "id", ignore = true)
    void toUpdateEntity(@MappingTarget OrderItem entity, UpdateOrderItemRequest request);
}

