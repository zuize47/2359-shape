package com.example.demo.security.rest;

import com.example.demo.security.SecurityUtils;
import com.example.demo.security.dto.UserDto;
import com.example.demo.security.model.User;
import com.example.demo.security.service.UserService;
import com.example.demo.security.utils.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<UserDto> getActualUser() {
        return SecurityUtils.getCurrentUsername().flatMap(userService::getUserWithAuthorities).map(e ->{
            return ResponseEntity.ok(UserMapper.INSTANCE.toDto(e));
        }).orElse(ResponseEntity.notFound().build());

    }
    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUser() {
        return ResponseEntity.ok(this.userService.allUser().stream().map(UserMapper.INSTANCE::toDto).collect(Collectors.toList()));
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

            User userSave = this.userService.getUserWithAuthorities(userDto.getUsername()).map(e ->{
                // Update current user
                User user = e;
                UserMapper.INSTANCE.updateUserFromDto(userDto, user);
                if(!StringUtils.isEmpty(userDto.getPassword()) ){
                    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                }
                return user;
            }).orElseGet( () ->{
                if(StringUtils.isEmpty(userDto.getPassword())){
                    return null;
                }

                // update password
                User user = UserMapper.INSTANCE.toEntity(userDto);

                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                user.setActivated(true);
                return user;


            });
            // save
            try {
                if(userSave == null)
                    throw new Exception();
                userService.save(userSave);
                return ResponseEntity.ok(UserMapper.INSTANCE.toDto(userSave));
            }catch (Exception ex){
                return new ResponseEntity("Error when save user", HttpStatus.BAD_REQUEST);
            }


        }

    }

    @GetMapping("/{userName}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> getUser(@PathVariable("userName") String username) {
        return this.userService.getUserWithAuthorities(username).map(e ->{
            return ResponseEntity.ok(UserMapper.INSTANCE.toDto(e));
        }).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{userName}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<Void> deleteUser(@PathVariable("userName") String username){
        if(this.userService.deleteUser(username)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }


}
