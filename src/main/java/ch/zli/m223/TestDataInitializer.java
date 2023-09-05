package ch.zli.m223;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import ch.zli.m223.model.AppUser;
import ch.zli.m223.model.Booking;
import ch.zli.m223.model.Follower;
import ch.zli.m223.model.Review;
import ch.zli.m223.model.WorkspaceAvailability;
import ch.zli.m223.service.BookingService;
import ch.zli.m223.service.FollowerService;
import ch.zli.m223.service.ReviewService;
import ch.zli.m223.service.UserService;
import ch.zli.m223.service.WorkspaceAvailabilityService;

import javax.enterprise.event.Observes;
import io.quarkus.runtime.StartupEvent;
import javax.inject.Inject;
import java.util.Date;

@ApplicationScoped
public class TestDataInitializer {

    @Inject
    private UserService userService;

    @Inject
    private BookingService bookingService;

    @Inject
    private FollowerService followerService;

    @Inject
    private ReviewService reviewService;

    @Inject
    private WorkspaceAvailabilityService availabilityService;

    @Transactional
    public void initializeTestData(@Observes StartupEvent event) {
        AppUser user1 = new AppUser();
        user1.setName("Max");
        user1.setEmail("max@example.com");
        user1.setPassword("passwort123");
        user1.setAdmin(false);
        userService.createUser(user1);

        AppUser user2 = new AppUser();
        user2.setName("Admin2");
        user2.setEmail("Admin@admin2.com");
        user2.setPassword("passwort456");
        user2.setAdmin(true);
        userService.createUser(user2);

        Booking booking1 = new Booking();
        booking1.setUser(user1);
        booking1.setDate(new Date());
        booking1.setType("Vormittag");
        booking1.setStatus("approved");
        bookingService.createBooking(booking1);

        Booking booking2 = new Booking();
        booking2.setUser(user2);
        booking2.setDate(new Date());
        booking2.setType("Nachmittag");
        booking2.setStatus("pending");
        bookingService.createBooking(booking2);

        Follower follower1 = new Follower();
        follower1.setUser(user1);
        follower1.setFollowed_user(user2);
        followerService.followUser(user1, user2);

        Follower follower2 = new Follower();
        follower2.setUser(user2);
        follower2.setFollowed_user(user1);
        followerService.followUser(user2, user1);

        Review review1 = new Review();
        review1.setUser(user1);
        review1.setBooking(booking1);
        review1.setRating(4);
        review1.setComment("Toller Raum!");
        reviewService.createReview(review1);

        Review review2 = new Review();
        review2.setUser(user2);
        review2.setBooking(booking2);
        review2.setRating(3);
        review2.setComment("Ok, könnte besser sein.");
        reviewService.createReview(review2);

        WorkspaceAvailability availability1 = new WorkspaceAvailability();
        availability1.setDate(new Date());
        availability1.setTotalWorkspaces(50);
        availability1.setAvailableWorkspace(30);
        availability1.setOccupiedWorkspaces(20);
        availabilityService.createAvailability(availability1);

        WorkspaceAvailability availability2 = new WorkspaceAvailability();
        availability2.setDate(new Date());
        availability2.setTotalWorkspaces(60);
        availability2.setAvailableWorkspace(40);
        availability2.setOccupiedWorkspaces(20);
        availabilityService.createAvailability(availability2);
    }
}
