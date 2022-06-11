package com.ssg.task.api.db.repository;


import com.ssg.task.api.db.entity.Item;
import com.ssg.task.api.db.entity.ItemPromotion;
import com.ssg.task.api.db.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemPromotionRepository extends JpaRepository<ItemPromotion,Long>{
    ItemPromotion save(ItemPromotion itemPromotion);
    void delete(ItemPromotion itemPromotion);
    Optional<ItemPromotion> findByPromotion(Promotion promotion);
    Optional<ItemPromotion> findByItem(Item item);
}
