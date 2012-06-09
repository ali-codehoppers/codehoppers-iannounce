/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import hibernate.entities.Person;
import java.util.Date;
import java.util.UUID;
import xtras.Consts;
import xtras.SendEmail;

/**
 *
 * @author Awais
 */
public class Register extends BaseActionClass
{

    private String xmlResponse;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String gender;
    private String dob;

    public void setDob(String dob)
    {
        this.dob = dob;
    }

    public void setEmail(String email)
    {
        this.email = email;
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

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public String execute() throws Exception
    {
        if (request.getHeader("User-Agent").contains("UNAVAILABLE"))
          {
            //if(true){
//            String xml = "<register><isRegistered>"; //"<response><responseCode>"
            String xml = "<response><responseCode>";

            if (personService.findByName(username).isEmpty())
              {
                //split date of birth string to day month year
                String[] inputdate = dob.split("/");
                Date dob1 = new Date(Integer.parseInt(inputdate[0]) - 1900, Integer.parseInt(inputdate[1]) - 1, Integer.parseInt(inputdate[2]));

                //convert male to true, female to false
                Boolean fl_gender = true;
                if (gender.compareTo("0") == 0)
                  {
                    fl_gender = false;
                  }

                float zero = 0;
                String verificationCode = UUID.randomUUID().toString();      //reference http://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string-in-java
                Person person = new Person(0, firstName, lastName, dob1, username, password, fl_gender, false, email, verificationCode, 0, 0, true, false, zero);

                Integer newId = personService.addNew(person);
//                System.out.print("this is newID="+newId);

                if (newId != 0)
                  {
                    //send verification code by email
                    String emailBody = generateEmailBody(person);
                     SendEmail verificationmail = new SendEmail(person.getEmail(), emailBody, "iAnnounce::Welcome to iAnnounce");

//                    xml += "true</isRegistered><description>" + "You have been successfully registered. An email has been sent on your ID for verification. Please verify before login"; //0<responseCode><responseMessage>"+Consts.responseCodes[0]+"</responseMessage><register>Conts.Registration_Success</register>"
                    xml += "0</responseCode><responseMessage>" + Consts.responseCodes[0] + "</responseMessage><register>" + Consts.REGISTERATION_SUCCESS + "</register>";
                  } else
                  {
//                    xml += "false</isRegistered><description>Error, please try again";  //16<responseCode><responseMessage>"+Consts.responseCodes[16]+"</responseMessage>
                    xml += "16</responseCode><responseMessage>" + Consts.responseCodes[16] + "</responseMessage>";
                  }
              } //on duplicate username that has been deleted
            else
              {
                if (!personService.findByName(username).get(0).isActive())
                  {
//                    xml += "false</isRegistered><description>Account with this username has been deleted. Please try another one."; //
                    xml += "5</responseCode><responseMessage>" + Consts.responseCodes[5] + "</responseMessage>";
                  } //on simple duplicate username
                else
                  {
//                    xml += "false</isRegistered><description>This username is not available. Please try another one."; //3<responseCode><responseMessage>"+Consts.responseCodes[3]+"</responseMessage>
                    xml += "3</responseCode><responseMessage>" + Consts.responseCodes[3] + "</responseMessage>";
                  }
              }

//            xml += "</description></register>"; //</response>

            xml += "</response>";
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
        retval += "Thank you for registering to iAnnounce with username '" + person.getUsername() + "'. To verify your account please click on the link below or paste in on your browser.\n";
        retval += "http://localhost:8080/sample/do/PersonVerification?username=" + person.getUsername() + "&verificationCode=" + person.getVerificationcode();
        retval += "\n\nRegards\nTeam iAnnounce";
        return retval;
    }
}
