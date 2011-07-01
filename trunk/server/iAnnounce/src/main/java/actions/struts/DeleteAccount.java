/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package actions.struts;

import com.opensymphony.xwork2.ActionSupport;
import hibernate.entities.Person;
import hibernate.entities.UserSession;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import services.PersonService;
import services.UserSessionService;

/**
 *
 * @author Awais
 */
public class DeleteAccount extends ActionSupport implements ServletRequestAware{
    
    private HttpServletRequest request;

    private String xmlResponse;

    private String sessionId;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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



        if (request.getHeader("User-Agent").contains("UNAVAILABLE")) {
            //if(true){
            UserSessionService service = getService();
            List<UserSession> userSessionList = service.findByName(sessionId);
            Boolean validSession = false;
            String username = "";

            //check if session is valid and username exists
            if (!userSessionList.isEmpty() && userSessionList.get(0).getStatuss()) {
                validSession = true;
                username = userSessionList.get(0).getUsername();
            }
            String xml = "";

            if (!validSession) {   //no session registered
                xml = "<forceLogin/>";
            } else {
                userSessionList = service.getAll();
                PersonService personService = getPersonService();
                Person person = personService.findByName(username).get(0);

                //confirm password
                if (person.getPassword().compareTo(password) == 0) {

                    //deactivate account
                    person.setActive(false);
                    personService.addOrUpdate(person);

                    //terminate all sessions of that user
                    for (int i = 0; i < userSessionList.size(); ++i) {
                        UserSession userSession = userSessionList.get(i);
                        if (userSession.getUsername().compareTo(username) == 0 && userSession.getStatuss()) {
                            userSession.setStatuss(false);
                            service.addOrUpdate(userSession);
                        }
                    }
                    xml = "<DeleteAccount>true</DeleteAccount>";
                } else {
                    xml = "<DeleteAccount>Invalid Password</DeleteAccount>";
                }
            }
            xmlResponse=xml;
            return "MOBILE";
        }
        else{
            return "PC";
        }

    }

    private UserSessionService getService() {
        ApplicationContext ap = WebApplicationContextUtils.getRequiredWebApplicationContext(org.apache.struts2.ServletActionContext.getServletContext());
        UserSessionService service = (UserSessionService) ap.getBean("usersessionService");
        return service;
    }

    private PersonService getPersonService() {
        ApplicationContext ap = WebApplicationContextUtils.getRequiredWebApplicationContext(org.apache.struts2.ServletActionContext.getServletContext());
        PersonService service = (PersonService) ap.getBean("personService");
        return service;
    }
}

