package com.ssg.task.api.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromotionRequestDto {
    String name;
    Integer discountAmount;
    Double discountRate;
    String displayStart;
    String displayEnd;
}
