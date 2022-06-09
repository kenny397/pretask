package com.ssg.task.api.service;

import com.ssg.task.api.dto.request.PromotionRequestDto;

public interface PromotionService {
    public String addPromotion(PromotionRequestDto promotionRequestDto);
    public void deletePromotion(String promotionName);

}
