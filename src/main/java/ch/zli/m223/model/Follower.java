package ch.zli.m223.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Follower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long follower_id;

    @ManyToOne
    private AppUser user;

    @ManyToOne
    private AppUser followed_user;

    public Long getFollower_id() {
        return follower_id;
    }

    public void setFollower_id(Long follower_id) {
        this.follower_id = follower_id;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public AppUser getFollowed_user() {
        return followed_user;
    }

    public void setFollowed_user(AppUser followed_user) {
        this.followed_user = followed_user;
    }
}
