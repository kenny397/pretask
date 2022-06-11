package com.ssg.task.api.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemPromotionResponseDto {
    String itemName;
    String promotionName;
    Integer price;
    Integer discountPrice;
    DiscountType discountType;
    String discounting;
    LocalDateTime promotionStart;
    LocalDateTime promotionEnd;

}
