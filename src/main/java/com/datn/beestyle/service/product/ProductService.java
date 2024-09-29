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
import com.datn.beestyle.mapper.ProductMapper;
import com.datn.beestyle.repository.ProductRepository;
import com.datn.beestyle.service.category.ICategoryService;
import com.datn.beestyle.service.product.attributes.brand.IBrandService;
import com.datn.beestyle.service.product.attributes.material.IMaterialService;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductService
        extends GenericServiceAbstract<Product, Long, CreateProductRequest, UpdateProductRequest, ProductResponse>
        implements IProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final IBrandService brandService;
    private final IMaterialService materialService;
    private final ICategoryService categoryService;


    public ProductService(IGenericRepository<Product, Long> entityRepository,
                          IGenericMapper<Product, CreateProductRequest, UpdateProductRequest, ProductResponse> mapper,
                          EntityManager entityManager, ProductRepository productRepository,
                          ProductMapper productMapper, IBrandService brandService, IMaterialService materialService,
                          ICategoryService categoryService) {
        super(entityRepository, mapper, entityManager);
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.brandService = brandService;
        this.materialService = materialService;
        this.categoryService = categoryService;
    }

    @Override
    public PageResponse<List<UserProductResponse>> findAllByCategoryId(Pageable pageable, int categoryId) {
        Page<Product> productPages = productRepository.findAllForUserByCategoryId(pageable, categoryId);
        List<UserProductResponse> userProductResponses = productPages.get().map(productMapper::toUserProductResponse).toList();
        return PageResponse.<List<UserProductResponse>>builder()
                .pageNo(pageable.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalElements(productPages.getTotalElements())
                .totalPages(productPages.getTotalPages())
                .items(userProductResponses)
                .build();
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
    protected void afterConvertCreateRequest(CreateProductRequest request, Product product) {
        Integer brandId = request.getBrandId();
        if (brandId != null) product.setBrand(brandService.getById(brandId));

        Integer materialId = request.getBrandId();
        if (materialId != null) product.setMaterial(materialService.getById(materialId));

        Integer categoryId = request.getBrandId();
        if (categoryId != null) product.setCategory(categoryService.getById(categoryId));
    }

    @Override
    protected void afterConvertUpdateRequest(UpdateProductRequest request, Product entity) {

    }

    @Override
    protected String getEntityName() {
        return "Product";
    }


}
