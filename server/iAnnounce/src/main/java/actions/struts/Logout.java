/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import hibernate.entities.UserSession;
import java.util.List;

/**
 *
 * @author Awais
 */
public class Logout extends BaseActionClass
  {

    private String xmlResponse;
    private String sessionId;

    public void setSessionId(String sessionId)
      {
        this.sessionId = sessionId;
      }

    @Override
    public String execute() throws Exception
      {

        if (request.getHeader("User-Agent").contains("UNAVAILABLE"))
          {
            // if(true){

            List<UserSession> userSessionList = userSessionService.findByName(sessionId);
            Boolean validSession = false;
            //check if valid session
            if (!userSessionList.isEmpty() && userSessionList.get(0).getStatuss())
              {
                validSession = true;
              }


            String xml;

            if (!validSession)
              {   //no session registered
                xml = "<forceLogin/>";
              } else
              {
                UserSession userSession = userSessionList.get(0);
                userSession.setStatuss(false);
                userSessionService.addOrUpdate(userSession);
                xml = "<Logout>Logged out</Logout>";
              }
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
