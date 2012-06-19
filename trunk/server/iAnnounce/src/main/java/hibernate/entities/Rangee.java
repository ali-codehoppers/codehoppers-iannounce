package hibernate.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "rangee")
@NamedQuery(name = "Rangee.findByName", query = "select p from Rangee p where p.s like ?")
//get range by name
public class Rangee {

    private int ra_id;
    private String s;
    private int valuee;

    public Rangee() {
    }

    public Rangee(int ra_id, String s, int valuee) {
        this.ra_id = ra_id;
        this.s = s;
        this.valuee = valuee;
    }

    @Id
    @GeneratedValue
    public int getRa_id() {
        return ra_id;
    }

    public void setRa_id(int ra_id) {
        this.ra_id = ra_id;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public int getValuee() {
        return valuee;
    }

    public void setValuee(int valuee) {
        this.valuee = valuee;
    }
}
