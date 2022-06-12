package com.ssg.task.api.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPromotionResponseDto that = (ItemPromotionResponseDto) o;
        return Objects.equals(itemName, that.itemName) && Objects.equals(promotionName, that.promotionName) && Objects.equals(price, that.price) && Objects.equals(discountPrice, that.discountPrice) && discountType == that.discountType && Objects.equals(discounting, that.discounting) && Objects.equals(promotionStart, that.promotionStart) && Objects.equals(promotionEnd, that.promotionEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemName, promotionName, price, discountPrice, discountType, discounting, promotionStart, promotionEnd);
    }
}
