/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import hibernate.entities.Community;
import hibernate.entities.Person;
import xtras.Consts;

/**
 *
 * @author Muaz
 */
public class CreateCommunity extends BaseActionClass {

    private String title;
    private String description;
    private boolean isPrivate;
    private String sessionId;
    private String xmlResponse;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String execute() throws Exception {
        if (request.getHeader("User-Agent").contains("UNAVAILABLE")) {

            /*
             * System.out.println(username); System.out.println(title);
             * System.out.println(description); System.out.println(isPrivate);
             */

            Person person = personService.findByName(username).get(0);
            Community community = new Community();
            community.setTitle(title);
            community.setDescription(description);
            community.setIsPrivate(isPrivate);
            community.setOwner(person.getId());
            int id = communityService.addNew(community);
            
            String xml = "";
            xml += "<neighbour><id>" + id + "</id>";
            xml += "<Title>" + community.getTitle() + "</Title>";
            xml += "<Description>" + community.getDescription() + "</Description>";
            xml += "<isPrivate>" + community.isIsPrivate() + "</isPrivate>";
            xml += "<owner>" + community.getOwner() + "</owner>";
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
