package com.datn.beestyle.service.order.item;

import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.order.item.CreateOrderItemRequest;
import com.datn.beestyle.dto.order.item.OrderItemResponse;
import com.datn.beestyle.dto.order.item.UpdateOrderItemRequest;
import com.datn.beestyle.entity.order.OrderItem;
import com.datn.beestyle.repository.OrderItemRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrderItemService
        extends GenericServiceAbstract<OrderItem, Long, CreateOrderItemRequest, UpdateOrderItemRequest, OrderItemResponse>
        implements IOrderItemService {
    private final OrderItemRepository orderItemRepository;

    public OrderItemService(IGenericRepository<OrderItem, Long> entityRepository,
                            IGenericMapper<OrderItem, CreateOrderItemRequest, UpdateOrderItemRequest, OrderItemResponse> mapper,
                            EntityManager entityManager, OrderItemRepository orderItemRepository) {
        super(entityRepository, mapper, entityManager);
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public List<OrderItemResponse> getAllByOrderId(Long orderId) {
        return orderItemRepository.findAllByOrderId(orderId);
    }

    @Override
    protected List<CreateOrderItemRequest> beforeCreateEntities(List<CreateOrderItemRequest> requests) {
        return null;
    }

    @Override
    protected List<UpdateOrderItemRequest> beforeUpdateEntities(List<UpdateOrderItemRequest> requests) {
        return null;
    }

    @Override
    protected void beforeCreate(CreateOrderItemRequest request) {

    }

    @Override
    protected void beforeUpdate(Long aLong, UpdateOrderItemRequest request) {

    }

    @Override
    protected void afterConvertCreateRequest(CreateOrderItemRequest request, OrderItem entity) {

    }

    @Override
    protected void afterConvertUpdateRequest(UpdateOrderItemRequest request, OrderItem entity) {

    }

    @Override
    protected String getEntityName() {
        return "Order Item";
    }
}
