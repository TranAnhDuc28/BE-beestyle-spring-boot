package com.datn.beestyle.service.product;

import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.product.CreateProductRequest;
import com.datn.beestyle.dto.product.ProductResponse;
import com.datn.beestyle.dto.product.UpdateProductRequest;
import com.datn.beestyle.dto.product.UserProductResponse;
import com.datn.beestyle.dto.product.variant.ProductVariantResponse;
import com.datn.beestyle.entity.product.Product;
import com.datn.beestyle.enums.GenderProduct;
import com.datn.beestyle.enums.Status;
import com.datn.beestyle.exception.InvalidDataException;
import com.datn.beestyle.mapper.ProductMapper;
import com.datn.beestyle.repository.ProductRepository;
import com.datn.beestyle.service.category.ICategoryService;
import com.datn.beestyle.service.brand.IBrandService;
import com.datn.beestyle.service.material.IMaterialService;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public PageResponse<List<UserProductResponse>> getAllByCategoryId(Pageable pageable, int categoryId) {
        int page = 0;
        if (pageable.getPageNumber() > 0) page = pageable.getPageNumber() - 1;

        PageRequest pageRequest = PageRequest.of(page, pageable.getPageSize());
        Page<Product> productPages = productRepository.findAllForUserByCategoryId(pageRequest, categoryId);
        List<UserProductResponse> userProductResponses = productPages.get().map(productMapper::toUserProductResponse).toList();
        return PageResponse.<List<UserProductResponse>>builder()
                .pageNo(pageRequest.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalElements(productPages.getTotalElements())
                .totalPages(productPages.getTotalPages())
                .items(userProductResponses)
                .build();
    }

    @Override
    public PageResponse<List<ProductResponse>> getProductsByFields(Pageable pageable, String keyword,
                                                                   String category, String genderProduct, String brandIds,
                                                                   String materialIds, String status) {
        int page = 0;
        if (pageable.getPageNumber() > 0) page = pageable.getPageNumber() - 1;

        Integer categoryId = null;
        if (category != null) {
            try {
                categoryId = Integer.parseInt(category);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        Integer statusValue = null;
        if (status != null) {
            Status statusEnum = Status.fromString(status);
            if (statusEnum != null) statusValue = statusEnum.getValue();
        }

        Integer genderProductValue = null;
        if (genderProduct != null) {
            GenderProduct genderProductEnum = GenderProduct.fromString(genderProduct);
            if (genderProductEnum != null) genderProductValue = genderProductEnum.getValue();
        }

        List<Integer> brandIdList = null;
        String[] brandIdsStr = brandIds != null ? brandIds.split(",") : null;
        if(brandIdsStr != null) {
            brandIdList = new ArrayList<>();
            for (String strId: brandIdsStr) {
                int id;
                try {
                    id = Integer.parseInt(strId);
                } catch (Exception e) {
                    continue;
                }
                brandIdList.add(id);
            }
        }


        List<Integer> materialIdList = null;
        String[] materialIdsStr = materialIds != null ? materialIds.split(",") : null;
        if (materialIdsStr != null) {
            materialIdList = new ArrayList<>();
            for (String strId: materialIdsStr) {
                int id;
                try {
                    id = Integer.parseInt(strId);
                } catch (Exception e) {
                    continue;
                }
                materialIdList.add(id);
            }
        }

        PageRequest pageRequest = PageRequest.of(page, pageable.getPageSize());
        Page<ProductResponse> productResponsePages = productRepository.findAllByFields(pageRequest, keyword, categoryId,
                genderProductValue, brandIdList, materialIdList, statusValue);
        return PageResponse.<List<ProductResponse>>builder()
                .pageNo(pageRequest.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalElements(productResponsePages.getTotalElements())
                .totalPages(productResponsePages.getTotalPages())
                .items(productResponsePages.getContent())
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
        String productName = request.getProductName().trim();
        if (productRepository.existsByProductName(productName))
            throw new InvalidDataException("Product name already exists");
        request.setProductName(productName);
    }

    @Override
    protected void beforeUpdate(Long id, UpdateProductRequest request) {

    }

    @Override
    protected void afterConvertCreateRequest(CreateProductRequest request, Product product) {
        Integer brandId = request.getBrandId();
        if (brandId != null) product.setBrand(brandService.getById(brandId));

        Integer materialId = request.getMaterialId();
        if (materialId != null) product.setMaterial(materialService.getById(materialId));

        Integer categoryId = request.getCategoryId();
        if (categoryId != null) product.setCategory(categoryService.getById(categoryId));
    }

    @Override
    protected void afterConvertUpdateRequest(UpdateProductRequest request, Product product) {
        Optional<Product> productByName = productRepository.findByProductName(request.getProductName());
        if (productByName.isPresent() && !productByName.get().getId().equals(product.getId())) {
            throw new InvalidDataException("Product name already exists");
        }

        Integer brandId = request.getBrandId();
        if (brandId != null) product.setBrand(brandService.getById(brandId));

        Integer materialId = request.getMaterialId();
        if (materialId != null) product.setMaterial(materialService.getById(materialId));

        Integer categoryId = request.getCategoryId();
        if (categoryId != null) product.setCategory(categoryService.getById(categoryId));
    }

    @Override
    protected String getEntityName() {
        return "Product";
    }

    public List<Object[]> getAllProductsWithDetails(List<Long> productIds) {
        return productRepository.findAllProductsWithDetails(productIds);
    }

}
