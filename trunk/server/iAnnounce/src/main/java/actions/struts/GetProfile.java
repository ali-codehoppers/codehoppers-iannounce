/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import com.opensymphony.xwork2.ActionSupport;
import hibernate.entities.Person;
import hibernate.entities.UserSession;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import services.AnnouncementService;
import services.PersonService;
import services.UserSessionService;

/**
 *
 * @author Awais
 */
public class GetProfile extends BaseActionClass
  {

    private String xmlResponse;
    private String sessionId;
    private String username;

    public void setSessionId(String sessionId)
      {
        this.sessionId = sessionId;
      }

    public void setUsername(String username)
      {
        this.username = username;
      }

    @Override
    public String execute() throws Exception
      {

        if (request.getHeader("User-Agent").contains("UNAVAILABLE"))
          {
            //  if(true){

            // check valid session

            List<UserSession> userSessionList = userSessionService.findByName(sessionId);
            Boolean validSession = false;

            if (!userSessionList.isEmpty() && userSessionList.get(0).getStatuss())
              {
                validSession = true;
              }



            String xml;
            if (!validSession) //no session registered
              {
                xml = "<forceLogin/>";
              } else
              {
                xml = "<Profile>\n";
//                String username = request.getParameter("username");
                List<Person> personList = personService.findByName(username);
                if (personList.size() > 0)
                  {
                    Person person = personList.get(0);
                    int age = calculateAge(person.getDateOfBirth());


                    int numOfPosts = announcementService.findByName(username).size();

                    //convert gender from bool to int
                    int gender = 0;
                    if (person.isGender())
                      {
                        gender = 1;
                      }

                    xml += "<firstName>" + person.getFirstName() + "</firstName>\n";
                    xml += "<lastName>" + person.getLastName() + "</lastName>\n";
                    xml += "<username>" + username + "</username>\n";
                    xml += "<rating>" + person.getAvgrating() + "</rating>\n";
                    xml += "<age>" + age + "</age>\n";
                    xml += "<gender>" + gender + "</gender>\n";
                    xml += "<numOfPosts>" + numOfPosts + "</numOfPosts>\n";

                  }

                xml += "</Profile>";
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

    private int calculateAge(Date dateOfBirth)
      {  //source http://stackoverflow.com/questions/1116123/how-do-i-calculate-someones-age-in-java
        Calendar now = Calendar.getInstance();
        Calendar dob = Calendar.getInstance();
        dob.setTime(dateOfBirth);

        int year1 = now.get(Calendar.YEAR);
        int year2 = dob.get(Calendar.YEAR);
        int age = year1 - year2;
        int month1 = now.get(Calendar.MONTH);
        int month2 = dob.get(Calendar.MONTH);
        if (month2 > month1)
          {
            age--;
          } else if (month1 == month2)
          {
            int day1 = now.get(Calendar.DAY_OF_MONTH);
            int day2 = dob.get(Calendar.DAY_OF_MONTH);
            if (day2 > day1)
              {
                age--;
              }
          }
        return age;
      }
  }