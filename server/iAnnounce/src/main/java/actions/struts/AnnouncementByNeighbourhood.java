/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import hibernate.entities.Announcement;
import java.util.List;
import xtras.Consts;

/**
 *
 * @author Muaz
 */
public class AnnouncementByNeighbourhood extends BaseActionClass {

    private int neighbourhood_id;
    private String xmlResponse;
    private String sessionId;

    public void setNeighbourhood_id(int neighbourhood_id) {
        this.neighbourhood_id = neighbourhood_id;
    }

    public String execute() throws Exception {
        if (request.getHeader("User-Agent").contains("UNAVAILABLE")) {
            List<Announcement> announcements = announcementService.findByNeighbourhoodId(neighbourhood_id);
            String xml = "";
            int numAnnouncements = 0;
            for (int i = 0; i < announcements.size(); i++) {
                Announcement announcement = announcements.get(i);

                xml += "<announcement><id>" + announcement.getA_id() + "</id>";
                xml += "<announcer>" + announcement.getUsername_FK() + "</announcer>";
                xml += "<Description>" + announcement.getAnnouncement() + "</Description>";
                xml += "<timestamp>" + announcement.getTtime() + "</timestamp>";
                xml += "<noOfComments>0</noOfComments>";
                xml += "<averageRating>" + announcement.getTotalRating() + "</averageRating>";
                xml += "<currentUserRating>0</currentUserRating>";
                xml += "<longitude>" + announcement.getLongitude() + "</longitude>";
                xml += "<latitude>" + announcement.getLatitude() + "</latitude>";
                xml += "<distance>0</distance>"; //in kilometers
                xml += "<likes>0</likes>";
                xml += "<dislikes>0</dislikes>";
                xml += "</announcement>";
                numAnnouncements++;
            }
            if (numAnnouncements != 0) {
                xml = "<response><responseCode>0</responseCode><responseMessage>" + Consts.responseCodes[0] + "</responseMessage><getAnnouncements>" + xml + "</getAnnouncements>";
            } else {
                xml += "<response><responseCode>17</responseCode><responseMessage>" + Consts.responseCodes[17] + "</responseMessage>";
            }
            xmlResponse = xml;
           // System.out.print(xml);
            return "MOBILE";
        } else {
            return "PC";
        }
    }

    public String getXmlResponse() {
        return xmlResponse;
    }
    
    
}
