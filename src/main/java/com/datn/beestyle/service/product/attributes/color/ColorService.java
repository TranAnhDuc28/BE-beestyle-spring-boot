package com.datn.beestyle.service.product.attributes.color;

import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.product.attributes.color.ColorResponse;
import com.datn.beestyle.dto.product.attributes.color.CreateColorRequest;
import com.datn.beestyle.dto.product.attributes.color.UpdateColorRequest;
import com.datn.beestyle.entity.product.attributes.Color;
import com.datn.beestyle.repository.ColorRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

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

    @Override
    public List<ColorResponse> getAllById(Set<Integer> integers) {
        return null;
    }
}
