package com.example.openschool_jwt.security;

import com.example.openschool_jwt.model.User;
import com.example.openschool_jwt.model.dto.auth.JwtResponse;
import com.example.openschool_jwt.model.enums.Role;
import com.example.openschool_jwt.model.exception.AccessDeniedException;
import com.example.openschool_jwt.model.security.JwtProperties;
import com.example.openschool_jwt.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String createAccessToken(final Long userId, final String email, final Role roles) {
        Claims claims = Jwts.claims().subject(email).add("id", userId).add("roles", resolveRoles(roles)).build();
        Instant validity = Instant.now().plus(jwtProperties.getTokenExpiration(), ChronoUnit.HOURS);
        return Jwts.builder().claims(claims).expiration(Date.from(validity)).signWith(key).compact();
    }

    private String resolveRoles(final Role role) {
        return role.name();
    }

    public String createRefreshToken(final Long userId, final String username) {
        Claims claims = Jwts.claims().subject(username).add("id", userId).build();
        Instant validity = Instant.now().plus(jwtProperties.getRefreshTokenExpiration(), ChronoUnit.DAYS);
        return Jwts.builder().claims(claims).expiration(Date.from(validity)).signWith(key).compact();
    }

    public JwtResponse refreshUserTokens(final String refreshToken) {

        if (!isValid(refreshToken)) {
            throw new AccessDeniedException();
        }
        Long userId = getId(refreshToken);
        User user = userService.findById(userId);
        return JwtResponse.builder().id(userId).email(user.getEmail()).accessToken(createAccessToken(userId, user.getEmail(), user.getRole())).refreshToken(createRefreshToken(userId, user.getEmail())).build();
    }

    public boolean isValid(final String token) {
        Jws<Claims> claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
        return claims.getPayload().getExpiration().after(new Date());
    }

    private Long getId(final String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().get("id", Long.class);
    }

    private String getUsername(final String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public Authentication getAuthentication(final String token) {
        String username = getUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
