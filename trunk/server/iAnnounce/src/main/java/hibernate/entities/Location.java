/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Muaz
 */
@Entity
@Table(name = "location")
public class Location implements Serializable {

    private int id;
    private int neighbourhoodId;
    private String name;
    private String description;
    private double latitude;
    private double longitude;
    private int addedBy;

    public void setAddedBy(int addedBy) {
        this.addedBy = addedBy;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setNeighbourhoodId(int neighbourhoodId) {
        this.neighbourhoodId = neighbourhoodId;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Location() {
    }

    public int getAddedBy() {
        return addedBy;
    }

    public String getDescription() {
        return description;
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getNeighbourhoodId() {
        return neighbourhoodId;
    }

    public String getName() {
        return name;
    }


}
