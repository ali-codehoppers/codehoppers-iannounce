/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import hibernate.entities.Comment;
import hibernate.entities.UserSession;
import java.util.List;
import xtras.insertionSortCommentDate;

/**
 *
 * @author Awais
 */
public class CommentGet extends BaseActionClass
  {

    private String xmlResponse;
    private String sessionId;
    private String announcementId;

    public void setAnnouncementId(String announcementId)
      {
        this.announcementId = announcementId;
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

            //check valid user session
            if (!userSessionList.isEmpty() && userSessionList.get(0).getStatuss())
              {
                validSession = true;
              }

            String xml = "<GetComments>\n";

            if (!validSession) //no session registered
              {
                xml = "<forceLogin/>";
              } else
              {
                //get comments of the announcement
                List<Comment> commentList = commentService.findByName(Integer.parseInt(announcementId));

                //sort list according to time
                commentList = (new insertionSortCommentDate(commentList)).mySort();

                for (int index = 0; index < commentList.size(); index++)
                  {
                    Comment comment = commentList.get(index);
                    xml += "<packet>\n";
                    xml += "<username>" + comment.getUsername_FK() + "</username>\n";
                    xml += "<comment>" + comment.getComment() + "</comment>\n";
                    xml += "<time>" + comment.getTtime() + "</time>\n";
                    xml += "</packet>\n";
                  }

                xml += "</GetComments>";
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
