package ch.zli.m223.service;

import ch.zli.m223.model.AppUser;
import ch.zli.m223.model.Follower;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class FollowerService {

    @Inject
    EntityManager entityManager;

    @Transactional
    public Follower followUser(AppUser followerUser, AppUser followedUser) {
        Follower follower = new Follower();
        follower.setUser(followerUser);
        follower.setFollowed_user(followedUser);
        entityManager.persist(follower);
        return follower;
    }

    @Transactional
    public void unfollowUser(AppUser followerUser, AppUser followedUser) {
        Follower follower = entityManager.createQuery(
                "SELECT f FROM Follower f WHERE f.user = :followerUser AND f.followed_user = :followedUser",
                Follower.class)
                .setParameter("followerUser", followerUser)
                .setParameter("followedUser", followedUser)
                .getSingleResult();

        entityManager.remove(follower);
    }

    public List<Follower> getFollowersForUser(Long userId) {
        return entityManager
                .createQuery("SELECT f FROM Follower f WHERE f.followed_user.user_id = :userId", Follower.class)
                .setParameter("userId", userId)
                .getResultList();
    }

}
