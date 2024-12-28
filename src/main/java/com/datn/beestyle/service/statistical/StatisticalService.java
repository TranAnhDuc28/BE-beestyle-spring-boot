package com.datn.beestyle.service.statistical;

import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.order.CreateOrderRequest;
import com.datn.beestyle.dto.order.OrderResponse;
import com.datn.beestyle.dto.order.UpdateOrderRequest;
import com.datn.beestyle.dto.product.variant.ProductVariantResponse;
import com.datn.beestyle.entity.order.Order;
import com.datn.beestyle.enums.Status;
import com.datn.beestyle.repository.OrderRepository;
import com.datn.beestyle.repository.ProductVariantRepository;
import com.datn.beestyle.repository.StatisticalRepository;
import com.datn.beestyle.service.address.IAddressService;
import com.datn.beestyle.service.customer.ICustomerService;
import com.datn.beestyle.service.voucher.IVoucherService;
import com.datn.beestyle.util.AppUtils;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class StatisticalService {
    private final OrderRepository orderRepository;
    private final ProductVariantRepository productVariantRepository;

    public StatisticalService(OrderRepository orderRepository, ProductVariantRepository productVariantRepository ) {
        this.orderRepository = orderRepository;
        this.productVariantRepository = productVariantRepository;
    }

    public PageResponse<List<ProductVariantResponse>> getProductVariantsByStock(Pageable pageable,
                                                                                String orderByStock) {

        Page<ProductVariantResponse> productVariantResponsePages = productVariantRepository.filterProductVariantsByStock(
                pageable, orderByStock
        );

        return PageResponse.<List<ProductVariantResponse>>builder()
                .pageNo(productVariantResponsePages.getNumber() + 1)
                .pageSize(productVariantResponsePages.getSize())
                .totalElements(productVariantResponsePages.getTotalElements())
                .totalPages(productVariantResponsePages.getTotalPages())
                .items(productVariantResponsePages.getContent())
                .build();
    }
}
