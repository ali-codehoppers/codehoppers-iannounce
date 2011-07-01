/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package actions.struts;

import com.opensymphony.xwork2.ActionSupport;
import hibernate.entities.Person;
import java.util.Date;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import services.PersonService;
import xtras.SendEmail;

/**
 *
 * @author Awais
 */
public class Register extends ActionSupport implements ServletRequestAware{
    
    private HttpServletRequest request;

    private String xmlResponse;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String gender;
    private String dob;

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
            String xml = "<register><isRegistered>";

            if (service.findByName(username).isEmpty()) {
                //split date of birth string to day month year
                String[] inputdate = dob.split("/");
                Date dob1 = new Date(Integer.parseInt(inputdate[0]) - 1900, Integer.parseInt(inputdate[1]) - 1, Integer.parseInt(inputdate[2]));

                //convert male to true, female to false
                Boolean fl_gender = true;
                if (gender.compareTo("0") == 0) {
                    fl_gender = false;
                }

                float zero = 0;
                String verificationCode = UUID.randomUUID().toString();      //reference http://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string-in-java
                Person person = new Person(0, firstName, lastName, dob1, username, password, fl_gender, false, email, verificationCode, 0, 0, true, false, zero);

                Integer newId = service.addNew(person);
//                System.out.print("this is newID="+newId);

                if (newId != 0) {
                    //send verification code by email
                    String emailBody = generateEmailBody(person);
                   // SendEmail verificationmail = new SendEmail(person.getEmail(), emailBody, "iAnnounce::Welcome to iAnnounce");

                    xml += "true</isRegistered><description>" + "You have been successfully registered. An email has been sent on your ID for verification. Please verify before login";
                } else {
                    xml += "false</isRegistered><description>Error, please try again";
                }
            } //on duplicate username that has been deleted
            else if (!service.findByName(username).get(0).isActive()) {
                xml += "false</isRegistered><description>Account with this username has been deleted. Please try another one.";
            } //on simple duplicate username
            else {
                xml += "false</isRegistered><description>This username is not available. Please try another one.";
            }

            xml += "</description></register>";
            xmlResponse=xml;

//            System.out.println("xml="+xml);


        return "MOBILE";
    }
        else{
            
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
        retval = "Dear User,\n";
        retval += "Thank you for registering to iAnnounce with username '" + person.getUsername() + "'. To verify your account please click on the link below or paste in on your browser.\n";
        retval += "http://localhost:8080/sample/do/PersonVerification?username=" + person.getUsername() + "&verificationCode=" + person.getVerificationcode();
        retval += "\n\nRegards\nTeam iAnnounce";
        return retval;
    }


}
