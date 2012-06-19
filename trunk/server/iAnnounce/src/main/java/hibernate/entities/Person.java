package hibernate.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "person")
@NamedQuery(name = "Person.findByName", query = "select p from Person p where p.username like ?")
//get user by username
public class Person implements Serializable {

    private int id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String username;
    private String password;
    private boolean gender;
    private boolean type;
    private String email;
    private String verificationcode;
    private double latitude;
    private double longitude;
    private boolean active;
    private boolean verified;
    private float avgrating;

    public Person() {
    }

    public Person(int id, String firstName, String lastName, Date dateOfBirth, String username, String password, boolean gender, boolean type, String email, String verificationcode, double latitude, double longitude, boolean active, boolean verified, float avgrating) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.type = type;
        this.verificationcode = verificationcode;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
        this.active = active;
        this.verified = verified;
        this.avgrating = avgrating;
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getVerificationcode() {
        return verificationcode;
    }

    public void setVerificationcode(String verificationcode) {
        this.verificationcode = verificationcode;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public float getAvgrating() {
        return avgrating;
    }

    public void setAvgrating(float avgrating) {
        this.avgrating = avgrating;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    @Temporal(javax.persistence.TemporalType.DATE)
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
