package com.mt.hibernate.entities;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@javax.persistence.Table(name = "lookup")
@NamedQueries({
    @NamedQuery(name = "LookUp.findByName", query = "select c from LookUp c where c.type like ?")})
public class LookUp implements Serializable {

    @Expose
    private int id;
    @Expose
    private String type;
    @Expose
    private String code;
    @Expose
    private String description;

    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }
}
