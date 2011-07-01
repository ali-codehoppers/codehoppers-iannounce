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

    public void setServletRequest(HttpServletRequest hsr) {
        this.request=hsr;
    }

    @Override
    public String execute() throws Exception {
        PersonService service = getService();

        if (request.getHeader("User-Agent").contains("UNAVAILABLE")) {
            //if(true){
            UserSessionService userSessionService = getUserSessionService();
            List<UserSession> userSessionList = userSessionService.findByName(request.getParameter("sessionId"));
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
                person.setFirstName(request.getParameter("firstName"));
                person.setLastName(request.getParameter("lastName"));

                //split date of birth to day month year
                String[] inputdate = request.getParameter("dateOfBirth").split("/");
                Date dob = new Date(Integer.parseInt(inputdate[0]) - 1900, Integer.parseInt(inputdate[1]) - 1, Integer.parseInt(inputdate[2]));
                person.setDateOfBirth(dob);

                //change male to true female to 0
                Boolean gender = true;
                if (request.getParameter("gender").compareTo("0") == 0) {
                    gender = false;
                }
                person.setGender(gender);
               
//                System.out.println("oldpas="+(request.getParameter("oldPassword")).length());

                //if password change request
                if ((request.getParameter("oldPassword")).length() != 0) {
                    //check if old password given matches the record

                    if ((person.getPassword()).compareTo(request.getParameter("oldPassword")) == 0) {
                        person.setPassword(request.getParameter("newPassword"));
                        service.addOrUpdate(person);

                        xml += "Information updated. The updated information has been sent on your email id " + person.getEmail();
                    } else {
                        xml += "Old password did not match.";
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
                request.setAttribute("responsexml", xml);
                
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
