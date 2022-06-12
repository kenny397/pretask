package com.ssg.task.api.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class PromotionRequestDto {
    String name;
    Integer discountAmount;
    Double discountRate;
    String displayStart;
    String displayEnd;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PromotionRequestDto that = (PromotionRequestDto) o;
        return Objects.equals(name, that.name) && Objects.equals(discountAmount, that.discountAmount) && Objects.equals(discountRate, that.discountRate) && Objects.equals(displayStart, that.displayStart) && Objects.equals(displayEnd, that.displayEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, discountAmount, discountRate, displayStart, displayEnd);
    }
}
