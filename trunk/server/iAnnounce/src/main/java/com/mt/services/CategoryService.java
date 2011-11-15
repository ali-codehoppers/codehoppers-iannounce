package com.mt.services;

import com.mt.hibernate.entities.Category;
import com.mt.idao.ICategoryDao;
import java.util.List;

public class CategoryService {

    private ICategoryDao categoryDao;

    public CategoryService(ICategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public List<Category> findByName(String name) {
        return categoryDao.findByName(name);
    }

    public List<Category> findEagerCategoryList() {
        return categoryDao.findEagerCategoryList();
    }
    
    public Integer addNew(Category newInstance) {
        return categoryDao.addNew(newInstance);
    }

    public Category getById(Integer id) {
        return categoryDao.getById(id);
    }

    public void update(Category transientObject) {
        categoryDao.update(transientObject);
    }

    public void addOrUpdate(Category obj) {
        categoryDao.addOrUpdate(obj);
    }

    public void delete(Category persistentObject) {
        categoryDao.delete(persistentObject);
    }

    public void deleteById(Integer id) {
        categoryDao.deleteById(id);
    }

    public List<Category> getAll() {
        return categoryDao.getAll();
    }
}
