package com.ssg.task.api.db.repository;

import com.ssg.task.api.db.entity.Item;
import com.ssg.task.api.db.entity.Type;
import com.ssg.task.api.db.entity.User;
import com.ssg.task.api.db.entity.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void 유저추가(){
        User user = createUser("서형준",UserStatus.SIGN,Type.NORMAL);
        User saveUser = userRepository.save(user);
        assertThat(saveUser).isEqualTo(user);
    }

    @Test
    public void 유저제거(){
        User user = createUser("서형준",UserStatus.SIGN,Type.NORMAL);
        User saveUser = userRepository.save(user);
        assertThat(saveUser).isEqualTo(user);
        userRepository.delete(user);
        Optional<User> findUser = userRepository.findByName(user.getName());
        assertThat(findUser.isEmpty()).isTrue();
    }

    public User createUser(String name, UserStatus userStatus, Type type){
        User user = new User();
        user.setName(name);
        user.setStatus(userStatus);
        user.setType(type);
        return user;
    }
}