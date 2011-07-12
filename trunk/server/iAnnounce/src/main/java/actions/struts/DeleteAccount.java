/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import hibernate.entities.Person;
import hibernate.entities.UserSession;
import java.util.List;
import xtras.Consts;

/**
 *
 * @author Awais
 */
public class DeleteAccount extends BaseActionClass
{

    private String xmlResponse;
    private String password;

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public String execute() throws Exception
    {

        if (request.getHeader("User-Agent").contains("UNAVAILABLE"))
          {
//            String xml = ""; //<response><responseCode>
            String xml="<response><responseCode>";
            List<UserSession> userSessionList = userSessionService.getAll();

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
                        xml+="0</responseCode><responseMessage>"+Consts.responseCodes[0]+"</responseMessage><deleteAccount>"+Consts.DELETEACCOUNT_SUCCESS+"</deleteAccount>";
                      }
                  }
//                xml = "<DeleteAccount>true</DeleteAccount>"; //0<responseCode><responseMessage>"+Consts.responseCodes[0]+"</responseMessage><deleteAccount>Const.DELETEACCOUNT_SUCCESS<deleteAccount>"
              } else
              {
//                xml = "<DeleteAccount>Invalid Password</DeleteAccount>"; //13<responseCode><responseMessage>"+Consts.responseCodes[13]+"</responseMessage>"
                xml+="13</responseCode><responseMessage>"+Consts.responseCodes[13]+"</responseMessage>";
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
