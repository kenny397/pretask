package com.ssg.task.api.db.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssg.task.api.db.entity.*;
import com.ssg.task.api.dto.PromotionSearchCondition;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import static com.ssg.task.api.db.entity.QItem.item;
import static com.ssg.task.api.db.entity.QItemPromotion.itemPromotion;
import static com.ssg.task.api.db.entity.QPromotion.promotion;

public class ItemPromotionRepositoryImpl implements ItemPromotionRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public ItemPromotionRepositoryImpl(EntityManager em){
        this.queryFactory=new JPAQueryFactory(em);
    }


    @Override
    public  List<ItemPromotion> findPromotion(PromotionSearchCondition searchCondition) {

        List<ItemPromotion> fetch= queryFactory.selectFrom(itemPromotion)
                .join(itemPromotion.item, item).fetchJoin()
                .join(itemPromotion.promotion, promotion).fetchJoin()
                .where(item.name.eq(searchCondition.getItemName()),vaildItemDate(),vaildPromotionDate())
                .fetch();


        return fetch;
    }

    private BooleanExpression vaildItemDate(){
        return item.displayStartDate.before(LocalDateTime.now()).and(item.displayEndDate.after(LocalDateTime.now()));
    }
    private BooleanExpression vaildPromotionDate(){
        return promotion.promotionStart.before(LocalDateTime.now()).and(promotion.promotionEnd.after(LocalDateTime.now()));
    }
}
