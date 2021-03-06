package com.ssg.task.api.db.repository;

import com.ssg.task.api.db.entity.ItemPromotion;
import com.ssg.task.api.dto.PromotionSearchCondition;

import java.util.List;

public interface ItemPromotionRepositoryCustom {
    List<ItemPromotion> findPromotion(PromotionSearchCondition searchCondition);
}
