package com.ssg.task.api.db.repository;

import com.ssg.task.api.db.entity.Item;
import com.ssg.task.api.db.entity.Type;
import com.ssg.task.api.db.entity.User;
import com.ssg.task.api.db.entity.UserStatus;
import com.ssg.task.api.dto.ItemDto;
import com.ssg.task.api.dto.ItemSearchCondition;
import org.assertj.core.api.Assertions;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@Transactional
class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void 아이템추가(){
        Item item = createItem("노브랜드 버터링",Type.NORMAL,20000,"2022-04-30","2023-01-01");
        Item saveItem = itemRepository.save(item);
        assertThat(saveItem).isEqualTo(item);
    }

    @Test
    public void 아이템삭제(){
        Item item = createItem("노브랜드 버터링",Type.NORMAL,20000,"2022-04-30","2023-01-01");
        Item saveItem = itemRepository.save(item);
        assertThat(saveItem).isEqualTo(item);
        itemRepository.delete(item);
        Optional<Item> findItem = itemRepository.findByName(item.getName());
        assertThat(findItem.isEmpty()).isTrue();
    }
    @Test
    public void 사용자가_구매가능한_상품조회(){
        User user = createUser("서형준",UserStatus.SIGN,Type.NORMAL);

        userRepository.save(user);

        Item item = createItem("아이템1",Type.NORMAL,20000,"2022-04-30","2023-01-01");
        itemRepository.save(item);

        Item item2 = createItem("아이템2",Type.NORMAL,20000,"2022-04-30","2023-01-01");
        itemRepository.save(item2);

        ItemDto itemDto = new ItemDto(item.getName(),item.getPrice());
        ItemDto itemDto2 = new ItemDto(item2.getName(),item2.getPrice());

        List<ItemDto>itemList = new ArrayList<>();
        itemList.add(itemDto);
        itemList.add(itemDto2);
        ItemSearchCondition searchCondition = new ItemSearchCondition();
        searchCondition.setUserName(user.getName());
        searchCondition.setUserType(user.getType());
        List<ItemDto> purchase = itemRepository.findPurchaseItem(searchCondition);
        assertThat(itemList).isEqualTo(purchase);

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

    public User createUser(String name, UserStatus userStatus, Type type){
        User user = new User();
        user.setName(name);
        user.setStatus(userStatus);
        user.setType(type);
        return user;
    }

}