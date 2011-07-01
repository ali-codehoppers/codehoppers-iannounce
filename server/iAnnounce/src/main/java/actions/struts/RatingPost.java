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
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import services.AnnouncementService;
import services.PersonService;
import services.RatingService;
import services.UserSessionService;

/**
 *
 * @author Awais
 */
public class RatingPost extends ActionSupport implements ServletRequestAware {

    private HttpServletRequest request;

    public void setServletRequest(HttpServletRequest hsr) {
        this.request = hsr;
    }

    @Override
    public String execute() throws Exception {


        RatingService service = getService();

        if (request.getHeader("User-Agent").contains("UNAVAILABLE")) {
            //if(true){

            UserSessionService userSessionService = getUserSessionService();
            List<UserSession> userSessionList = userSessionService.findByName(request.getParameter("sessionId"));
            Boolean validSession = false;
            String username = "";
            //check valid session and get username
            if (!userSessionList.isEmpty() && userSessionList.get(0).getStatuss()) {
                validSession = true;
                username = userSessionList.get(0).getUsername();
            }

            AnnouncementService announcementService = getAnnouncementService();
            PersonService personService = getPersonService();
            String xml = "<Rate>";

            if (!validSession) //no session registered
            {
                xml = "<forceLogin/>";
            } else {
                Announcement announcement = announcementService.getById(Integer.parseInt(request.getParameter("announcementId")));

                Boolean status = true;
                //get user rating
                if (request.getParameter("status").compareTo("0") == 0) { //rate down
                    status = false;
                    announcement.setTotalRating(announcement.getTotalRating() - 1);
                } else {    //rate up
                    announcement.setTotalRating(announcement.getTotalRating() + 1);
                }
                announcementService.addOrUpdate(announcement);

                //get all announcements of the ranked user
                List<Announcement> announcementList = announcementService.findByName(announcement.getUsername_FK());

                //calculate average rating
                float userAvgRating = 0;
                for (int i = 0; i < announcementList.size(); ++i) {
                    Announcement ann = announcementList.get(i);
                    userAvgRating = userAvgRating + ann.getTotalRating();
                }
                userAvgRating = userAvgRating / (float) announcementList.size();

                //update new average rating in DB
                Person person = personService.findByName(announcement.getUsername_FK()).get(0);
                person.setAvgrating(userAvgRating);
                personService.addOrUpdate(person);

                //record currunt rating in DB
                Rating rating = new Rating(0, username, Integer.parseInt(request.getParameter("announcementId")), status);
                Integer newId = service.addNew(rating);
                if (newId != 0) {
                    xml += "Successfully rated.";
                } else {
                    xml += "Error occured. Please try again.";
                }

                xml += "</Rate>";
            }

            request.setAttribute("responsexml", xml);
            return "MOBILE";
        } else {

//            RatingForm pForm = (RatingForm) form;
//            service.addNew(pForm.getRating());

            return "PC";
        }
    }

    private RatingService getService() {
        ApplicationContext ap = WebApplicationContextUtils.getRequiredWebApplicationContext(org.apache.struts2.ServletActionContext.getServletContext());
        RatingService service = (RatingService) ap.getBean("ratingService");
        return service;
    }

    private UserSessionService getUserSessionService() {
        ApplicationContext ap = WebApplicationContextUtils.getRequiredWebApplicationContext(org.apache.struts2.ServletActionContext.getServletContext());
        UserSessionService service = (UserSessionService) ap.getBean("usersessionService");
        return service;
    }

    private AnnouncementService getAnnouncementService() {
        ApplicationContext ap = WebApplicationContextUtils.getRequiredWebApplicationContext(org.apache.struts2.ServletActionContext.getServletContext());
        AnnouncementService service = (AnnouncementService) ap.getBean("announcementService");
        return service;
    }

    private PersonService getPersonService() {
        ApplicationContext ap = WebApplicationContextUtils.getRequiredWebApplicationContext(org.apache.struts2.ServletActionContext.getServletContext());
        PersonService service = (PersonService) ap.getBean("personService");
        return service;
    }
}
