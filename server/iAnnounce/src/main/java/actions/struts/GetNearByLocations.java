/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import hibernate.entities.Person;
import java.util.List;
import java.util.ListIterator;
import xtras.Consts;

/**
 *
 * @author Muaz
 */
public class GetNearByLocations extends BaseActionClass {

    private String xmlResponse;
    private String sessionId;
    private String neighbourhood_id;

    public void setNeighbourhood_id(String neighbourhood_id) {
        this.neighbourhood_id = neighbourhood_id;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String execute() throws Exception {
        if (request.getHeader("User-Agent").contains("UNAVAILABLE")) {
            //List<Location> 
            Person person = personService.findByName(username).get(0);
            List list = locationService.getNearbyLocations(person.getLatitude(), person.getLongitude(), 2, Integer.parseInt(neighbourhood_id));
            String xml = "";
            for (ListIterator iter = list.listIterator(); iter.hasNext();) {
                Object[] row = (Object[]) iter.next();
                xml += "<location><id>" + row[0] + "</id>";
                xml += "<name>" + row[2] + "</name>";
                xml += "<description>" + row[3] + "</description>";
                xml += "<latitude>" + row[4] + "</latitude>";
                xml += "<longitude>" + row[5] + "</longitude>";
                xml += "<distance>" + row[7] + "</distance>";
                xml += "</location>";
            }
            if (list.size() != 0) {
                xml = "<response><responseCode>0</responseCode><responseMessage>" + Consts.responseCodes[0] + "</responseMessage><locations>" + xml + "</locations>";
            } else {
                xml += "<response><responseCode>17</responseCode><responseMessage>" + Consts.responseCodes[17] + "</responseMessage>";
            }
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
