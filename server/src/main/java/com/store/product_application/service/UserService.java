package com.store.product_application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.product_application.common.exceptions.HibernateException;
import com.store.product_application.dto.RegisterRequest;
import com.store.product_application.model.Review;
import com.store.product_application.model.User;
import com.store.product_application.repository.ReviewRepository;
import com.store.product_application.repository.UserRepository;

/*
 * @author Pukhraj Singh
 * @version 1.0
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReviewRepository reviewRepository;

    /**
     * This method is used to fetch the count of number of users
     * present in DB.
     * 
     * @return count of users
     * @throws HibernateException
     */
    public long getCount() throws HibernateException {
        long result = 0;

        try {
            result = userRepository.count();
        } catch (Exception e) {
            throw new HibernateException("Failed to count users!!");
        }

        return result;
    }

    /**
     * This method is used to save the user in DB.
     * 
     * @param user
     * @throws HibernateException
     */
    public void save(User user) throws HibernateException {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new HibernateException("Failed to save user!!");
        }
    }

    /**
     * This method is used to fetch the user from DB based on review id.
     * 
     * @param id
     * @return user
     * @throws HibernateException
     */
    public User getUserById(Long id) throws HibernateException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent())
            throw new HibernateException("user not exist!!");
        return user.get();
    }

    /**
     * This method is used to fetch all the review given by particular user.
     * 
     * @param id
     * @return list of reviews
     * @throws HibernateException
     */
    public List<Review> getReviewByUserId(long id) throws HibernateException {

        List<Review> reviews;

        try {
            reviews = reviewRepository.findByUserId(id);
        } catch (Exception e) {
            throw new HibernateException("failed to fetch user reviews!!");
        }

        return reviews;
    }

    /**
     * This method is used to check whether all the signup fields are filled
     * and throw error based on it.
     * 
     * @param req
     * @throws HibernateException
     */
    public void checkSignupFieldEmpty(RegisterRequest req) throws HibernateException {
        if (req.getFirstname().isBlank()
                || req.getLastname().isBlank()
                || req.getEmail().isBlank()
                || req.getPassword().isBlank())
            throw new HibernateException("Please fill all the fields!!");
    }

    /**
     * This method is used to check whether all the login fields are filled
     * and throw error based on it.
     * 
     * @param email
     * @param password
     * @throws HibernateException
     */
    public void checkLoginFieldEmpty(String email, String password) throws HibernateException {
        if (email.isBlank()
                || password.isBlank())
            throw new HibernateException("Please fill all the fields!!");
    }

    /**
     * This method is used to check user exist or not. This method
     * throws error based on isSignUp param which tells whether
     * need to throw the error for signup page or login page
     * 
     * @param email
     * @param isSignUp
     * @return user
     * @throws HibernateException
     */
    public User checkUserExist(String email, boolean isSignUp) throws HibernateException {

        User user = null;

        try {
            user = userRepository.findByEmail(email);
        } catch (Exception e) {
            throw new HibernateException("Failed to check user exist or not!!");
        }

        if (isSignUp && user != null)
            throw new HibernateException("Email already exist!!");

        if (!isSignUp && user == null)
            throw new HibernateException("Need to create account before login!!");

        return user;
    }

    /**
     * This method is used to validate the password provided by user.
     * 
     * @param password
     * @throws HibernateException
     */
    public void passwordValidate(String password) throws HibernateException {
        boolean invalidLength = password.length() <= 8;

        boolean firstLetterNotCapital = !(password.charAt(0) >= 'A'
                && password.charAt(0) <= 'Z');

        boolean notContainNumbers = !password.chars()
                .anyMatch(item -> item >= '0' && item <= '9');

        boolean notSpecialCharacter = !(password.chars()
                .anyMatch(item -> {
                    String special = "!~@#$%^&*()_+=";
                    for (char x : special.toCharArray())
                        if (item == x)
                            return true;
                    return false;
                }));

        if (invalidLength
                || firstLetterNotCapital
                || notContainNumbers
                || notSpecialCharacter)
            throw new HibernateException(
                    "Password length must be greater than 8, first letter should be capital and must conatin digits and 1 special character!!");
    }

    /**
     * This method is used to update the user detail of a already
     * existing user in DB.
     * 
     * @param userFromDB
     * @param user
     * @return updated user
     */
    public User update(User userFromDB, User user) {

        if (user.getFirstname() != null && !user.getFirstname().isBlank())
            userFromDB.setFirstname(user.getFirstname());

        if (user.getLastname() != null && !user.getLastname().isBlank())
            userFromDB.setLastname(user.getLastname());

        if (user.getEmail() != null && !user.getEmail().isBlank())
            userFromDB.setEmail(user.getEmail());

        if (user.getRole() != null)
            userFromDB.setRole(user.getRole());

        return userFromDB;
    }

    /**
     * This method is used to delete the user from DB based on user id.
     * 
     * @param id
     * @return deleted user detail
     * @throws HibernateException
     */
    public User deleteUser(Long id) throws HibernateException {
        User user = this.getUserById(id);
        try {
            userRepository.delete(user);
        } catch (Exception e) {
            throw new HibernateException("Failed to delete user!!");
        }
        return user;
    }
}
