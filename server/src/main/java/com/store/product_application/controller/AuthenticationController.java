package com.store.product_application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.product_application.common.exceptions.HibernateException;
import com.store.product_application.dto.AuthenticationRequest;
import com.store.product_application.dto.AuthenticationResponse;
import com.store.product_application.dto.RegisterRequest;
import com.store.product_application.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

/*
 * @author Pukhraj Singh
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @CrossOrigin
    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signup(
            @RequestBody RegisterRequest req) throws HibernateException {
        return new ResponseEntity<>(authenticationService.register(req), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest req) throws HibernateException {
        return new ResponseEntity<>(authenticationService.authenticate(req), HttpStatus.OK);
    }

}
