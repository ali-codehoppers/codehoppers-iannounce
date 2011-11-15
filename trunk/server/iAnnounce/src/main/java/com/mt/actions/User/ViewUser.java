/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.actions.User;

import com.mt.hibernate.entities.User;
import com.mt.services.UserService;
import com.opensymphony.xwork2.ActionSupport;

public class ViewUser extends ActionSupport
{

    private int userId;
    private String message;
    private User user;
    
    private UserService userService;

    public void setUser(User user) {
        this.user = user;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    
    @Override
    public String execute() throws Exception
    {
        user = userService.getById(userId);
        return SUCCESS;
    }

    public User getUser()
    {
        return user;
    }

    public String getMessage()
    {
        return message;
    }
    
}
