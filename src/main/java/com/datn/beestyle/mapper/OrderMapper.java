package com.datn.beestyle.mapper;

import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.dto.material.UpdateMaterialRequest;
import com.datn.beestyle.dto.order.CreateOrderRequest;
import com.datn.beestyle.dto.order.OrderResponse;
import com.datn.beestyle.dto.order.UpdateOrderRequest;
import com.datn.beestyle.entity.order.Order;
import com.datn.beestyle.enums.OrderChannel;
import com.datn.beestyle.enums.OrderStatus;
import com.datn.beestyle.enums.PaymentMethod;
import com.datn.beestyle.enums.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
@Mapper(componentModel = "spring")
public interface OrderMapper extends IGenericMapper<Order, CreateOrderRequest, UpdateOrderRequest, OrderResponse> {

    @Override
    @Mapping(target = "shippingAddress", ignore = true)
    OrderResponse toEntityDto(Order entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderTrackingNumber", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "shippingFee", constant = "0")
    @Mapping(target = "totalAmount", constant = "0")
    @Mapping(target = "orderChannel", source = ".", qualifiedByName = "orderChannelIdCreate")
    @Mapping(target = "orderStatus", source = ".", qualifiedByName = "orderStatusIdCreate")
    @Override
    Order toCreateEntity(CreateOrderRequest orderRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "orderChannel", source = ".", qualifiedByName = "orderChannelIdUpdate")
    @Mapping(target = "orderStatus", source = ".", qualifiedByName = "orderStatusIdUpdate")
    @Mapping(target = "paymentMethod", source = ".", qualifiedByName = "paymentMethodIdUpdate")
    void toUpdateEntity(@MappingTarget Order entity, UpdateOrderRequest request);

    @Named("orderStatusIdCreate")
    default int orderStatusIdCreate(CreateOrderRequest request) {
        return OrderStatus.valueOf(request.getOrderStatus()).getValue();
    }

    @Named("orderChannelIdCreate")
    default int orderChannelIdCreate(CreateOrderRequest request) {
        return OrderChannel.valueOf(request.getOrderChannel()).getValue();
    }

    @Named("orderStatusIdUpdate")
    default int orderStatusIdUpdate(UpdateOrderRequest request) {
        return OrderStatus.valueOf(request.getOrderStatus()).getValue();
    }

    @Named("orderChannelIdUpdate")
    default int orderChannelIdUpdate(UpdateOrderRequest request) {
        return OrderChannel.valueOf(request.getOrderChannel()).getValue();
    }

    @Named("paymentMethodIdUpdate")
    default int paymentMethodIdUpdate(UpdateOrderRequest request) {
        return PaymentMethod.valueOf(request.getPaymentMethod()).getValue();
    }
}

