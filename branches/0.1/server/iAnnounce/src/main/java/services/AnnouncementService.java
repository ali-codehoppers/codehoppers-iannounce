package services;

import hibernate.entities.Announcement;
import idao.IAnnouncementDao;
import java.util.List;

public class AnnouncementService {

    private IAnnouncementDao ADao;

    public AnnouncementService(IAnnouncementDao ADao) {
        this.ADao = ADao;
    }

    public List<Announcement> findByName(String name) {
        return ADao.findByName(name);
    }

    public Integer addNew(Announcement newInstance) {
        return ADao.addNew(newInstance);
    }

    public Announcement getById(Integer id) {
        return ADao.getById(id);
    }

    public void update(Announcement transientObject) {
        ADao.update(transientObject);
    }

    public void addOrUpdate(Announcement obj) {
        ADao.addOrUpdate(obj);
    }

    public void delete(Announcement persistentObject) {
        ADao.delete(persistentObject);
    }

    public void deleteById(Integer id) {
        ADao.deleteById(id);
    }

    public List<Announcement> getAll() {
        return ADao.getAll();
    }
}
