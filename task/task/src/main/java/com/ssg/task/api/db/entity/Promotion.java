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
public class Promotion extends BaseEntity {
    private String name;
    private Integer discountAmount;
    private Double discountRate;
    private String promotionStart;
    private String promotionEnd;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "promotion")
    private List<ItemPromotion> itemPromotionList=new ArrayList<>();

}
