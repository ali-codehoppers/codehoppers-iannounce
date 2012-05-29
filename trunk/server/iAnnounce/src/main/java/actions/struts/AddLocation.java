/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import hibernate.entities.Location;
import hibernate.entities.Person;

/**
 *
 * @author Muaz
 */
public class AddLocation extends BaseActionClass {

    private String sessionId;
    private String neighbourhoodId;
    private String name;
    private String description;
    private String latitude;
    private String longitude;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNeighbourhoodId(String neighbourhoodId) {
        this.neighbourhoodId = neighbourhoodId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }


    
    
    public String execute() throws Exception {
        if (request.getHeader("User-Agent").contains("UNAVAILABLE")) {
            
            System.out.println(neighbourhoodId);
            System.out.println(name);
            System.out.println(description);
            System.out.println(latitude);
            System.out.println(longitude);
            
            Person person = personService.findByName(username).get(0);
            Location location = new Location();
            location.setName(name);
            location.setDescription(description);
            location.setLatitude(Double.parseDouble(latitude));
            location.setLongitude(Double.parseDouble(longitude));
            location.setNeighbourhoodId(Integer.parseInt(neighbourhoodId));
            location.setAddedBy(person.getId());
            locationService.addNew(location);
            return "MOBILE";
        } else {
            return "PC";
        }
    }
}
