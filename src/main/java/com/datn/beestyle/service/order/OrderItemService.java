package com.datn.beestyle.service.order;

import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.order.OrderResponse;
import com.datn.beestyle.dto.order.item.CreateOrderItemRequest;
import com.datn.beestyle.dto.order.item.OrderItemResponse;
import com.datn.beestyle.dto.order.item.UpdateOrderItemRequest;
import com.datn.beestyle.entity.order.Order;
import com.datn.beestyle.entity.order.OrderItem;
import com.datn.beestyle.enums.Status;
import com.datn.beestyle.mapper.OrderItemMapper;
import com.datn.beestyle.repository.OrderItemRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderItemService extends GenericServiceAbstract<OrderItem, Long, CreateOrderItemRequest, UpdateOrderItemRequest, OrderItemResponse>
        implements IOrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    public OrderItemService(IGenericRepository<OrderItem, Long> entityRepository,
                            IGenericMapper<OrderItem, CreateOrderItemRequest, UpdateOrderItemRequest,
                                    OrderItemResponse> mapper, EntityManager entityManager,
                            OrderItemRepository orderItemRepository, OrderItemMapper orderItemMapper) {
        super(entityRepository, mapper, entityManager);
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
    }

    public PageResponse<List<OrderItemResponse>> getOrderItemsDTO(
            Pageable pageable, String search, Long id
    ) {
        Map<Long, String> productNames;
        List<OrderItemResponse> orderItemResponses;

        int page = pageable.getPageNumber() > 0 ? pageable.getPageNumber() - 1 : 0;
        PageRequest pageRequest = PageRequest.of(page, pageable.getPageSize(), Sort.by("id").descending());

        Page<OrderItem> orderItemPage = this.orderItemRepository.findAllByKeywordAndId(id, pageRequest);

        List<Long> ids = orderItemPage.stream()
                .map(orderItem -> orderItem.getProductVariant() != null ? orderItem.getProductVariant().getId() : null)
                .distinct()
                .toList();

        if (ids.isEmpty()) {
            productNames = null;
        } else {
            productNames = this.orderItemRepository.findOrderItemByProduct(ids).stream()
                    .collect(Collectors.toMap(object -> (Long) object[0], object -> (String) object[1]));
        }

        orderItemResponses = orderItemPage.stream().map(item -> {
            OrderItemResponse orderItemResponse = orderItemMapper.toEntityDto(item);

            if (item.getProductVariant() != null) {
                Hibernate.initialize(item.getProductVariant());
                orderItemResponse.setProductVariant(item.getProductVariant());
            } else {
                orderItemResponse.setProductVariant(null);
            }
            return orderItemResponse;
        }).toList();


        return PageResponse.<List<OrderItemResponse>>builder()
                .pageNo(pageRequest.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalElements(orderItemPage.getTotalElements())
                .totalPages(orderItemPage.getTotalPages())
                .items(orderItemResponses)
                .build();
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
        return "Order";
    }

    @Override
    public OrderItemResponse create(CreateOrderItemRequest request) {
        return null;
    }

    @Override
    public OrderItemResponse update(Long aLong, UpdateOrderItemRequest request) {
        return null;
    }
}
