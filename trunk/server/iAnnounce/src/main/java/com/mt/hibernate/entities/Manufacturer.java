package com.mt.hibernate.entities;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.NamedQuery;

/**
 *
 * @author CodeHopper
 */
@Entity
@javax.persistence.Table(name = "manufacturers")
@NamedQuery(name = "Manufacturer.findByName", query = "select c from Manufacturer c where c.name like ?")
public class Manufacturer implements Serializable {

    @Expose
    private int id;
    @Expose
    private String name;
    @Expose
    private String address;
    @Expose
    private String city;
    @Expose
    private String state;
    @Expose
    private String contactNo;
    @Expose
    private String email;
    @Expose
    private String website;
    private List<Product> productsList;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setProductsList(List<Product> productsList) {
        this.productsList = productsList;
    }

    public Manufacturer() {
    }

    public Manufacturer(int id, String name, String address, String city, String state, String contactNo, String email, String website) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.contactNo = contactNo;
        this.email = email;
        this.website = website;
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getContactNo() {
        return contactNo;
    }

    public String getEmail() {
        return email;
    }

    public String getState() {
        return state;
    }

    public String getWebsite() {
        return website;
    }

    @OneToMany(targetEntity = Product.class, fetch = FetchType.LAZY, mappedBy = "manufacturerId")
    public List<Product> getProductsList() {
        return productsList;
    }
}
