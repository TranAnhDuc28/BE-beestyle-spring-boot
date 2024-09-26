package com.datn.beestyle.service.product;

import com.datn.beestyle.common.IGenericService;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.product.CreateProductRequest;
import com.datn.beestyle.dto.product.ProductResponse;
import com.datn.beestyle.dto.product.UpdateProductRequest;
import com.datn.beestyle.dto.product.UserProductResponse;
import com.datn.beestyle.entity.product.Product;

public interface IProductService
        extends IGenericService<Product, Long, CreateProductRequest, UpdateProductRequest, ProductResponse> {

    PageResponse<UserProductResponse> findAllByCategoryId();
}
