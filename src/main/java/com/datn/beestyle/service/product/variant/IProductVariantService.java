package com.datn.beestyle.service.product.variant;

import com.datn.beestyle.common.IGenericService;
import com.datn.beestyle.dto.product.CreateProductRequest;
import com.datn.beestyle.dto.product.ProductResponse;
import com.datn.beestyle.dto.product.UpdateProductRequest;
import com.datn.beestyle.dto.product.variant.CreateProductVariantRequest;
import com.datn.beestyle.dto.product.variant.ProductVariantResponse;
import com.datn.beestyle.dto.product.variant.UpdateProductVariantRequest;
import com.datn.beestyle.entity.product.Product;
import com.datn.beestyle.entity.product.ProductVariant;

import java.util.List;
import java.util.Optional;

public interface IProductVariantService
        extends IGenericService<ProductVariant, Long, CreateProductVariantRequest, UpdateProductVariantRequest, ProductVariantResponse> {
    Optional<Object[]> getAllProductsWithDetails(List<Long> productIds);
    void updateProductVariant(Integer promotionId, List<Integer> ids);
}
