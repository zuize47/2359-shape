package com.example.demo.shape.service

import com.example.demo.security.repository.UserRepository
import com.example.demo.security.model.User
import com.example.demo.security.service.UserService
import spock.lang.Specification

class UserServiceSpec extends Specification {

    static final long USER_ID =125L
    static final String USER_NAME = "username"
    UserService userService
    UserRepository userRepository = Mock()

    def setup() {
        userService = new UserService(userRepository)
    }

    def 'should return empty when finding user which does not exist'() {
        given:
            userRepository.findOneWithAuthoritiesByUsername(_) >> Optional.empty()
        when:
            Optional<User> foundUser = userService.getUserWithAuthorities(USER_NAME)
        then:
            foundUser.isPresent() == false
    }

    def 'should find user by username'() {
        given:
            User user = new User(id: USER_ID, username: USER_NAME, firstName: USER_NAME, lastName: USER_NAME,
                    email: "${USER_NAME}@${USER_NAME}")
            userRepository.findOneWithAuthoritiesByUsername(USER_NAME) >> Optional.of(user)
        when:
            def foundUser = userService.getUserWithAuthorities(USER_NAME).get()
        then:
            foundUser.id == user.id
            foundUser.username == user.username
            foundUser.firstName == user.firstName
            foundUser.lastName == user.lastName
            foundUser.email == user.email
    }

    def 'should saved ok'() {
        given:
        User user = new User(id: USER_ID, username: USER_NAME, firstName: USER_NAME, lastName: USER_NAME,
                email: "${USER_NAME}@${USER_NAME}")
            userRepository.save(user) >> user
        when:
            def foundUser = userService.save(user)
        then:
        foundUser.id == user.id
        foundUser.username == user.username
        foundUser.firstName == user.firstName
        foundUser.lastName == user.lastName
        foundUser.email == user.email
    }

}
