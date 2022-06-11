package com.ssg.task.api.db.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssg.task.api.db.entity.Type;
import com.ssg.task.api.dto.ItemDto;
import com.ssg.task.api.dto.ItemSearchCondition;
import com.ssg.task.api.dto.QItemDto;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.ssg.task.api.db.entity.QItem.item;


public class ItemRepositoryImpl implements ItemRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ItemRepositoryImpl(EntityManager em){
        this.queryFactory=new JPAQueryFactory(em);
    }

    @Override
    public List<ItemDto> isPurchase(ItemSearchCondition itemSearchCondition) {
        return queryFactory
                .select(new QItemDto(item.name, item.price))
                .from(item)
                .where(typeEq(itemSearchCondition.getUserType()),vaildDate())
                .fetch();
    }

    private BooleanExpression typeEq(Type type){
        if(type == Type.EE){
            return item.type.eq(type).and(item.type.eq(Type.NORMAL));
        }
        return item.type.eq(type);
    }
    private BooleanExpression vaildDate(){
        return item.displayStartDate.before(LocalDateTime.now()).and(item.displayEndDate.after(LocalDateTime.now()));
    }
    
}
