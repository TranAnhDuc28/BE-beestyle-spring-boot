package com.datn.beestyle.service.product.attributes.material;

import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.product.attributes.material.CreateBrandRequest;
import com.datn.beestyle.dto.product.attributes.material.MaterialResponse;
import com.datn.beestyle.dto.product.attributes.material.UpdateBrandRequest;
import com.datn.beestyle.entity.product.attributes.Material;
import com.datn.beestyle.repository.MaterialRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class MaterialService
        extends GenericServiceAbstract<Material, Integer, CreateBrandRequest, UpdateBrandRequest, MaterialResponse>
        implements IMaterialService {
    private final MaterialRepository materialRepository;

    public MaterialService(IGenericRepository<Material, Integer> entityRepository,
                           IGenericMapper<Material, CreateBrandRequest, UpdateBrandRequest, MaterialResponse> mapper,
                           EntityManager entityManager, MaterialRepository materialRepository) {
        super(entityRepository, mapper, entityManager);
        this.materialRepository = materialRepository;
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

        List<Integer> existingIds = materialRepository.findAllById(ids).stream().map(Material::getId).toList();
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
    protected void afterConvertCreateRequest(CreateBrandRequest request, Material entity) {

    }

    @Override
    protected void afterConvertUpdateRequest(UpdateBrandRequest request, Material entity) {

    }

    @Override
    protected String getEntityName() {
        return "Material";
    }


}
