package com.fastkart.authservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class JwtHelperTest {

    @InjectMocks
    private JwtHelper jwtHelper;

    @Mock
    private SecretKey secretKey;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUsernameFromToken() {
        String token = createSampleToken();
        String username = jwtHelper.getUsernameFromToken(token);
        assertEquals("testuser", username);
    }

    @Test
    public void testGetExpirationDateFromToken() {
        String token = createSampleToken();
        Date expirationDate = jwtHelper.getExpirationDateFromToken(token);
        assertNotNull(expirationDate);
    }

    @Test
    public void testGetClaimFromToken() {
        String token = createSampleToken();
        Claims claims = Jwts.claims();
        claims.setSubject("testuser");
        when(secretKey.getAlgorithm()).thenReturn(SignatureAlgorithm.HS512.getJcaName());

        String subject = jwtHelper.getClaimFromToken(token, Claims::getSubject);
        assertEquals("testuser", subject);
    }

    @Test
    public void testGenerateToken() {
        UserDetails userDetails = new User("testuser", "password", new ArrayList<>());
        String token = jwtHelper.generateToken(userDetails);
        assertNotNull(token);
    }

    @Test
    public void testValidateToken() {
        UserDetails userDetails = new User("testuser", "password", new ArrayList<>());
        String token = createSampleToken();
        boolean isValid = jwtHelper.validateToken(token, userDetails);
        assertTrue(isValid);
    }

    private String createSampleToken() {
        Map<String, Object> claims = new HashMap<>();
        return jwtHelper.generateToken(new User("testuser", "password", new ArrayList<>()));
    }
}
