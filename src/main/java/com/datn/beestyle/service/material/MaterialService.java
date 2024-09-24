package com.datn.beestyle.service.material;

import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.common.IGenericServiceAbstract;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.material.CreateMaterialRequest;
import com.datn.beestyle.dto.material.MaterialResponse;
import com.datn.beestyle.dto.material.UpdateMaterialRequest;
import com.datn.beestyle.entity.product.properties.Material;
import com.datn.beestyle.repository.MaterialRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class MaterialService
        extends IGenericServiceAbstract<Material, Integer, CreateMaterialRequest, UpdateMaterialRequest, MaterialResponse>
        implements IMaterialService {

    private final MaterialRepository materialRepository;

    public MaterialService(IGenericRepository<Material, Integer> entityRepository,
                           IGenericMapper<Material, CreateMaterialRequest, UpdateMaterialRequest, MaterialResponse> mapper,
                           EntityManager entityManager, MaterialRepository materialRepository) {
        super(entityRepository, mapper, entityManager);
        this.materialRepository = materialRepository;
    }


    @Override
    public PageResponse<?> searchByName(Pageable pageable, String name, boolean deleted) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Material> materialPage = materialRepository.findByNameContainingAndDeleted(pageRequest, name, deleted);
        List<MaterialResponse> materialResponseList = mapper.toEntityDtoList(materialPage.getContent());
        return PageResponse.builder()
                .pageNo(pageable.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalElements(materialPage.getTotalElements())
                .totalPages(materialPage.getTotalPages())
                .items(materialResponseList)
                .build();
    }

    @Override
    public List<MaterialResponse> createMaterials(List<CreateMaterialRequest> requestList) {
        List<Material> materialList = mapper.toCreateEntityList(requestList);
        return mapper.toEntityDtoList(materialRepository.saveAll(materialList));
    }

    @Override
    public List<MaterialResponse> updateMaterials(List<UpdateMaterialRequest> requestList) {
        if (requestList.isEmpty()) return Collections.emptyList();

        List<UpdateMaterialRequest> validRequests = requestList.stream()
                        .filter(dto -> dto.getId() != null || StringUtils.hasText(dto.getMaterialName()))
                        .toList();
        if (validRequests.isEmpty()) return Collections.emptyList();

        List<Integer> ids = validRequests.stream().map(UpdateMaterialRequest::getId).toList();

        List<Integer> existingIds = materialRepository.findAllById(ids).stream()
                .map(Material::getId)
                .toList();
        if (existingIds.isEmpty()) return Collections.emptyList();

        List<Material> materialsToUpdate = mapper.toUpdateEntityList(validRequests.stream()
                .filter(dto -> existingIds.contains(dto.getId()))
                .toList());

        return mapper.toEntityDtoList(materialRepository.saveAll(materialsToUpdate));
    }

    @Override
    protected void beforeCreate(CreateMaterialRequest request) {

    }

    @Override
    protected void beforeUpdate(UpdateMaterialRequest request) {

    }

    @Override
    protected void afterConvertCreateRequest(CreateMaterialRequest request, Material entity) {

    }

    @Override
    protected void afterConvertUpdateRequest(UpdateMaterialRequest request, Material entity) {

    }

    @Override
    protected String getEntityName() {
        return "Material";
    }


}
