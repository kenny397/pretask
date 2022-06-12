package com.ssg.task.api.db.repository;

import com.ssg.task.api.db.entity.Item;
import com.ssg.task.api.db.entity.ItemPromotion;
import com.ssg.task.api.db.entity.Promotion;
import com.ssg.task.api.db.entity.Type;
import com.ssg.task.api.dto.PromotionSearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemPromotionRepositoryTest {

    @Autowired
    ItemPromotionRepository itemPromotionRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    PromotionRepository promotionRepository;

    @Test
    public void 프로모션에_아이템_추가(){
        Item item = createItem("아이템1",Type.NORMAL,20000,"2022-04-30","2023-01-01");
        Promotion promotion = createPromotion("프로모션1","2022-04-01","2023-11-01",1000);

        Item saveItem = itemRepository.save(item);
        Promotion savePromotion = promotionRepository.save(promotion);
        ItemPromotion itemPromotion = createItemPromotion(item,promotion);

        ItemPromotion saveItemPromotion = itemPromotionRepository.save(itemPromotion);
        assertThat(saveItemPromotion).isEqualTo(itemPromotion);
    }

    @Test
    public void 프로모션에_아이템_삭제(){
        Item item = createItem("아이템1",Type.NORMAL,20000,"2022-04-30","2023-01-01");
        Promotion promotion = createPromotion("프로모션1","2022-04-01","2023-11-01",1000);

        Item saveItem = itemRepository.save(item);
        Promotion savePromotion = promotionRepository.save(promotion);

        ItemPromotion itemPromotion = createItemPromotion(item,promotion);

        ItemPromotion saveItemPromotion = itemPromotionRepository.save(itemPromotion);
        assertThat(saveItemPromotion).isEqualTo(itemPromotion);
        itemPromotionRepository.delete(itemPromotion);
        Optional<ItemPromotion> findItemPromotion = itemPromotionRepository.findByItem(item);
        assertThat(findItemPromotion.isEmpty()).isTrue();
    }

    @Test
    public void 상품에_존재하는_프로모션조회(){

        Item item = createItem("아이템1",Type.NORMAL,20000,"2022-04-30","2023-01-01");
        Promotion promotion = createPromotion("프로모션1","2022-04-01","2023-11-01",1000);
        Promotion promotion2 = createPromotion("프로모션2","2022-04-01","2023-11-01",3000);

        Item saveItem = itemRepository.save(item);
        Promotion savePromotion = promotionRepository.save(promotion);
        Promotion savePromotion2 = promotionRepository.save(promotion2);

        List<ItemPromotion> itemPromotions = new ArrayList<>();
        ItemPromotion itemPromotion = createItemPromotion(saveItem,savePromotion);
        ItemPromotion itemPromotion2 = createItemPromotion(saveItem,savePromotion2);

        ItemPromotion saveItemPromotion = itemPromotionRepository.save(itemPromotion);
        ItemPromotion saveItemPromotion2 = itemPromotionRepository.save(itemPromotion2);
        itemPromotions.add(saveItemPromotion);
        itemPromotions.add(saveItemPromotion2);

        PromotionSearchCondition searchCondition = new PromotionSearchCondition();
        searchCondition.setItemName(saveItem.getName());
        List<ItemPromotion> findItemPromotions = itemPromotionRepository.findPromotion(searchCondition);

        assertThat(findItemPromotions.size()).isEqualTo(itemPromotions.size());
        assertThat(findItemPromotions).isEqualTo(itemPromotions);


    }

    public LocalDateTime strToDate(String input){
        String[]dates = input.split("-");
        LocalDateTime localDateTime = LocalDateTime.of(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]),Integer.parseInt(dates[2]),0,0);
        return localDateTime;
    }

    public Item createItem(String name,Type type,int price, String startDate,String endDate){
        Item item = new Item();
        item.setName(name);
        item.setType(type);
        item.setPrice(price);
        item.setDisplayStartDate(strToDate(startDate));
        item.setDisplayEndDate(strToDate(endDate));
        return item;
    }



    public Promotion createPromotion(String name,String startDate,String endDate, int amount){
        Promotion promotion = new Promotion();
        promotion.setName(name);
        promotion.setPromotionStart(strToDate(startDate));
        promotion.setPromotionEnd(strToDate(endDate));
        promotion.setDiscountAmount(amount);
        return promotion;
    }

    public ItemPromotion createItemPromotion(Item item, Promotion promotion){
        ItemPromotion itemPromotion = new ItemPromotion();
        itemPromotion.setItem(item);
        itemPromotion.setPromotion(promotion);
        return itemPromotion;
    }
}