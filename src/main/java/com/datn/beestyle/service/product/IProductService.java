package com.datn.beestyle.service.product;

import com.datn.beestyle.common.IGenericService;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.product.CreateProductRequest;
import com.datn.beestyle.dto.product.ProductResponse;
import com.datn.beestyle.dto.product.UpdateProductRequest;
import com.datn.beestyle.dto.product.UserProductResponse;
import com.datn.beestyle.dto.product.variant.ProductVariantResponse;
import com.datn.beestyle.entity.product.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService
        extends IGenericService<Product, Long, CreateProductRequest, UpdateProductRequest, ProductResponse> {

    PageResponse<List<UserProductResponse>> getAllByCategoryId(Pageable pageable, int categoryId);
    PageResponse<List<ProductResponse>> getProductsByFields(Pageable pageable, String keyword,
                                                            String category, String gender, String brandIds,
                                                            String materialIds, String status);
    List<Object[]> getAllProductsWithDetails(List<Long> productIds);
}
