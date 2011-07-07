/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import hibernate.entities.Person;
import hibernate.entities.UserSession;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Awais
 */
public class EditProfile extends BaseActionClass
{

    private String xmlResponse;
    private String oldPassword;
    private String newPassword;
    private String gender;
    private String firstName;
    private String lastName;
    private String dateOfBirth;

    public void setDateOfBirth(String dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setNewPassword(String newPassword)
    {
        this.newPassword = newPassword;
    }

    public void setOldPassword(String oldPassword)
    {
        this.oldPassword = oldPassword;
    }

    @Override
    public String execute() throws Exception
    {

        if (request.getHeader("User-Agent").contains("UNAVAILABLE"))
          {

            boolean sendMail = true;

            String xml;
            xml = "<EditProfile>"; //<response><responseCode>
            //get record
            Person person = personService.findByName(username).get(0);

            // get details
            person.setFirstName(firstName);
            person.setLastName(lastName);

            //split date of birth to day month year
            String[] inputdate = dateOfBirth.split("/");
            Date dob = new Date(Integer.parseInt(inputdate[0]) - 1900, Integer.parseInt(inputdate[1]) - 1, Integer.parseInt(inputdate[2]));
            person.setDateOfBirth(dob);

            //change male to true female to 0
            Boolean fl_gender = true;
            if (gender.compareTo("0") == 0)
              {
                fl_gender = false;
              }
            person.setGender(fl_gender);

            //if password change request
            if ((oldPassword).length() != 0)
              {
                //check if old password given matches the record

                if ((person.getPassword()).compareTo(oldPassword) == 0)
                  {
                    person.setPassword(newPassword);
                    personService.addOrUpdate(person);

                    xml += "Information updated. The updated information has been sent on your email address" + person.getEmail(); //0<responseCode><responseMessage>"+Consts.responseCodes[0]+"</responseMessage><editProfile>Const.EDITPROFILE_SUCCESS</editProfile>"
                  } else
                  {
                    xml += "Incorrect Old Password.";//14<responseCode><responseMessage>"+Consts.responseCodes[14]+"</responseMessage>
                    sendMail = false;
                  }
              } else
              {
                personService.addOrUpdate(person);
                xml += "Information updated. The updated information has been sent on your email id " + person.getEmail(); //0<responseCode><responseMessage>"+Consts.responseCodes[0]+"</responseMessage><editProfile>Const.EDITPROFILE_SUCCESS</editProfile>"
              }

            xml += "</EditProfile>"; //</response>

            if (sendMail)
              {//email updated information
                String emailBody = generateEmailBody(person);
                //SendEmail verificationmail = new SendEmail(person.getEmail(), emailBody, "iAnnounce::Account information updated");
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

    private String generateEmailBody(Person person)
    {
        String retval;
        retval = "Dear User,\n";
        retval += "Your account information has been updated as below\n";
        retval += "First Name : " + person.getFirstName() + "\n";
        retval += "Last Name : " + person.getLastName() + "\n";
//      retval += "Password : " + person.getPassword() + "\n";
        retval += "Gender : ";
        if (person.isGender())
          {
            retval += "Male";
          } else
          {
            retval += "Female";
          }
        retval += "\n";
        retval += "Date of Birth : " + person.getDateOfBirth() + "\n";
        retval += "\n\nRegards\nTeam iAnnounce";
        return retval;
    }
}
