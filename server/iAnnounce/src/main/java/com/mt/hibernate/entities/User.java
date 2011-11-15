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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@javax.persistence.Table(name = "users")
@NamedQueries({
    @NamedQuery(name = "User.findByName", query = "select u from User u where u.name like ?")})
public class User {

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
    private String contactNo;
    @Expose
    private String contactNo2;
    @Expose
    private String email;
    @Expose
    private String username;
    @Expose
    private String password;

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

    public void setStateId(String state) {
        this.stateId = stateId;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public void setContactNo2(String contactNo2) {
        this.contactNo2 = contactNo2;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
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
    public State getState() {
        return state;
    }

    @Column(insertable = false, updatable = false, name = "stateId")
    public int getStateId() {
        return stateId;
    }

    @Column(name = "contactNo")
    public String getContactNo() {
        return contactNo;
    }

    @Column(name = "contactNo2")
    public String getContactNo2() {
        return contactNo2;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
