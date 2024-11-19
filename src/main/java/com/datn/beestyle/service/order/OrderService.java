package com.datn.beestyle.service.order;

import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.order.CreateOrderRequest;
import com.datn.beestyle.dto.order.OrderResponse;
import com.datn.beestyle.dto.order.UpdateOrderRequest;
import com.datn.beestyle.entity.Voucher;
import com.datn.beestyle.entity.order.Order;
<<<<<<< HEAD
import com.datn.beestyle.entity.user.Customer;
import com.datn.beestyle.enums.*;
=======
import com.datn.beestyle.enums.OrderChannel;
import com.datn.beestyle.enums.Status;
>>>>>>> ee23191801e6c6287e495bb989978a11a4ae2e84
import com.datn.beestyle.mapper.OrderMapper;
import com.datn.beestyle.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
=======
>>>>>>> ee23191801e6c6287e495bb989978a11a4ae2e84
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService
        extends GenericServiceAbstract<Order, Long, CreateOrderRequest, UpdateOrderRequest, OrderResponse>
        implements IOrderService {

    private final OrderRepository orderRepository;

    public OrderService(IGenericRepository<Order, Long> entityRepository,
                        IGenericMapper<Order, CreateOrderRequest, UpdateOrderRequest, OrderResponse> mapper,
                        EntityManager entityManager, OrderRepository orderRepository) {
        super(entityRepository, mapper, entityManager);
        this.orderRepository = orderRepository;
    }

<<<<<<< HEAD
    public PageResponse<List<OrderResponse>> getOrdersDTO(
            Pageable pageable, String q, String status
    ) {
=======
    public PageResponse<List<OrderResponse>> getOrdersFilterByFields(Pageable pageable, String keyword, String orderChannel,
                                                                       String orderStatus) {
        int page = 0;
        if (pageable.getPageNumber() > 0) page = pageable.getPageNumber() - 1;
        PageRequest pageRequest = PageRequest.of(page, pageable.getPageSize(),
                Sort.by("createdAt", "id").descending());

        Integer orderChannelValue = null;
        if (orderChannel != null) {
            OrderChannel orderChannelEnum = OrderChannel.fromString(orderChannel.toUpperCase());
            orderChannelValue = orderChannelEnum != null ? orderChannelEnum.getValue() : null;
        }
>>>>>>> ee23191801e6c6287e495bb989978a11a4ae2e84

        Integer statusValue = null;
        if (orderStatus != null) {
            Status statusEnum = Status.fromString(orderStatus.toUpperCase());
            statusValue = statusEnum != null ? statusEnum.getValue() : null;
        }

<<<<<<< HEAD
        PageRequest pageRequest = PageRequest.of(page, pageable.getPageSize(),
                Sort.by("id").descending());

        Page<Order> orderPages = this.orderRepository.findAllByKeywordAndStatus(q, status, pageRequest);

        List<Long> ids = orderPages.get().map(order ->
                order.getCustomer() != null ? order.getCustomer().getId() : null).distinct().toList();

        if (ids.isEmpty()) {
            customerNames = null;
        } else {
            customerNames = this.orderRepository.findCustomerNameById(ids).stream()
                    .collect(Collectors.toMap(object -> (Long) object[0], object -> (String) object[1]));
        }

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
=======
        Page<OrderResponse> orderResponsePages = orderRepository.findAllByFields(pageRequest, keyword, orderChannelValue,
                statusValue);
>>>>>>> ee23191801e6c6287e495bb989978a11a4ae2e84

        return PageResponse.<List<OrderResponse>>builder()
                .pageNo(pageRequest.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalElements(orderResponsePages.getTotalElements())
                .totalPages(orderResponsePages.getTotalPages())
                .items(orderResponsePages.getContent())
                .build();
    }

    public void createBill() {
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        final int RANDOM_PART_LENGTH = 5;
        final int SEQUENTIAL_PART_LENGTH = 5;

        StringBuilder randomPart = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < RANDOM_PART_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            randomPart.append(CHARACTERS.charAt(index));
        }

        String uniquePart = String.valueOf(System.currentTimeMillis()).substring(5, 10);
        String trackingNumber = "#" + randomPart + uniquePart;

        Order order = new Order();
        order.initializeOrder(trackingNumber, 1L);

        this.orderRepository.save(order);
    }

    public void updateBill(UpdateOrderRequest order, Long id) {
        Order o = this.orderRepository.findById(id).get();
        if (order != null) {
            o.setPhoneNumber(order.getPhoneNumber());
            o.setPaymentMethod(order.getPaymentMethod());
            o.setCustomer(o.getCustomer());
            o.setOrderChannel(o.getOrderChannel());
            o.setTotalAmount(o.getTotalAmount());
            o.setShippingFee(o.getShippingFee());
            o.setStatus(o.getStatus());
            o.setVoucher(o.getVoucher());
            o.setShippingAddress(o.getShippingAddress());
            o.setPaymentDate(
                    Timestamp.valueOf(LocalDateTime.now())
            );
            o.setUpdatedAt(LocalDateTime.now());
        }
        this.orderRepository.save(o);
    }


    @Override
    public List<OrderResponse> getOrdersPending() {
        return orderRepository.findOrdersByOrderChannelAndOrderStatus(0, 0);
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
