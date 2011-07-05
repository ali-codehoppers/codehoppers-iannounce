/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import hibernate.entities.Announcement;
import hibernate.entities.Person;
import hibernate.entities.Rating;
import hibernate.entities.UserSession;
import java.util.List;

/**
 *
 * @author Awais
 */
public class RatingPost extends BaseActionClass
  {

    private String xmlResponse;
    private String sessionId;
    private String announcementId;
    private String status;

    public void setAnnouncementId(String announcementId)
      {
        this.announcementId = announcementId;
      }

    public void setSessionId(String sessionId)
      {
        this.sessionId = sessionId;
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
            //if(true){


            List<UserSession> userSessionList = userSessionService.findByName(sessionId);
            Boolean validSession = false;
            String username = "";
            //check valid session and get username
            if (!userSessionList.isEmpty() && userSessionList.get(0).getStatuss())
              {
                validSession = true;
                username = userSessionList.get(0).getUsername();
              }



            String xml = "<Rate>";

            if (!validSession) //no session registered
              {
                xml = "<forceLogin/>";
              } else
              {
                Announcement announcement = announcementService.getById(Integer.parseInt(announcementId));

                Boolean fl_status = true;
                //get user rating
                if (status.compareTo("0") == 0)
                  { //rate down
                    fl_status = false;
                    announcement.setTotalRating(announcement.getTotalRating() - 1);
                  } else
                  {    //rate up
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
                    xml += "Successfully rated.";
                  } else
                  {
                    xml += "Error occured. Please try again.";
                  }

                xml += "</Rate>";
              }

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
