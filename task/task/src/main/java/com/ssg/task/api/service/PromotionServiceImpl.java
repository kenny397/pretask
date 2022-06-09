package com.ssg.task.api.service;

import com.ssg.task.api.db.entity.Promotion;
import com.ssg.task.api.db.entity.User;
import com.ssg.task.api.db.repository.PromotionRepository;
import com.ssg.task.api.dto.request.PromotionRequestDto;
import com.ssg.task.api.exception.ExistException;
import com.ssg.task.api.exception.NameNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService{

    private final PromotionRepository promotionRepository;

    @Transactional
    @Override
    public String addPromotion(PromotionRequestDto promotionRequestDto) {
        Optional<Promotion> promotionOptional = promotionRepository.findByName(promotionRequestDto.getName());
        if(promotionOptional.isPresent())throw new ExistException(promotionRequestDto.getName());
        Promotion promotion =new Promotion();
        promotion.setName(promotionRequestDto.getName());
        promotion.setDiscountAmount(promotionRequestDto.getDiscountAmount());
        promotion.setDiscountRate(promotionRequestDto.getDiscountRate());
        promotion.setPromotionStart(promotionRequestDto.getDisplayStart());
        promotion.setPromotionEnd(promotionRequestDto.getDisplayEnd());
        Promotion savePromotion = promotionRepository.save(promotion);
        return savePromotion.getName();
    }

    @Transactional
    @Override
    public void deletePromotion(String promotionName) {
        Optional<Promotion> optionalPromotion = promotionRepository.findByName(promotionName);
        optionalPromotion.orElseThrow(()->new NameNotFoundException(promotionName));
        promotionRepository.delete(optionalPromotion.get());
    }
}
