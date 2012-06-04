/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import hibernate.entities.Announcement;
import hibernate.entities.Person;
import hibernate.entities.Rating;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;


import xtras.Consts;
import xtras.insertionSortAnnouncementDate;

/**
 *
 * @author Awais
 */
public class AnnouncementGet extends BaseActionClass
{

    private String xmlResponse;
    private String latitude;
    private String longitude;
    private String pageNum;

    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

    public void setPageNum(String pageNum)
    {
        this.pageNum = pageNum;
    }

    @Override
    public String execute() throws Exception
    {

        //update currunt location of person

        Person person = personService.findByName(username).get(0);
        person.setLatitude(Double.parseDouble(latitude));
        person.setLongitude(Double.parseDouble(longitude));
        personService.addOrUpdate(person);


        if (request.getHeader("User-Agent").contains("UNAVAILABLE"))
          {

            String xml;
//            xml = "<announcements>"; //"<response><responseCode>0</responseCode><responseMessage>"+Consts.responseCodes[0]+"</responseMessage><getAnnouncements>"
//            xml = "<response><responseCode>";
            xml = "";
            int page = Integer.valueOf(pageNum);
            int counter = 0;

            List<Announcement> announcementList = announcementService.getAll();

            //sorts the list in decesnding order by date ttime
            announcementList = (new insertionSortAnnouncementDate(announcementList)).mySort();
            Collections.reverse(announcementList);

            int numAnnouncements = 0;

            for (int index = 0; index < announcementList.size(); index++)
              {  //traverse list
                Announcement announcement = announcementList.get(index);
//                Logger log=Logger.getLogger(AnnouncementGet.class);
//                    log.error("distance of announce"+index+"="+distFrom(Double.parseDouble(latitude), Double.parseDouble(longitude), announcement.getLatitude(), announcement.getLongitude())+"AnnRad="+announcement.getRadius());
                float dist=distFrom(Double.parseDouble(latitude), Double.parseDouble(longitude), announcement.getLatitude(), announcement.getLongitude());
                //test if in range
                if (dist<= announcement.getRadius())
                  {
                    ++counter;
                    //select announcement of the given page number
                    if (counter > (page - 1) * 10 && counter <= page * 10)
                      {
                        int curruntRating = 0;
                        int noOfComments = 0;

                        //get number of comments on that announcement
                        noOfComments = commentService.findByName(announcement.getA_id()).size();

                        List<Rating> ratingList = ratingService.findByName(announcement.getA_id());

                        int likes = 0;
                        int dislikes = 0;

                        //rating by the currunt user
                        for (int indexr = 0; indexr < ratingList.size(); indexr++)
                          {
                            Rating rating = ratingList.get(indexr);

                            if (rating.isStatus())
                              {
                                likes++;
                              } else
                              {
                                dislikes++;
                              }


                            if (rating.getUsername().compareTo(username) == 0)
                              {
                                if (rating.isStatus())
                                  {
                                    curruntRating = 1;
                                  } else
                                  {
                                    curruntRating = -1;
                                  }
                              }
                          }

                        if (announcement.getTotalRating() > -10)
                          {  //hiding a bad rated announcement
                            xml += "<announcement><id>" + announcement.getA_id() + "</id>";
                            xml += "<announcer>" + announcement.getUsername_FK() + "</announcer>";
                            xml += "<Description>" + announcement.getAnnouncement() + "</Description>";
                            xml += "<timestamp>" + announcement.getTtime() + "</timestamp>";
                            xml += "<noOfComments>" + noOfComments + "</noOfComments>";
                            xml += "<averageRating>" + announcement.getTotalRating() + "</averageRating>";
                            xml += "<currentUserRating>" + curruntRating + "</currentUserRating>";
                            xml += "<longitude>" + announcement.getLongitude() + "</longitude>";
                            xml += "<latitude>" + announcement.getLatitude() + "</latitude>";

                            if((Float.toString(dist)).length()>5){
                            xml+="<distance>"+(Float.toString(dist)).substring(0, 4)+"</distance>"; //in kilometers
                                }
                            else{
                            xml+="<distance>"+Float.toString(dist)+"</distance>"; //in kilometers
                            }

                            xml+="<likes>"+likes+"</likes>";
                            xml+="<dislikes>"+dislikes+"</dislikes>";
                            xml += "</announcement>";
                            numAnnouncements++;
                          } else
                          {
                            --counter;  //if bad rated
                          }
                      } else
                      {
                        if (counter > page * 10)
                          {
                            break;
                          }
                      }
                  }

              }


            if (numAnnouncements != 0)
              {
                xml = "<response><responseCode>0</responseCode><responseMessage>" + Consts.responseCodes[0] + "</responseMessage><getAnnouncements>" + xml + "</getAnnouncements>";
              } else
              {
                xml += "<response><responseCode>17</responseCode><responseMessage>" + Consts.responseCodes[17] + "</responseMessage>";
              }

//            xml += "</announcements>"; //</getAnnouncements></response>
            xml += "</response>";


            xmlResponse = xml;


            return "MOBILE";
          } else
          {
            return "PC";
          }
    }

    public String getXmlResponse()
    {
        return xmlResponse;
    }

    //source of code with some modifications http://stackoverflow.com/questions/120283/working-with-latitude-longitude-values-in-java
    public static float distFrom(double lat1, double lng1, double lat2, double lng2)
    {
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