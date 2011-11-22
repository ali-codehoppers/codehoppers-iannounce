/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.hibernate.entities;

import com.google.gson.annotations.Expose;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@javax.persistence.Table(name = "companies")
@NamedQueries({
    @NamedQuery(name = "Company.findByName", query = "select u from Company u where u.name like ?")})
public class Company {

    @Expose
    private int id;
    @Expose
    private String name;
    @Expose
    private String address;
    @Expose
    private String city;
    @Expose
    private int stateId;
    @Expose
    private State state;
    @Expose
    private String zip;
    @Expose
    private String phoneNo;
    @Expose
    private String faxNo;
    @Expose
    private String webAddress;
    @Expose
    private String companyCategory;

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

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setCompanyCategory(String companyCategory) {
        this.companyCategory = companyCategory;
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

    @ManyToOne(targetEntity = State.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "stateId")
    public State getState() {
        return state;
    }

    @Column(insertable = false, updatable = false, name = "stateId")
    public int getStateId() {
        return stateId;
    }

    public String getFaxNo() {
        return faxNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getWebAddress() {
        return webAddress;
    }

    public String getZip() {
        return zip;
    }

    public String getCompanyCategory() {
        return companyCategory;
    }
}
