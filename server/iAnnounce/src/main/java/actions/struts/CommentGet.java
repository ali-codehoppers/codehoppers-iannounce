/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import hibernate.entities.Comment;
import java.util.List;
import xtras.insertionSortCommentDate;

/**
 *
 * @author Awais
 */
public class CommentGet extends BaseActionClass
  {

    private String xmlResponse;
    private String announcementId;

    public void setAnnouncementId(String announcementId)
      {
        this.announcementId = announcementId;
      }

    @Override
    public String execute() throws Exception
      {
        if (request.getHeader("User-Agent").contains("UNAVAILABLE"))
          {

            String xml = "<GetComments>\n";


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
