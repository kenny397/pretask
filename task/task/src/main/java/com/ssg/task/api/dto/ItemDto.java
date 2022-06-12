package com.ssg.task.api.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class ItemDto {
    String itemName;
    Integer price;

    @QueryProjection
    public ItemDto(String itemName, Integer price) {
        this.itemName = itemName;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDto itemDto = (ItemDto) o;
        return Objects.equals(itemName, itemDto.itemName) && Objects.equals(price, itemDto.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemName, price);
    }
}
