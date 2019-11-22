package com.example.demo.security.rest;

import com.example.demo.security.SecurityUtils;
import com.example.demo.security.dto.UserDto;
import com.example.demo.security.model.Authority;
import com.example.demo.security.model.User;
import com.example.demo.security.service.UserService;
import com.example.demo.security.utils.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    public UserRestController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public ResponseEntity<User> getActualUser() {
        return ResponseEntity.ok(SecurityUtils.getCurrentUsername().flatMap(userService::getUserWithAuthorities).get());
    }
    @PutMapping("/register")
    public ResponseEntity<UserDto> register (@Valid @RequestBody UserDto userDto) {

        if(userDto.getAuthorities().size() == 0){
            userDto.getAuthorities().add("ROLE_USER");
        }
        // just admin have permission to create another admin
        if(userDto.getAuthorities().contains("ROLE_ADMIN") && !SecurityUtils.hasRole("ROLE_ADMIN")){
            // throw exception 403
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }else {
            // update password
            User user = UserMapper.INSTANCE.toEntity(userDto);
            user.setPassword(passwordEncoder.encode(userDto.getPasswordPlain()));
            // save
            try {
                userService.save(user);
                return ResponseEntity.ok(UserMapper.INSTANCE.toDto(user));
            }catch (Exception ex){
                return new ResponseEntity("Duplicate login name", HttpStatus.BAD_REQUEST);
            }

        }

    }
}
