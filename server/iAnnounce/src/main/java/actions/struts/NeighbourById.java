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
public class NeighbourById extends BaseActionClass {

    private String xmlResponse;
    private int id;
    private String sessionId;

    public void setId(int id) {
        this.id = id;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String execute() throws Exception {
        if (request.getHeader("User-Agent").contains("UNAVAILABLE")) {
            Community community = communityService.getById(id);
            Person person = personService.findByName(username).get(0);
            List<Neighbour> neighbours = neighbourService.findMembershipStatus(id, person.getId());
            boolean isMember = false;
            if(neighbours.size()>0){
                isMember = true;
            }else if(community.getOwner() == person.getId()){
                isMember = true;
            }
            String xml = "";
            xml += "<neighbour><id>" + community.getId() + "</id>";
            xml += "<Title>" + community.getTitle() + "</Title>";
            xml += "<Description>" + community.getDescription() + "</Description>";
            xml += "<isPrivate>" + community.isIsPrivate() + "</isPrivate>";
            xml += "<owner>" + community.getOwner() + "</owner>";
            xml += "<isMember>" + isMember + "</isMember>";
            xml += "</neighbour>";
            xml = "<response><responseCode>0</responseCode><responseMessage>" + Consts.responseCodes[0] + "</responseMessage><getNeighbourhood>" + xml + "</getNeighbourhood>";
            xml += "</response>";
            //System.out.println(xml);
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
