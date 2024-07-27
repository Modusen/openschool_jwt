package com.example.openschool_jwt.repository;

import com.example.openschool_jwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByLastname(String lastname);

    @Query(value = """
            SELECT COUNT(*) > 0
            FROM USERS
            WHERE LASTNAME = :lastname AND EMAIL = :email
            """, nativeQuery = true)
    Boolean isUserExists(String lastname, String email);
    @Query(value = """
            SELECT * FROM USERS
            WHERE USERS.ROLES = 'ROLE_ADMIN'
            """, nativeQuery = true)
    List<User> findAdminUsers();

    @Query(value = """
            SELECT * FROM USERS
            WHERE USERS.ROLES = 'ROLE_USER'
            """, nativeQuery = true)
    List<User> findRegularUsers();
}
