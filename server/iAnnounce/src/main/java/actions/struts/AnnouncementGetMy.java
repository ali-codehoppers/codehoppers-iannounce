/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import hibernate.entities.Announcement;
import hibernate.entities.UserSession;
import java.util.Collections;
import java.util.List;
import xtras.insertionSortAnnouncementDate;

/**
 *
 * @author Awais
 */
public class AnnouncementGetMy extends BaseActionClass
  {

    private String xmlResponse;
    private String sessionId;
    private String pageNum;

    public void setPageNum(String pageNum)
      {
        this.pageNum = pageNum;
      }

    public void setSessionId(String sessionId)
      {
        this.sessionId = sessionId;
      }

    @Override
    public String execute() throws Exception
      {
        List<UserSession> userSessionList = userSessionService.findByName(sessionId);
        Boolean validSession = false;
        String username = "";

        //get username from session
        if (!userSessionList.isEmpty() && userSessionList.get(0).getStatuss())
          {
            validSession = true;
            username = userSessionList.get(0).getUsername();
          }

        if (request.getHeader("User-Agent").contains("UNAVAILABLE"))
          {
            //if (true) {
            String xml;

            if (!validSession)
              {   //no session registered
                xml = "<forceLogin/>";
              } else
              {
                xml = "<myAnnouncements>";
                int page = Integer.valueOf(pageNum);
                int counter = 0;

                List<Announcement> announcementList = announcementService.findByName(username);

                //sorts the list in decesnding order by date ttime
                announcementList = (new insertionSortAnnouncementDate(announcementList)).mySort();
                Collections.reverse(announcementList);

                for (int index = 0; index < announcementList.size(); index++)
                  {
                    Announcement announcement = announcementList.get(index);

                    //get announcements according to page number
                    ++counter;
                    if (counter > (page - 1) * 10 && counter <= page * 10)
                      {
                        int noOfComments = 0;

                        //get total number of comments on the announcement
                        noOfComments = commentService.findByName(announcement.getA_id()).size();

                        xml += "<announcement>\n<id>" + announcement.getA_id() + "</id>\n";
                        xml += "<Description>" + announcement.getAnnouncement() + "</Description>\n";
                        xml += "<timestamp>" + announcement.getTtime() + "</timestamp>\n";
                        xml += "<noOfComments>" + noOfComments + "</noOfComments>\n";
                        xml += "<averageRating>" + announcement.getTotalRating() + "</averageRating>\n";
                        xml += "<longitude>" + announcement.getLongitude() + "</longitude>\n";
                        xml += "<latitude>" + announcement.getLatitude() + "</latitude>\n";
                        xml += "</announcement>\n";

                      } else if (counter > page * 10)
                      {
                        break;
                      }

                  }
                xml += "</myAnnouncements>";
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
