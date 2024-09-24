package com.datn.beestyle.service.brand;

import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.common.IGenericServiceAbstract;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.brand.BrandResponse;
import com.datn.beestyle.dto.brand.CreateBrandRequest;
import com.datn.beestyle.dto.brand.UpdateBrandRequest;
import com.datn.beestyle.entity.product.properties.Brand;
import com.datn.beestyle.repository.BrandRepository;
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
public class BrandService
        extends IGenericServiceAbstract<Brand, Integer, CreateBrandRequest, UpdateBrandRequest, BrandResponse>
        implements IBrandService {

    private final BrandRepository brandRepository;

    public BrandService(IGenericRepository<Brand, Integer> entityRepository,
                        IGenericMapper<Brand, CreateBrandRequest, UpdateBrandRequest, BrandResponse> mapper,
                        EntityManager entityManager, BrandRepository brandRepository) {
        super(entityRepository, mapper, entityManager);
        this.brandRepository = brandRepository;
    }


    @Override
    public PageResponse<?> searchByName(Pageable pageable, String name, boolean deleted) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Brand> brandPage = brandRepository.findByNameContainingAndDeleted(pageRequest, name, deleted);
        List<BrandResponse> brandResponseList = mapper.toEntityDtoList(brandPage.getContent());
        return PageResponse.builder()
                .pageNo(pageable.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalElements(brandPage.getTotalElements())
                .totalPages(brandPage.getTotalPages())
                .items(brandResponseList)
                .build();
    }

    @Override
    public List<BrandResponse> createBrands(List<CreateBrandRequest> requestList) {
        List<Brand> brandList = mapper.toCreateEntityList(requestList);
        return mapper.toEntityDtoList(brandRepository.saveAll(brandList));
    }

    @Override
    protected void beforeCreate(CreateBrandRequest request) {

    }

    @Override
    protected void beforeUpdate(UpdateBrandRequest request) {

    }

    @Override
    protected void afterConvertCreateRequest(CreateBrandRequest request, Brand entity) {

    }

    @Override
    protected void afterConvertUpdateRequest(UpdateBrandRequest request, Brand entity) {

    }

    @Override
    protected String getEntityName() {
        return null;
    }
}
