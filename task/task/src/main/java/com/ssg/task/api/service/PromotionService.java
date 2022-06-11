package com.ssg.task.api.service;

import com.ssg.task.api.dto.request.PromotionItemsRequestDto;
import com.ssg.task.api.dto.request.PromotionRequestDto;
import com.ssg.task.api.dto.response.ItemPromotionResponseDto;

public interface PromotionService {
    public String addPromotion(PromotionRequestDto promotionRequestDto);
    public void deletePromotion(String promotionName);
    public void addItemsToPromotion(PromotionItemsRequestDto promotionItemsRequestDto);
    public ItemPromotionResponseDto findPromotion(String itemName);
}
