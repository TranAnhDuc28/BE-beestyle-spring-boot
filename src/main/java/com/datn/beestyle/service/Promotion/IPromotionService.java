package com.datn.beestyle.service.Promotion;

import com.datn.beestyle.dto.PageResponse;
import com.datn.beestyle.dto.material.CreateMaterialRequest;
import com.datn.beestyle.dto.material.MaterialResponse;
import com.datn.beestyle.dto.promotion.CreatePromotionRequest;
import com.datn.beestyle.dto.promotion.PromotionResponse;
import com.datn.beestyle.entity.Promotion;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPromotionService {
    PageResponse<?> searchByName(Pageable pageable, String name);
    List<PromotionResponse> createPromotions(List<CreatePromotionRequest> requestList);
}
