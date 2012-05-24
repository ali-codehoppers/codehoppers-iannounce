/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import hibernate.entities.Comment;
import java.util.List;
import xtras.Consts;
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

//            String xml = "<GetComments>\n"; // <response><responseCode>0<responseCode><responseMessage>"+Consts.responseCodes[0]+"</responseMessage><getComments>

            String xml="<response><responseCode>";

            

            //get comments of the announcement
            List<Comment> commentList = commentService.findByName(Integer.parseInt(announcementId));

            //sort list according to time
            commentList = (new insertionSortCommentDate(commentList)).mySort();

            if (commentList.isEmpty())
              {
                xml+="18</responseCode><responseMessage>"+Consts.responseCodes[18]+"</responseMessage>";
              } else
              {
                xml+="0</responseCode><responseMessage>"+Consts.responseCodes[0]+"</responseMessage><getComments>";
                for (int index = 0; index < commentList.size(); index++)
                  {
                    Comment comment = commentList.get(index);
                    xml += "<comment>"; //<comment>
                    xml += "<username>" + comment.getUsername_FK() + "</username>";
                    xml += "<description>" + comment.getComment() + "</description>"; //description
                    xml += "<time>" + comment.getTtime() + "</time>";
                    xml += "</comment>\n";
                  }
                xml+="</getComments>";
              }



//            xml += "</GetComments>"; //</getComments></response>
            xml+="</response>";

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
