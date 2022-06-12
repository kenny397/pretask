package com.ssg.task.api.service;

import com.ssg.task.api.db.entity.Type;
import com.ssg.task.api.db.entity.User;
import com.ssg.task.api.db.entity.UserStatus;
import com.ssg.task.api.db.repository.UserRepository;
import com.ssg.task.api.dto.request.UserRequestDto;
import com.ssg.task.api.exception.ExistException;
import com.ssg.task.api.exception.NameNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.awt.*;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void 유저_생성(){
        UserRequestDto userRequestDto=new UserRequestDto();
        userRequestDto.setName("서형준");
        userRequestDto.setType(0L);

        User user = new User();
        user.setName(userRequestDto.getName());
        user.setStatus(UserStatus.SIGN);
        user.setType(Type.NORMAL);

        given(userRepository.save(any())).willReturn(user);
        String newUserName= userService.signUp(userRequestDto);

        given(userRepository.findByName(userRequestDto.getName())).willReturn(Optional.ofNullable(user));
        User findUser = userRepository.findByName(newUserName).get();

        assertThat(user.getId()).isEqualTo(findUser.getId());
        assertThat(user.getName()).isEqualTo(findUser.getName());
        assertThat(user.getType()).isEqualTo(findUser.getType());
    }

    @Test
    public void 유저_생성_중복_회원_예외() {
        UserRequestDto userRequestDto=new UserRequestDto();
        userRequestDto.setName("서형준");
        userRequestDto.setType(0L);

        User user = new User();
        user.setName(userRequestDto.getName());
        user.setStatus(UserStatus.SIGN);
        user.setType(Type.NORMAL);

        given(userRepository.findByName(userRequestDto.getName())).willReturn(Optional.ofNullable(user));

        ExistException existException = assertThrows(ExistException.class, () -> userService.signUp(userRequestDto));
        assertThat(userRequestDto.getName()+"가 존재합니다.").isEqualTo(existException.getMessage());
    }

    @Test
    public void 유저_삭제(){
        String userName="서형준";

        User user = new User();
        user.setName(userName);
        user.setStatus(UserStatus.SIGN);
        user.setType(Type.NORMAL);

        given(userRepository.findByName(userName)).willReturn(Optional.ofNullable(user));
        Optional<User> deleteUserBefore = userRepository.findByName(userName);
        assertThat(deleteUserBefore.get()).isEqualTo(user);

        userService.deleteUser(userName);

        given(userRepository.findByName(userName)).willReturn(Optional.ofNullable(null));

        Optional<User> deleteUserAfter = userRepository.findByName(userName);
        assertThat(deleteUserAfter).isEqualTo(Optional.ofNullable(null));
    }

    @Test
    public void 유저_삭제_존재하지_않은_회원_예외() {
        String userName="서형준";

        User user = new User();
        user.setName(userName);
        user.setStatus(UserStatus.SIGN);
        user.setType(Type.NORMAL);

        given(userRepository.findByName(userName)).willReturn(Optional.ofNullable(null));

        NameNotFoundException nameNotFoundException = assertThrows(NameNotFoundException.class, () -> userService.deleteUser(userName));
        assertThat(userName+"가 존재하지 않습니다.").isEqualTo(nameNotFoundException.getMessage());
    }

    @Test
    public void 유저_탈퇴(){
        String userName="서형준";
        UserRequestDto userRequestDto=new UserRequestDto();
        userRequestDto.setName(userName);
        userRequestDto.setType(0L);

        User user = new User();
        user.setName(userName);
        user.setStatus(UserStatus.SIGN);
        user.setType(Type.NORMAL);

        given(userRepository.findByName(userName)).willReturn(Optional.ofNullable(user));

        userService.resign(userName);
        User userAfter=new User();
        userAfter.setName(user.getName());
        userAfter.setType(user.getType());
        userAfter.setStatus(UserStatus.RESIGN);

        assertThat(UserStatus.RESIGN).isEqualTo(userAfter.getStatus());


    }

    @Test
    public void 유저_탈퇴_존재하지_않은_회원_예외() {
        String userName="서형준";

        User user = new User();
        user.setName(userName);
        user.setStatus(UserStatus.SIGN);
        user.setType(Type.NORMAL);

        given(userRepository.findByName(userName)).willReturn(Optional.ofNullable(null));

        NameNotFoundException nameNotFoundException = assertThrows(NameNotFoundException.class, () -> userService.resign(userName));
        assertThat(userName+"가 존재하지 않습니다.").isEqualTo(nameNotFoundException.getMessage());
    }
}