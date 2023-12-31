package com.example.foodka.repository;

import com.example.foodka.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {
    Optional<Users> findFirstByPhoneNumber(String phoneNumber);
}
