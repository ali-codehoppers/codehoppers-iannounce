package iAnnounce.prototype.version1;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.xml.sax.SAXException;

import android.util.Log;
import android.util.Xml;
import android.widget.Toast;
/**
 * User Class for storing information of a user
 * @author Awais
 *@version 1
 */
public class User {
	/**
	 * For Storing Firstname of user
	 */
	public String firstName;
	/**
	 * For storing last name of the user
	 */
	public String lastName;
	/**
	 * For storing the username of a user
	 */
	public String userName;
	/**
	 * For storing the age of a user
	 */
	public String age;
	/**
	 * For storing the number of posts a user have made
	 */
	
	public String numofPost;
	/**
	 * For storing the password of a user
	 */	
	private String password;
	
	/**
	 * For storing the date of birth of a user
	 */
	public String dob;
	/**
	 * For storing the gender of a user
	 */
	public String gender;
	
		
	private String uid;
	private String sessionId;
	
	/**
	 * For storing the average rating of the user 
	 */
	
	public String averageRating;
	/**
	 * Email of the user
	 */
	public String email;
	
	/**
	 * Getter for password
	 * @return String Password
	 */
	
	public String getPassword() {
		return password;
	}
	
	/**
	 * Setter for password
	 * @param password string
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * getter for the sessionId
	 * @return String will return sessionId of the user
	 */
	public String getSessionId() {
		return sessionId;
	}
	
	/**
	 * setter for the session id
	 * @param sessionId String
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	
	/* Functions :D */
	
	/**
	 * For registering the user
	 */
	
	public String register(){
		HttpPostRequest httpRe=new HttpPostRequest();
		String response1="";
		try {
			response1=httpRe.register(this.firstName, this.lastName, this.userName, this.password, this.email,this.gender, this.dob);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		MyXmlHandler myhandler=new MyXmlHandler();
		
		try {
			Xml.parse(response1, myhandler);
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
				
		
		ServerResponse obj_serRes=myhandler.obj_serverResp1;
		
		return obj_serRes.isRegistered+":"+obj_serRes.register_response;
		
				
	}
	
	/**
	 * For login a user
	 * @return xml string from the server
	 */
	public  String Login(){
		HttpPostRequest httpRe=new HttpPostRequest();
		
		String response1="";
		response1=httpRe.login(this.userName,this.password);
//		Log.e("gaga",response1);
		
		MyXmlHandler myhandler=new MyXmlHandler();
		
		try {
			Xml.parse(response1, myhandler);
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
		ServerResponse obj_serRes=myhandler.obj_serverResp1;
		if(obj_serRes.isLoggedin){
			this.sessionId=obj_serRes.session_id;
			return obj_serRes.session_id;
		}
		else{
			return obj_serRes.login_Error;
		}
		
	}
	
	/**
	 * Function if a user have forgot his password
	 * @return xml string from the server
	 */
	public String forgetPassword(){
		HttpPostRequest httpRe=new HttpPostRequest();
		
		String response1="";
		response1=httpRe.forgetPassword(this.userName);
		
		MyXmlHandler myhandler=new MyXmlHandler();
		
		try {
			Xml.parse(response1, myhandler);
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
		ServerResponse obj_serRes=myhandler.obj_serverResp1;
		return obj_serRes.ForgotPassNotification;
		
	}
	/**
	 * function for getting the profile of the user which will be saved in this class members variables
	 */
	
	public void getProfile(){
		HttpPostRequest httpRe=new HttpPostRequest();
		
		String response1="";
		response1=httpRe.getProfile(this.sessionId,this.userName);
		
		MyXmlHandler myhandler=new MyXmlHandler();
		
		try {
			Xml.parse(response1, myhandler);
		} catch (SAXException e) {
			e.printStackTrace();
		}
		this.firstName=myhandler.obj_serverResp1.userProfile.firstName;
		this.age=myhandler.obj_serverResp1.userProfile.age;
		this.averageRating=myhandler.obj_serverResp1.userProfile.averageRating;		
		this.gender=myhandler.obj_serverResp1.userProfile.gender;
		this.lastName=myhandler.obj_serverResp1.userProfile.lastName;
		this.numofPost=myhandler.obj_serverResp1.userProfile.numofPost;
		
		
	}
	
	
	
	

}
