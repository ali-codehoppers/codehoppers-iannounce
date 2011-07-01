/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package actions.struts;

import com.opensymphony.xwork2.ActionSupport;
import hibernate.entities.Announcement;
import hibernate.entities.Person;
import hibernate.entities.UserSession;
import java.sql.Timestamp;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import services.AnnouncementService;
import services.PersonService;
import services.RangeService;
import services.UserSessionService;

/**
 *
 * @author Awais
 */
public class AnnouncementPost extends ActionSupport implements ServletRequestAware{

    private HttpServletRequest request;
    
    private String xmlResponse;

    private String sessionId;
    private String range;
    private String announce;
    private String longitude;
    private String latitude;

    public String getAnnounce() {
        return announce;
    }

    public void setAnnounce(String announce) {
        this.announce = announce;
    }

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

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
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
        
        AnnouncementService service = getService();


        if (request.getHeader("User-Agent").contains("UNAVAILABLE")) {
            //if (true) {
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
            String xml;

            if (!validSession) //no session registered
            {
                xml = "<forceLogin/>";
            } else {
                xml = "<announce>";


                //get range in km against string
                RangeService rangeService = getRangeService();
                int radius = rangeService.findByName(range).get(0).getValuee();

                //get currunt timestamp to inseert in DB
                java.util.Date date = new java.util.Date();
                Timestamp time = new Timestamp(date.getTime());

                //normal announcement with 0 ranking
                Announcement announcement = new Announcement(0, Double.parseDouble(latitude), Double.parseDouble(longitude), announce, radius, time, false, username, 0);

                Integer newId = service.addNew(announcement);

                if (newId != 0) {
                    xml += "Announcement successfully posted. Will be shortly available";
                } else {
                    xml += "Error. Please try again";
                }

                xml += "</announce>";
            }

            xmlResponse=xml;
            return "MOBILE";
        }
        else{

//        AnnouncementForm pForm = (AnnouncementForm) form;
//        service.addNew(pForm.getAnnouncement());

        return "PC";
        }
    }

    private AnnouncementService getService() {
        ApplicationContext ap = WebApplicationContextUtils.getRequiredWebApplicationContext(org.apache.struts2.ServletActionContext.getServletContext());
        AnnouncementService service = (AnnouncementService) ap.getBean("announcementService");
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

    private RangeService getRangeService() {
        ApplicationContext ap = WebApplicationContextUtils.getRequiredWebApplicationContext(org.apache.struts2.ServletActionContext.getServletContext());
        RangeService service = (RangeService) ap.getBean("RangeService");
        return service;
    }
}
