package actions.struts;

import hibernate.entities.Announcement;
import hibernate.entities.Person;
import java.sql.Timestamp;

/**
 *
 * @author Awais
 */
public class AnnouncementPost extends BaseActionClass
  {

    private String xmlResponse;
    
    private String range;
    private String announce;
    private String longitude;
    private String latitude;

    public void setAnnounce(String announce)
      {
        this.announce = announce;
      }

    public void setLatitude(String latitude)
      {
        this.latitude = latitude;
      }

    public void setLongitude(String longitude)
      {
        this.longitude = longitude;
      }

    public void setRange(String range)
      {
        this.range = range;
      }


    @Override
    public String execute() throws Exception
      {
        

        if (request.getHeader("User-Agent").contains("UNAVAILABLE"))
          {
        
                //update currunt location of person

                Person person = personService.findByName(username).get(0);
                person.setLatitude(Double.parseDouble(latitude));
                person.setLongitude(Double.parseDouble(longitude));
                personService.addOrUpdate(person);
        
            String xml;

            
                xml = "<announce>";


                //get range in km against string

                int radius = rangeService.findByName(range).get(0).getValuee();

                //get currunt timestamp to inseert in DB
                java.util.Date date = new java.util.Date();
                Timestamp time = new Timestamp(date.getTime());

                //normal announcement with 0 ranking
                Announcement announcement = new Announcement(0, Double.parseDouble(latitude), Double.parseDouble(longitude), announce, radius, time, false, username, 0);

                Integer newId = announcementService.addNew(announcement);

                if (newId != 0)
                  {
                    xml += "Announcement successfully posted. Will be shortly available";
                  } else
                  {
                    xml += "Error. Please try again";
                  }

                xml += "</announce>";
              

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
  }
