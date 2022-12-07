package com.petrovskiy.mds.controller;

import com.petrovskiy.mds.dao.UserDao;
import com.petrovskiy.mds.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.AopTestUtils;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ContextConfiguration
@ExtendWith(SpringExtension.class)
public class UserCacheTest {


    private UserDao mock;
    @Autowired
    private UserDao userDao;

    User user = User.builder()
            .id(UUID.fromString("3087eee4-7d02-4e7d-b90f-1d21ba44ba8e"))
            .companyId(5L)
            .created(LocalDateTime.now())
            .email("test@gmail.com")
            .username("hello")
            .name("test")
            .build();
    User user2 = User.builder()
            .id(UUID.fromString("3087eee4-7d02-4e7d-b90f-1d21ba44ba8a"))
            .companyId(5L)
            .created(LocalDateTime.now())
            .email("testo@gmail.com")
            .username("helloo")
            .name("testo")
            .build();
    String UUID1="3087eee4-7d02-4e7d-b90f-1d21ba44ba8e";
    String UUID2="3087eee4-7d02-4e7d-b90f-1d21ba44ba8a";


    @EnableCaching
    @Configuration
    public static class CachingTestConfig {

        @Bean
        public UserDao userRepositoryMockImplementation() {
            return Mockito.mock(UserDao.class);
        }

        @Bean
        public CacheManager cacheManager() {
            return new ConcurrentMapCacheManager("users");
        }
    }

    @BeforeEach
    void setUp() {
        mock = AopTestUtils.getTargetObject(userDao);
        reset(mock);
        when(mock.findById(eq(UUID.fromString(UUID1))))
                .thenReturn(of(user));
        when(mock.findById(eq(UUID.fromString(UUID2))))
                .thenReturn(of(user2))
                .thenThrow(new RuntimeException("User should be cached!"));
    }

    @Test
    void givenCachedUser_whenFindByID_thenRepositoryShouldNotBeHit() {

        assertEquals(of(user2), userDao.findById(UUID.fromString(UUID2)));
        verify(mock).findById(UUID.fromString(UUID2));

        assertEquals(of(user2), userDao.findById(UUID.fromString(UUID2)));
        assertEquals(of(user2), userDao.findById(UUID.fromString(UUID2)));

        verifyNoMoreInteractions(mock);
    }

    @Test
    void givenNotCachedUser_whenFindByID_thenRepositoryShouldBeHit() {
        assertEquals(of(user), userDao.findById(UUID.fromString(UUID1)));
        assertEquals(of(user), userDao.findById(UUID.fromString(UUID1)));
        assertEquals(of(user), userDao.findById(UUID.fromString(UUID1)));

        verify(mock, times(3)).findById(UUID.fromString(UUID1));
    }


}
