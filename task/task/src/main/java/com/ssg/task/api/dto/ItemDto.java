package com.ssg.task.api.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto {
    String itemName;
    Integer price;

    @QueryProjection
    public ItemDto(String itemName, Integer price) {
        this.itemName = itemName;
        this.price = price;
    }
}
