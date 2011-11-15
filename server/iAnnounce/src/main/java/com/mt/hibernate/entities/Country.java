package com.mt.hibernate.entities;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 *
 * @author CodeHopper
 */
@Entity
@javax.persistence.Table(name = "countries")
@NamedQueries(
  {
    @NamedQuery(name = "Country.findByName", query = "select c from Country c where c.name like ?")
  })
public class Country implements Serializable
{

    @Expose
    private int id;
    @Expose
    private String name;
    private List<State> states;

    public void setId(int id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setStates(List<State> states)
    {
        this.states = states;
    }

    public Country()
    {
        
    }

    public Country(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Country(String name)
    {
        this.name = name;
    }

    @Id
    @GeneratedValue
    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    @OneToMany(targetEntity = State.class, fetch = FetchType.LAZY, mappedBy = "countryId" )
    public List<State> getStates()
    {
        return states;
    }
}
