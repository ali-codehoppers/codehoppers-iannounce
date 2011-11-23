package com.mt.hibernate.entities;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
    private List<Company> companys;

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

    public void setCompanys(List<Company> companys) {
        this.companys = companys;
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

    @Column(insertable = false, updatable = false, name="countryId")
    public int getCountryId() {
        return countryId;
    }

    @ManyToOne(targetEntity = Country.class, fetch = FetchType.LAZY)
    @JoinColumn(name="countryId")
    public Country getCountry() {
        return country;
    }

    @OneToMany(targetEntity = Company.class, fetch = FetchType.LAZY, mappedBy = "stateId")
    public List<Company> getCompanys() {
        return companys;
    }
}
