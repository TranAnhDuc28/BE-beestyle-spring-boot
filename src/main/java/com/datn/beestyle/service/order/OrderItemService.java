package com.datn.beestyle.service.order;

import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.order.item.CreateOrderItemRequest;
import com.datn.beestyle.dto.order.item.OrderItemResponse;
import com.datn.beestyle.dto.order.item.UpdateOrderItemRequest;
import com.datn.beestyle.entity.order.OrderItem;
import com.datn.beestyle.entity.product.Product;
import com.datn.beestyle.mapper.OrderItemMapper;
import com.datn.beestyle.mapper.ProductMapper;
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

@Slf4j
@Service
public class OrderItemService extends GenericServiceAbstract<OrderItem, Long, CreateOrderItemRequest, UpdateOrderItemRequest, OrderItemResponse>
        implements IOrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final ProductMapper productMapper;

    public OrderItemService(IGenericRepository<OrderItem, Long> entityRepository,
                            IGenericMapper<OrderItem, CreateOrderItemRequest, UpdateOrderItemRequest,
                                    OrderItemResponse> mapper, EntityManager entityManager,
                            OrderItemRepository orderItemRepository, OrderItemMapper orderItemMapper, ProductMapper productMapper) {
        super(entityRepository, mapper, entityManager);
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
        this.productMapper = productMapper;
    }

    public PageResponse<List<OrderItemResponse>> getOrderItemsDTO(
            Pageable pageable,
            Long id
    ) {
        List<OrderItemResponse> orderItemResponses;

        int page = pageable.getPageNumber() > 0 ? pageable.getPageNumber() - 1 : 0;
        PageRequest pageRequest = PageRequest.of(page, pageable.getPageSize(), Sort.by("id").ascending());

        Page<OrderItem> orderItemPage = this.orderItemRepository.findAllById(id, pageRequest);

        orderItemResponses = orderItemPage.stream().map(item -> {
            OrderItemResponse orderItemResponse = orderItemMapper.toEntityDto(item);

            if (item.getProductVariant() != null) {
                Hibernate.initialize(item.getProductVariant());
                orderItemResponse.setProductVariant(item.getProductVariant());
            } else {
                orderItemResponse.setProductVariant(null);
            }

//            if (item.getProductVariant().getProduct() != null) {
//                Long ids = item.getProductVariant().getProduct().getId();
//                List<Product> products = this.orderItemRepository.findProductById(ids);
//                UserProductResponse productResponse = new UserProductResponse();
//                for (Product product : products) {
//                    productResponse =
//                            this.productMapper.toEntityDto(product);
//                }
//                orderItemResponse.setProductResponse(productResponse);
//            } else {
//                orderItemResponse.setProductResponse(null);
//            }
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
