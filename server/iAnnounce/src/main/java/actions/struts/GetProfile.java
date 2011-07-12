/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import hibernate.entities.Person;
import hibernate.entities.UserSession;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import xtras.Consts;

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

    @Override
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

            xml = "<response><responseCode>";
            if (!validSession) //no session registered
              {
//                xml = "<forceLogin/>"; // "1<responseCode><responseMessage>"+Consts.responseCodes[1]+"</responseMessage>"
                xml += "1</responseCode><responseMessage>" + Consts.responseCodes[1] + "</responseMessage>";
              } else
              {
//                xml = "<Profile>\n"; //"0<responseCode><responseMessage>"+Consts.responseCodes[0]+"</responseMessage><profile>

                List<Person> personList = personService.findByName(username);
                if (personList.size() > 0)
                  {
                    xml += "0</responseCode><responseMessage>"+Consts.responseCodes[0]+"</responseMessage><getProfile>";

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

                    String Str_Rating=Float.toString(person.getAvgrating());
                    if(Str_Rating.length()>8){
                        Str_Rating=Str_Rating.substring(0,7);
                    }
                    
                    xml += "<rating>" + Str_Rating + "</rating>\n";
                    xml += "<age>" + age + "</age>\n";
                    xml += "<gender>" + gender + "</gender>\n";
                    xml += "<numOfPosts>" + numOfPosts + "</numOfPosts>\n";

                    xml+= "</getProfile>";

                  } else
                  {
                    //if the person doesn't exits :D
                    xml+="19<responseCode><responseMessage>"+Consts.responseCodes[19]+"</responseMessage>";
                  }
                
//                xml += "</Profile>"; //</response>
                xml += "</response>";
                
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
