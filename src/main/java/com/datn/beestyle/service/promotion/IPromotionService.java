package com.datn.beestyle.service.promotion;

import com.datn.beestyle.common.IGenericService;
import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.promotion.CreatePromotionRequest;
import com.datn.beestyle.dto.promotion.PromotionResponse;
import com.datn.beestyle.dto.promotion.UpdatePromotionRequest;
import com.datn.beestyle.entity.Promotion;
import org.springframework.data.domain.Pageable;

public interface IPromotionService
        extends IGenericService<Promotion, Integer, CreatePromotionRequest, UpdatePromotionRequest, PromotionResponse> {
    PageResponse<?> getAllByNameAndStatus(Pageable pageable, String name, String status, String discountType);
//    PageResponse<?> getAllByNameAndStatus(Pageable pageable, String name, String status);

}
