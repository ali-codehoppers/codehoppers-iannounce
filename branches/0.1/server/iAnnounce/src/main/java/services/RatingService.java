package services;

import hibernate.entities.Rating;
import idao.IRatingDao;
import java.util.List;

public class RatingService {

    private IRatingDao ratingDao;

    public RatingService(IRatingDao ratingDao) {
        this.ratingDao = ratingDao;
    }

    public List<Rating> findByName(int name) {
        return ratingDao.findByName(name);
    }

    public Integer addNew(Rating newInstance) {
        return ratingDao.addNew(newInstance);
    }

    public Rating getById(Integer id) {
        return ratingDao.getById(id);
    }

    public void update(Rating transientObject) {
        ratingDao.update(transientObject);
    }

    public void addOrUpdate(Rating obj) {
        ratingDao.addOrUpdate(obj);
    }

    public void delete(Rating persistentObject) {
        ratingDao.delete(persistentObject);
    }

    public void deleteById(Integer id) {
        ratingDao.deleteById(id);
    }

    public List<Rating> getAll() {
        return ratingDao.getAll();
    }
}
