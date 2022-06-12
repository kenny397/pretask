package com.ssg.task.api.db.repository;

import com.ssg.task.api.db.entity.Item;
import com.ssg.task.api.db.entity.Promotion;
import com.ssg.task.api.db.entity.Type;
import com.ssg.task.api.dto.PromotionSearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PromotionRepositoryTest {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    PromotionRepository promotionRepository;

    @Test
    public void 프로모션추가(){
        Promotion promotion = createPromotion("프로모션1","2022-04-01","2023-11-01",1000);

        Promotion savePromotion = promotionRepository.save(promotion);
        assertThat(savePromotion).isEqualTo(promotion);
    }
    @Test
    public void 프로모션삭제(){
        Promotion promotion = createPromotion("프로모션1","2022-04-01","2023-11-01",1000);

        Promotion savePromotion = promotionRepository.save(promotion);
        assertThat(savePromotion).isEqualTo(promotion);
        promotionRepository.delete(promotion);
        Optional<Promotion> findPromotion = promotionRepository.findByName(promotion.getName());
        assertThat(findPromotion.isEmpty()).isTrue();
    }



    public LocalDateTime strToDate(String input){
        String[]dates=input.split("-");
        LocalDateTime localDateTime =LocalDateTime.of(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]),Integer.parseInt(dates[2]),0,0);
        return localDateTime;
    }

    public Promotion createPromotion(String name,String startDate,String endDate, int amount){
        Promotion promotion = new Promotion();
        promotion.setName(name);
        promotion.setPromotionStart(strToDate(startDate));
        promotion.setPromotionEnd(strToDate(endDate));
        promotion.setDiscountAmount(amount);
        return promotion;
    }
}