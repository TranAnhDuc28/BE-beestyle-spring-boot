package com.datn.beestyle.service.product.attributes.brand;

import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.product.attributes.brand.BrandResponse;
import com.datn.beestyle.dto.product.attributes.brand.CreateBrandRequest;
import com.datn.beestyle.dto.product.attributes.brand.UpdateBrandRequest;
import com.datn.beestyle.entity.product.attributes.Brand;
import com.datn.beestyle.enums.Status;
import com.datn.beestyle.repository.BrandRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class BrandService
        extends GenericServiceAbstract<Brand, Integer, CreateBrandRequest, UpdateBrandRequest, BrandResponse>
        implements IBrandService {

    private final BrandRepository brandRepository;

    public BrandService(IGenericRepository<Brand, Integer> entityRepository,
                        IGenericMapper<Brand, CreateBrandRequest, UpdateBrandRequest, BrandResponse> mapper,
                        EntityManager entityManager, BrandRepository brandRepository) {
        super(entityRepository, mapper, entityManager);
        this.brandRepository = brandRepository;
    }

    public PageResponse<?> getAllByNameAndStatus(Pageable pageable, String name, String status) {
        Integer statusValue = null;
        if (StringUtils.hasText(status)) statusValue = Status.valueOf(status.toUpperCase()).getValue();

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt", "id"));

        Page<Brand> materialPage = brandRepository.findByNameContainingAndStatus(pageRequest, name, statusValue);
        List<BrandResponse> materialResponseList = mapper.toEntityDtoList(materialPage.getContent());

        return PageResponse.builder()
                .pageNo(pageable.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalElements(materialPage.getTotalElements())
                .totalPages(materialPage.getTotalPages())
                .items(materialResponseList)
                .build();
    }
    @Override
    protected List<CreateBrandRequest> beforeCreateEntities(List<CreateBrandRequest> requests) {
        return requests;
    }

    @Override
    protected List<UpdateBrandRequest> beforeUpdateEntities(List<UpdateBrandRequest> requests) {
        List<UpdateBrandRequest> validRequests = requests.stream().filter(dto -> dto.getId() != null).toList();
        if (validRequests.isEmpty()) return Collections.emptyList();

        List<Integer> ids = validRequests.stream().map(UpdateBrandRequest::getId).toList();

        List<Integer> existingIds = brandRepository.findAllById(ids).stream().map(Brand::getId).toList();
        if (existingIds.isEmpty()) return Collections.emptyList();

        return validRequests.stream().filter(dto -> existingIds.contains(dto.getId())).toList();
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
        return "Brand";
    }
}
