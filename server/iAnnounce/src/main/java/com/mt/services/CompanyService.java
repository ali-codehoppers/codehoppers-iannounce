package com.mt.services;

import com.mt.hibernate.entities.Company;
import com.mt.idao.ICompanyDao;
import java.util.List;

public class CompanyService {

    private ICompanyDao iCompanyDao;

    public CompanyService(ICompanyDao iCompanyDao) {
        this.iCompanyDao = iCompanyDao;
    }

    public List<Company> findByName(String name) {
        return iCompanyDao.findByName(name);
    }

    public Integer addNew(Company newInstance) {
        return iCompanyDao.addNew(newInstance);
    }

    public Company getById(Integer id) {
        return iCompanyDao.getById(id);
    }

    public void update(Company transientObject) {
        iCompanyDao.update(transientObject);
    }

    public void addOrUpdate(Company obj) {
        iCompanyDao.addOrUpdate(obj);
    }

    public void delete(Company persistentObject) {
        iCompanyDao.delete(persistentObject);
    }

    public void deleteById(Integer id) {
        iCompanyDao.deleteById(id);
    }

    public List<Company> getAll() {
        return iCompanyDao.getAll();
    }
}
