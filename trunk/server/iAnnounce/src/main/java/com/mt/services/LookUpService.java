package com.mt.services;

import com.mt.hibernate.entities.LookUp;
import com.mt.idao.ILookUpDao;
import java.util.List;

public class LookUpService {

    private ILookUpDao lookUpDao;

    public LookUpService(ILookUpDao lookUpDao) {
        this.lookUpDao = lookUpDao;
    }

    public List<LookUp> findByName(String name) {
        return lookUpDao.findByName(name);
    }

    public Integer addNew(LookUp newInstance) {
        return lookUpDao.addNew(newInstance);
    }

    public LookUp getById(Integer id) {
        return lookUpDao.getById(id);
    }

    public void update(LookUp transientObject) {
        lookUpDao.update(transientObject);
    }

    public void addOrUpdate(LookUp obj) {
        lookUpDao.addOrUpdate(obj);
    }

    public void delete(LookUp persistentObject) {
        lookUpDao.delete(persistentObject);
    }

    public void deleteById(Integer id) {
        lookUpDao.deleteById(id);
    }

    public List<LookUp> getAll() {
        return lookUpDao.getAll();
    }
}
