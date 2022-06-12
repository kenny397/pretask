package com.ssg.task.api.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class PromotionItemsRequestDto {
    String promotion;
    String items;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PromotionItemsRequestDto that = (PromotionItemsRequestDto) o;
        return Objects.equals(promotion, that.promotion) && Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(promotion, items);
    }
}
