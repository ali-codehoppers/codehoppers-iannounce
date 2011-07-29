package iAnnounce.prototype.version1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

/**
 * Class to generate the postrequests to server for communication. Data is returned from methods as string.
 * @author Awais Akhtar
 * @version 1
 */	

public class HttpPostRequest {

	private BufferedReader in = null;
	private HttpClient client = new DefaultHttpClient();
	private HttpPost request = new HttpPost();
	public boolean isError;
	public String xception;
	public String xmlStringResponse;


	public HttpPostRequest() {		 		 
		super();
		isError=false;
	}
	private String base_url="http://192.168.0.100:8080/iAnnounce";  
	//	 private String base_url="http://192.168.1.2:8080/do";
	private String URL_register=base_url+"/register";
	private String URL_forgotPassword=base_url+"/forgetpassword";
	private String URL_login=base_url+"/login";
	private String URL_PostAnnouncement=base_url+"/postannouncement";
	private String URL_getAnnouncements=base_url+"/getannouncements";
	public String URL_getProfile=base_url+"/getprofile";
	public String URL_postComment=base_url+"/postcomment";
	public String URL_getComments=base_url+"/getcomments";
	public String URL_rateAnnouncement=base_url+"/rate";
	public String URL_Logout=base_url+"/logout";
	public String uRL_deleteProfile=base_url+"/deleteaccount";
	public String uRL_editProfile=base_url+"/editprofile";
	public String uRL_myannouncements=base_url+"/getmyannouncements";



