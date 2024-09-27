package com.datn.beestyle.service.user.promotion;

import com.datn.beestyle.common.IGenericService;
import com.datn.beestyle.dto.promotion.CreatePromotionRequest;
import com.datn.beestyle.dto.promotion.PromotionResponse;
import com.datn.beestyle.dto.promotion.UpdatePromotionRequest;
import com.datn.beestyle.entity.Promotion;

public interface IPromotionService
        extends IGenericService<Promotion, Integer, CreatePromotionRequest, UpdatePromotionRequest, PromotionResponse> {
}
