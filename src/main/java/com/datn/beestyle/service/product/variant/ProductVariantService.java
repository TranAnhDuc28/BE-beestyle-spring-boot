package com.datn.beestyle.service.product.variant;

import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.product.CreateProductRequest;
import com.datn.beestyle.dto.product.ProductResponse;
import com.datn.beestyle.dto.product.UpdateProductRequest;
import com.datn.beestyle.dto.product.variant.CreateProductVariantRequest;
import com.datn.beestyle.dto.product.variant.ProductVariantResponse;
import com.datn.beestyle.dto.product.variant.UpdateProductVariantRequest;
import com.datn.beestyle.entity.product.ProductVariant;
import com.datn.beestyle.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductVariantService extends GenericServiceAbstract<ProductVariant, Long, CreateProductVariantRequest,
        UpdateProductVariantRequest, ProductVariantResponse>
        implements IProductVariantService {
    private final ProductVariantRepository productVariantRepository;

    public ProductVariantService(IGenericRepository<ProductVariant, Long> entityRepository,
                                 IGenericMapper<ProductVariant, CreateProductVariantRequest, UpdateProductVariantRequest
                                         , ProductVariantResponse> mapper, EntityManager entityManager,
                                 ProductVariantRepository productVariantRepository) {
        super(entityRepository, mapper, entityManager);
        this.productVariantRepository = productVariantRepository;
    }


    @Override
    protected List<CreateProductVariantRequest> beforeCreateEntities(List<CreateProductVariantRequest> requests) {
        return null;
    }

    @Override
    protected List<UpdateProductVariantRequest> beforeUpdateEntities(List<UpdateProductVariantRequest> requests) {
        List<UpdateProductVariantRequest> validRequests = requests.stream()
                .filter(dto -> dto.getId() != null)
                .toList();
        if (validRequests.isEmpty()) return Collections.emptyList();

        List<Long> ids = validRequests.stream()
                .map(dto -> dto.getId().longValue())
                .toList();

        // Lấy các ID đã tồn tại từ repository
        List<Long> existingIds = productVariantRepository.findAllById(ids).stream()
                .map(ProductVariant::getId)
                .toList();

        if (existingIds.isEmpty()) return Collections.emptyList();

        return validRequests.stream()
                .filter(dto -> existingIds.contains(dto.getId()))
                .toList();
    }

    @Override
    @Transactional
    public void updateProductVariant(Integer promotionId, List<Integer> ids) {
        productVariantRepository.updatePromotionForVariants(promotionId, ids);
    }

    @Override
    protected void beforeCreate(CreateProductVariantRequest request) {

    }

    @Override
    protected void beforeUpdate(Long aLong, UpdateProductVariantRequest request) {

    }

    @Override
    protected void afterConvertCreateRequest(CreateProductVariantRequest request, ProductVariant entity) {

    }

    @Override
    protected void afterConvertUpdateRequest(UpdateProductVariantRequest request, ProductVariant entity) {

    }

    @Override
    protected String getEntityName() {
        return null;
    }


    @Override
    public Optional<Object[]> getAllProductsWithDetails(List<Long> productIds) {
        return productVariantRepository.findAllProductsWithDetails(productIds);
    }

}
