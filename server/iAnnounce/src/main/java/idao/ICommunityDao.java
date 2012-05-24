/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package idao;

import hibernate.entities.Community;
import java.util.List;

/**
 *
 * @author Muaz
 */
public interface ICommunityDao extends IDaoGeneric<Community, Integer> {
    List<Community> findByOwner(int id);
}
