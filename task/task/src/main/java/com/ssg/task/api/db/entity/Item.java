package com.ssg.task.api.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Item extends BaseEntity {
    private String name;
    @Enumerated(EnumType.STRING)
    private Type type;
    private Integer price;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime displayStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime displayEndDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "item")
    private List<ItemPromotion> itemPromotionList=new ArrayList<>();
}
