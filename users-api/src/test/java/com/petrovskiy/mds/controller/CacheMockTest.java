package com.petrovskiy.mds.controller;

import com.petrovskiy.mds.UserApiApplication;
import com.petrovskiy.mds.dao.UserDao;
import com.petrovskiy.mds.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserApiApplication.class)
public class CacheMockTest {


    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private UserDao repository;
    private User user;


    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(UUID.fromString("3087eee4-7d02-4e7d-b90f-1d21ba44ba8e"))
                .companyId(5L)
                .created(LocalDateTime.now())
                .email("test@gmail.com")
                .username("hello")
                .name("test")
                .build();
        repository.save(user);
        repository.save(user);
    }

    private Optional<User> getCachedUsers(String name) {
        return ofNullable(cacheManager.getCache("users")).map(c -> c.get(name, User.class));
    }

    @Test
    void givenUserThatShouldBeCached_whenFindByEmail_thenResultShouldBePutInCache() {
        Optional<User> user = repository.findByEmail("test@gmail.com");
        assertEquals(user, getCachedUsers("test"));
    }

    @Test
    void givenUsersThatShouldNotBeCached_whenFindByID_thenResultShouldNotBePutInCache() {
        repository.findById(UUID.fromString("3087eee4-7d02-4e7d-b90f-1d21ba44ba8e"));
        assertEquals(empty(), getCachedUsers("Masloria"));
    }


}
