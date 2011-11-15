package com.mt.services;

import com.mt.hibernate.entities.SubCategory;
import com.mt.idao.ISubCategoryDao;
import java.util.List;

public class SubCategoryService {

    private ISubCategoryDao subCategoryDao;

    public SubCategoryService(ISubCategoryDao subSubCategoryDao) {
        this.subCategoryDao = subSubCategoryDao;
    }

    public List<SubCategory> findByName(String name) {
        return subCategoryDao.findByName(name);
    }

    public Integer addNew(SubCategory newInstance) {
        return subCategoryDao.addNew(newInstance);
    }

    public SubCategory getById(Integer id) {
        return subCategoryDao.getById(id);
    }

    public void update(SubCategory transientObject) {
        subCategoryDao.update(transientObject);
    }

    public void addOrUpdate(SubCategory obj) {
        subCategoryDao.addOrUpdate(obj);
    }

    public void delete(SubCategory persistentObject) {
        subCategoryDao.delete(persistentObject);
    }

    public void deleteById(Integer id) {
        subCategoryDao.deleteById(id);
    }

    public List<SubCategory> getAll() {
        return subCategoryDao.getAll();
    }
}
