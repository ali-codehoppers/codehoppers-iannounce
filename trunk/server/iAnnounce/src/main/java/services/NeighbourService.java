/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import hibernate.entities.Community;
import hibernate.entities.Neighbour;
import idao.INeighbourDao;
import java.util.List;

/**
 *
 * @author Muaz
 */
public class NeighbourService {
    
     private INeighbourDao iNeighboursDao;

    public NeighbourService(INeighbourDao iNeighboursDao) {
        this.iNeighboursDao = iNeighboursDao;
    }
    public List<Neighbour> findMembershipStatus(int neighbourhoodId,int personId){
        return iNeighboursDao.findMembershipStatus(neighbourhoodId, personId);
    }
    public List<Neighbour> findByPerson(int personId){
        return iNeighboursDao.findByPerson(personId);
    }
    public Integer addNew(Neighbour newInstance) {
        return iNeighboursDao.addNew(newInstance);
    }

    public Neighbour getById(Integer id) {
        return iNeighboursDao.getById(id);
    }

    public void update(Neighbour transientObject) {
        iNeighboursDao.update(transientObject);
    }

    public void addOrUpdate(Neighbour obj) {
        iNeighboursDao.addOrUpdate(obj);
    }

    public void delete(Neighbour persistentObject) {
        iNeighboursDao.delete(persistentObject);
    }

    public void deleteById(Integer id) {
        iNeighboursDao.deleteById(id);
    }

    public List<Neighbour> getAll() {
        return iNeighboursDao.getAll();
    }
    
    public List getNearbyObjects(){
        return iNeighboursDao.getNearbyObjects();
    }
    
}
