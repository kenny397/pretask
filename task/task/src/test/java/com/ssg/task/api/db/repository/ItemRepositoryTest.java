package com.ssg.task.api.db.repository;

import com.ssg.task.api.db.entity.Item;
import com.ssg.task.api.db.entity.Type;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 아이템추가(){
        Item item = new Item();
        item.setName("노브랜드 버터링");
        item.setType(Type.NORMAL);
        item.setPrice(20000);
        item.setDisplayStartDate("2022-01-01");
        item.setDisplayEndDate("2023-01-01");
        Item saveItem = itemRepository.save(item);
        assertThat(saveItem).isEqualTo(item);
    }

    @Test
    public void 아이템제거(){

    }

}