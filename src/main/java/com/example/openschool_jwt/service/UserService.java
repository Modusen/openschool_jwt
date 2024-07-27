package com.example.openschool_jwt.service;

import com.example.openschool_jwt.model.User;
import com.example.openschool_jwt.model.dto.user.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User findByEmail(String email);
    User findById(Long id);
    List<User> getAdminUsers();
    List<UserDto> getAllUsers();
    List<User> getUsers();
    UserDto update(UserDto newUser);
    UserDto create(UserDto userDto);
    void delete(Long id);
}
