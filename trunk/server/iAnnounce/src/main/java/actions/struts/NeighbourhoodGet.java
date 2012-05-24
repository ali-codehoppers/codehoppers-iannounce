/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import hibernate.entities.Community;
import java.util.List;
import xtras.Consts;

/**
 *
 * @author Muaz
 */
public class NeighbourhoodGet extends BaseActionClass {

    private String xmlResponse;
    private String sessionId;

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String execute() throws Exception {
        if (request.getHeader("User-Agent").contains("UNAVAILABLE")) {
            List<Community> communities = communityService.getAll();
            String xml = "";
            int numAnnouncements = 0;
            for (int i = 0; i < communities.size(); i++) {

                xml += "<neighbour><id>" + communities.get(i).getId() + "</id>";
                xml += "<Title>" + communities.get(i).getTitle() + "</Title>";
                xml += "<Description>" + communities.get(i).getDescription() + "</Description>";
                xml += "<isPrivate>" + communities.get(i).isIsPrivate() + "</isPrivate>";
                xml += "<owner>" + communities.get(i).getOwner() + "</owner>";
                xml += "</neighbour>";
                numAnnouncements++;
            }

            if (numAnnouncements != 0) {
                xml = "<response><responseCode>0</responseCode><responseMessage>" + Consts.responseCodes[0] + "</responseMessage><getNeighbourhood>" + xml + "</getNeighbourhood>";
            } else {
                xml += "<response><responseCode>17</responseCode><responseMessage>" + Consts.responseCodes[17] + "</responseMessage>";
            }

//            xml += "</announcements>"; //</getAnnouncements></response>
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
