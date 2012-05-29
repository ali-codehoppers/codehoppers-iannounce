package iAnnounce.prototype.version1;

import java.util.ArrayList;
/**
 * A well structured data which is returned by the XML parser
 * @author Awais
 * @version 1
 */

public class ServerResponse {
	/**
	 * if registration is successful or not?
	 */
	public boolean isRegistered;
	/**
	 * Response we got after registration call. Consists of text from the server
	 */
	public String register_response;
	/**
	 * If loggin was successfull or 
	 */
	
	public boolean isLoggedin;
	/**
	 * text for Logging in error 
	 */
	public String login_Error;
	/**
	 * session id after loggin in
	 */
	public String session_id;
	/**
	 * Forgot passnotification is text in response of forgot usecase
	 */
	
	public String forgotPassResponse;
	
	/**
	 * Response after posting an announcement
	 */
	
	public String PostAnnouncementResponse;
	
	/**
	 * If  forceLogin is encounter then set to true else false.
	 */
	
	boolean forceLogin;
	/**
	 * Response after rating an announcement
	 */
	public String rateResponse;
	/**
	 * Response after posting a comment on an announcement
	 */
	
	public String postComResponse;
	/**
	 * response of server after logging out
	 */
	public String logoutResponse;
	/**
	 * Response of server after deleting and account
	 */
	public String delAccResponse;
	/**
	 * Response of server after edit profile request
	 */
	public String editProResponse;
	
	
	User userProfile;
	
	
	ArrayList<Classcomment> comments;
	
	
	public ArrayList<Announcements> feed;
	
	public ArrayList<Neigbhours> neigbhours;
	
	public ArrayList<Location> locations;	
	
	public String responseCode;
	public String responseMessage;
	
	

	public ServerResponse() {
		super();
		isRegistered=false;
		isLoggedin=false;
		forceLogin=false;
		userProfile=new User();
		feed=new ArrayList<Announcements>();
		neigbhours = new ArrayList<Neigbhours>();
		comments=new ArrayList<Classcomment>();
		locations= new ArrayList<Location>();
	}
	
	
	
}