	/**
	 * To send the httppost request to server on defined url for registering a user
	 * @return String consists of server response (xml)
	 * @param firstName String consists of firstname of user
	 * @param lastName String consists of lastname of user
	 * @param username String
	 * @param password String
	 * @param email String
	 * @param gender String consists of 0 for female 1 for male
	 * @param dob date of birth in DD/MM/YYYY format 
	 */
	public void register(String firstName, String lastName,String username, String password,String email,String gender, String DOB) throws ClientProtocolException, IOException {
		try {
			request.setURI(new URI(URL_register));
			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("firstName", firstName));
			postParameters.add(new BasicNameValuePair("lastName", lastName));
			postParameters.add(new BasicNameValuePair("email", email));
			postParameters.add(new BasicNameValuePair("username", username));
			postParameters.add(new BasicNameValuePair("password", AeSimpleMD5.MD5(password)));
			postParameters.add(new BasicNameValuePair("gender", gender));
			postParameters.add(new BasicNameValuePair("dob", DOB));
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters);
			request.setEntity(formEntity);
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			xmlStringResponse = sb.toString();
		} catch (Exception e) {
			isError=true;
			xception=e.getMessage();
		}	
		

	}

	/**
	 * Method for getting the announcements on the base of  
	 * @param sessionID sessionid of logged in user.
	 * @param latitude	current latitude of user
	 * @param longitude	current longitude of user
	 * @param pagenum	each page shows 10 announcements, all announcements divided into pages, Pages starts from 1
	 * @return response from server as string (xml) 
	 */
	public void getAnnoucnements(String sessionID,String latitude,String longitude,String pagenum){
		try{
			request.setURI(new URI(URL_getAnnouncements));

			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("sessionId", sessionID));
			postParameters.add(new BasicNameValuePair("latitude", latitude));
			postParameters.add(new BasicNameValuePair("longitude", longitude));
			postParameters.add(new BasicNameValuePair("pageNum", pagenum));


			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters);
			request.setEntity(formEntity);
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			xmlStringResponse = sb.toString();
			


		} catch (Exception e) {
			isError=true;
			xception=e.getMessage();
		}	



	}

	/**
	 * Method for login, session will be generated
	 * @param username
	 * @param Password
	 * @return  xml string returned by server
	 */
	public void login(String username,String Password){
		try{
			request.setURI(new URI(URL_login));

			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("username", username));
			postParameters.add(new BasicNameValuePair("password", AeSimpleMD5.MD5(Password)));

			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters);
			request.setEntity(formEntity);
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			xmlStringResponse = sb.toString();



		} catch (Exception e) {
			isError=true;
			xception=e.getMessage();
		}	

	}

	/**
	 * Method if user forgets his password.
	 * @param username
	 * @return  server response as xml string 
	 */
	public void forgetPassword(String username){
		try{
			request.setURI(new URI(URL_forgotPassword));

			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("username", username));

			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters);
			request.setEntity(formEntity);
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			xmlStringResponse = sb.toString();	
		} catch (Exception e) {
			isError=true;
			xception = e.getMessage();
		}	
		

	}

	/**
	 *  Method for posting and announcement based on users current location with a specified range
	 * @param sessionId
	 * @param range
	 * @param Announcement
	 * @param Longitude
	 * @param Latitude
	 * @return server response as xml string
	 */
	public void PostAnnouncement(String sessionId,String range,String Announcement,String Longitude, String Latitude){
		try{
			request.setURI(new URI(URL_PostAnnouncement));

			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("sessionId", sessionId));
			postParameters.add(new BasicNameValuePair("range", range));
			postParameters.add(new BasicNameValuePair("announce", Announcement));
			postParameters.add(new BasicNameValuePair("latitude", Latitude));
			postParameters.add(new BasicNameValuePair("longitude", Longitude));

			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters);
			request.setEntity(formEntity);
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			xmlStringResponse = sb.toString();	
		} catch (Exception e) {
			isError=true;
			xception=e.getMessage();
		}	



	}

	/**
	 * Method for getting the Profile of user. User must have a session to get hit profile/someone else's profile
	 * @param sessionId
	 * @param userName
	 * @return xml string
	 */
	public void getProfile(String sessionId,String userName){
		try{
			request.setURI(new URI(URL_getProfile));
			
			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("sessionId", sessionId));
			postParameters.add(new BasicNameValuePair("username", userName));

			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters);
			request.setEntity(formEntity);
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			xmlStringResponse = sb.toString();			
		} catch (Exception e) {
			isError=true;
			xception=e.getMessage();
		}

	}

	/**
	 * String to post the comment on some announcement. 
	 * @param sessionId
	 * @param comment
	 * @param annoucementId
	 * @return server response as xml string
	 */
	public void postComment(String sessionId,String comment, String annoucementId){
		try{
			request.setURI(new URI(URL_postComment));

			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("sessionId", sessionId));
			postParameters.add(new BasicNameValuePair("commentToPost", comment));
			postParameters.add(new BasicNameValuePair("announcementId", annoucementId));

			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters);
			request.setEntity(formEntity);
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			xmlStringResponse = sb.toString();	
			
		} catch (Exception e) {
			isError=true;
			xception=e.getMessage();
		}	

	}
	/**
	 * Method to get the comments of a specified announcements
	 * @param sessionId
	 * @param annoucementId
	 * @return server response as xml string
	 */

	public void getComments(String sessionId,String annoucementId){
		try{
			request.setURI(new URI(URL_getComments));

			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("sessionId", sessionId));
			postParameters.add(new BasicNameValuePair("announcementId", annoucementId));

			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters);
			request.setEntity(formEntity);
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			xmlStringResponse = sb.toString();

		} catch (Exception e) {
			isError=true;
			xception=e.getMessage();
		}	

	}

	/**
	 * Method for rating an announcement
	 * @param sessionId session id of user logged in
	 * @param annoucementId
	 * @param status 0 for rating up and 1 for rating down
	 * @return server response as xml string
	 */
	public void rateAnnouncement(String sessionId,String annoucementId, String status){
		try{
			request.setURI(new URI(URL_rateAnnouncement));

			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("sessionId", sessionId));
			postParameters.add(new BasicNameValuePair("announcementId", annoucementId));
			postParameters.add(new BasicNameValuePair("status", status));

			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters);
			request.setEntity(formEntity);
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			xmlStringResponse = sb.toString();	
		} catch (Exception e) {
			isError=true;
			xception=e.getMessage();
		}	
		
	}

	/**
	 * Method for user to logout. Session of user will be destroyed at server side
	 * @param sessionId
	 * @return server response as xml string
	 */
	public void logout(String sessionId){
		try{
			request.setURI(new URI(URL_Logout));

			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("sessionId", sessionId));			

			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters);
			request.setEntity(formEntity);
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			xmlStringResponse = sb.toString();	
		} catch (Exception e) {
			isError=true;
			xception=e.getMessage();
		}	
		
	}

	/**
	 * Method for deleting a profile.
	 * @param sessionId
	 * @param Password
	 * @return parsable xml string from server
	 */

	public void deleteProfile(String sessionId, String Password){
		try{
			request.setURI(new URI(uRL_deleteProfile));

			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("sessionId", sessionId));

			postParameters.add(new BasicNameValuePair("password", AeSimpleMD5.MD5(Password)));

			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters);
			request.setEntity(formEntity);
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			xmlStringResponse = sb.toString();	
		} catch (Exception e) {
			isError=true;
			xception=e.getMessage();
		}	


	}
	/**
	 * Method for updating the profile of user.
	 * @param sessionId
	 * @param oldPassword
	 * @param newPassword
	 * @param gender
	 * @param fname
	 * @param lname
	 * @param dob
	 * @return parsable xml string from server
	 */

	public void editProfile(String sessionId, String oldPassword, String newPassword, String gender,String fname,String lname, String dob){
		try{
			request.setURI(new URI(uRL_editProfile));
			if(oldPassword.length()!=0){
				oldPassword=AeSimpleMD5.MD5(oldPassword);
			}
			if(newPassword.length()!=0){
				newPassword=AeSimpleMD5.MD5(newPassword);
			}

			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("sessionId", sessionId));
			postParameters.add(new BasicNameValuePair("newPassword", newPassword));
			postParameters.add(new BasicNameValuePair("oldPassword", oldPassword));
			postParameters.add(new BasicNameValuePair("gender", gender));
			postParameters.add(new BasicNameValuePair("firstName", fname));
			postParameters.add(new BasicNameValuePair("lastName", lname));
			postParameters.add(new BasicNameValuePair("dateOfBirth", dob));


			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters);
			request.setEntity(formEntity);
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			xmlStringResponse = sb.toString();	
		} catch (Exception e) {
			isError=true;
			xception=e.getMessage();
		}	

	}
	/**
	 * Method for the getting user's announcements only
	 * @param sessionId
	 * @param pagenum
	 * @return server response xml string
	 */

	public void getMyAnnouncements(String sessionId,String pagenum){
		try{
			request.setURI(new URI(uRL_myannouncements));

			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("sessionId", sessionId));
			postParameters.add(new BasicNameValuePair("pageNum", pagenum));

			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters);
			request.setEntity(formEntity);
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			xmlStringResponse = sb.toString();	
		} catch (Exception e) {
			isError=true;
			xception=e.getMessage();
		}	

	}
}


