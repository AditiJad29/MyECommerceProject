package com.practice.ecommerce.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUtils {
    public static final Logger LOGGER = LoggerFactory.getLogger(JWTUtils.class);
    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJWTTokenFromUsername(UserDetails userDetails) {
        String username = userDetails.getUsername();
        Date today = new Date();

        return Jwts.builder()
                .subject(username)
                .issuedAt(today)
                .expiration(new Date(today.getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return decryptToken(token).getPayload().getSubject();
    }

    public String getTokenFromHeader(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        LOGGER.info("Bearer token received in request:: " + bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateJWTToken(String token) {
        try {
            decryptToken(token);
            return true;
        } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException jwte) {
            LOGGER.error("JWT exception: {}", jwte.getMessage());
        } catch (IllegalArgumentException iae) {
            LOGGER.error("JWT claims string is empty : {}", iae.getMessage());
        }
        return false;
    }

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    private io.jsonwebtoken.Jws<io.jsonwebtoken.Claims> decryptToken(String token) {
        return Jwts.parser().verifyWith(key()).build().parseSignedClaims(token);
    }

}
