package com.mt.services;

import com.mt.hibernate.entities.Country;
import com.mt.idao.ICountryDao;
import java.util.List;

public class CountryService {

    private ICountryDao countryDao;

    public CountryService(ICountryDao countryDao) {
        this.countryDao = countryDao;
    }

    public List<Country> findByName(String name) {
        return countryDao.findByName(name);
    }

    public Integer addNew(Country newInstance) {
        return countryDao.addNew(newInstance);
    }

    public Country getById(Integer id) {
        return countryDao.getById(id);
    }

    public void update(Country transientObject) {
        countryDao.update(transientObject);
    }

    public void addOrUpdate(Country obj) {
        countryDao.addOrUpdate(obj);
    }

    public void delete(Country persistentObject) {
        countryDao.delete(persistentObject);
    }

    public void deleteById(Integer id) {
        countryDao.deleteById(id);
    }

    public List<Country> getAll() {
        return countryDao.getAll();
    }
}
