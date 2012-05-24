package actions.struts;

import hibernate.entities.UserSession;
import java.util.List;
import xtras.Consts;

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
            xml="<response><responseCode>";

            if (!validSession)
              {   //no session registered
//                xml = "<forceLogin/>"; //1<responseCode><responseMessage>"+Consts.responseCodes[1]+"</responseMessage>"
                xml += "1</responseCode><responseMessage>"+Consts.responseCodes[1]+"</responseMessage>";
                
              } else
              {
                UserSession userSession = userSessionList.get(0);
                userSession.setStatuss(false);
                userSessionService.addOrUpdate(userSession);
//                xml = "<Logout>Logged out</Logout>";//0<responseCode><responseMessage>"+Consts.responseCodes[0]+"</responseMessage><logOut>Consts.LOGOUT_SUCCESS</logOut>"
                xml += "0</responseCode><responseMessage>"+Consts.responseCodes[0]+"</responseMessage><logOut>"+Consts.LOGOUT_SUCCESS+"</logOut>";
                
              }

            xml+="</response>";
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
