package com.store.product_application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.product_application.common.enums.ReviewStatus;
import com.store.product_application.common.exceptions.HibernateException;
import com.store.product_application.dto.ReviewResponse;
import com.store.product_application.model.Product;
import com.store.product_application.model.Review;
import com.store.product_application.model.User;
import com.store.product_application.repository.ReviewRepository;

/*
 * @author Pukhraj Singh
 * @version 1.0
 */
@Service
public class ReviewService {

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    ReviewRepository reviewRepository;

    /**
     * This method is used to get the count of number of reviews present
     * in DB.
     * 
     * @return count of user reviews of products
     * @throws HibernateException
     */
    public long getCount() throws HibernateException {
        long result = 0;

        try {
            result = reviewRepository.count();
        } catch (Exception e) {
            throw new HibernateException("Failed to count reviews!!");
        }

        return result;
    }

    /**
     * This method is used to add the user and product detail with review detail
     * on ReviewResponse dto.
     * 
     * @param review
     * @return ReviewResponse dto
     * @throws HibernateException
     */
    public ReviewResponse fillResponse(Review review) throws HibernateException {

        User user = userService.getUserById(review.getUserId());
        Product product = productService.getProductById(review.getProductId());

        return new ReviewResponse(review, user, product);
    }

    /**
     * This method is used to populate the detail of all the reviews with the
     * product detail and user details from DB.
     * 
     * @param reviews
     * @return list of reviews along with product and user detail
     * @throws HibernateException
     */
    public List<ReviewResponse> fillAllResponses(List<Review> reviews) throws HibernateException {
        List<ReviewResponse> result = new ArrayList<>();

        for (Review review : reviews)
            result.add(fillResponse(review));

        return result;
    }

    /**
     * This method is used to fetch all the reviews from DB.
     * 
     * @return list of reviews
     * @throws HibernateException
     */
    public List<Review> fetchAllReviews() throws HibernateException {

        List<Review> result = null;

        try {
            result = (List<Review>) reviewRepository.findAll();
        } catch (Exception e) {
            throw new HibernateException("failed to fetch reviews!!");
        }

        return result;
    }

    /**
     * This method is used to fetch all the reviews filter by their
     * status.
     * 
     * @param status
     * @return list of reviews based on their status
     * @throws HibernateException
     */
    public List<Review> fetchReviews(ReviewStatus status) throws HibernateException {

        List<Review> result = null;

        try {
            result = reviewRepository.findByStatus(status);
        } catch (Exception e) {
            throw new HibernateException("failed to fetch reviews!!");
        }

        return result;
    }

    /**
     * This method is used to update the review with the status
     * given as parameter along with review id.
     * 
     * @param id
     * @param status
     * @return review with updated status
     * @throws HibernateException
     */
    public Review updateReviewStatus(long id, ReviewStatus status) throws HibernateException {

        // fetching review
        Review review = this.getReviewById(id);

        // updating status
        review.setStatus(status);

        // saving review
        this.saveReview(review);

        return review;
    }

    /**
     * This method is used to validate the range of rating given by
     * user.
     * 
     * @param rating
     * @throws HibernateException
     */
    public void checkRating(int rating) throws HibernateException {
        if (rating < 1 || rating > 5)
            throw new HibernateException("Rating must be between 1 to 5.");
    }

    public void checkContent(String content) throws HibernateException {
        if (content.length() < 20 || content.length() > 400)
            throw new HibernateException("Review length must be between 20 to 400.");
    }

    /**
     * This method is used to update the rating and content of review fetched from
     * DB with the help of review id.
     * 
     * @param id
     * @param rating
     * @param content
     * @return updated review
     * @throws HibernateException
     */
    public Review updateReview(long id, int rating, String content) throws HibernateException {

        // fetching review
        Review review = this.getReviewById(id);

        // updating status
        review.setStatus(ReviewStatus.PENDING);

        // validating
        checkRating(rating);
        checkContent(content);

        // updating rating and content
        review.setRating(rating);
        review.setContent(content);

        // saving review
        this.saveReview(review);

        return review;
    }

    /**
     * This method is used to delete the review from DB
     * based on review id.
     * 
     * @param id
     * @return deleted review
     * @throws HibernateException
     */
    public Review deleteReview(long id) throws HibernateException {

        Review review = this.getReviewById(id);

        try {
            reviewRepository.delete(review);
        } catch (Exception e) {
            throw new HibernateException("Failed to delete review!!");
        }

        return review;
    }

    /**
     * This method is used to save the review in DB.
     * 
     * @param review
     * @throws HibernateException
     */
    public void saveReview(Review review) throws HibernateException {
        // validating
        checkRating(review.getRating());
        checkContent(review.getContent());

        try {
            reviewRepository.save(review);
        } catch (Exception e) {
            throw new HibernateException("Failed to save Review!!");
        }

    }

    /**
     * This method is used to fetch the review from DB based
     * on review id.
     * 
     * @param id
     * @return review fetched using it's id
     * @throws HibernateException
     */
    public Review getReviewById(long id) throws HibernateException {

        Optional<Review> review;

        try {
            review = reviewRepository.findById(id);
        } catch (Exception e) {
            throw new HibernateException("Failed to fetch review!!");
        }

        if (!review.isPresent())
            throw new HibernateException("review not exist!!");

        return review.get();
    }

}
