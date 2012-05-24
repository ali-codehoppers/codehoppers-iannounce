/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Muaz
 */
@Entity
@NamedQueries({
@NamedQuery(name="Community.findByOwner", query="select c from Community c where c.owner = ?")
})
public class Community implements Serializable{
    
    private int id;
    private String title;
    private String description;
    private boolean isPrivate;
    private int owner;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Community() {
    }

    
    public String getDescription() {
        return description;
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public boolean isIsPrivate() {
        return isPrivate;
    }


    public int getOwner() {
        return owner;
    }

    public String getTitle() {
        return title;
    }

    
    
}
