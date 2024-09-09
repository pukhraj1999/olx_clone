package com.store.product_application.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

/*
 * @author Pukhraj Singh
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class JwtService {

    // Generated using Encryption Key Generator website
    private final String SECRET_KEY = "6B597033733676397924423F4528482B4D6251655468576D5A7134743777217A";

    /**
     * This method is used to extract the email from
     * jwt token
     * 
     * @param token
     * @return gives user email
     */
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * This method is used to generate jwt token from user details
     * and extractClaims.
     * 
     * @param extractClaims
     * @param userDetails
     * @return generate token using Jwts builder
     */
    public String generateToken(
            Map<String, Object> extractClaims,
            UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * This method is used to generate JWT token using
     * user details only.
     * 
     * @param userDetails
     * @return generated token using user details only
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * This method is used to check whether the token
     * is valid or not.
     * 
     * @param token
     * @param userDetails
     * @return gives true if token is valid
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userEmail = extractEmail(token);
        return userEmail.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * This method checks whether the token is expired or not.
     * 
     * @param token
     * @return gives true if token is expired
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * This method is used to fetch the expiration date.
     * 
     * @param token
     * @return Date of token expiration
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * 
     * This method is used to extract the single claim of your desire
     * from jwt token.
     * 
     * @param <T>           any datatype
     * @param token
     * @param claimResolver
     * @return extract single desirable claim
     */
    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    /**
     * This method is used to extract content or claims of JWT token.
     * 
     * @param token
     * @return set signing key and return claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * This method is used to get sign in key with the
     * help of BASE64 decoder and applying hmacSha
     * encryption algo.
     * 
     * @return Key using applying hmacSha encryption algo
     */
    private Key getSignInKey() {
        byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }

}
