/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import hibernate.entities.Community;
import hibernate.entities.Neighbour;
import hibernate.entities.Person;
import java.util.List;
import xtras.Consts;

/**
 *
 * @author Muaz
 */
public class NeighbourByPerson extends BaseActionClass {

    private String xmlResponse;
    private String sessionId;

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String execute() throws Exception {
        if (request.getHeader("User-Agent").contains("UNAVAILABLE")) {
            Person person = personService.findByName(username).get(0);
            List<Neighbour> neighbours = neighbourService.findByPerson(person.getId());
            String xml = "";
            for (int i = 0; i < neighbours.size(); i++) {
                Community community = neighbours.get(i).getNeigbhour();
                xml += "<neighbour><id>" + community.getId() + "</id>";
                xml += "<Title>" + community.getTitle() + "</Title>";
                xml += "<Description>" + community.getDescription() + "</Description>";
                xml += "<isPrivate>" + community.isIsPrivate() + "</isPrivate>";
                xml += "<owner>" + community.getOwner() + "</owner>";
                xml += "</neighbour>";
            }
            List<Community> communitys = communityService.findByOwner(person.getId());
            for (int i = 0; i < communitys.size(); i++) {
                Community community = communitys.get(i);
                xml += "<neighbour><id>" + community.getId() + "</id>";
                xml += "<Title>" + community.getTitle() + "</Title>";
                xml += "<Description>" + community.getDescription() + "</Description>";
                xml += "<isPrivate>" + community.isIsPrivate() + "</isPrivate>";
                xml += "<owner>" + community.getOwner() + "</owner>";
                xml += "</neighbour>";
            }
            xml = "<response><responseCode>0</responseCode><responseMessage>" + Consts.responseCodes[0] + "</responseMessage><getNeighbourhood>" + xml + "</getNeighbourhood>";
            xml += "</response>";
          //  System.out.println(xml);
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
