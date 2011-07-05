/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.struts;

import com.opensymphony.xwork2.ActionSupport;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import services.AnnouncementService;
import services.CommentService;
import services.PersonService;
import services.RangeService;
import services.RatingService;
import services.UserSessionService;

/**
 *
 * @author CodeHopper
 */
public class BaseActionClass extends ActionSupport implements ServletRequestAware
  {

    protected String username;
    protected HttpServletRequest request;
    protected UserSessionService userSessionService;
    protected PersonService personService;
    protected CommentService commentService;
    protected AnnouncementService announcementService;
    protected RangeService rangeService;
    protected RatingService ratingService;

    public void setUsername(String username)
      {
        this.username = username;
      }

    public void setServletRequest(HttpServletRequest hsr)
      {
        request = hsr;
      }

    public void setAnnouncementService(AnnouncementService announcementService)
      {
        this.announcementService = announcementService;
      }

    public void setCommentService(CommentService commentService)
      {
        this.commentService = commentService;
      }

    public void setPersonService(PersonService personService)
      {
        this.personService = personService;
      }

    public void setRangeService(RangeService rangeService)
      {
        this.rangeService = rangeService;
      }

    public void setRatingService(RatingService ratingService)
      {
        this.ratingService = ratingService;
      }

    public void setUserSessionService(UserSessionService userSessionService)
      {
        this.userSessionService = userSessionService;
      }
  }
