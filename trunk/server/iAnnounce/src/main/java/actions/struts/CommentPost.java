/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package actions.struts;

import com.opensymphony.xwork2.ActionSupport;
import hibernate.entities.Comment;
import hibernate.entities.UserSession;
import java.sql.Timestamp;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import services.CommentService;
import services.UserSessionService;

/**
 *
 * @author Awais
 */
public class CommentPost  extends ActionSupport implements ServletRequestAware {

    private HttpServletRequest request;

    public void setServletRequest(HttpServletRequest hsr) {
        this.request = hsr;
    }

    @Override
    public String execute() throws Exception {


        CommentService service = getService();
        if (request.getHeader("User-Agent").contains("UNAVAILABLE")) {
            //if(true){

            UserSessionService userSessionService = getUserSessionService();
            List<UserSession> userSessionList = userSessionService.findByName(request.getParameter("sessionId"));
            Boolean validSession = false;
            String username = "";
            if (!userSessionList.isEmpty() && userSessionList.get(0).getStatuss()) {
                validSession = true;
                username = userSessionList.get(0).getUsername();
            }

            String xml = "<PostComment>";

            if (!validSession) //no session registered
            {
                xml = "<forceLogin/>";
            } else {

                //get currunt time stamp for insert in DB
                java.util.Date date = new java.util.Date();
                Timestamp time = new Timestamp(date.getTime());
                Comment comment = new Comment(0, username, Integer.parseInt(request.getParameter("announcementId")), request.getParameter("commentToPost"), time);

                Integer newId = service.addNew(comment);
                if (newId != 0) {
                    xml += "Comment successfully posted";
                } else {
                    xml += "Error occurred. Please try again";
                }

                xml += "</PostComment>";
            }
            request.setAttribute("responsexml", xml);
            return "MOBILE";
        }
        else{
//          CommentForm pForm = (CommentForm) form;
//        service.addNew(pForm.getComment());

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

