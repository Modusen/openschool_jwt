package com.example.openschool_jwt.service.impl;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.example.openschool_jwt.config.TestConfig;
import com.example.openschool_jwt.model.dto.user.UserDto;
import com.example.openschool_jwt.model.User;
import com.example.openschool_jwt.model.mapper.UserMapper;
import com.example.openschool_jwt.model.exception.ResourceNotFoundException;
import com.example.openschool_jwt.model.enums.Role;
import com.example.openschool_jwt.repository.UserRepository;
import com.example.openschool_jwt.service.UserService;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;
    @MockBean
    private UserMapper userMapper;

    @Autowired
    private UserService userService;


    @Test
    public void testGetById_Success() throws Exception {
        Long id = 1L;
        String firstName = "user";
        String lastName = "user";
        String middleName = "user";
        String email = "user@example.com";
        UserDto user = new UserDto(id, firstName, lastName, middleName, email, "password", Role.ROLE_USER);
        User expectedUserDto = new User(id, firstName, lastName, middleName, email, "password", Role.ROLE_USER);

        when(userRepository.findById(id)).thenReturn(Optional.of(expectedUserDto));
        when(userMapper.convertToFullEntity(user)).thenReturn(expectedUserDto);

        User actualUserDto = userService.findById(id);

        assertNotNull(actualUserDto);
        assertEquals(expectedUserDto, actualUserDto);
    }

    @Test
    void getByNotExistingId() {
        Long id = 1L;
        when(userRepository.findById(id))
                .thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> userService.findById(id));
        verify(userRepository).findById(id);
    }

    @Test
    public void testGetUserById_Success() throws Exception {
        Long id = 1L;
        String firstName = "user";
        String lastName = "user";
        String middleName = "user";
        String email = "user@example.com";
        User user = new User(id, firstName, lastName, middleName, email, "password", Role.ROLE_USER);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User actualUser = userService.findById(id);

        assertNotNull(actualUser);
        assertEquals(user, actualUser);
    }

    @Test
    void getByUsername() {
        String email = "username@gmail.com";
        User user = new User();
        user.setEmail(email);
        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));
        User testUser = userService.findByEmail(email);
        verify(userRepository).findByEmail(email);
        assertEquals(user, testUser);
    }

    @Test
    void getByNotExistingUsername() {
        String email = "username@gmail.com";
        when(userRepository.findByEmail(email))
                .thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> userService.findByEmail(email));
        verify(userRepository).findByEmail(email);
    }

    @Test
    void delete() {
        Long id = 1L;
        userService.delete(id);
        verify(userRepository).deleteById(id);
    }

}