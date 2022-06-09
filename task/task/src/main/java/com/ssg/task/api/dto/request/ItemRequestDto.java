package com.ssg.task.api.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRequestDto {
    String name;
    Long type;
    Integer price;
    String displayStart;
    String displayEnd;
}
