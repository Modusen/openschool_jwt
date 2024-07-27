package com.example.openschool_jwt.service;

import com.example.openschool_jwt.model.User;
import com.example.openschool_jwt.model.dto.user.UserDto;
import com.example.openschool_jwt.model.enums.Role;
import com.example.openschool_jwt.model.exception.UserAlreadyExistsException;
import com.example.openschool_jwt.model.mapper.UserMapper;
import com.example.openschool_jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public List<User> getAdminUsers() {
        return userRepository.findAdminUsers();
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findRegularUsers();
    }


    @Override
    public UserDto update(UserDto newUser) {
        User oldUser = userRepository.findById(newUser.getId())
                .orElseThrow(() ->
                        new NoSuchElementException("User not found."));
        oldUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        oldUser.setEmail(newUser.getEmail());
        oldUser.setFirstname(newUser.getFirstname());
        oldUser.setLastname(newUser.getLastname());
        oldUser.setMiddleName(newUser.getMiddleName());
        userRepository.saveAndFlush(oldUser);
        return userMapper.convertToDto(oldUser);

    }

    @Override
    public UserDto create(UserDto userDto) {
        User user = userMapper.convertToFullEntity(userDto);

        if (userRepository.isUserExists(user.getLastname(), user.getEmail())) {
            throw new UserAlreadyExistsException("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        return userMapper.convertToFullDto(userRepository.save(user));
    }


    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}