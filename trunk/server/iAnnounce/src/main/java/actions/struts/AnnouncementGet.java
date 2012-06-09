/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import hibernate.entities.Person;
import hibernate.entities.Rating;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ListIterator;


import xtras.Consts;

/**
 *
 * @author Awais
 */
public class AnnouncementGet extends BaseActionClass {

    private String xmlResponse;
    private String latitude;
    private String longitude;
    private String pageNum;

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    @Override
    public String execute() throws Exception {

        //update currunt location of person

        Person person = personService.findByName(username).get(0);
        person.setLatitude(Double.parseDouble(latitude));
        person.setLongitude(Double.parseDouble(longitude));
        personService.addOrUpdate(person);


        if (request.getHeader("User-Agent").contains("UNAVAILABLE")) {

            String xml;
//            xml = "<announcements>"; //"<response><responseCode>0</responseCode><responseMessage>"+Consts.responseCodes[0]+"</responseMessage><getAnnouncements>"
//            xml = "<response><responseCode>";
            xml = "";
            int page = Integer.valueOf(pageNum);
            int counter = 0;

            // List<Announcement> announcementList = announcementService.getAll();
            List announcementList = announcementService.getAnnouncements(Double.parseDouble(latitude), Double.parseDouble(longitude), page);

            //sorts the list in decesnding order by date ttime
           /*
             * announcementList = (new
             * insertionSortAnnouncementDate(announcementList)).mySort();
             * Collections.reverse(announcementList);
             */
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
                    xml += "<announcer>" + row[7] + "</announcer>";
                    xml += "<Description>" + row[1] + "</Description>";
                    xml += "<timestamp>" + row[5] + "</timestamp>";
                    xml += "<noOfComments>" + noOfComments + "</noOfComments>";
                    xml += "<averageRating>" + row[8] + "</averageRating>";
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


            return "MOBILE";
        } else {
            return "PC";
        }
    }

    public String getXmlResponse() {
        return xmlResponse;
    }

    //source of code with some modifications http://stackoverflow.com/questions/120283/working-with-latitude-longitude-values-in-java
    public static float distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;

        return new Float(dist).floatValue();
    }
}
