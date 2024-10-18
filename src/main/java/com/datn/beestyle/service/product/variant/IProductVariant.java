package com.datn.beestyle.service.product.variant;

import com.datn.beestyle.common.IGenericService;
import com.datn.beestyle.dto.product.CreateProductRequest;
import com.datn.beestyle.dto.product.ProductResponse;
import com.datn.beestyle.dto.product.UpdateProductRequest;
import com.datn.beestyle.entity.product.Product;
import com.datn.beestyle.entity.product.ProductVariant;

public interface IProductVariant
        extends IGenericService<ProductVariant, Long, CreateProductRequest, UpdateProductRequest, ProductResponse> {
}
