/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package actions.struts;

import com.opensymphony.xwork2.ActionSupport;
import hibernate.entities.UserSession;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import services.UserSessionService;

/**
 *
 * @author Awais
 */
public class Logout extends ActionSupport implements ServletRequestAware {

    private HttpServletRequest request;

    public void setServletRequest(HttpServletRequest hsr) {
        this.request=hsr;
    }

    @Override
    public String execute() throws Exception {
        if (request.getHeader("User-Agent").contains("UNAVAILABLE")) {
            // if(true){
            UserSessionService service = getService();
            List<UserSession> userSessionList = service.findByName(request.getParameter("sessionId"));
            Boolean validSession = false;
            //check if valid session
            if (!userSessionList.isEmpty() && userSessionList.get(0).getStatuss()) {
                validSession = true;
            }


            String xml;

            if (!validSession) {   //no session registered
                xml = "<forceLogin/>";
            } else {
                UserSession userSession = userSessionList.get(0);
                userSession.setStatuss(false);
                service.addOrUpdate(userSession);
                xml = "<Logout>Logged out</Logout>";
            }
            request.setAttribute("responsexml", xml);
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
}
