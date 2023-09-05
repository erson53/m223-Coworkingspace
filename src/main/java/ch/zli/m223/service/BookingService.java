package ch.zli.m223.service;

import ch.zli.m223.model.Booking;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class BookingService {

    @Inject
    EntityManager entityManager;

    @Transactional
    public Booking createBooking(Booking newBooking) {
        try {
            entityManager.persist(newBooking);
            return newBooking;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Booking> getAllBookings() {
        return entityManager.createQuery("SELECT b FROM Booking b", Booking.class).getResultList();
    }

    @Transactional
    public boolean deleteBooking(Long bookingId) {
        Booking bookingToDelete = entityManager.find(Booking.class, bookingId);
        if (bookingToDelete != null) {
            entityManager.remove(bookingToDelete);
            return true;
        } else {
            return false;
        }
    }

    public List<Booking> getBookingsByStatus(String status) {
        TypedQuery<Booking> query = entityManager.createQuery(
                "SELECT b FROM Booking b WHERE b.status = :status", Booking.class);
        query.setParameter("status", status);
        return query.getResultList();
    }

    @Transactional
    public Booking updateBookingStatus(Long bookingId, String newStatus) {
        Booking bookingToUpdate = entityManager.find(Booking.class, bookingId);

        if (bookingToUpdate != null) {
            bookingToUpdate.setStatus(newStatus);
            return entityManager.merge(bookingToUpdate);
        } else {
            return null;
        }
    }
}