package com.datn.beestyle.service.order.item;

import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.order.item.CreateOrderItemRequest;
import com.datn.beestyle.dto.order.item.OrderItemResponse;
import com.datn.beestyle.dto.order.item.PatchUpdateQuantityOrderItem;
import com.datn.beestyle.dto.order.item.UpdateOrderItemRequest;
import com.datn.beestyle.entity.order.Order;
import com.datn.beestyle.entity.order.OrderItem;
import com.datn.beestyle.entity.product.ProductVariant;
import com.datn.beestyle.exception.InvalidDataException;
import com.datn.beestyle.repository.OrderItemRepository;
import com.datn.beestyle.service.order.OrderService;
import com.datn.beestyle.service.product.variant.ProductVariantService;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class OrderItemService
        extends GenericServiceAbstract<OrderItem, Long, CreateOrderItemRequest, UpdateOrderItemRequest, OrderItemResponse>
        implements IOrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderService orderService;
    private final ProductVariantService productVariantService;

    public OrderItemService(IGenericRepository<OrderItem, Long> entityRepository,
                            IGenericMapper<OrderItem, CreateOrderItemRequest, UpdateOrderItemRequest, OrderItemResponse> mapper,
                            EntityManager entityManager, OrderItemRepository orderItemRepository, OrderService orderService,
                            ProductVariantService productVariantService) {
        super(entityRepository, mapper, entityManager);
        this.orderItemRepository = orderItemRepository;
        this.orderService = orderService;
        this.productVariantService = productVariantService;
    }

    @Override
    public List<OrderItemResponse> getAllByOrderId(Long orderId) {
        return orderItemRepository.findAllByOrderId(orderId);
    }

    @Transactional
    @Override
    public int patchUpdateQuantity(PatchUpdateQuantityOrderItem request) {
        if (request.getId() == null) throw new InvalidDataException("Id hóa đơn chi tiết không hợp lệ.");
        this.getById(request.getId());


        ProductVariant productVariant = productVariantService.getById(request.getProductVariantId());
        int newQuantityProductVariant = productVariant.getQuantityInStock() - request.getQuantity();
        productVariantService.updateQuantityProductVariant(, request.getProductVariantId(), , newQuantityProductVariant);

        return orderItemRepository.updateQuantityOrderItem(request.getId(), request.getQuantity());
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
        Long orderId = request.getOrderId();
        Order order = orderService.getById(orderId);
        entity.setOrder(order);

        Long productVariantId = request.getProductVariantId();
        ProductVariant productVariant = productVariantService.getById(productVariantId);
        entity.setProductVariant(productVariant);
    }

    @Override
    protected void afterConvertUpdateRequest(UpdateOrderItemRequest request, OrderItem entity) {

    }

    @Override
    protected String getEntityName() {
        return "Order Item";
    }
}
