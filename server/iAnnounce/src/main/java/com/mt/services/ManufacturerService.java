package com.mt.services;

import com.mt.hibernate.entities.Manufacturer;
import com.mt.idao.IManufacturerDao;
import java.util.List;

public class ManufacturerService {

    private IManufacturerDao manufacturerDao;

    public ManufacturerService(IManufacturerDao manufacturerDao) {
        this.manufacturerDao = manufacturerDao;
    }

    public List<Manufacturer> findByName(String name) {
        return manufacturerDao.findByName(name);
    }

    public Integer addNew(Manufacturer newInstance) {
        return manufacturerDao.addNew(newInstance);
    }

    public Manufacturer getById(Integer id) {
        return manufacturerDao.getById(id);
    }

    public void update(Manufacturer transientObject) {
        manufacturerDao.update(transientObject);
    }

    public void addOrUpdate(Manufacturer obj) {
        manufacturerDao.addOrUpdate(obj);
    }

    public void delete(Manufacturer persistentObject) {
        manufacturerDao.delete(persistentObject);
    }

    public void deleteById(Integer id) {
        manufacturerDao.deleteById(id);
    }

    public List<Manufacturer> getAll() {
        return manufacturerDao.getAll();
    }
}
