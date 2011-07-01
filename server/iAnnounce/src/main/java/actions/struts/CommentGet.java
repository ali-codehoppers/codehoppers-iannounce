/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package actions.struts;

import com.opensymphony.xwork2.ActionSupport;
import hibernate.entities.Comment;
import hibernate.entities.UserSession;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import services.CommentService;
import services.UserSessionService;
import xtras.insertionSortCommentDate;

/**
 *
 * @author Awais
 */
public class CommentGet extends ActionSupport implements ServletRequestAware{
    private HttpServletRequest request;

    private String xmlResponse;

    private String sessionId;
    private String announcementId;

    public String getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(String announcementId) {
        this.announcementId = announcementId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getXmlResponse() {
        return xmlResponse;
    }

    public void setXmlResponse(String xmlResponse) {
        this.xmlResponse = xmlResponse;
    }

    


    public void setServletRequest(HttpServletRequest hsr) {
        this.request=hsr;
    }

    @Override
    public String execute() throws Exception {
         CommentService service = getService();
        if (request.getHeader("User-Agent").contains("UNAVAILABLE")) {
            //if(true){

            UserSessionService userSessionService = getUserSessionService();
            List<UserSession> userSessionList = userSessionService.findByName(sessionId);
            Boolean validSession = false;

            //check valid user session
            if (!userSessionList.isEmpty() && userSessionList.get(0).getStatuss()) {
                validSession = true;
            }

            String xml = "<GetComments>\n";

            if (!validSession) //no session registered
            {
                xml = "<forceLogin/>";
            } else {
                //get comments of the announcement
                List<Comment> commentList = service.findByName(Integer.parseInt(announcementId));

                //sort list according to time
                commentList = (new insertionSortCommentDate(commentList)).mySort();

                for (int index = 0; index < commentList.size(); index++) {
                    Comment comment = commentList.get(index);
                    xml += "<packet>\n";
                    xml += "<username>" + comment.getUsername_FK() + "</username>\n";
                    xml += "<comment>" + comment.getComment() + "</comment>\n";
                    xml += "<time>" + comment.getTtime() + "</time>\n";
                    xml += "</packet>\n";
                }

                xml += "</GetComments>";
            }
            xmlResponse=xml;
            return "MOBILE";
        }
        else{
             return "PC";
        }

    }

    private CommentService getService() {
        ApplicationContext ap = WebApplicationContextUtils.getRequiredWebApplicationContext(org.apache.struts2.ServletActionContext.getServletContext());
        CommentService service = (CommentService) ap.getBean("commentService");
        return service;
    }

    private UserSessionService getUserSessionService() {
        ApplicationContext ap = WebApplicationContextUtils.getRequiredWebApplicationContext(org.apache.struts2.ServletActionContext.getServletContext());
        UserSessionService service = (UserSessionService) ap.getBean("usersessionService");
        return service;
    }
}

