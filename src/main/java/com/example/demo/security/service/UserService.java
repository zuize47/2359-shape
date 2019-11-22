package com.example.demo.security.service;

import com.example.demo.security.SecurityUtils;
import com.example.demo.security.model.User;
import com.example.demo.security.repository.UserRepository;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String username) {
        return userRepository.findOneWithAuthoritiesByUsername(username);
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public boolean deleteUser(String username) {
        // can not delete yourself
        if(SecurityUtils.getCurrentUsername().equals(username))
            return false;
        return this.userRepository.findOneWithAuthoritiesByUsername(username).map(e -> {
            this.userRepository.deleteById(e.getId());
            return true;
        }).orElse(false);
    }

    public List<User> allUser() {
        return this.userRepository.findAll();
    }
}
