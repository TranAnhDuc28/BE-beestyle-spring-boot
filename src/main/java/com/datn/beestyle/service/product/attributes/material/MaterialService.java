
package com.datn.beestyle.service.product.attributes.material;

import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.product.attributes.material.CreateMaterialRequest;
import com.datn.beestyle.dto.product.attributes.material.MaterialResponse;
import com.datn.beestyle.dto.product.attributes.material.UpdateMaterialRequest;
import com.datn.beestyle.entity.product.attributes.Material;
import com.datn.beestyle.enums.Status;
import com.datn.beestyle.repository.MaterialRepository;
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
public class MaterialService
        extends GenericServiceAbstract<Material, Integer, CreateMaterialRequest, UpdateMaterialRequest, MaterialResponse>
        implements IMaterialService {
    private final MaterialRepository materialRepository;

    public MaterialService(IGenericRepository<Material, Integer> entityRepository,
                           IGenericMapper<Material, CreateMaterialRequest, UpdateMaterialRequest, MaterialResponse> mapper,
                           EntityManager entityManager, MaterialRepository materialRepository) {
        super(entityRepository, mapper, entityManager);
        this.materialRepository = materialRepository;
    }

    public PageResponse<?> getAllByNameAndStatus(Pageable pageable, String name, String status) {
        Integer statusValue = null;
        if (StringUtils.hasText(status)) statusValue = Status.valueOf(status.toUpperCase()).getValue();

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt", "id"));

        Page<Material> materialPage = materialRepository.findByNameContainingAndStatus(pageRequest, name, statusValue);
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
    protected List<CreateMaterialRequest> beforeCreateEntities(List<CreateMaterialRequest> requests) {
        return requests;
    }

    @Override
    protected List<UpdateMaterialRequest> beforeUpdateEntities(List<UpdateMaterialRequest> requests) {
        List<UpdateMaterialRequest> validRequests = requests.stream().filter(dto -> dto.getId() != null).toList();
        if (validRequests.isEmpty()) return Collections.emptyList();

        List<Integer> ids = validRequests.stream().map(UpdateMaterialRequest::getId).toList();

        List<Integer> existingIds = materialRepository.findAllById(ids).stream().map(Material::getId).toList();
        if (existingIds.isEmpty()) return Collections.emptyList();

        return validRequests.stream().filter(dto -> existingIds.contains(dto.getId())).toList();
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
