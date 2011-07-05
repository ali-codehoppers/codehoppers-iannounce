/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import hibernate.entities.Comment;
import hibernate.entities.UserSession;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Awais
 */
public class CommentPost extends BaseActionClass
  {

    private String xmlResponse;
    private String sessionId;
    private String commentToPost;
    private String announcementId;

    public void setAnnouncementId(String announcementId)
      {
        this.announcementId = announcementId;
      }

    public void setCommentToPost(String commentToPost)
      {
        this.commentToPost = commentToPost;
      }

    public void setSessionId(String sessionId)
      {
        this.sessionId = sessionId;
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
            if (!userSessionList.isEmpty() && userSessionList.get(0).getStatuss())
              {
                validSession = true;
                username = userSessionList.get(0).getUsername();
              }

            String xml = "<PostComment>";

            if (!validSession) //no session registered
              {
                xml = "<forceLogin/>";
              } else
              {

                //get currunt time stamp for insert in DB
                java.util.Date date = new java.util.Date();
                Timestamp time = new Timestamp(date.getTime());
                Comment comment = new Comment(0, username, Integer.parseInt(announcementId), commentToPost, time);

                Integer newId = commentService.addNew(comment);
                if (newId != 0)
                  {
                    xml += "Comment successfully posted";
                  } else
                  {
                    xml += "Error occurred. Please try again";
                  }

                xml += "</PostComment>";
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
