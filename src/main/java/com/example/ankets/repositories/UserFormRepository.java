package com.example.ankets.repositories;

import com.example.ankets.model.UserForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFormRepository extends JpaRepository<UserForm, Long> {
    List<UserForm> findByUser(String login);
}
