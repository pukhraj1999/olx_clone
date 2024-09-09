package com.store.product_application.repository;

import org.springframework.data.repository.CrudRepository;

import com.store.product_application.model.User;

/*
 * @author Pukhraj Singh
 * @version 1.0
 */
public interface UserRepository extends CrudRepository<User, Long> {

    public User findByEmail(String email);

}
