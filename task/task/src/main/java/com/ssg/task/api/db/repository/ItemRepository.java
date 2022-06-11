package com.ssg.task.api.db.repository;

import com.ssg.task.api.db.entity.Item;
import com.ssg.task.api.db.entity.User;
import com.ssg.task.api.dto.ItemSearchCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item,Long> ,ItemRepositoryCustom {
    Item save(Item item);
    void delete(Item item);
    Optional<Item> findByName(String name);


}
