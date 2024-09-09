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

import com.store.product_application.common.enums.ReviewStatus;
import com.store.product_application.common.exceptions.HibernateException;
import com.store.product_application.dto.ReviewResponse;
import com.store.product_application.model.Review;
import com.store.product_application.service.ReviewService;

/*
 * @author Pukhraj Singh
 * @version 1.0
 */
@RestController
@CrossOrigin
@RequestMapping("/api/product/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @CrossOrigin
    @GetMapping("/all")
    public ResponseEntity<Object> getAllReviews() throws HibernateException {

        List<Review> reviews = reviewService.fetchAllReviews();

        List<ReviewResponse> result = reviewService.fillAllResponses(reviews);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Object> getReview(@PathVariable("id") long id) throws HibernateException {

        Review review = reviewService.getReviewById(id);

        ReviewResponse result = reviewService.fillResponse(review);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/all/accepted")
    public ResponseEntity<Object> getAcceptedReview() throws HibernateException {

        List<Review> reviews = reviewService.fetchReviews(ReviewStatus.ACCEPTED);

        List<ReviewResponse> result = reviewService.fillAllResponses(reviews);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/all/rejected")
    public ResponseEntity<Object> getRejectedReview() throws HibernateException {

        List<Review> reviews = reviewService.fetchReviews(ReviewStatus.REJECTED);

        List<ReviewResponse> result = reviewService.fillAllResponses(reviews);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/all/pending")
    public ResponseEntity<Object> getPendingReview() throws HibernateException {

        List<Review> reviews = reviewService.fetchReviews(ReviewStatus.PENDING);

        List<ReviewResponse> result = reviewService.fillAllResponses(reviews);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/send")
    public ResponseEntity<Object> sendReview(@RequestBody Review review) throws HibernateException {

        // uploading status
        review.setStatus(ReviewStatus.PENDING);

        // saving in DB
        reviewService.saveReview(review);

        ReviewResponse result = reviewService.fillResponse(review);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping("/accept/{id}")
    public ResponseEntity<Object> acceptReview(
            @PathVariable("id") Long id) throws HibernateException {

        // feeding review with required info
        Review review = reviewService.updateReviewStatus(id, ReviewStatus.ACCEPTED);

        ReviewResponse result = reviewService.fillResponse(review);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping("/reject/{id}")
    public ResponseEntity<Object> rejectReview(
            @PathVariable("id") Long id) throws HibernateException {

        // feeding review with required info
        Review review = reviewService.updateReviewStatus(id, ReviewStatus.REJECTED);

        ReviewResponse result = reviewService.fillResponse(review);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateReview(
            @RequestBody Review review,
            @PathVariable("id") Long id) throws HibernateException {

        // feeding review with required info
        Review updatedReview = reviewService.updateReview(id, review.getRating(), review.getContent());

        ReviewResponse result = reviewService.fillResponse(updatedReview);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteReview(
            @PathVariable("id") Long id) throws HibernateException {

        Review deletedReview = reviewService.deleteReview(id);

        ReviewResponse result = reviewService.fillResponse(deletedReview);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
