/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


/**
 *
 * @author Muaz
 */
@Entity
@NamedQueries({
@NamedQuery(name = "Neighbour.findMembershipStatus", query = "select n from Neighbour n where n.neighbourhoodId = ? AND n.personId = ? "),
@NamedQuery(name = "Neighbour.findByPerson", query = "select n from Neighbour n where n.personId = ? ")
})
public class Neighbour implements Serializable {

    private int id;
    private int neighbourhoodId;
    private Community neigbhour;
    private int personId;

    public void setId(int id) {
        this.id = id;
    }

    public void setNeigbhour(Community neigbhour) {
        this.neigbhour = neigbhour;
    }

    public void setNeighbourhoodId(int neighbourhoodId) {
        this.neighbourhoodId = neighbourhoodId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public Neighbour() {
       // System.out.println("Neighbour Entity");
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    @ManyToOne(targetEntity = Community.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "neighbourhoodId")
    public Community getNeigbhour() {
        return neigbhour;
    }

    @Column(insertable = false, updatable = false, name = "neighbourhoodId")
    public int getNeighbourhoodId() {
        return neighbourhoodId;
    }

    public int getPersonId() {
        return personId;
    }
}
