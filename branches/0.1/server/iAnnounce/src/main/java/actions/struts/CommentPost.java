/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import hibernate.entities.Comment;
import java.sql.Timestamp;
import xtras.Consts;

/**
 *
 * @author Awais
 */
public class CommentPost extends BaseActionClass
{

    private String xmlResponse;
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

    @Override
    public String execute() throws Exception
    {
        if (request.getHeader("User-Agent").contains("UNAVAILABLE"))
          {
//            String xml = "<PostComment>"; //<response><responseCode>
            String xml="<response><responseCode>";

            

            //get currunt time stamp for insert in DB
            java.util.Date date = new java.util.Date();
            Timestamp time = new Timestamp(date.getTime());
            Comment comment = new Comment(0, username, Integer.parseInt(announcementId), commentToPost, time);

            Integer newId = commentService.addNew(comment);
            if (newId != 0)
              {
//                xml += "Comment successfully posted"; //0<responseCode><responseMessage>"+Consts.responseCodes[0]+"</responseMessage><postComment>Consts.COMMENTPOST_SUCCESS</postComment>"
                xml += "0</responseCode><responseMessage>"+Consts.responseCodes[0]+"</responseMessage><postComment>"+Consts.COMMENTPOST_SUCCESS+"</postComment>";
              } else
              {
//                xml += "Error occurred. Please try again"; //12<responseCode><responseMessage>"+Consts.responseCodes[12]+"</responseMessage>"
                xml += "12</responseCode><responseMessage>"+Consts.responseCodes[12]+"</responseMessage>";
              }

//            xml += "</PostComment>"; //</response>
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
}
