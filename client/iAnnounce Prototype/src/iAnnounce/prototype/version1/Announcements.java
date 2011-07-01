package iAnnounce.prototype.version1;

/**
 * Class for an announcement. 
 * @author Awais
 *
 */

public class Announcements {
	/**
	 * Will consist the username of announcement
	 */
	public String announcer;
	
	/**
	 * Will consist of announcement id
	 */
	public String announcement_id;
	/**
	 * Will consist of description or text of the announcement
	 */
	public String description;
	/**
	 * Will consists of date and time of the announcemet seperated by a space
	 */
	public String timestamp;
	
	/**
	 * will consists of rating of the announcement
	 */
	public String averageRating;
	/**
	 * will consists of longitude of announcement where it was posted
	 */
	public String longitude;
	/**
	 * will consists of longitude of announcement where it was posted
	 */
	public String latitude;
	/**
	 * will consists of state that if the current logged in user have rated or not have rated the announcement yet. -1: for thumbs down, 0:for not rate,1:thumbs rated
	 */
	public String currentUserRating;
	/**
	 * will consists of number of comments which are posted on this announcement
	 */
	public String noOfComments;	

}
