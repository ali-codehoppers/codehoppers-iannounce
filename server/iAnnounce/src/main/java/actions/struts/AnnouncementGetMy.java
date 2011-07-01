/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import com.opensymphony.xwork2.ActionSupport;
import hibernate.entities.Announcement;
import hibernate.entities.UserSession;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import services.AnnouncementService;
import services.CommentService;
import services.UserSessionService;
import xtras.insertionSortAnnouncementDate;

/**
 *
 * @author Awais
 */
public class AnnouncementGetMy extends ActionSupport implements ServletRequestAware {

    private HttpServletRequest request;


    private String xmlResponse;

    private String sessionId;
    private String pageNum;

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
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
        this.request = hsr;
    }

    @Override
    public String execute() throws Exception {

        AnnouncementService service = getService();
        CommentService commentService = getCommentService();
        UserSessionService userSessionService = getUserSessionService();

        List<UserSession> userSessionList = userSessionService.findByName(sessionId);
        Boolean validSession = false;
        String username = "";

        //get username from session
        if (!userSessionList.isEmpty() && userSessionList.get(0).getStatuss()) {
            validSession = true;
            username = userSessionList.get(0).getUsername();
        }

        if (request.getHeader("User-Agent").contains("UNAVAILABLE")) {
            //if (true) {
            String xml;

            if (!validSession) {   //no session registered
                xml = "<forceLogin/>";
            } else {
                xml = "<myAnnouncements>";
                int page = Integer.valueOf(pageNum);
                int counter = 0;

                List<Announcement> announcementList = service.findByName(username);

                //sorts the list in decesnding order by date ttime
                announcementList = (new insertionSortAnnouncementDate(announcementList)).mySort();
                Collections.reverse(announcementList);

                for (int index = 0; index < announcementList.size(); index++) {
                    Announcement announcement = announcementList.get(index);

                    //get announcements according to page number
                    ++counter;
                    if (counter > (page - 1) * 10 && counter <= page * 10) {
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

                    } else if (counter > page * 10) {
                        break;
                    }

                }
                xml += "</myAnnouncements>";
            }

            xmlResponse=xml;

            
            return "MOBILE";
        } else {
            return "PC";
        }
    }

    private AnnouncementService getService() {
        ApplicationContext ap = WebApplicationContextUtils.getRequiredWebApplicationContext(org.apache.struts2.ServletActionContext.getServletContext());
        AnnouncementService service = (AnnouncementService) ap.getBean("announcementService");
        return service;
    }

    private CommentService getCommentService() {
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
