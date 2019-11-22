package com.example.demo.security.utils;

import com.example.demo.security.dto.UserDto;
import com.example.demo.security.model.User;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
@DecoratedWith(UserMapperDecoder.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
        @Mapping(target = "passwordPlain", ignore = true),
        @Mapping(target = "authorities", ignore = true)

    })

    UserDto toDto(User user);

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "password", ignore = true),
        @Mapping(target = "activated", ignore = true),
        @Mapping(target = "authorities", ignore = true)

    })

    User toEntity(UserDto userDto);

}
