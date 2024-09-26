package com.datn.beestyle.service.product;

import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.product.CreateProductRequest;
import com.datn.beestyle.dto.product.ProductResponse;
import com.datn.beestyle.dto.product.UpdateProductRequest;
import com.datn.beestyle.dto.product.UserProductResponse;
import com.datn.beestyle.entity.product.Product;
import com.datn.beestyle.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductService
    extends GenericServiceAbstract<Product, Long, CreateProductRequest, UpdateProductRequest, ProductResponse>
    implements IProductService {

    private final ProductRepository productRepository;

    public ProductService(IGenericRepository<Product, Long> entityRepository,
                          IGenericMapper<Product, CreateProductRequest, UpdateProductRequest, ProductResponse> mapper,
                          EntityManager entityManager, ProductRepository productRepository) {
        super(entityRepository, mapper, entityManager);
        this.productRepository = productRepository;
    }

    @Override
    public PageResponse<UserProductResponse> findAllByCategoryId() {
        return null;
    }

    @Override
    protected List<CreateProductRequest> beforeCreateEntities(List<CreateProductRequest> requests) {
        return null;
    }

    @Override
    protected List<UpdateProductRequest> beforeUpdateEntities(List<UpdateProductRequest> requests) {
        return null;
    }

    @Override
    protected void beforeCreate(CreateProductRequest request) {

    }

    @Override
    protected void beforeUpdate(UpdateProductRequest request) {

    }

    @Override
    protected void afterConvertCreateRequest(CreateProductRequest request, Product entity) {

    }

    @Override
    protected void afterConvertUpdateRequest(UpdateProductRequest request, Product entity) {

    }

    @Override
    protected String getEntityName() {
        return "Product";
    }


}
