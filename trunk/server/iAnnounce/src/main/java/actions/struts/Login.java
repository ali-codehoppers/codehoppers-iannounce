/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import hibernate.entities.Person;
import hibernate.entities.UserSession;
import java.util.List;
import java.util.UUID;
import xtras.Consts;


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
            

            String xml = "<login><isLoggedIn>"; //<response><responseCode>

            //check if username exists
            List<Person> personList = personService.findByName(username);
            if (personList.isEmpty())
              {
                xml += "false</isLoggedIn><description>No such user exists</description>"; //+"6"+"</responseCode><responseMessage>"+Consts.responseCodes[6]+"</responseMessage>"                
              } else
              {
                Person person = personList.get(0);
                if (person.isType())
                  {
                    xml += "false</isLoggedIn><description>This is a premiuim account and cannot be accessed from here</description>"; //+"7"+"</responseCode><responseMessage>"+Consts.responseCodes[7]+"</responseMessage>"
                  } else
                  {
                    if (!person.isActive()) //if account has been deleted
                      {
                        xml += "false</isLoggedIn><description>This account has been deactivated.</description>"; // +"8"+"</responseCode><responseMessage>"+Consts.responseCodes[8]+"</responseMessage>"
                      } else
                      {
                        if (!person.isVerified()) //if account has not yet been verified
                          {
                            xml += "false</isLoggedIn><description>Please verify account before logging in.</description>"; // +"9"+"</responseCode><responseMessage>"+Consts.responseCodes[9]+"</responseMessage>"
                          } else
                          {   //if password is incorrect
                            if (person.getPassword().compareTo(password) != 0)
                              {
                                xml += "false</isLoggedIn><description>Incorrect password.</description>";// +"2"+"</responseCode><responseMessage>"+Consts.responseCodes[2]+"</responseMessage>"
                              } else
                              {
                                //generate session ID
                                String sessionCode = UUID.randomUUID().toString();
                                UserSession userSession = new UserSession(0, sessionCode, username, true);

                                //insert session in Db
                                Integer newId = userSessionService.addNew(userSession);

                                if (newId != 0)
                                  {
                                    xml += "true</isLoggedIn><sessionId>" + sessionCode + "</sessionId>"; // +"0"+"</responseCode><responseMessage>"+Consts.responseCodes[0]+"</responseMessage><sessionId>"+sessionCode+</sessionId>
                                  } else
                                  {
                                    xml += "false</isLoggedIn><description>Error, please try again.</description>"; // +"10"+"</responseCode><responseMessage>"+Consts.responseCodes[10]+"</responseMessage>
                                  }
                              }
                          }
                      }
                  }
              }

            xml += "</login>"; // </login></response>
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
