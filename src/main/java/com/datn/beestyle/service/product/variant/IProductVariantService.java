package com.datn.beestyle.service.product.variant;

import com.datn.beestyle.common.IGenericService;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.product.variant.CreateProductVariantRequest;
import com.datn.beestyle.dto.product.variant.ProductVariantResponse;
import com.datn.beestyle.dto.product.variant.UpdateProductVariantRequest;
import com.datn.beestyle.entity.product.ProductVariant;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IProductVariantService
        extends IGenericService<ProductVariant, Long, CreateProductVariantRequest, UpdateProductVariantRequest, ProductVariantResponse> {

    PageResponse<List<ProductVariantResponse>> getProductsByFieldsByProductId(Pageable pageable, String productIdStr,
                                                                              String keyword, String colorIds,
                                                                              String sizeIds, String status);
    Optional<Object[]> getAllProductsWithDetails(List<Long> productIds);
    void updateProductVariant(Integer promotionId, List<Integer> ids);
}
