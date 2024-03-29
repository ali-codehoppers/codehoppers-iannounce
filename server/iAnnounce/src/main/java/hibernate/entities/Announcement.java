package hibernate.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.*;

@Entity
@Table(name = "announcement")
@NamedQueries({
    @NamedQuery(name = "Announcement.findByName", query = "select a from Announcement a where a.username_FK like ?"),
    @NamedQuery(name = "Announcement.findAllByNeighbourhoodId", query = "select a from Announcement a where a.neighbourhood_id = ?")
})
//get announcements by username
public class Announcement implements Serializable {

    private int a_id;
    private double latitude;
    private double longitude;
    private String announcement;
    private int radius;
    private boolean isExpired;
    private Timestamp ttime;
    private Timestamp expireTime;
    private boolean type;
    private String username_FK;
    private int totalRating;
    private int neighbourhood_id;

    public Announcement(int a_id, double latitude, double longitude, String announcement, int radius, boolean isExpired, Timestamp ttime, Timestamp expireTime, boolean type, String username_FK, int totalRating, int neighbourhood_id) {
        this.a_id = a_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.announcement = announcement;
        this.radius = radius;
        this.isExpired = isExpired;
        this.ttime = ttime;
        this.expireTime = expireTime;
        this.type = type;
        this.username_FK = username_FK;
        this.totalRating = totalRating;
        this.neighbourhood_id = neighbourhood_id;
    }

    public Announcement() {
    }

    @Id
    @GeneratedValue
    public int getA_id() {
        return a_id;
    }

    public void setA_id(int a_id) {
        this.a_id = a_id;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Timestamp getTtime() {
        return ttime;
    }

    public void setTtime(Timestamp ttime) {
        this.ttime = ttime;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public void setExpireTime(Timestamp expireTime) {
        this.expireTime = expireTime;
    }



    public void setIsExpired(boolean isExpired) {
        this.isExpired = isExpired;
    }

    public Timestamp getExpireTime() {
        return expireTime;
    }



    public boolean isIsExpired() {
        return isExpired;
    }

    public String getUsername_FK() {
        return username_FK;
    }

    public void setUsername_FK(String username_FK) {
        this.username_FK = username_FK;
    }

    public int getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
    }

    public int getNeighbourhood_id() {
        return neighbourhood_id;
    }

    public void setNeighbourhood_id(int neighbourhood_id) {
        this.neighbourhood_id = neighbourhood_id;
    }
}
