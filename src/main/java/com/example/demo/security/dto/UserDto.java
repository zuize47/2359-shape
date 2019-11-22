package com.example.demo.security.dto;

import com.example.demo.security.model.Authority;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class UserDto {

    @NotNull
    @Size(min = 4, max = 50)
    String username;
    @NotNull
    @Size(min = 5, max = 100)
    String passwordPlain;
    @NotNull
    @Size(min = 4, max = 50)
    String firstName;
    @NotNull
    @Size(min = 4, max = 50)
    String lastName;

    @NotNull
    @Size(min = 4, max = 50)
    String email;

    @NotNull
    Set<String> authorities;
}
