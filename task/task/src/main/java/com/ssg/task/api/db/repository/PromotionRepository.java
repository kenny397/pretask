package com.ssg.task.api.db.repository;

import com.ssg.task.api.db.entity.Promotion;
import com.ssg.task.api.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromotionRepository extends JpaRepository<Promotion,Long> {
    Promotion save(Promotion promotion);
    void delete(Promotion promotion);
    Optional<Promotion> findByName(String name);
}
