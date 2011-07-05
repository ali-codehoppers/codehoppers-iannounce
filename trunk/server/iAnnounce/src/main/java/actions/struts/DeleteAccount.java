/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import hibernate.entities.Person;
import hibernate.entities.UserSession;
import java.util.List;

/**
 *
 * @author Awais
 */
public class DeleteAccount extends BaseActionClass
  {

    private String xmlResponse;
    private String sessionId;
    private String password;

    public void setPassword(String password)
      {
        this.password = password;
      }

    public void setSessionId(String sessionId)
      {
        this.sessionId = sessionId;
      }

    

    @Override
    public String execute() throws Exception
      {

        if (request.getHeader("User-Agent").contains("UNAVAILABLE"))
          {
            //if(true){

            List<UserSession> userSessionList = userSessionService.findByName(sessionId);
            Boolean validSession = false;
            String username = "";

            //check if session is valid and username exists
            if (!userSessionList.isEmpty() && userSessionList.get(0).getStatuss())
              {
                validSession = true;
                username = userSessionList.get(0).getUsername();
              }
            String xml = "";

            if (!validSession)
              {   //no session registered
                xml = "<forceLogin/>";
              } else
              {
                userSessionList = userSessionService.getAll();

                Person person = personService.findByName(username).get(0);

                //confirm password
                if (person.getPassword().compareTo(password) == 0)
                  {
                    //deactivate account
                    person.setActive(false);
                    personService.addOrUpdate(person);

                    //terminate all sessions of that user
                    for (int i = 0; i < userSessionList.size(); ++i)
                      {
                        UserSession userSession = userSessionList.get(i);
                        if (userSession.getUsername().compareTo(username) == 0 && userSession.getStatuss())
                          {
                            userSession.setStatuss(false);
                            userSessionService.addOrUpdate(userSession);
                          }
                      }
                    xml = "<DeleteAccount>true</DeleteAccount>";
                  } else
                  {
                    xml = "<DeleteAccount>Invalid Password</DeleteAccount>";
                  }
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
