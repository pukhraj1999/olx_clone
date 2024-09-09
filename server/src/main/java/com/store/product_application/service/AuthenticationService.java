package com.store.product_application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.store.product_application.common.enums.UserRole;
import com.store.product_application.common.exceptions.HibernateException;
import com.store.product_application.dto.AuthenticationRequest;
import com.store.product_application.dto.AuthenticationResponse;
import com.store.product_application.dto.RegisterRequest;
import com.store.product_application.model.User;
import com.store.product_application.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/*
 * @author Pukhraj Singh
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    /**
     * This method takes the RegisterRequest dto and validate it.
     * After validating jwtService get's used to generate token and
     * return AuthenticationResponse dto.
     * 
     * @param req
     * @return AuthenticationResponse
     * @throws HibernateException
     */
    public AuthenticationResponse register(RegisterRequest req) throws HibernateException {

        // checking all fields filled
        userService.checkSignupFieldEmpty(req);

        // checking user exist or not
        userService.checkUserExist(req.getEmail(), true);

        // validating password
        userService.passwordValidate(req.getPassword());

        // creating user
        User user = User.builder()
                .firstname(req.getFirstname())
                .lastname(req.getLastname())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(UserRole.USER)
                .build();

        // saving user in DB
        userService.save(user);

        // generating token
        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .user(user)
                .token(jwtToken)
                .build();
    }

    /**
     * This method is used to validate the AuthenticationRequest dto and
     * return Authentication response accordingly.
     * 
     * @param req
     * @return AuthenticationResponse
     * @throws HibernateException
     */
    public AuthenticationResponse authenticate(AuthenticationRequest req) throws HibernateException {

        // checking user exist or not
        User user = userService.checkUserExist(req.getEmail(), false);

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.getEmail(),
                            req.getPassword()));
        } catch (AuthenticationException e) {
            throw new HibernateException("Invalid Credentials!!");
        }

        if (user == null)
            throw new HibernateException("Unauthorized user!!");

        // generating token
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .user(user)
                .build();
    }

}
