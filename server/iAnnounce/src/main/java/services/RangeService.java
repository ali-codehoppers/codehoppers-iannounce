package services;

import hibernate.entities.Rangee;
import idao.IRangeDao;
import java.util.List;

public class RangeService {

    private IRangeDao RangeDao;

    public RangeService(IRangeDao RangeDao) {
        this.RangeDao = RangeDao;
    }

    public List<Rangee> findByName(String name) {
        return RangeDao.findByName(name);
    }

    public Integer addNew(Rangee newInstance) {
        return RangeDao.addNew(newInstance);
    }

    public Rangee getById(Integer id) {
        return RangeDao.getById(id);
    }

    public void update(Rangee transientObject) {
        RangeDao.update(transientObject);
    }

    public void addOrUpdate(Rangee obj) {
        RangeDao.addOrUpdate(obj);
    }

    public void delete(Rangee persistentObject) {
        RangeDao.delete(persistentObject);
    }

    public void deleteById(Integer id) {
        RangeDao.deleteById(id);
    }

    public List<Rangee> getAll() {
        return RangeDao.getAll();
    }
}
