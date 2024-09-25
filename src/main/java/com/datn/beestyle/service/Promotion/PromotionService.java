package com.datn.beestyle.service.Promotion;

import com.datn.beestyle.common.GenericServiceAbstract;
import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.dto.promotion.CreatePromotionRequest;
import com.datn.beestyle.dto.promotion.PromotionResponse;
import com.datn.beestyle.dto.promotion.UpdatePromotionRequest;
import com.datn.beestyle.entity.Promotion;
import com.datn.beestyle.repository.PromotionRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class PromotionService
        extends GenericServiceAbstract<Promotion, Integer, CreatePromotionRequest, UpdatePromotionRequest, PromotionResponse>
        implements IPromotionService {

    private final PromotionRepository promotionRepository;

    public PromotionService(IGenericRepository<Promotion, Integer> entityRepository,
                            IGenericMapper<Promotion, CreatePromotionRequest, UpdatePromotionRequest, PromotionResponse> mapper,
                            EntityManager entityManager, PromotionRepository promotionRepository) {
        super(entityRepository, mapper, entityManager);
        this.promotionRepository = promotionRepository;
    }

    @Override
    protected List<CreatePromotionRequest> beforeCreateEntities(List<CreatePromotionRequest> requests) {
        return requests;
    }

    @Override
    protected List<UpdatePromotionRequest> beforeUpdateEntities(List<UpdatePromotionRequest> requests) {
        List<UpdatePromotionRequest> validRequests = requests.stream().filter(dto -> dto.getId() != null).toList();
        if (validRequests.isEmpty()) return Collections.emptyList();

        List<Integer> ids = validRequests.stream().map(UpdatePromotionRequest::getId).toList();

        List<Integer> existingIds = promotionRepository.findAllById(ids).stream().map(Promotion::getId).toList();
        if (existingIds.isEmpty()) return Collections.emptyList();

        return validRequests.stream().filter(dto -> existingIds.contains(dto.getId())).toList();
    }

    @Override
    protected void beforeCreate(CreatePromotionRequest request) {

    }

    @Override
    protected void beforeUpdate(UpdatePromotionRequest request) {

    }

    @Override
    protected void afterConvertCreateRequest(CreatePromotionRequest request, Promotion entity) {

    }

    @Override
    protected void afterConvertUpdateRequest(UpdatePromotionRequest request, Promotion entity) {

    }

    @Override
    protected String getEntityName() {
        return "Promotion";
    }
}
