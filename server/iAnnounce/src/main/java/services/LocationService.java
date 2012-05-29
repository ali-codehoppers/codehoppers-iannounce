/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import hibernate.entities.Location;
import idao.ILocationDao;
import java.util.List;

/**
 *
 * @author Muaz
 */
public class LocationService {
        private ILocationDao locationDao;

    public LocationService(ILocationDao locationDao) {
        this.locationDao = locationDao;
    }
   /* public List<Neighbour> findMembershipStatus(int neighbourhoodId,int personId){
        return iNeighboursDao.findMembershipStatus(neighbourhoodId, personId);
    }
    public List<Neighbour> findByPerson(int personId){
        return iNeighboursDao.findByPerson(personId);
    }*/
    public Integer addNew(Location newInstance) {
        return this.locationDao.addNew(newInstance);
    }

    public Location getById(Integer id) {
        return this.locationDao.getById(id);
    }

    public void update(Location transientObject) {
        this.locationDao.update(transientObject);
    }

    public void addOrUpdate(Location obj) {
        this.locationDao.addOrUpdate(obj);
    }

    public void delete(Location persistentObject) {
        this.locationDao.delete(persistentObject);
    }

    public void deleteById(int id) {
        this.locationDao.deleteById(id);
    }

     public List getNearbyLocations(double latitude,double longitude,int range,int neighbourId){
        return locationDao.getNearbyLocations(latitude, longitude, range, neighbourId);
    }
    
}
