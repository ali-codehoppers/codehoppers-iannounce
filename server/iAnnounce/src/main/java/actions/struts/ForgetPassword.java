/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package actions.struts;

import com.opensymphony.xwork2.ActionSupport;
import hibernate.entities.Person;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import services.PersonService;

/**
 *
 * @author Awais
 */
public class ForgetPassword extends ActionSupport implements ServletRequestAware{
    private HttpServletRequest request;

    public void setServletRequest(HttpServletRequest hsr) {
        this.request=hsr;
    }

    @Override
    public String execute() throws Exception {

        PersonService service = getService();

        if (request.getHeader("User-Agent").contains("UNAVAILABLE")) {
            //if(true){
            String xml = "<forgotPassword>";

            List<Person> personList = service.findByName(request.getParameter("username"));

            //check if username exists
            if (personList.isEmpty()) {
                xml += "No such user exists";
            } else {
                Person person = personList.get(0);

                //generate new password
                String newPassword = UUID.randomUUID().toString().substring(0, 10).replaceAll("-", "");
                person.setPassword(newPassword);
                service.addOrUpdate(person);

                //email new password to the person
                String emailId = person.getEmail();
                String emailBody = generateEmailBody(person);
                //SendEmail passwordmail = new SendEmail(emailId, emailBody, "iAnnounce::Your password");
                xml += "An email with your password has been sent to " + emailId + " Please check your email to get your password.";
            }

            xml += "</forgotPassword>";

            request.setAttribute("responsexml", xml);
            return "MOBILE";
        } else {
            return "PC";
        }
    }

    private PersonService getService() {
        ApplicationContext ap = WebApplicationContextUtils.getRequiredWebApplicationContext(org.apache.struts2.ServletActionContext.getServletContext());
        PersonService service = (PersonService) ap.getBean("personService");
        return service;
    }

    private String generateEmailBody(Person person) {
        String retval;
        retval = "Dear ";
        if (person.isGender()) {
            retval += "Mr.";
        } else {
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
