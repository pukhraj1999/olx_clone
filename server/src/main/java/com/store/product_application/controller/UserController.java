package com.store.product_application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.product_application.common.enums.UserRole;
import com.store.product_application.common.exceptions.HibernateException;
import com.store.product_application.model.Review;
import com.store.product_application.model.User;
import com.store.product_application.service.UserService;

/*
 * @author Pukhraj Singh
 * @version 1.0
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateUser(
            @RequestBody User user,
            @PathVariable("id") long id) throws HibernateException {

        User userFromDB = null;

        // fetching user
        userFromDB = userService.getUserById(id);

        // updating
        userFromDB = userService.update(userFromDB, user);

        // storing updated user in DB
        userService.save(userFromDB);

        return new ResponseEntity<>(userFromDB, HttpStatus.OK);
    }

    @PutMapping("change/admin/{id}")
    public ResponseEntity<Object> changeUserRole(
            @PathVariable("id") long id) throws HibernateException {

        // fetching user
        User user = userService.getUserById(id);

        // making admin
        user.setRole(UserRole.ADMIN);

        // storing updated user in DB
        userService.save(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteUser(
            @PathVariable("id") long id) throws HibernateException {

        User user = userService.deleteUser(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<Object> fetchAllReviews(@PathVariable("id") long id) throws HibernateException {

        // getting all products
        List<Review> reviews = userService.getReviewByUserId(id);

        return new ResponseEntity<Object>(reviews, HttpStatus.OK);
    }
}
