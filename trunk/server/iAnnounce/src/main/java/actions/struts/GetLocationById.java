/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import hibernate.entities.Location;
import hibernate.entities.Person;
import java.util.List;
import java.util.ListIterator;
import xtras.Consts;

/**
 *
 * @author Muaz
 */
public class GetLocationById extends BaseActionClass {

    private String xmlResponse;
    private String sessionId;
    private String location_id;

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String execute() throws Exception {
        if (request.getHeader("User-Agent").contains("UNAVAILABLE")) {
            //List<Location> 
            Person person = personService.findByName(username).get(0);
            Location location = locationService.getById(Integer.parseInt(location_id));
            String xml = "";
            xml += "<location><id>" + location.getId() + "</id>";
            xml += "<name>" + location.getName() + "</name>";
            xml += "<description>" + location.getDescription() + "</description>";
            xml += "<latitude>" + location.getLatitude() + "</latitude>";
            xml += "<longitude>" + location.getLongitude() + "</longitude>";
            xml += "<distance>0</distance>";
            xml += "</location>";
            xml = "<response><responseCode>0</responseCode><responseMessage>" + Consts.responseCodes[0] + "</responseMessage><locations>" + xml + "</locations>";
            xmlResponse = xml;
            return "MOBILE";

        } else {
            return "PC";
        }
    }

    public String getXmlResponse() {
        return xmlResponse;
    }
}
