package com.ssg.task.api.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class ItemRequestDto {
    String name;
    Long type;
    Integer price;
    String displayStart;
    String displayEnd;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemRequestDto that = (ItemRequestDto) o;
        return Objects.equals(name, that.name) && Objects.equals(type, that.type) && Objects.equals(price, that.price) && Objects.equals(displayStart, that.displayStart) && Objects.equals(displayEnd, that.displayEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, price, displayStart, displayEnd);
    }
}
