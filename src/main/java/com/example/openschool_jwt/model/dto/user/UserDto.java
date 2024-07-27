package com.example.openschool_jwt.model.dto.user;

import com.example.openschool_jwt.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

    private String firstname;

    private String lastname;

    private String middleName;

    private String email;

    private String password;

    private Role role;
}
