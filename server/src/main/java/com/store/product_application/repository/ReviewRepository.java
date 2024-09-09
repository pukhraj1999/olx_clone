package com.store.product_application.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.store.product_application.common.enums.ReviewStatus;
import com.store.product_application.model.Review;

/*
 * @author Pukhraj Singh
 * @version 1.0
 */
public interface ReviewRepository extends CrudRepository<Review, Long> {

    public List<Review> findByStatus(ReviewStatus status);

    public List<Review> findByUserId(long id);

    public List<Review> findByProductId(long id);
}
