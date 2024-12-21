package com.datn.beestyle.service.order;

import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.address.AddressResponse;
import com.datn.beestyle.dto.order.CreateOrderRequest;
import com.datn.beestyle.dto.order.OrderResponse;
import com.datn.beestyle.dto.order.UpdateOrderRequest;
import com.datn.beestyle.entity.Voucher;
import com.datn.beestyle.entity.order.Order;
import com.datn.beestyle.enums.OrderChannel;
import com.datn.beestyle.enums.OrderStatus;
import com.datn.beestyle.exception.InvalidDataException;
import com.datn.beestyle.exception.ResourceNotFoundException;
import com.datn.beestyle.repository.OrderRepository;
import com.datn.beestyle.service.address.AddressService;
import com.datn.beestyle.service.voucher.VoucherService;
import com.datn.beestyle.util.AppUtils;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Slf4j
@Service
public class OrderService
        extends GenericServiceAbstract<Order, Long, CreateOrderRequest, UpdateOrderRequest, OrderResponse>
        implements IOrderService {

    private final OrderRepository orderRepository;
    private final AddressService addressService;
    private final VoucherService voucherService;

    public OrderService(IGenericRepository<Order, Long> entityRepository,
                        IGenericMapper<Order, CreateOrderRequest, UpdateOrderRequest, OrderResponse> mapper,
                        EntityManager entityManager, OrderRepository orderRepository, AddressService addressService, VoucherService voucherService) {
        super(entityRepository, mapper, entityManager);
        this.orderRepository = orderRepository;
        this.addressService = addressService;
        this.voucherService = voucherService;
    }

    public PageResponse<List<OrderResponse>> getOrdersFilterByFields(Pageable pageable, Map<String, String> filters) {
        int page = 0;
        if (pageable.getPageNumber() > 0) page = pageable.getPageNumber() - 1;
        PageRequest pageRequest = PageRequest.of(page, pageable.getPageSize(),
                Sort.by("createdAt", "id").descending());

        String keyword = filters.getOrDefault("keyword", null);

        LocalDateTime startDate = null;
        LocalDateTime endDate = null;
        // Xử lý startDate
        try {
            if (filters.get("startDate") != null) {
                startDate = LocalDate.parse(filters.get("startDate")).atStartOfDay();
            }
        } catch (Exception e) {
            log.error("Định dạng ngày không hợp lệ cho startDate: " + filters.get("startDate"));
        }

        // Xử lý endDate
        try {
            if (filters.get("endDate") != null) {
                endDate = LocalDate.parse(filters.get("endDate")).atTime(LocalTime.MAX);
            }
        } catch (Exception e) {
            log.error("Định dạng ngày không hợp lệ cho endDate: " + filters.get("endDate"));
        }

        Integer month = filters.get("month") != null ? Integer.parseInt(filters.get("month")) : null;
        Integer year = filters.get("year") != null ? Integer.parseInt(filters.get("year")) : null;

        Integer orderChannelValue = null;
        String orderChannel = filters.getOrDefault("orderChannel", null);
        if (orderChannel != null) {
            OrderChannel orderChannelEnum = OrderChannel.fromString(orderChannel.toUpperCase());
            orderChannelValue = orderChannelEnum != null ? orderChannelEnum.getValue() : null;
        }

        List<Integer> orderStatusIdList = null;
        String orderStatusValues = filters.getOrDefault("orderStatus", null);
        if (orderStatusValues != null) {
            orderStatusIdList = AppUtils.handleStringIdsToIntegerIdList(orderStatusValues);
        }

        Page<OrderResponse> orderResponsePages = orderRepository.findAllByFields(pageRequest, keyword, startDate, endDate,
                month, year, orderChannelValue, orderStatusIdList);

        return PageResponse.<List<OrderResponse>>builder()
                .pageNo(pageRequest.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalElements(orderResponsePages.getTotalElements())
                .totalPages(orderResponsePages.getTotalPages())
                .items(orderResponsePages.getContent())
                .build();
    }

    @Override
    public List<OrderResponse> getOrdersPending() {
        return orderRepository.findOrdersByOrderChannelAndOrderStatus(OrderChannel.OFFLINE.getValue(), OrderStatus.PENDING.getValue());
    }

    /**
     * Lấy thông tin chi tiết đầy đủ nhất của hóa đơn
     *
     * @param orderId
     * @return
     */
    @Override
    public OrderResponse getOrderByOrderId(Long orderId) {
        // lấy thông tin hóa đơn
        Optional<OrderResponse> order = orderRepository.findOrderById(orderId);
        if (order.isEmpty()) throw new ResourceNotFoundException(this.getEntityName() + " not found.");

        // lấy thông tin địa chỉ giao hàng
        Long addressId = order.get().getAddressId();
        if (addressId != null) {
            AddressResponse address = addressService.getDtoById(addressId);
            StringJoiner stringJoiner = new StringJoiner(", ");

            if (address.getAddressName() != null && !address.getAddressName().isEmpty()) {
                stringJoiner.add(address.getAddressName());
            }
            stringJoiner.add(address.getCommune());
            stringJoiner.add(address.getDistrict());
            stringJoiner.add(address.getCity());

            order.get().setShippingAddress(stringJoiner.toString());
        }

        // lấy thông tin voucher áp dụng
        Integer voucherId = order.get().getVoucherId();
        if (voucherId != null) {
            Voucher voucher = voucherService.getById(voucherId);
            order.get().setVoucherName(voucher.getVoucherName());
        }

        return order.get();
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
        int countOrderPending = orderRepository.countByCreatedByAndAndOrderStatus(1L, OrderStatus.PENDING.getValue());
        if (countOrderPending >= 20)
            throw new InvalidDataException("Hóa đơn chờ tạo tối đa 20, vui lòng sử dụng để tiếp tục tạo! ");
    }

    @Override
    protected void beforeUpdate(Long id, UpdateOrderRequest request) {

    }

    @Override
    protected void afterConvertCreateRequest(CreateOrderRequest request, Order entity) {
        entity.setOrderTrackingNumber(AppUtils.generateOrderTrackingNumber());
        entity.setCreatedBy(1L);
        entity.setUpdatedBy(1L);
    }

    @Override
    protected void afterConvertUpdateRequest(UpdateOrderRequest request, Order entity) {

    }

    @Override
    protected String getEntityName() {
        return "Order";
    }
}
