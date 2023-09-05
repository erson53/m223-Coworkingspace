package ch.zli.m223.service;

import ch.zli.m223.model.WorkspaceAvailability;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.text.ParseException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class WorkspaceAvailabilityService {

    @Inject
    EntityManager entityManager;

    public WorkspaceAvailability getAvailabilityByDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(dateString);
            List<WorkspaceAvailability> availabilities = entityManager
                    .createQuery("SELECT wa FROM WorkspaceAvailability wa WHERE wa.date = :date",
                            WorkspaceAvailability.class)
                    .setParameter("date", date)
                    .getResultList();

            if (!availabilities.isEmpty()) {
                return availabilities.get(0);
            } else {
                return null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<WorkspaceAvailability> getAllAvailabilities() {
        return entityManager.createQuery("SELECT wa FROM WorkspaceAvailability wa", WorkspaceAvailability.class)
                .getResultList();
    }

    @Transactional
    public WorkspaceAvailability createAvailability(WorkspaceAvailability newAvailability) {
        try {
            entityManager.persist(newAvailability);
            return newAvailability;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public WorkspaceAvailability updateAvailabilityByDate(String date, WorkspaceAvailability updatedAvailability) {
        WorkspaceAvailability existingAvailability = entityManager.find(WorkspaceAvailability.class, date);

        if (existingAvailability != null) {
            existingAvailability.setTotalWorkspaces(updatedAvailability.getTotalWorkspaces());
            existingAvailability.setAvailableWorkspace(updatedAvailability.getAvailableWorkspace());
            existingAvailability.setOccupiedWorkspaces(updatedAvailability.getOccupiedWorkspaces());
            return entityManager.merge(existingAvailability);
        } else {
            return null;
        }
    }
}
