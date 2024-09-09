package com.store.product_application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.product_application.common.exceptions.HibernateException;
import com.store.product_application.model.Product;
import com.store.product_application.model.Review;
import com.store.product_application.repository.ProductRepository;
import com.store.product_application.repository.ReviewRepository;

/*
 * @author Pukhraj Singh
 * @version 1.0
 */
@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ReviewRepository reviewRepository;

    /**
     * This method is used to get the count of number of the
     * products stored in DB.
     * 
     * @return count of products
     * @throws HibernateException
     */
    public long getCount() throws HibernateException {
        long result = 0;

        try {
            result = productRepository.count();
        } catch (Exception e) {
            throw new HibernateException("Failed to count products!!");
        }

        return result;
    }

    /**
     * This method is used to save the products in DB.
     * 
     * @param product
     * @throws HibernateException
     */
    public void saveProduct(Product product) throws HibernateException {
        try {
            productRepository.save(product);
        } catch (Exception e) {
            throw new HibernateException("Failed to save Product!!");
        }
    }

    /**
     * This method is used to serach the products based the detail of
     * product fetched from client
     * 
     * @param code
     * @param brand
     * @param name
     * @return list of products based on user search
     * @throws HibernateException
     */
    public List<Product> search(String code, String brand, String name) throws HibernateException {

        List<Product> result = null;

        if (code == null || brand == null || name == null)
            throw new HibernateException("Please fill all fields!!");

        try {
            if (!code.isBlank() && brand.isBlank() && name.isBlank()) {
                result = productRepository.findByCode(code);
                return result;
            }

            if (code.isBlank() && !brand.isBlank() && name.isBlank()) {
                result = productRepository.findByBrand(brand);
                return result;
            }

            if (code.isBlank() && brand.isBlank() && !name.isBlank()) {
                result = productRepository.findByNameContaining(name);
                return result;
            }

            if (!code.isBlank() && !brand.isBlank() && name.isBlank()) {
                result = productRepository.findByCodeAndBrand(code, brand);
                return result;
            }

            if (!code.isBlank() && brand.isBlank() && !name.isBlank()) {
                result = productRepository.findByCodeAndNameContaining(code, name);
                return result;
            }

            if (code.isBlank() && !brand.isBlank() && !name.isBlank()) {
                result = productRepository.findByBrandAndNameContaining(brand, name);
                return result;
            }

            if (!code.isBlank() && !brand.isBlank() && !name.isBlank()) {
                result = productRepository.findByCodeAndBrandAndNameContaining(code, brand, name);
                return result;
            }

        } catch (Exception e) {
            throw new HibernateException("failed to fetch products!!");
        }

        return result;
    }

    /**
     * This method checks the whether the product code already exist
     * and throw HibernateException if it does.
     * 
     * @param code
     * @throws HibernateException
     */
    public void checkProductCode(String code) throws HibernateException {
        List<Product> product = null;

        try {
            product = productRepository.findByCode(code);
        } catch (Exception e) {
            throw new HibernateException("failed to fetch product code!!");
        }

        if (!product.isEmpty())
            throw new HibernateException("Product Code already exist!!");

    }

    /**
     * This method is used to fetch all the products stored
     * in DB.
     * 
     * @return list of products
     * @throws HibernateException
     */
    public List<Product> getAllProducts() throws HibernateException {

        List<Product> products;

        try {
            products = (List<Product>) productRepository.findAll();
        } catch (Exception e) {
            throw new HibernateException("Failed to fetch all Products!!");
        }

        return products;
    }

    /**
     * This method is used to fetch the product based on product id.
     * 
     * @param id
     * @return product based on product id
     * @throws HibernateException
     */
    public Product getProductById(long id) throws HibernateException {

        Optional<Product> product;

        try {
            product = productRepository.findById(id);
        } catch (Exception e) {
            throw new HibernateException("Failed to fetch Product!!");
        }

        if (!product.isPresent())
            throw new HibernateException("Product not exist!!");

        return product.get();
    }

    /**
     * This method is used to fetch the review of a particular product
     * based on product id.
     * 
     * @param id
     * @return list of reviews
     * @throws HibernateException
     */
    public List<Review> getReviewByProductId(long id) throws HibernateException {

        List<Review> reviews;

        try {
            reviews = reviewRepository.findByProductId(id);
        } catch (Exception e) {
            throw new HibernateException("failed to fetch product reviews!!");
        }

        return reviews;
    }

    /**
     * This method is used to update the detail of already existed product.
     * 
     * @param productFromDB
     * @param product
     * @throws HibernateException
     */
    public void update(Product productFromDB, Product product) throws HibernateException {
        if (product.getBrand() != null && !product.getBrand().isBlank())
            productFromDB.setBrand(product.getBrand());

        if (product.getCode() != null && !product.getCode().isBlank())
            productFromDB.setCode(product.getCode());

        if (product.getName() != null && !product.getName().isBlank())
            productFromDB.setName(product.getName());

        if (product.getContent() != null && !product.getContent().isBlank())
            productFromDB.setContent(product.getContent());

        if (product.getImg() != null && !product.getImg().isBlank())
            productFromDB.setImg(product.getImg());
    }

    /**
     * This method is used to delete the product based on the
     * product id.
     * 
     * @param id
     * @return deleted product detail
     * @throws HibernateException
     */
    public Product deleteProductById(long id) throws HibernateException {

        Product product;

        try {
            product = this.getProductById(id);
            productRepository.delete(product);
        } catch (Exception e) {
            throw new HibernateException("Failed to delete Product!!");
        }

        return product;
    }
}
