package com.datn.beestyle.service.product.attributes.brand;

import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.dto.product.attributes.brand.BrandResponse;
import com.datn.beestyle.dto.product.attributes.brand.CreateBrandRequest;
import com.datn.beestyle.dto.product.attributes.brand.UpdateBrandRequest;
import com.datn.beestyle.entity.product.attributes.Brand;
import com.datn.beestyle.repository.BrandRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

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

    @Override
    public List<BrandResponse> getAllById(Set<Integer> integers) {
        return null;
    }
}
