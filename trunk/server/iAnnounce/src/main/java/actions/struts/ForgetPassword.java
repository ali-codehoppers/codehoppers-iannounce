/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import hibernate.entities.Person;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Awais
 */
public class ForgetPassword extends BaseActionClass
  {

    private String xmlResponse;
   
    @Override
    public String execute() throws Exception
      {

        if (request.getHeader("User-Agent").contains("UNAVAILABLE"))
          {
            //if(true){
            String xml = "<forgotPassword>"; // <response><responseCode>

            List<Person> personList = personService.findByName(username);

            //check if username exists
            if (personList.isEmpty())
              {
                xml += "No such user exists"; // 6<responseCode><responseMessage>"+Consts.responseCodes[6]+"</responseMessage>
              } else
              {
                Person person = personList.get(0);

                //generate new password
                String newPassword = UUID.randomUUID().toString().substring(0, 10).replaceAll("-", "");
                person.setPassword(newPassword);
                personService.addOrUpdate(person);

                //email new password to the person
                String emailId = person.getEmail();
                String emailBody = generateEmailBody(person);
                //SendEmail passwordmail = new SendEmail(emailId, emailBody, "iAnnounce::Your password");
                xml += "An email with your password has been sent to " + emailId + " Please check your email to get your password."; //0<responseCode><responseMessage>"+Consts.responseCodes[0]+"</responseMessage><forgotPassword>Const.FORGETPASS</forgotPassword>
              }

            xml += "</forgotPassword>"; //</response>

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

    private String generateEmailBody(Person person)
      {
        String retval;
        retval = "Dear ";
        if (person.isGender())
          {
            retval += "Mr.";
          } else
          {
            retval += "Ms.";
          }
        retval += person.getFirstName() + " " + person.getLastName() + ",\n";
        retval += "Username:" + person.getUsername() + "\n";
//        retval += "Password:" + person.getPassword() + "\n";
        retval += "Please change your password as soon as you login.";
        retval += "\n\nRegards\nTeam iAnnounce";
        return retval;
      }
  }
