/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import hibernate.entities.Announcement;
import hibernate.entities.Person;
import hibernate.entities.Rating;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ListIterator;
import xtras.Consts;

/**
 *
 * @author Muaz
 */
public class AnnouncementByNeighbourhood extends BaseActionClass {

    private int neighbourhood_id;
    private String xmlResponse;
    private String sessionId;
    private String pageNum;

    public void setNeighbourhood_id(int neighbourhood_id) {
        this.neighbourhood_id = neighbourhood_id;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String execute() throws Exception {
        if (request.getHeader("User-Agent").contains("UNAVAILABLE")) {
            int page = Integer.parseInt(pageNum);
            Person person = personService.findByName(username).get(0);
            List announcementList = announcementService.getAnnouncementsByNeighbourhood(person.getLatitude(), person.getLongitude(), neighbourhood_id, page);
            String xml = "";
            int numAnnouncements = 0;
            //System.out.println("=====>" + announcementList.size());
            for (ListIterator iter = announcementList.listIterator(); iter.hasNext();) {
                Object[] row = (Object[]) iter.next();
                int curruntRating = 0;
                int noOfComments = 0;

                //get number of comments on that announcement
                noOfComments = commentService.findByName((Integer) row[0]).size();
                List<Rating> ratingList = ratingService.findByName((Integer) row[0]);

                int likes = 0;
                int dislikes = 0;

                //rating by the currunt user
                for (int indexr = 0; indexr < ratingList.size(); indexr++) {
                    Rating rating = ratingList.get(indexr);
                    if (rating.isStatus()) {
                        likes++;
                    } else {
                        dislikes++;
                    }
                    if (rating.getUsername().compareTo(username) == 0) {
                        if (rating.isStatus()) {
                            curruntRating = 1;
                        } else {
                            curruntRating = -1;
                        }
                    }
                }
                DecimalFormat twoDForm = new DecimalFormat("#.##");
                Double distance = Double.valueOf(twoDForm.format(row[10]));
                xml += "<announcement><id>" + row[0] + "</id>";
                xml += "<announcer>" + row[9] + "</announcer>";
                xml += "<Description>" + row[1] + "</Description>";
                xml += "<timestamp>" + row[7] + "</timestamp>";
                xml += "<noOfComments>" + noOfComments + "</noOfComments>";
                xml += "<averageRating>" + row[10] + "</averageRating>";
                xml += "<currentUserRating>" + curruntRating + "</currentUserRating>";
                xml += "<longitude>" + row[3] + "</longitude>";
                xml += "<latitude>" + row[2] + "</latitude>";
                xml += "<distance>" + distance + "</distance>"; //in kilometers
                xml += "<likes>" + likes + "</likes>";
                xml += "<dislikes>" + dislikes + "</dislikes>";
                xml += "</announcement>";
                numAnnouncements++;
            }


            if (numAnnouncements != 0) {
                xml = "<response><responseCode>0</responseCode><responseMessage>" + Consts.responseCodes[0] + "</responseMessage><getAnnouncements>" + xml + "</getAnnouncements>";
            } else {
                xml += "<response><responseCode>17</responseCode><responseMessage>" + Consts.responseCodes[17] + "</responseMessage>";
            }

//            xml += "</announcements>"; //</getAnnouncements></response>
            xml += "</response>";

            // System.out.println(xml);
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
