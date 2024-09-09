package com.store.product_application.dto;

import com.store.product_application.model.Product;
import com.store.product_application.model.Review;
import com.store.product_application.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @author Pukhraj Singh
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {

    private Review review;
    private User user;
    private Product product;
}
