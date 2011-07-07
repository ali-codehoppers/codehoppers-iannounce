/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import hibernate.entities.Announcement;
import hibernate.entities.Person;
import hibernate.entities.Rating;
import java.util.List;

/**
 *
 * @author Awais
 */
public class RatingPost extends BaseActionClass
{

    private String xmlResponse;
    private String announcementId;
    private String status;

    public void setAnnouncementId(String announcementId)
    {
        this.announcementId = announcementId;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Override
    public String execute() throws Exception
    {
        if (request.getHeader("User-Agent").contains("UNAVAILABLE"))
          {
            String xml = "<Rate>"; //<response><responseCode>
            Announcement announcement = announcementService.getById(Integer.parseInt(announcementId));
            Boolean fl_status = true;
            //get user rating
            if (status.compareTo("0") == 0)
              { //rate down
                fl_status = false;
                announcement.setTotalRating(announcement.getTotalRating() - 1);
              } else
              {
                announcement.setTotalRating(announcement.getTotalRating() + 1);
              }
            announcementService.addOrUpdate(announcement);
            //get all announcements of the ranked user
            List<Announcement> announcementList = announcementService.findByName(announcement.getUsername_FK());
            //calculate average rating
            float userAvgRating = 0;
            for (int i = 0; i < announcementList.size(); ++i)
              {
                Announcement ann = announcementList.get(i);
                userAvgRating = userAvgRating + ann.getTotalRating();
              }
            userAvgRating = userAvgRating / (float) announcementList.size();

            //update new average rating in DB
            Person person = personService.findByName(announcement.getUsername_FK()).get(0);
            person.setAvgrating(userAvgRating);
            personService.addOrUpdate(person);

            //record currunt rating in DB
            Rating rating = new Rating(0, username, Integer.parseInt(announcementId), fl_status);
            Integer newId = ratingService.addNew(rating);
            if (newId != 0)
              {
                xml += "Successfully rated."; //0<responseCode><responseMessage>"+Consts.responseCodes[0]+"</responseMessage><rate>Consts.RATING_SUCCESS</rate>"
              } else
              {
                xml += "Error occured. Please try again."; //15<responseCode><responseMessage>"+Consts.responseCodes[15]+"</responseMessage>"
              }

            xml += "</Rate>"; //</response>


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
}
