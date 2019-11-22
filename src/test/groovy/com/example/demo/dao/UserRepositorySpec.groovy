package com.example.demo.shape.respository

import com.example.demo.security.model.Authority
import com.example.demo.security.model.User
import com.example.demo.security.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
class UserRepositorySpec extends Specification {

    @Autowired
    private UserRepository userRepository


    def "find user by username" () {
        def user = new User()
        user.setUsername("user1")
        user.password = "user1"
        user.activated = true
        user.firstName = "user1"
        user.lastName = "user1"
        user.email = "user1"
        def auth = new Authority()
        auth.setName("ROLE_USER")
        user.authorities.add(auth)
        user.authorities.add( new Authority("ROLE_ADMIN"))
        def savedUserEntity  = userRepository.save(user)

        when: "load user entity"
            def userFromDb = userRepository.findOneWithAuthoritiesByUsername(savedUserEntity.username).get()

        then:"saved and retrieved entity by id must be equal"
            savedUserEntity == userFromDb
            savedUserEntity.getId() == userFromDb.id
            savedUserEntity.getFirstName() == userFromDb.firstName
            savedUserEntity.getLastName() == userFromDb.lastName
            savedUserEntity.getEmail() == userFromDb.email
            savedUserEntity.getPassword() == userFromDb.password
            savedUserEntity.isActivated() == userFromDb.activated
            savedUserEntity.getAuthorities() == userFromDb.authorities

    }

}
