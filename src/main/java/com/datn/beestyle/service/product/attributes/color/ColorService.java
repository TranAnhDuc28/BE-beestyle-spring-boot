package com.datn.beestyle.service.product.attributes.color;

import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.product.attributes.color.ColorResponse;
import com.datn.beestyle.dto.product.attributes.color.CreateColorRequest;
import com.datn.beestyle.dto.product.attributes.color.UpdateColorRequest;
import com.datn.beestyle.entity.product.attributes.Color;
import com.datn.beestyle.enums.Status;
import com.datn.beestyle.repository.ColorRepository;
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
public class ColorService
        extends GenericServiceAbstract<Color, Integer, CreateColorRequest, UpdateColorRequest, ColorResponse>
        implements IColorService {

    private final ColorRepository colorRepository;

    public ColorService(IGenericRepository<Color, Integer> entityRepository,
                        IGenericMapper<Color, CreateColorRequest, UpdateColorRequest, ColorResponse> mapper,
                        EntityManager entityManager, ColorRepository colorRepository) {
        super(entityRepository, mapper, entityManager);
        this.colorRepository = colorRepository;
    }

    public PageResponse<?> getAllByNameAndStatus(Pageable pageable, String name, String status) {
        Integer statusValue = null;
        if (StringUtils.hasText(status)) statusValue = Status.valueOf(status.toUpperCase()).getValue();

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt", "id"));

        Page<Color> materialPage = colorRepository.findByNameContainingAndStatus(pageRequest, name, statusValue);
        List<ColorResponse> materialResponseList = mapper.toEntityDtoList(materialPage.getContent());

        return PageResponse.builder()
                .pageNo(pageable.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalElements(materialPage.getTotalElements())
                .totalPages(materialPage.getTotalPages())
                .items(materialResponseList)
                .build();
    }
    @Override
    protected List<CreateColorRequest> beforeCreateEntities(List<CreateColorRequest> requests) {
        return requests;
    }

    @Override
    protected List<UpdateColorRequest> beforeUpdateEntities(List<UpdateColorRequest> requests) {
        List<UpdateColorRequest> validRequests = requests.stream().filter(dto -> dto.getId() != null).toList();
        if (validRequests.isEmpty()) return Collections.emptyList();

        List<Integer> ids = validRequests.stream().map(UpdateColorRequest::getId).toList();

        List<Integer> existingIds = colorRepository.findAllById(ids).stream().map(Color::getId).toList();
        if (existingIds.isEmpty()) return Collections.emptyList();

        return validRequests.stream().filter(dto -> existingIds.contains(dto.getId())).toList();
    }

    @Override
    protected void beforeCreate(CreateColorRequest request) {

    }

    @Override
    protected void beforeUpdate(UpdateColorRequest request) {

    }

    @Override
    protected void afterConvertCreateRequest(CreateColorRequest request, Color entity) {

    }

    @Override
    protected void afterConvertUpdateRequest(UpdateColorRequest request, Color entity) {

    }

    @Override
    protected String getEntityName() {
        return "Color";
    }
}
