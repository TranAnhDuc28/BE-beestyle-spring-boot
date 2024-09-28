package com.datn.beestyle.service.product.attributes.size;

import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.product.attributes.size.CreateSizeRequest;
import com.datn.beestyle.dto.product.attributes.size.SizeResponse;
import com.datn.beestyle.dto.product.attributes.size.UpdateSizeRequest;
import com.datn.beestyle.entity.product.attributes.Size;
import com.datn.beestyle.repository.SizeRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SizeService
        extends GenericServiceAbstract<Size, Integer, CreateSizeRequest, UpdateSizeRequest, SizeResponse>
        implements ISizeService {
    private final SizeRepository sizeRepository;

    public SizeService(IGenericRepository<Size, Integer> entityRepository,
                       IGenericMapper<Size, CreateSizeRequest, UpdateSizeRequest, SizeResponse> mapper,
                       EntityManager entityManager, SizeRepository sizeRepository) {
        super(entityRepository, mapper, entityManager);
        this.sizeRepository = sizeRepository;
    }

    public PageResponse<?> getAllByNameAndDeleted(Pageable pageable, String name, Short status) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt", "id"));

        Page<Size> materialPage = sizeRepository.findByNameContainingAndStatus(pageRequest, name, status);
        List<SizeResponse> materialResponseList = mapper.toEntityDtoList(materialPage.getContent());
        return PageResponse.builder()
                .pageNo(pageable.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalElements(materialPage.getTotalElements())
                .totalPages(materialPage.getTotalPages())
                .items(materialResponseList)
                .build();
    }

    @Override
    protected List<CreateSizeRequest> beforeCreateEntities(List<CreateSizeRequest> requests) {
        return requests;
    }

    @Override
    protected List<UpdateSizeRequest> beforeUpdateEntities(List<UpdateSizeRequest> requests) {
        List<UpdateSizeRequest> validRequests = requests.stream().filter(dto -> dto.getId() != null).toList();
        if (validRequests.isEmpty()) return Collections.emptyList();

        List<Integer> ids = validRequests.stream().map(UpdateSizeRequest::getId).toList();

        List<Integer> existingIds = sizeRepository.findAllById(ids).stream().map(Size::getId).toList();
        if (existingIds.isEmpty()) return Collections.emptyList();

        return validRequests.stream().filter(dto -> existingIds.contains(dto.getId())).toList();
    }

    @Override
    protected void beforeCreate(CreateSizeRequest request) {

    }

    @Override
    protected void beforeUpdate(UpdateSizeRequest request) {

    }

    @Override
    protected void afterConvertCreateRequest(CreateSizeRequest request, Size entity) {

    }

    @Override
    protected void afterConvertUpdateRequest(UpdateSizeRequest request, Size entity) {

    }

    @Override
    protected String getEntityName() {
        return "Size";
    }
}
