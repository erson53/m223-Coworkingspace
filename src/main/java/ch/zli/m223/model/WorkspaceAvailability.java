package ch.zli.m223.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class WorkspaceAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long availability_id;

    private Date date;
    private int totalWorkspaces;
    private int availableWorkspace;
    private int occupiedWorkspaces;

    public Long getAvailability_id() {
        return availability_id;
    }

    public void setAvailability_id(Long availability_id) {
        this.availability_id = availability_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTotalWorkspaces() {
        return totalWorkspaces;
    }

    public void setTotalWorkspaces(int totalWorkspaces) {
        this.totalWorkspaces = totalWorkspaces;
    }

    public int getAvailableWorkspace() {
        return availableWorkspace;
    }

    public void setAvailableWorkspace(int availableWorkspace) {
        this.availableWorkspace = availableWorkspace;
    }

    public int getOccupiedWorkspaces() {
        return occupiedWorkspaces;
    }

    public void setOccupiedWorkspaces(int occupiedWorkspaces) {
        this.occupiedWorkspaces = occupiedWorkspaces;
    }
}
