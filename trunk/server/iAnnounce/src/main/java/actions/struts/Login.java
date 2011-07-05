/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import hibernate.entities.Person;
import hibernate.entities.UserSession;
import java.util.List;
import java.util.UUID;


/**
 *
 * @author Awais
 */
public class Login extends BaseActionClass
  {

    private String xmlResponse;
    private String username;
    private String password;

    public void setPassword(String password)
      {
        this.password = password;
      }

    public void setUsername(String username)
      {
        this.username = username;
      }

    public void setXmlResponse(String xmlResponse)
      {
        this.xmlResponse = xmlResponse;
      }

    @Override
    public String execute() throws Exception
      {

        if (request.getHeader("User-Agent").contains("UNAVAILABLE"))
          {
            // if(true){
            boolean userExists = false;

            String xml = "<login><isLoggedIn>";

            //check if username exists
            List<Person> personList = personService.findByName(username);
            if (personList.isEmpty())
              {
                xml += "false</isLoggedIn><description>No such user exists</description>";
              } else
              {
                Person person = personList.get(0);
                if (person.isType())
                  {
                    xml += "false</isLoggedIn><description>This is a premiuim account and cannot be accessed from here</description>";
                  } else
                  {
                    if (!person.isActive()) //if account has been deleted
                      {
                        xml += "false</isLoggedIn><description>This account has been deactivated.</description>";
                      } else
                      {
                        if (!person.isVerified()) //if account has not yet been verified
                          {
                            xml += "false</isLoggedIn><description>Please verify account before logging in.</description>";
                          } else
                          {   //if password is incorrect
                            if (person.getPassword().compareTo(password) != 0)
                              {
                                xml += "false</isLoggedIn><description>Incorrect password.</description>";
                              } else
                              {
                                //generate session ID
                                String sessionCode = UUID.randomUUID().toString();
                                UserSession userSession = new UserSession(0, sessionCode, username, true);

                                //insert session in Db
                                Integer newId = userSessionService.addNew(userSession);

                                if (newId != 0)
                                  {
                                    xml += "true</isLoggedIn><sessionId>" + sessionCode + "</sessionId>";
                                  } else
                                  {
                                    xml += "false</isLoggedIn><description>Error, please try again.</description>";
                                  }
                              }
                          }
                      }
                  }
              }

            xml += "</login>";
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
