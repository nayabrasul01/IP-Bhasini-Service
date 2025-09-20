package com.esic.checklist.utility;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.esic.checklist.repository.LoggedInUserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class JwtUtil {
	
	@Autowired
	private LoggedInUserRepository loggedInUserRepository;

    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private long expiration;

//    private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
    
    public String extractUsername(String token) {
		return getClaims(token).getSubject();
	}

    public boolean validateToken(String token) {
		try {
			Claims claims = getClaims(token);
			
			String jti = claims.getId(); // maps to session_id
	        return loggedInUserRepository.existsBySessionIdAndIsValid(jti, true);
		} catch (ExpiredJwtException e) {
			log.warn("Token expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.warn("Unsupported JWT: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			log.warn("Malformed JWT: {}", e.getMessage());
		} catch (SignatureException e) {
			log.warn("Invalid signature: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.warn("Illegal argument token: {}", e.getMessage());
		}
		return false;
	}

	public Claims getClaims(String token) {
		return Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody();
	}
	
	public String extractJti(String token) {
	    return Jwts.parserBuilder()
	    		.setSigningKey(secret)
	    		.build()
	            .parseClaimsJws(token)
	            .getBody()
	            .getId();
    }

}
