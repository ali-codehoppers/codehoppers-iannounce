package services;

import hibernate.entities.UserSession;
import idao.IUserSessionDao;
import java.util.List;

public class UserSessionService {

    private IUserSessionDao USDao;

    public UserSessionService(IUserSessionDao USDao) {
        this.USDao = USDao;
    }

    public List<UserSession> findByName(String name) {
        return USDao.findByName(name);
    }

    public Integer addNew(UserSession newInstance) {
        return USDao.addNew(newInstance);
    }

    public UserSession getById(Integer id) {
        return USDao.getById(id);
    }

    public void update(UserSession transientObject) {
        USDao.update(transientObject);
    }

    public void addOrUpdate(UserSession obj) {

        USDao.addOrUpdate(obj);
    }

    public void delete(UserSession persistentObject) {
        USDao.delete(persistentObject);
    }

    public void deleteById(Integer id) {
        USDao.deleteById(id);
    }

    public List<UserSession> getAll() {
        return USDao.getAll();
    }
}
