package com.datn.beestyle.service.product.variant;

import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.product.variant.CreateProductVariantRequest;
import com.datn.beestyle.dto.product.variant.ProductVariantResponse;
import com.datn.beestyle.dto.product.variant.UpdateProductVariantRequest;
import com.datn.beestyle.entity.product.ProductVariant;
import com.datn.beestyle.enums.Status;
import com.datn.beestyle.repository.ProductVariantRepository;
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
public class ProductVariantService
        extends GenericServiceAbstract<ProductVariant, Long, CreateProductVariantRequest, UpdateProductVariantRequest, ProductVariantResponse>
        implements IProductVariantService {

    private final ProductVariantRepository productVariantRepository;

    public ProductVariantService(IGenericRepository<ProductVariant, Long> entityRepository,
                                 IGenericMapper<ProductVariant, CreateProductVariantRequest, UpdateProductVariantRequest, ProductVariantResponse> mapper,
                                 EntityManager entityManager, ProductVariantRepository productVariantRepository) {
        super(entityRepository, mapper, entityManager);
        this.productVariantRepository = productVariantRepository;
    }

    @Override
    public PageResponse<List<ProductVariantResponse>> getProductsByFieldsByProductId(Pageable pageable, String productIdStr,
                                                                                     String keyword, String colorIds,
                                                                                     String sizeIds, String status) {
        Integer productId = null;
        if (productIdStr != null) {
            try {
                productId = Integer.parseInt(productIdStr);
            } catch (Exception e) {
                throw new IllegalArgumentException("ID sản phẩm phải là số.");
            }
        }

        int page = 0;
        if (pageable.getPageNumber() > 0) page = pageable.getPageNumber() - 1;
        PageRequest pageRequest = PageRequest.of(page, pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt", "id"));

        List<Integer> colorIdList = AppUtils.handleStringIdsToIntegerIdList(colorIds);
        List<Integer> sizeIdList = AppUtils.handleStringIdsToIntegerIdList(sizeIds);

        Integer statusValue = null;
        if (status != null) {
            Status statusEnum = Status.fromString(status);
            if (statusEnum != null) statusValue = statusEnum.getValue();
        }

        Page<ProductVariantResponse> productVariantResponsePages =
                productVariantRepository.findAllByFieldsByProductId(pageRequest, productId, keyword, colorIdList, sizeIdList, statusValue);
        return PageResponse.<List<ProductVariantResponse>>builder()
                .pageNo(pageRequest.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalElements(productVariantResponsePages.getTotalElements())
                .totalPages(productVariantResponsePages.getTotalPages())
                .items(productVariantResponsePages.getContent())
                .build();
    }

    @Override
    protected List<CreateProductVariantRequest> beforeCreateEntities(List<CreateProductVariantRequest> requests) {
        return null;
    }

    @Override
    protected List<UpdateProductVariantRequest> beforeUpdateEntities(List<UpdateProductVariantRequest> requests) {
        return null;
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
        return "Product variant";
    }


}
