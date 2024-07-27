package com.example.openschool_jwt.controller;

import com.example.openschool_jwt.model.User;
import com.example.openschool_jwt.model.dto.user.UserDto;
import com.example.openschool_jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/admin")
    public List<UserDto> getAdminUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("")
    public ResponseEntity<UserDto> update(@RequestBody UserDto dto) {
        log.info("UserController.update");
        return ResponseEntity.ok(userService.update(dto));
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        log.info("UserController.getById");
        return ResponseEntity.ok(userService.findById(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable final Long id) {
        log.info("UserController.deleteById");
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
