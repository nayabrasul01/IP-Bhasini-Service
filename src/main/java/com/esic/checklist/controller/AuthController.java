package com.esic.checklist.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esic.checklist.dto.LoginRequest;
import com.esic.checklist.model.IpDetails;
import com.esic.checklist.service.IpDetailsService;
import com.esic.checklist.service.LdapAuthService;
import com.esic.checklist.utility.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;
    
    @Autowired
    private final IpDetailsService ipDetailsService;
    
    @Autowired
    private final LdapAuthService ldapAuthService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
		String username = loginRequest.getUsername();
		String password = loginRequest.getPassword();
		
		// Authenticate against LDAP
        boolean isAuthenticated = ldapAuthService.authenticate(username, password);
//    	boolean isAuthenticated = true; // TEMP: Bypass LDAP for testing
    	Map<String, String> response = new HashMap<>();
        if (isAuthenticated) {
            String token = jwtUtil.generateToken(username);

            // Send JWT in cookie + response
            ResponseCookie cookie = ResponseCookie.from("jwt", token)
                    .httpOnly(true)
                    .secure(false) // change to true in HTTPS
                    .path("/")
                    .maxAge(30 * 60) // 30 min
                    .build();

            response.put("status", "success");
            response.put("message", "Valid credentials");
            response.put("token", token);
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(response);
        }
        response.put("status", "failure");
        response.put("message", "Invalid credentials");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    
    }
        
    @GetMapping("/ip-details/{ipNumber}")
    public ResponseEntity<?> getByIpNumber(@PathVariable String ipNumber) {
        Optional<IpDetails> details = ipDetailsService.getByIpNumber(ipNumber);
        return details.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
}
