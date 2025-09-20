package com.esic.checklist.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esic.checklist.annotations.Traceable;
import com.esic.checklist.dto.LoginRequest;
import com.esic.checklist.model.IpDetails;
import com.esic.checklist.model.LoggedInUser;
import com.esic.checklist.repository.LoggedInUserRepository;
import com.esic.checklist.service.IpDetailsService;
import com.esic.checklist.service.LdapAuthService;
import com.esic.checklist.utility.JwtUtil;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	@Autowired
    private final JwtUtil jwtUtil;
    
    @Autowired
    private final IpDetailsService ipDetailsService;
    
    @Autowired
    private final LdapAuthService ldapAuthService;
    
    @Autowired
    private final LoggedInUserRepository loggedInUserRepository;

    @Traceable
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
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
            
            // save logged in user session
            String jti = jwtUtil.extractJti(token);
            LoggedInUser log = new LoggedInUser();
            log.setUserId(username);
            log.setSessionId(jti);
            log.setValid(true);
            log.setIpAddress(request.getRemoteAddr());
            log.setCreatedBy("SYSTEM");
            loggedInUserRepository.save(log);

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(response);
        }
        response.put("status", "failure");
        response.put("message", "Invalid credentials");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    
    }
    
    @Traceable
    @GetMapping("/validate")
    public ResponseEntity<Map<String, String>> validateToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        Map<String, String> response = new HashMap<>();
        response.put("status", "failure");
        response.put("message", "Missing or invalid Authorization header");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }

        String token = authHeader.substring(7);
        try {
            boolean isValid = jwtUtil.validateToken(token);

            if (isValid) {
                String username = jwtUtil.getClaims(token).getSubject();
                response.put("status", "success");
                response.put("message", "Token is valid with user: " + username);
                return ResponseEntity.ok().body(response);
            } else {
            	response.put("status", "failure");
                response.put("message", "Invalid or expired token.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(response);
            }
        } catch (Exception ex) {
        	response.put("status", "failure");
            response.put("message", "Token validation failed.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(response);
        }
    }
    
    @Traceable
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            Claims claims = jwtUtil.getClaims(token);
            String jti = claims.getId(); 

            loggedInUserRepository.invalidateSession(jti);
        }
        return ResponseEntity.ok("Logged out successfully");
    }
    
    @Traceable    
    @GetMapping("/ip-details/{ipNumber}")
    public ResponseEntity<?> getByIpNumber(@PathVariable String ipNumber) {
        Optional<IpDetails> details = ipDetailsService.getByIpNumber(ipNumber);
        return details.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
}
