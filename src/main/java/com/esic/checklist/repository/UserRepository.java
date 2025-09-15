package com.esic.checklist.repository;

import com.esic.checklist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIpNumber(String ipNumber);
}
