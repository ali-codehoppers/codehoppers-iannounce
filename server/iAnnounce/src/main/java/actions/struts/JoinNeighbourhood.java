/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import hibernate.entities.Community;
import hibernate.entities.Neighbour;
import hibernate.entities.Person;

/**
 *
 * @author Muaz
 */
public class JoinNeighbourhood extends BaseActionClass {

    private int neighbourId;

    public void setNeighbourId(int neighbourId) {
        this.neighbourId = neighbourId;
    }

    public String execute() throws Exception {
        if (request.getHeader("User-Agent").contains("UNAVAILABLE")) {
            Community neighbourhood = communityService.getById(neighbourId);
            Person person = personService.findByName(username).get(0);
            Neighbour neighbour = new Neighbour();
            neighbour.setNeigbhour(neighbourhood);
            neighbour.setPersonId(person.getId());
            neighbourService.addNew(neighbour);

            return "MOBILE";
        } else {
            return "PC";
        }

    }
}
