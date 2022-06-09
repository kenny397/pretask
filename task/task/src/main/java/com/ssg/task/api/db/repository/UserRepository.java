package com.ssg.task.api.db.repository;

import com.ssg.task.api.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    User save(User user);
    void delete(User user);
    Optional<User> findByName(String name);
}
