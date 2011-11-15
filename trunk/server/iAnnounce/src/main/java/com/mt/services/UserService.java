package com.mt.services;

import com.mt.hibernate.entities.User;
import com.mt.idao.IUserDao;
import java.util.List;

public class UserService {

    private IUserDao userDao;

    public UserService(IUserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> findByName(String name) {
        return userDao.findByName(name);
    }

    public List<User> findEagerCategoryList() {
        return userDao.findEagerCategoryList();
    }
    
    public Integer addNew(User newInstance) {
        return userDao.addNew(newInstance);
    }

    public User getById(Integer id) {
        return userDao.getById(id);
    }

    public void update(User transientObject) {
        userDao.update(transientObject);
    }

    public void addOrUpdate(User obj) {
        userDao.addOrUpdate(obj);
    }

    public void delete(User persistentObject) {
        userDao.delete(persistentObject);
    }

    public void deleteById(Integer id) {
        userDao.deleteById(id);
    }

    public List<User> getAll() {
        return userDao.getAll();
    }
}
