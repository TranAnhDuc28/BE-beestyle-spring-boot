package com.datn.beestyle.service.order;

import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.category.CategoryResponse;
import com.datn.beestyle.dto.customer.CustomerResponse;
import com.datn.beestyle.dto.order.CreateOrderRequest;
import com.datn.beestyle.dto.order.OrderResponse;
import com.datn.beestyle.dto.order.UpdateOrderRequest;
import com.datn.beestyle.entity.order.Order;
import com.datn.beestyle.enums.Status;
import com.datn.beestyle.mapper.OrderMapper;
import com.datn.beestyle.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService
        extends GenericServiceAbstract<Order, Long, CreateOrderRequest, UpdateOrderRequest, OrderResponse>
        implements IOrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    public OrderService(IGenericRepository<Order, Long> entityRepository,
                        IGenericMapper<Order, CreateOrderRequest, UpdateOrderRequest,
                                OrderResponse> mapper, EntityManager entityManager,
                        OrderRepository orderRepository, OrderMapper orderMapper
    ) {
        super(entityRepository, mapper, entityManager);
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public PageResponse<List<OrderResponse>> getOrdersDTO(
            Pageable pageable, String search, String status
//            String dateStart,
//            String dateEnd
    ) {

        Map<Long, String> customerNames;
        List<OrderResponse> orderResponses;
        int page = pageable.getPageNumber() > 0 ?
                pageable.getPageNumber() - 1 : 0;
        Integer statusValue = null;
        if (status != null) {
            Status statusEnum = Status.fromString(status.toUpperCase());
            statusValue = statusEnum != null ? statusEnum.getValue() : null;
        }

        PageRequest pageRequest = PageRequest.of(page, pageable.getPageSize(),
                Sort.by("id").descending());

        Page<Order> orderPages = this.orderRepository.findAllByKeywordAndStatus(search, status, pageRequest);

//        LocalDate start = dateStart != null ? LocalDate.parse(dateStart) : null;
//        LocalDate end = dateEnd != null ? LocalDate.parse(dateEnd) : null;

        List<Long> ids = orderPages.get().map(order ->
                order.getCustomer() != null ? order.getCustomer().getId() : null).distinct().toList();
        System.out.println(ids);

        if (ids.isEmpty()) {
            customerNames = null;
        } else {
            customerNames = this.orderRepository.findCustomerNameById(ids).stream()
                    .collect(Collectors.toMap(object -> (Long) object[0], object -> (String) object[1]));
        }
        System.out.println(customerNames);

        if (customerNames != null) {
            orderResponses = orderPages.get().map(order -> {
                OrderResponse orderResponse = orderMapper.toEntityDto(order);

                if (order.getCustomer().getFullName() != null) {
                    orderResponse.setCustomerName(customerNames.get(order.getCustomer().getId()));
                } else {
                    orderResponse.setCustomerName(null);
                }
                return orderResponse;
            }).toList();
        } else {
            orderResponses = orderPages.get().map(orderMapper::toEntityDto).toList();
        }

        return PageResponse.<List<OrderResponse>>builder()
                .pageNo(pageRequest.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalElements(orderPages.getTotalElements())
                .totalPages(orderPages.getTotalPages())
                .items(orderResponses)
                .build();
    }

    @Override
    protected List<CreateOrderRequest> beforeCreateEntities(List<CreateOrderRequest> requests) {
        return null;
    }

    @Override
    protected List<UpdateOrderRequest> beforeUpdateEntities(List<UpdateOrderRequest> requests) {
        return null;
    }

    @Override
    protected void beforeCreate(CreateOrderRequest request) {

    }

    @Override
    protected void beforeUpdate(Long aLong, UpdateOrderRequest request) {

    }

    @Override
    protected void afterConvertCreateRequest(CreateOrderRequest request, Order entity) {

    }

    @Override
    protected void afterConvertUpdateRequest(UpdateOrderRequest request, Order entity) {

    }

    @Override
    protected String getEntityName() {
        return "Order";
    }
}
