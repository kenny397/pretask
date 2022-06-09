package com.ssg.task.api.service;


import com.ssg.task.api.db.entity.Type;
import com.ssg.task.api.db.entity.User;
import com.ssg.task.api.db.entity.UserStatus;
import com.ssg.task.api.db.repository.UserRepository;
import com.ssg.task.api.dto.request.UserRequestDto;
import com.ssg.task.api.exception.ExistException;
import com.ssg.task.api.exception.NameNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public String signUp(UserRequestDto userRequestDto) {
        Optional<User> userOptional = userRepository.findByName(userRequestDto.getName());
        if(userOptional.isPresent())throw new ExistException(userRequestDto.getName());
        User user = new User();
        user.setName(userRequestDto.getName());
        if(userRequestDto.getType()==1)user.setType(Type.EE);
        else user.setType(Type.NORMAL);
        user.setStatus(UserStatus.SIGN);
        User saveUser = userRepository.save(user);
        return saveUser.getName();
    }

    @Transactional
    @Override
    public void resign(String userName) {
        Optional<User> userOptional = userRepository.findByName(userName);
        userOptional.orElseThrow(() -> new NameNotFoundException(userName));
        User user = userOptional.get();
        user.setStatus(UserStatus.RESIGN);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUser(String userName) {
        Optional<User> userOptional = userRepository.findByName(userName);
        userOptional.orElseThrow(() -> new NameNotFoundException(userName));
        userRepository.delete(userOptional.get());
    }
}
