package com.ssg.task.api.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Item extends BaseEntity {
    private String name;
    private String type;
    private Integer price;
    private String displayStartDate;
    private String displayEndDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "item")
    private List<ItemPromotion> itemPromotionList=new ArrayList<>();
}
