package com.esic.checklist.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esic.checklist.config.JwtUtil;
import com.esic.checklist.dto.LoginRequest;
import com.esic.checklist.dto.LoginResponse;
import com.esic.checklist.model.IpDetails;
import com.esic.checklist.model.User;
import com.esic.checklist.service.IpDetailsService;
import com.esic.checklist.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    
    @Autowired
    private final IpDetailsService ipDetailsService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        User user = userService.validateUser(request.getIpNumber(), request.getPassword());
        String token = jwtUtil.generateToken(user.getIpNumber());
        return ResponseEntity.ok(new LoginResponse(token, user.getId(), user.getName()));
    }
    
    @GetMapping("/ip-details/{ipNumber}")
    public ResponseEntity<?> getByIpNumber(@PathVariable String ipNumber) {
        Optional<IpDetails> details = ipDetailsService.getByIpNumber(ipNumber);
        return details.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
}
