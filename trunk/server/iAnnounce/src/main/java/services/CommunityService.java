/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import hibernate.entities.Community;
import idao.ICommunityDao;
import java.util.List;

/**
 *
 * @author Muaz
 */
public class CommunityService {
    
     private ICommunityDao communityDao;

    public CommunityService(ICommunityDao CDao) {
        this.communityDao = CDao;
    }

    public List<Community> findByOwner(int id){
        return communityDao.findByOwner(id);
    }
    public Integer addNew(Community newInstance) {
        return communityDao.addNew(newInstance);
    }

    public Community getById(Integer id) {
        return communityDao.getById(id);
    }

    public void update(Community transientObject) {
        communityDao.update(transientObject);
    }

    public void addOrUpdate(Community obj) {
        communityDao.addOrUpdate(obj);
    }

    public void delete(Community persistentObject) {
        communityDao.delete(persistentObject);
    }

    public void deleteById(Integer id) {
        communityDao.deleteById(id);
    }

    public List<Community> getAll() {
        return communityDao.getAll();
    }
    
}
