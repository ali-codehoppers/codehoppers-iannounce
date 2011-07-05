/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import hibernate.entities.Person;

/**
 *
 * @author Awais
 */
public class VerifyAccount extends BaseActionClass
  {

    private String username;
    private String verificationCode;

    public String getUsername()
      {
        return username;
      }

    public void setUsername(String username)
      {
        this.username = username;
      }

    public String getVerificationCode()
      {
        return verificationCode;
      }

    public void setVerificationCode(String verificationCode)
      {
        this.verificationCode = verificationCode;
      }

    @Override
    public String execute() throws Exception
      {

        Person person = personService.findByName(username).get(0);

        if (person.getVerificationcode().compareTo(verificationCode) == 0)
          {
            person.setVerified(true);   //change verified bit
            personService.addOrUpdate(person);

            //email account details
            String emailBody = generateEmailBody(person);
//          SendEmail verificationmail = new SendEmail(person.getEmail(), emailBody, "iAnnounce::Account verified");
            return SUCCESS;
          } else
          {
            return ERROR;
          }
      }

    private String generateEmailBody(Person person)
      {
        String retval;
        retval = "Dear User,\n";

        retval += "Your account has been verified. Below is your account information\n";
        retval += "First Name : " + person.getFirstName() + "\n";
        retval += "Last Name : " + person.getLastName() + "\n";
        retval += "Username : " + person.getUsername() + "\n";

        retval += "Gender : ";
        if (person.isGender())
          {
            retval += "Male";
          } else
          {
            retval += "Female";
          }
        retval += "\n";
        retval += "Date of Birth: " + person.getDateOfBirth() + "\n";
        retval += "\n\nRegards\nTeam iAnnounce";
        return retval;
      }
  }
