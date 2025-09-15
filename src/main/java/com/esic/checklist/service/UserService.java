package com.esic.checklist.service;

import com.esic.checklist.model.User;
import com.esic.checklist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User validateUser(String ipNumber, String password) {
        return userRepository.findByIpNumber(ipNumber)
                .filter(u -> u.getPassword().equals(password))
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }
}
