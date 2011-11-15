package com.mt.hibernate.entities;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@javax.persistence.Table(name = "states")
@NamedQueries({
    @NamedQuery(name = "State.findByName", query = "select c from State c where c.name like ?")
})
public class State implements Serializable {

    @Expose
    private int id;
    @Expose
    private String name;
    @Expose
    private int countryId;
    private Country country;
    private List<User> users;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public State() {
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Column(insertable = false, updatable = false)
    public int getCountryId() {
        return countryId;
    }

    @ManyToOne(targetEntity = Country.class, fetch = FetchType.LAZY)
    public Country getCountry() {
        return country;
    }

    @OneToMany(targetEntity = User.class, fetch = FetchType.LAZY, mappedBy = "stateId")
    public List<User> getUsers() {
        return users;
    }
}
