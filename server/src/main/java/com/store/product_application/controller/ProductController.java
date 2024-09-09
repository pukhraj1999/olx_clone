package com.store.product_application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.product_application.common.exceptions.HibernateException;
import com.store.product_application.model.Product;
import com.store.product_application.model.Review;
import com.store.product_application.service.ProductService;

/*
 * @author Pukhraj Singh
 * @version 1.0
 */
@RestController
@CrossOrigin
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @CrossOrigin
    @PostMapping("/create")
    public ResponseEntity<Object> createProduct(@RequestBody Product product) throws HibernateException {

        // checking code
        productService.checkProductCode(product.getCode());

        // saving in DB
        productService.saveProduct(product);

        return new ResponseEntity<Object>(product, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/public/search")
    public ResponseEntity<Object> searchProduct(@RequestBody Product product) throws HibernateException {

        List<Product> result = productService.search(product.getCode(), product.getBrand(), product.getName());

        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/public/all")
    public ResponseEntity<Object> fetchAllProducts() throws HibernateException {

        // getting all products
        List<Product> products = productService.getAllProducts();

        return new ResponseEntity<Object>(products, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("public/{id}")
    public ResponseEntity<Object> fetchProduct(@PathVariable("id") long id) throws HibernateException {

        Product product = productService.getProductById(id);

        return new ResponseEntity<Object>(product, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("public/{id}/reviews")
    public ResponseEntity<Object> fetchAllReviews(@PathVariable("id") long id) throws HibernateException {

        // getting all products
        List<Review> reviews = productService.getReviewByProductId(id);

        return new ResponseEntity<Object>(reviews, HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateProduct(
            @RequestBody Product product,
            @PathVariable("id") long id) throws HibernateException {

        Product productFromDB = productService.getProductById(id);

        // updating ...
        productService.update(productFromDB, product);

        // saving ...
        productService.saveProduct(productFromDB);
        ;

        return new ResponseEntity<Object>(productFromDB, HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") long id) throws HibernateException {

        // deleting product ...
        Product product = productService.deleteProductById(id);

        return new ResponseEntity<Object>(product, HttpStatus.OK);
    }
}
