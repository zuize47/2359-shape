package com.example.demo.security.utils;

import com.example.demo.security.dto.UserDto;
import com.example.demo.security.model.Authority;
import com.example.demo.security.model.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapperDecoder implements UserMapper {

    private final UserMapper delegate;

    public UserMapperDecoder(UserMapper delegate) {
        this.delegate = delegate;
    }

    @Override
    public UserDto toDto(User user) {
        UserDto dto = delegate.toDto(user);
        dto.setAuthorities(user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet()));
        return dto;
    }

    @Override
    public User toEntity(UserDto userDto) {
        User entity = delegate.toEntity(userDto);
        userDto.getAuthorities().forEach(e -> entity.getAuthorities().add(new Authority(e)));

        return entity;
    }

    @Override
    public void updateUserFromDto(UserDto userDto, User user) {
        user.getAuthorities().clear();
        userDto.getAuthorities().forEach(e -> user.getAuthorities().add(new Authority(e)));
    }
}
