/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import com.opensymphony.xwork2.ActionSupport;
import hibernate.entities.Announcement;
import hibernate.entities.Person;
import hibernate.entities.Rating;
import hibernate.entities.UserSession;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import services.AnnouncementService;
import services.CommentService;
import services.PersonService;
import services.RatingService;
import services.UserSessionService;
import xtras.insertionSortAnnouncementDate;

/**
 *
 * @author Awais
 */
public class AnnouncementGet extends ActionSupport implements ServletRequestAware {

    private HttpServletRequest request;

    private String xmlResponse;

    private String sessionId;
    private String latitude;
    private String longitude;
    private String pageNum;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

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
        RatingService ratingService = getRatingService();
        UserSessionService userSessionService = getUserSessionService();

        List<UserSession> userSessionList = userSessionService.findByName(sessionId);
        Boolean validSession = false;
        String username = "";

        //get username from session
        if (!userSessionList.isEmpty() && userSessionList.get(0).getStatuss()) {
            validSession = true;
            username = userSessionList.get(0).getUsername();

            //update currunt location of person
            PersonService personService = getPersonService();
            Person person = personService.findByName(username).get(0);
            person.setLatitude(Double.parseDouble(latitude));
            person.setLongitude(Double.parseDouble(longitude));
            personService.addOrUpdate(person);
        }

        if (request.getHeader("User-Agent").contains("UNAVAILABLE")) {
            //if(true){
            String xml;

            if (!validSession) {   //no session registered
                xml = "<forceLogin/>";
            } else {
                xml = "<announcements>";
                int page = Integer.valueOf(pageNum);
                int counter = 0;

                List<Announcement> announcementList = service.getAll();

                //sorts the list in decesnding order by date ttime
                announcementList = (new insertionSortAnnouncementDate(announcementList)).mySort();
                Collections.reverse(announcementList);

                for (int index = 0; index < announcementList.size(); index++) {  //traverse list
                    Announcement announcement = announcementList.get(index);

                    //test if in range
                    if (distFrom(Double.parseDouble(latitude), Double.parseDouble(longitude), announcement.getLatitude(), announcement.getLongitude()) <= announcement.getRadius()) {
                        ++counter;

                        //select announcement of the given page number
                        if (counter > (page - 1) * 10 && counter <= page * 10) {
                            int curruntRating = 0;
                            int noOfComments = 0;

                            //get number of comments on that announcement
                            noOfComments = commentService.findByName(announcement.getA_id()).size();

                            List<Rating> ratingList = ratingService.findByName(announcement.getA_id());

                            //rating by the currunt user
                            for (int indexr = 0; indexr < ratingList.size(); indexr++) {
                                Rating rating = ratingList.get(indexr);
                                if (rating.getUsername().compareTo(username) == 0) {
                                    if (rating.isStatus()) {
                                        curruntRating = 1;
                                    } else {
                                        curruntRating = -1;
                                    }
                                }
                            }

                            if (announcement.getTotalRating() > -10) {  //hiding a bad rated announcement
                                xml += "<announcement>\n<id>" + announcement.getA_id() + "</id>\n";
                                xml += "<announcer>" + announcement.getUsername_FK() + "</announcer>\n";
                                xml += "<Description>" + announcement.getAnnouncement() + "</Description>\n";
                                xml += "<timestamp>" + announcement.getTtime() + "</timestamp>\n";
                                xml += "<noOfComments>" + noOfComments + "</noOfComments>\n";
                                xml += "<averageRating>" + announcement.getTotalRating() + "</averageRating>\n";
                                xml += "<currentUserRating>" + curruntRating + "</currentUserRating>\n";
                                xml += "<longitude>" + announcement.getLongitude() + "</longitude>\n";
                                xml += "<latitude>" + announcement.getLatitude() + "</latitude>\n";
                                xml += "</announcement>\n";
                            } else {
                                --counter;  //if bad rated
                            }
                        } else if (counter > page * 10) {
                            break;
                        }
                    }

                }
                xml += "</announcements>";
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

    private RatingService getRatingService() {
        ApplicationContext ap = WebApplicationContextUtils.getRequiredWebApplicationContext(org.apache.struts2.ServletActionContext.getServletContext());
        RatingService service = (RatingService) ap.getBean("ratingService");
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

    private PersonService getPersonService() {
        ApplicationContext ap = WebApplicationContextUtils.getRequiredWebApplicationContext(org.apache.struts2.ServletActionContext.getServletContext());
        PersonService service = (PersonService) ap.getBean("personService");
        return service;
    }

    //source of code with some modifications http://stackoverflow.com/questions/120283/working-with-latitude-longitude-values-in-java
    public static float distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;

        return new Float(dist).floatValue();
    }
}
