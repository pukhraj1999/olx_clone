package com.store.product_application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.product_application.model.Product;

/*
 * @author Pukhraj Singh
 * @version 1.0
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    public List<Product> findByCode(String code);

    public List<Product> findByBrand(String brand);

    public List<Product> findByNameContaining(String name);

    public List<Product> findByCodeAndBrand(String code, String brand);

    public List<Product> findByCodeAndNameContaining(String code, String name);

    public List<Product> findByBrandAndNameContaining(String brand, String name);

    public List<Product> findByCodeAndBrandAndNameContaining(String code, String brand, String name);

}
