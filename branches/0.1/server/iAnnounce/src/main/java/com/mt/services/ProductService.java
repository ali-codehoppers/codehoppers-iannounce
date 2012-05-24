package com.mt.services;

import com.mt.hibernate.entities.Product;
import com.mt.idao.IProductDao;
import java.util.List;

public class ProductService {
    
    private IProductDao productDao;
    
    public ProductService(IProductDao productDao) {
        this.productDao = productDao;
    }
    
    public List<Product> findByName(String name) {
        return productDao.findByName(name);
    }
    
    public List<Product> findByCategory(int id) {
        return productDao.findByCategory(""+id);
    }
    
    public Integer addNew(Product newInstance) {
        return productDao.addNew(newInstance);
    }
    
    public Product getById(Integer id) {
        return productDao.getById(id);
    }
    
    public void update(Product transientObject) {
        productDao.update(transientObject);
    }
    
    public void addOrUpdate(Product obj) {
        productDao.addOrUpdate(obj);
    }
    
    public void delete(Product persistentObject) {
        productDao.delete(persistentObject);
    }
    
    public void deleteById(Integer id) {
        productDao.deleteById(id);
    }
    
    public List<Product> getAll() {
        return productDao.getAll();
    }
}
