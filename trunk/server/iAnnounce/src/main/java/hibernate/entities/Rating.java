package hibernate.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "rating")
@NamedQuery(name = "Rating.findByName", query = "select r from Rating r where r.a_id = ?")
//get rating by announcement
public class Rating implements Serializable {

    private int r_id;
    private String username;
    private int a_id;
    private boolean status;

    public Rating(int r_id, String username, int a_id, boolean status) {
        this.r_id = r_id;
        this.username = username;
        this.a_id = a_id;
        this.status = status;
    }

    public Rating() {
    }

    @Id
    @GeneratedValue
    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public int getA_id() {
        return a_id;
    }

    public void setA_id(int a_id) {
        this.a_id = a_id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
