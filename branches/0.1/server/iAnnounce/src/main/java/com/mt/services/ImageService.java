package com.mt.services;

import com.mt.hibernate.entities.Image;
import com.mt.idao.IImageDao;
import java.util.List;

public class ImageService {

    private IImageDao imageDao;

    public ImageService(IImageDao imageDao) {
        this.imageDao = imageDao;
    }

    public List<Image> findByName(String name) {
        return imageDao.findByName(name);
    }

    public Integer addNew(Image newInstance) {
        return imageDao.addNew(newInstance);
    }

    public Image getById(Integer id) {
        return imageDao.getById(id);
    }

    public void update(Image transientObject) {
        imageDao.update(transientObject);
    }

    public void addOrUpdate(Image obj) {
        imageDao.addOrUpdate(obj);
    }

    public void delete(Image persistentObject) {
        imageDao.delete(persistentObject);
    }

    public void deleteById(Integer id) {
        imageDao.deleteById(id);
    }

    public List<Image> getAll() {
        return imageDao.getAll();
    }
}
