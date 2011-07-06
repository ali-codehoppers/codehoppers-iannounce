/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import hibernate.entities.UserSession;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import services.UserSessionService;

/**
 *
 * @author CodeHopper
 */
public class VerifySession extends AbstractInterceptor
  {
//    private HttpServletRequest request;

    private String sessionId;
    private UserSessionService userSessionService;
    private Logger logger = Logger.getLogger(VerifySession.class);

    public void setUserSessionService(UserSessionService userSessionService)
      {
        this.userSessionService = userSessionService;
      }
    
//    public void setServletRequest(HttpServletRequest hsr)
//      {
//        request=hsr;
//      }

    

    @Override
    public String intercept(ActionInvocation ai)
      {
        HttpServletRequest request = ServletActionContext.getRequest();

        sessionId=request.getParameter("sessionId");
        String username;
        List<UserSession> userSessionList = userSessionService.findByName(sessionId);
        if (!userSessionList.isEmpty() && userSessionList.get(0).getStatuss())
          {
            username = userSessionList.get(0).getUsername();
            logger.error("sessionID=" + sessionId + ",username=" + username);
            ai.getStack().setValue("username", username);
            try
              {
                return ai.invoke();
              } catch (Exception ex)
              {
                logger.error(ex);
                return "ERROR";
              }

          } else
          {
            logger.error("Invalid session or session is expired, sessionID=" + sessionId);
            return "INVALIDSESSIONID";
          }
      }


  }
