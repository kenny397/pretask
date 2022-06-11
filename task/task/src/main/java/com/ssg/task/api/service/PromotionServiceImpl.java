package com.ssg.task.api.service;

import com.ssg.task.api.db.entity.Item;
import com.ssg.task.api.db.entity.ItemPromotion;
import com.ssg.task.api.db.entity.Promotion;
import com.ssg.task.api.db.repository.ItemRepository;
import com.ssg.task.api.db.repository.ItemPromotionRepository;
import com.ssg.task.api.db.repository.PromotionRepository;
import com.ssg.task.api.dto.PromotionSearchCondition;
import com.ssg.task.api.dto.request.PromotionItemsRequestDto;
import com.ssg.task.api.dto.request.PromotionRequestDto;
import com.ssg.task.api.dto.response.DiscountType;
import com.ssg.task.api.dto.response.ItemPromotionResponseDto;
import com.ssg.task.api.exception.ExistException;
import com.ssg.task.api.exception.NameNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService{

    private final PromotionRepository promotionRepository;
    private final ItemRepository itemRepository;
    private final ItemPromotionRepository itemPromotionRepository;
    @Transactional
    @Override
    public String addPromotion(PromotionRequestDto promotionRequestDto) {
        Optional<Promotion> promotionOptional = promotionRepository.findByName(promotionRequestDto.getName());
        if(promotionOptional.isPresent())throw new ExistException(promotionRequestDto.getName());
        Promotion promotion =new Promotion();
        promotion.setName(promotionRequestDto.getName());
        promotion.setDiscountAmount(promotionRequestDto.getDiscountAmount());
        promotion.setDiscountRate(promotionRequestDto.getDiscountRate());
        promotion.setPromotionStart(strToDate(promotionRequestDto.getDisplayStart()));
        promotion.setPromotionEnd(strToDate(promotionRequestDto.getDisplayEnd()));
        Promotion savePromotion = promotionRepository.save(promotion);
        return savePromotion.getName();
    }

    @Transactional
    @Override
    public void deletePromotion(String promotionName) {
        Optional<Promotion> promotionOptional = promotionRepository.findByName(promotionName);
        promotionOptional.orElseThrow(()->new NameNotFoundException(promotionName));
        promotionRepository.delete(promotionOptional.get());
    }

    @Transactional
    @Override
    public void addItemsToPromotion(PromotionItemsRequestDto promotionItemsRequestDto) {
        Optional<Promotion> promotionOptional = promotionRepository.findByName(promotionItemsRequestDto.getPromotion());
        Optional<Item> itemOptional = itemRepository.findByName(promotionItemsRequestDto.getItems());
        promotionOptional.orElseThrow(()->new NameNotFoundException(promotionItemsRequestDto.getPromotion()));
        itemOptional.orElseThrow(()->new NameNotFoundException(promotionItemsRequestDto.getItems()));
        ItemPromotion itemPromotion = new ItemPromotion();
        itemPromotion.setPromotion(promotionOptional.get());
        itemPromotion.setItem(itemOptional.get());
        itemPromotionRepository.save(itemPromotion);
    }

    @Override
    public ItemPromotionResponseDto findPromotion(String itemName) {
        Optional<Item> itemOptional = itemRepository.findByName(itemName);
        itemOptional.orElseThrow(()->new NameNotFoundException(itemName));
        Item item = itemOptional.get();
        PromotionSearchCondition searchCondition=new PromotionSearchCondition();
        searchCondition.setItemName(itemName);
        List<ItemPromotion> findItemPromotions = promotionRepository.findPromotion(searchCondition);

        ItemPromotionResponseDto itemPromotionResponseDto = new ItemPromotionResponseDto();
        itemPromotionResponseDto.setItemName(item.getName());
        itemPromotionResponseDto.setPrice(item.getPrice());
        itemPromotionResponseDto.setDiscountPrice(item.getPrice());
        for(ItemPromotion itemPromotion : findItemPromotions){
            Integer discountPrice=item.getPrice();
            Promotion promotion=itemPromotion.getPromotion();
            DiscountType discountType=DiscountType.RATE;
            String discounting="";
            if(itemPromotion.getPromotion().getDiscountAmount()==null){
                discounting=""+promotion.getDiscountRate();
                discountPrice=(int)(discountPrice*promotion.getDiscountRate());
            }else{
                discounting=""+promotion.getDiscountAmount();
                discountType=DiscountType.AMOUNT;
                discountPrice=(discountPrice-promotion.getDiscountAmount());
            }

            if(discountPrice>0&&itemPromotionResponseDto.getDiscountPrice()>discountPrice){
                itemPromotionResponseDto.setPromotionName(promotion.getName());
                itemPromotionResponseDto.setDiscountType(discountType);
                itemPromotionResponseDto.setDiscountPrice(discountPrice);
                itemPromotionResponseDto.setDiscounting(discounting);
                itemPromotionResponseDto.setPromotionStart(promotion.getPromotionStart());
                itemPromotionResponseDto.setPromotionEnd(promotion.getPromotionEnd());
            }
        }

        return itemPromotionResponseDto;
    }

    public LocalDateTime strToDate(String input){
        String[]dates=input.split("-");
        LocalDateTime localDateTime =LocalDateTime.of(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]),Integer.parseInt(dates[2]),0,0);
        return localDateTime;
    }
}
