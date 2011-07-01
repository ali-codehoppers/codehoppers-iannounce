/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package actions.struts;

import com.opensymphony.xwork2.ActionSupport;
import hibernate.entities.Person;
import hibernate.entities.UserSession;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import services.PersonService;
import services.UserSessionService;

/**
 *
 * @author Awais
 */
public class EditProfile extends ActionSupport implements ServletRequestAware{
    
    private HttpServletRequest request;

    private String xmlResponse;

    private String sessionId;
    private String oldPassword;
    private String newPassword;
    private String gender;
    private String firstName;
    private String lastName;
    private String dateOfBirth;

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getXmlResponse() {
        return xmlResponse;
    }

    public void setXmlResponse(String xmlResponse) {
        this.xmlResponse = xmlResponse;
    }

    

    public void setServletRequest(HttpServletRequest hsr) {
        this.request=hsr;
    }

    @Override
    public String execute() throws Exception {
        PersonService service = getService();

        if (request.getHeader("User-Agent").contains("UNAVAILABLE")) {
            //if(true){
            UserSessionService userSessionService = getUserSessionService();
            List<UserSession> userSessionList = userSessionService.findByName(sessionId);
            Boolean validSession = false;
            String username = "";
            boolean sendMail = true;

            //check if session is valid and get username
            if (!userSessionList.isEmpty() && userSessionList.get(0).getStatuss()) {
                validSession = true;
                username = userSessionList.get(0).getUsername();
            }

            String xml;
            if (!validSession) //no session registered
            {
                xml = "<forceLogin/>";
            } else {
                xml = "<EditProfile>";
                //get record
                Person person = service.findByName(username).get(0);

                // get details
                person.setFirstName(firstName);
                person.setLastName(lastName);

                //split date of birth to day month year
                String[] inputdate = dateOfBirth.split("/");
                Date dob = new Date(Integer.parseInt(inputdate[0]) - 1900, Integer.parseInt(inputdate[1]) - 1, Integer.parseInt(inputdate[2]));
                person.setDateOfBirth(dob);

                //change male to true female to 0
                Boolean fl_gender = true;
                if (gender.compareTo("0") == 0) {
                    fl_gender = false;
                }
                person.setGender(fl_gender);
               


                //if password change request
                if ((oldPassword).length() != 0) {
                    //check if old password given matches the record

                    if ((person.getPassword()).compareTo(oldPassword) == 0) {
                        person.setPassword(newPassword);
                        service.addOrUpdate(person);

                        xml += "Information updated. The updated information has been sent on your email id " + person.getEmail();
                    } else {
                        xml += "Incorrect Old Password.";
                        sendMail = false;
                    }
                } else {
                    service.addOrUpdate(person);
                    xml += "Information updated. The updated information has been sent on your email id " + person.getEmail();
                }



                xml += "</EditProfile>";

                if (sendMail) {//email updated information
                    String emailBody = generateEmailBody(person);
                    //SendEmail verificationmail = new SendEmail(person.getEmail(), emailBody, "iAnnounce::Account information updated");
                }
                xmlResponse=xml;
                
            }
            return "MOBILE";
        }else{

//        PersonForm pForm = (PersonForm) form;
//        service.addOrUpdate(pForm.getPerson());
        return "PC";
        }
    }

    private PersonService getService() {
        ApplicationContext ap = WebApplicationContextUtils.getRequiredWebApplicationContext(org.apache.struts2.ServletActionContext.getServletContext());
        PersonService service = (PersonService) ap.getBean("personService");
        return service;
    }

    private UserSessionService getUserSessionService() {
        ApplicationContext ap = WebApplicationContextUtils.getRequiredWebApplicationContext(org.apache.struts2.ServletActionContext.getServletContext());
        UserSessionService service = (UserSessionService) ap.getBean("usersessionService");
        return service;
    }

    private String generateEmailBody(Person person) {
        String retval;
        retval = "Dear User,\n";
        retval += "Your account information has been updated as below\n";
        retval += "First Name : " + person.getFirstName() + "\n";
        retval += "Last Name : " + person.getLastName() + "\n";
//      retval += "Password : " + person.getPassword() + "\n";
        retval += "Gender : ";
        if (person.isGender()) {
            retval += "Male";
        } else {
            retval += "Female";
        }
        retval += "\n";
        retval += "Date of Birth : " + person.getDateOfBirth() + "\n";
        retval += "\n\nRegards\nTeam iAnnounce";
        return retval;
    }
}
