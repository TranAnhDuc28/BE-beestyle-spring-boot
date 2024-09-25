package com.datn.beestyle.service.Promotion;

import com.datn.beestyle.common.IGenericService;
import com.datn.beestyle.dto.PageResponse;

import com.datn.beestyle.dto.promotion.CreatePromotionRequest;
import com.datn.beestyle.dto.promotion.PromotionResponse;
import com.datn.beestyle.dto.promotion.UpdatePromotionRequest;
import com.datn.beestyle.entity.Promotion;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPromotionService
        extends IGenericService<Promotion, Integer, CreatePromotionRequest, UpdatePromotionRequest, PromotionResponse> {

}
