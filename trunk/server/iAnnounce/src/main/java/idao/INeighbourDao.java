/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package idao;

import hibernate.entities.Neighbour;
import java.util.List;

/**
 *
 * @author Muaz
 */
public interface INeighbourDao extends IDaoGeneric<Neighbour, Integer> {
        List<Neighbour> findMembershipStatus(int neighbourhoodId,int personId);
        List<Neighbour> findByPerson(int personId);
    
}
