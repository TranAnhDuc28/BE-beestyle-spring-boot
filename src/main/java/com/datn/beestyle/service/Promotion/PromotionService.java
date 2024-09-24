package com.datn.beestyle.service.Promotion;

import com.datn.beestyle.common.IGenericMapper;
import com.datn.beestyle.common.IGenericRepository;
import com.datn.beestyle.common.IGenericServiceAbstract;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.promotion.CreatePromotionRequest;
import com.datn.beestyle.dto.promotion.PromotionResponse;
import com.datn.beestyle.dto.promotion.UpdatePromotionRequest;
import com.datn.beestyle.entity.Promotion;
import com.datn.beestyle.mapper.PromotionMapper;
import com.datn.beestyle.repository.PromotionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PromotionService
        extends IGenericServiceAbstract<Promotion, Integer, CreatePromotionRequest, UpdatePromotionRequest, PromotionResponse>
        implements IPromotionService {

    private final PromotionRepository promotionRepository;
    private final PromotionMapper promotionMapper;

    protected PromotionService(IGenericRepository<Promotion, Integer> entityRepository,
                               IGenericMapper<Promotion, CreatePromotionRequest, UpdatePromotionRequest, PromotionResponse> mapper,
                               PromotionRepository promotionRepository, PromotionMapper promotionMapper) {
        super(entityRepository, mapper);
        this.promotionRepository = promotionRepository;
        this.promotionMapper = promotionMapper;
    }

    @Override
    public PageResponse<?> searchByName(Pageable pageable, String name) {
        Page<Promotion> promotionPage = promotionRepository.findAllByNameContaining(pageable, name);
        List<PromotionResponse> promotionResponseList = promotionMapper.toEntityDtoList(promotionPage.getContent());
        return PageResponse.builder()
                .pageNo(pageable.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalElements(promotionPage.getTotalElements())
                .totalPages(promotionPage.getTotalPages())
                .items(promotionResponseList)
                .build();
    }

    @Override
    public List<PromotionResponse> createPromotions(List<CreatePromotionRequest> requestList) {
        List<Promotion> promotionList = promotionMapper.toCreateEntityList(requestList);
        return promotionMapper.toEntityDtoList(promotionRepository.saveAll(promotionList));
    }

    @Override
    protected void beforeCreate(CreatePromotionRequest request) {
        log.info("Validating before creating promotion: {}", request.getPromotionName());
        // Thực hiện logic cần thiết trước khi tạo khuyến mãi
    }

    @Override
    protected void beforeUpdate(UpdatePromotionRequest request) {
        log.info("Validating before updating promotion with ID: {}", request.getId());
        // Thực hiện logic trước khi cập nhật khuyến mãi
    }

    @Override
    protected void afterConvertCreateRequest(CreatePromotionRequest request, Promotion entity) {
        log.info("After converting CreatePromotionRequest to entity for promotion: {}", request.getPromotionName());
        // Thực hiện các thao tác sau khi chuyển đổi request thành entity
    }

    @Override
    protected void afterConvertUpdateRequest(UpdatePromotionRequest request, Promotion entity) {
        log.info("After converting UpdatePromotionRequest to entity for promotion ID: {}", request.getId());
        // Thực hiện các thao tác sau khi chuyển đổi request thành entity trong quá trình cập nhật
    }

    @Override
    protected String getEntityName() {
        return "Promotion";
    }
}
