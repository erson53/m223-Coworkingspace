package ch.zli.m223.service;

import ch.zli.m223.model.Review;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ReviewService {

    @Inject
    EntityManager entityManager;

    public List<Review> getAllReviews() {
        return entityManager.createQuery("SELECT r FROM Review r", Review.class)
                .getResultList();
    }

    @Transactional
    public Review createReview(Review newReview) {
        try {
            entityManager.persist(newReview);
            return newReview;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = entityManager.find(Review.class, reviewId);
        if (review != null) {
            entityManager.remove(review);
        }
    }
}