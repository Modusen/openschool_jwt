package com.example.openschool_jwt.security;

import com.example.openschool_jwt.model.User;
import com.example.openschool_jwt.model.enums.Role;
import com.example.openschool_jwt.model.security.AppUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JwtEntityFactory {
    public static AppUserDetails create(User user) {
        return new AppUserDetails(user, mapToGrantedAuthorities(Collections.singletonList(user.getRole())));
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles) {
        return roles.stream().map(Enum::name).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
