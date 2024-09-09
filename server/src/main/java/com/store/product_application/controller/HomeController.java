package com.store.product_application.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.product_application.common.exceptions.HibernateException;
import com.store.product_application.service.ProductService;
import com.store.product_application.service.ReviewService;
import com.store.product_application.service.UserService;

/**
 * @author Pukhraj Singh
 * @version 1.0
 * 
 */
@RestController
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    ReviewService reviewService;

    @CrossOrigin
    @GetMapping(path = "/")
    public String Home() {
        return "Welcome to Product Application API!!";
    }

    @CrossOrigin
    @GetMapping("/status")
    public ResponseEntity<Object> getStatus() throws HibernateException {

        Map<String, Object> result = new HashMap<>();

        result.put("users", userService.getCount());
        result.put("products", productService.getCount());
        result.put("reviews", reviewService.getCount());

        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }
}
