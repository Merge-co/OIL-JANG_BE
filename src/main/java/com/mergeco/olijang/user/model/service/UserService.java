package com.mergeco.olijang.user.model.service;

import com.mergeco.olijang.user.entity.User;
import com.mergeco.olijang.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Optional<User> findUser(String id) {
        Optional<User> user = userRepository.findByUserId(id);

        return user;
    }
}
