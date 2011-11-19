/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.actions.User;

import com.mt.hibernate.entities.State;
import com.mt.hibernate.entities.User;
import com.mt.services.StateService;
import com.mt.services.UserService;
import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author CodeHopper
 */
public class AddUser extends ActionSupport {

    private int countryId;
    private int stateId;
    private String name;
    private String address;
    private String city;
    private String contactNo;
    private String contactNo2;
    private String email;
    private String userName;
    private String password;
    User user;
    private UserService userervice;
    private StateService stateService;
    private String message;

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public void setContactNo2(String contactNo2) {
        this.contactNo2 = contactNo2;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setStateService(StateService stateService) {
        this.stateService = stateService;
    }

    public void setUserService(UserService userervice) {
        this.userervice = userervice;
    }

    @Override
    public String execute() throws Exception {

        System.out.println("State Id : " + stateId);

        
        
        State state = stateService.getById(stateId);

        if (state == null) {
            System.out.println("State is null");
        }
        else
            System.out.println("State is "+state.getName());

        user = new User();
        user.setName(name);
        user.setAddress(address);
        user.setCity(city);
        user.setContactNo(contactNo);
        user.setContactNo2(contactNo2);
        user.setEmail(email);
        user.setPassword(password);
        user.setState(state);
        user.setUsername(userName);

        userervice.addNew(user);
        message = "User added sucessfully";
        return SUCCESS;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }
}
