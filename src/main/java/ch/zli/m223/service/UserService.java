package ch.zli.m223.service;

import ch.zli.m223.model.AppUser;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserService {

    @Inject
    EntityManager entityManager;

    @Transactional
    public AppUser createUser(AppUser newUser) {
        try {
            entityManager.persist(newUser);
            return newUser;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<AppUser> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM AppUser u", AppUser.class).getResultList();
    }

    public AppUser getUserById(Long userId) {
        return entityManager.find(AppUser.class, userId);
    }

    @Transactional
    public boolean deleteUser(Long userId) {
        AppUser userToDelete = entityManager.find(AppUser.class, userId);
        if (userToDelete != null) {
            entityManager.remove(userToDelete);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public AppUser updateUser(Long userId, AppUser updatedUser) {
        AppUser existingUser = entityManager.find(AppUser.class, userId);
        if (existingUser != null) {
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setAdmin(updatedUser.isAdmin());
            return entityManager.merge(existingUser);
        } else {
            return null;
        }
    }

    public Optional<AppUser> findByEmail(String email) {
        TypedQuery<AppUser> query = entityManager.createQuery(
                "SELECT u FROM AppUser u WHERE u.email = :email", AppUser.class);
        query.setParameter("email", email);
        return query.getResultStream().findFirst();
    }
}
