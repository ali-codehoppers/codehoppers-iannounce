package iAnnounce.prototype.version1;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

/**
 * Default handler extended class used for parsing the xml
 * @author Awais
 *@version 1
 */

public class MyXmlHandler extends DefaultHandler{

	/**
	 * Structured parsed data is returned in this form
	 */

	ServerResponse obj_serverResp1;
	private Announcements a;
	private Classcomment com;



	/*private boolean fl_register;
	private boolean fl_registerCode;
	private boolean fl_registerText;

	private boolean fl_isLoggedin;
	private boolean fl_LoginDesc;

	private boolean fl_forgotPass;

	private boolean fl_postAnnouncement;

	private boolean fl_profile;
	private boolean fl_firstName;
	private boolean fl_lastName;
	private boolean fl_age;
	private boolean fl_gender;
	private boolean fl_avgRating;
	private boolean fl_numposts;

	private boolean fl_announcements;


	private boolean fl_getcomment;
	private boolean fl_com_packet;
	private boolean fl_com_time;
	private boolean fl_com_user;
	private boolean fl_com_txt;

	private boolean fl_post_comResponse;

	private boolean fl_logout_resp;

	private boolean fl_rate_resp;

	private boolean fl_delAcc_resp;

	private boolean fl_editProfile_resp;

	private boolean fl_myAnn;
	private boolean fl_myAnn_id;
	private boolean fl_myAnn_desc;
	private boolean fl_myAnn_longi;
	private boolean fl_myAnn_lati;
	private boolean fl_myAnn_timestamp;	
	private boolean fl_myAnn_avgRating;
	private boolean fl_myAnn_numOfComments;
	private boolean fl_myAnn_announcement;
	 */




	/*---modifications--*/

	private boolean fl_response;
	private boolean fl_responseCode;
	private boolean fl_responseMessage;

	private boolean fl_login;
	private boolean fl_sessionId;

	private boolean fl_getAnnouncements;
	private boolean fl_announcement;
	private boolean fl_announcement_id;
	private boolean fl_announcement_desc;
	private boolean fl_announcement_longi;
	private boolean fl_announcement_lati;
	private boolean fl_announcement_timestamp;
	private boolean fl_announcement_announcer;
	private boolean fl_announcement_avgRating;
	private boolean fl_announcement_currenUserRating;
	private boolean fl_announcement_numOfComments;


	private boolean fl_myAnn;
	private boolean fl_myAnn_id;
	private boolean fl_myAnn_desc;
	private boolean fl_myAnn_longi;
	private boolean fl_myAnn_lati;
	private boolean fl_myAnn_timestamp;	
	private boolean fl_myAnn_avgRating;
	private boolean fl_myAnn_numOfComments;
	private boolean fl_myAnn_announcement;

	private boolean fl_postAnnouncement;

	private boolean fl_getcomments;
	private boolean fl_com_comment;
	private boolean fl_com_time;
	private boolean fl_com_user;
	private boolean fl_com_txt;


	private boolean fl_comment_post;

	private boolean fl_deleteAccount;

	private boolean fl_profile;
	private boolean fl_firstName;
	private boolean fl_lastName;
	private boolean fl_age;
	private boolean fl_gender;
	private boolean fl_avgRating;
	private boolean fl_numposts;






	public MyXmlHandler() {
		super();

		obj_serverResp1=new ServerResponse();
		a=new Announcements();
		com=new Classcomment();

		fl_response=false;
		fl_responseCode=false;
		fl_responseMessage=false;

		fl_login=false;
		fl_sessionId=false;

		fl_getAnnouncements=false;
		fl_announcement=false;
		fl_announcement_id=false;
		fl_announcement_desc=false;
		fl_announcement_longi=false;
		fl_announcement_lati=false;
		fl_announcement_timestamp=false;
		fl_announcement_announcer=false;
		fl_announcement_avgRating=false;
		fl_announcement_currenUserRating=false;
		fl_announcement_numOfComments=false;

		fl_myAnn=false;
		fl_myAnn_announcement=false;
		fl_myAnn_id=false;
		fl_myAnn_desc=false;
		fl_myAnn_longi=false;
		fl_myAnn_lati=false;
		fl_myAnn_timestamp=false;	
		fl_myAnn_avgRating=false;
		fl_myAnn_numOfComments=false;



		fl_postAnnouncement=false;

		fl_getcomments=false;
		fl_com_comment=false;
		fl_com_time=false;
		fl_com_user=false;
		fl_com_txt=false;

		fl_comment_post=false;

		fl_deleteAccount=false;

		fl_profile=false;
		fl_firstName=false;
		fl_lastName=false;
		fl_gender=false;
		fl_age=false;
		fl_avgRating=false;
		fl_numposts=false;







		/* --old leftovers-- */





		//		fl_register = false;
		//		fl_registerCode = false;
		//		fl_registerText = false;
		fl_login=false;
		//		fl_isLoggedin=false;
		//		fl_LoginDesc=false;
		fl_sessionId=false;
		//		fl_forgotPass=false;
		//		fl_postAnnouncement=false;
		//
		//		fl_profile=false;
		//		fl_firstName=false;
		//		fl_lastName=false;
		//		fl_gender=false;
		//		fl_age=false;
		//		fl_avgRating=false;
		//		fl_numposts=false;



		//		
		//
		//		fl_post_comResponse=false;
		//
		//		fl_logout_resp=false;
		//
		//		fl_rate_resp=false;
		//
		//		fl_delAcc_resp=false;
		//
		//		fl_editProfile_resp=false;
		//






	}
	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		/*
		if(localName.equalsIgnoreCase("register")){
			fl_register=true;
		}
		else if(fl_register && localName.equalsIgnoreCase("isRegistered")){
			fl_registerCode=true;
		}
		else if(fl_register && localName.equalsIgnoreCase("Description")){
			fl_registerText=true;
		}

		if(localName.equalsIgnoreCase("login")){
			fl_login=true;
		}
		else if(fl_login && localName.equalsIgnoreCase("isLoggedin")){
			fl_isLoggedin=true;
		}
		else if (fl_login && localName.equalsIgnoreCase("description")) {
			fl_LoginDesc=true;
		}
		else if (fl_login && localName.equalsIgnoreCase("sessionId")) {
			fl_sessionId=true;
		}

		if(localName.equalsIgnoreCase("forgotPassword")){
			fl_forgotPass=true;
		}

		if(localName.equalsIgnoreCase("announce")){
			fl_postAnnouncement=true;
		}

		if(localName.equalsIgnoreCase("ForceLogin")){
			obj_serverResp1.forceLogin=true;
		}

		if(localName.equalsIgnoreCase("Profile")){
			fl_profile=true;
		}
		else if(fl_profile && localName.equalsIgnoreCase("firstName")){
			fl_firstName=true;
		}
		else if(fl_profile && localName.equalsIgnoreCase("lastName")){
			fl_lastName=true;
		}
		else if(fl_profile && localName.equalsIgnoreCase("gender")){
			fl_gender=true;
		}
		else if(fl_profile && localName.equalsIgnoreCase("age")){
			fl_age=true;
		}
		else if(fl_profile && localName.equalsIgnoreCase("rating")){
			fl_avgRating=true;
		}
		else if(fl_profile && localName.equalsIgnoreCase("numofPosts")){
			fl_numposts=true;
		}



		if(localName.equalsIgnoreCase("announcements")){
			fl_announcements=true;
		}
		else if(fl_announcements && localName.equalsIgnoreCase("announcement")){
			fl_announcement=true;
		}
		else if(fl_announcements && fl_announcement && localName.equalsIgnoreCase("id")){
			fl_announcement_id=true;
		}
		else if(fl_announcements && fl_announcement && localName.equalsIgnoreCase("description")){
			fl_announcement_desc=true;
		}
		else if(fl_announcements && fl_announcement && localName.equalsIgnoreCase("announcer")){
			fl_announcement_announcer=true;
		}
		else if(fl_announcements && fl_announcement && localName.equalsIgnoreCase("timestamp")){
			fl_announcement_timestamp=true;
		}
		else if(fl_announcements && fl_announcement && localName.equalsIgnoreCase("averageRating")){
			fl_announcement_avgRating=true;
		}
		else if(fl_announcements && fl_announcement && localName.equalsIgnoreCase("currentUserRating")){
			fl_announcement_currenUserRating=true;
		}
		else if(fl_announcements && fl_announcement && localName.equalsIgnoreCase("Longitude")){
			fl_announcement_longi=true;
		}
		else if(fl_announcements && fl_announcement && localName.equalsIgnoreCase("Latitude")){
			fl_announcement_lati=true;
		}
		else if(fl_announcements && fl_announcement && localName.equalsIgnoreCase("noOfComments")){
			fl_announcement_numOfComments=true;
		}

		if(localName.equalsIgnoreCase("GetComments")){			
			fl_getcomment=true;
		}
		else if(fl_getcomment && localName.equalsIgnoreCase("packet")){
			fl_com_packet=true;
		}
		else if(fl_getcomment && fl_com_packet && localName.equalsIgnoreCase("username")){
			fl_com_user=true;
		}
		else if(fl_getcomment && fl_com_packet && localName.equalsIgnoreCase("time")){
			fl_com_time=true;
		}
		else if(fl_getcomment && fl_com_packet && localName.equalsIgnoreCase("comment")){
			fl_com_txt=true;
		}

		if(localName.equalsIgnoreCase("rate")){
			fl_rate_resp=true;
		}
		if(localName.equalsIgnoreCase("postComment")){
			fl_post_comResponse=true;
		}

		if(localName.equalsIgnoreCase("logout")){
			fl_logout_resp=true;
		}
		if(localName.equalsIgnoreCase("deleteAccount")){
			fl_delAcc_resp=true;
		}
		if(localName.equalsIgnoreCase("editProfile")){
			fl_editProfile_resp=true;
		}

		if(localName.equalsIgnoreCase("myAnnouncements")){
			fl_myAnn=true;
		}
		else if(fl_myAnn && localName.equalsIgnoreCase("announcement")){
			fl_myAnn_announcement=true;
		}
		else if(fl_myAnn && fl_myAnn_announcement && localName.equalsIgnoreCase("id")){
			fl_myAnn_id=true;
		}
		else if(fl_myAnn && fl_myAnn_announcement && localName.equalsIgnoreCase("averageRating")){
			fl_myAnn_avgRating=true;
		}
		else if(fl_myAnn && fl_myAnn_announcement && localName.equalsIgnoreCase("description")){
			fl_myAnn_desc=true;
		}
		else if(fl_myAnn && fl_myAnn_announcement && localName.equalsIgnoreCase("latitude")){
			fl_myAnn_lati=true;
		}
		else if(fl_myAnn && fl_myAnn_announcement && localName.equalsIgnoreCase("longitude")){
			fl_myAnn_longi=true;
		}
		else if(fl_myAnn && fl_myAnn_announcement && localName.equalsIgnoreCase("noOfComments")){
			fl_myAnn_numOfComments=true;
		}
		else if(fl_myAnn && fl_myAnn_announcement && localName.equalsIgnoreCase("timestamp")){
			fl_myAnn_timestamp=true;
		}*/

		if(localName.equalsIgnoreCase("response")){
			fl_response=true;			
		}
		if(fl_response){
			if(localName.equalsIgnoreCase("responseCode")){
				fl_responseCode=true;
			}
			else if(localName.equalsIgnoreCase("responseMessage")){
				fl_responseMessage=true;
			}
			else if(localName.equalsIgnoreCase("login")){
				fl_login=true;
			}
			if(fl_login){
				if(localName.equalsIgnoreCase("sessionid")){
					fl_sessionId=true;
				}
			}
			if(localName.equalsIgnoreCase("getAnnouncements")){
				fl_getAnnouncements=true;
			}
			if(fl_getAnnouncements){
				if(localName.equalsIgnoreCase("announcement")){
					fl_announcement=true;
				}
				if(fl_announcement){
					if(localName.equalsIgnoreCase("id")){
						fl_announcement_id=true;
					}
					else if(localName.equalsIgnoreCase("description")){
						fl_announcement_desc=true;
					}
					else if(localName.equalsIgnoreCase("announcer")){
						fl_announcement_announcer=true;
					}
					else if(localName.equalsIgnoreCase("timestamp")){
						fl_announcement_timestamp=true;
					}
					else if(localName.equalsIgnoreCase("averageRating")){
						fl_announcement_avgRating=true;
					}
					else if(localName.equalsIgnoreCase("currentUserRating")){
						fl_announcement_currenUserRating=true;
					}
					else if(localName.equalsIgnoreCase("Longitude")){
						fl_announcement_longi=true;
					}
					else if(localName.equalsIgnoreCase("Latitude")){
						fl_announcement_lati=true;
					}
					else if(localName.equalsIgnoreCase("noOfComments")){
						fl_announcement_numOfComments=true;
					}
				}				
			}//get announcement

			if(localName.equalsIgnoreCase("myAnnouncements")){
				fl_myAnn=true;
			}
			if(fl_myAnn){
				if(localName.equalsIgnoreCase("announcement")){
					fl_myAnn_announcement=true;
				}
				if(fl_myAnn_announcement){
					if(localName.equalsIgnoreCase("id")){
						fl_myAnn_id=true;
					}
					else if(localName.equalsIgnoreCase("averageRating")){
						fl_myAnn_avgRating=true;
					}
					else if(localName.equalsIgnoreCase("description")){
						fl_myAnn_desc=true;
					}
					else if(localName.equalsIgnoreCase("latitude")){
						fl_myAnn_lati=true;
					}
					else if(localName.equalsIgnoreCase("longitude")){
						fl_myAnn_longi=true;
					}
					else if(localName.equalsIgnoreCase("noOfComments")){
						fl_myAnn_numOfComments=true;
					}
					else if(localName.equalsIgnoreCase("timestamp")){
						fl_myAnn_timestamp=true;
					}
				}

			} //myAnn


			if(localName.equalsIgnoreCase("postAnnouncement")){
				fl_postAnnouncement=true;				
			}//postAnnouncement

			if(localName.equalsIgnoreCase("getComments")){
				fl_getcomments=true;
			}
			if(fl_getcomments){
				if(localName.equalsIgnoreCase("comment")){
					fl_com_comment=true;
				}
				if(fl_com_comment){
					if(localName.equalsIgnoreCase("username")){
						fl_com_user=true;
					}
					else if(localName.equalsIgnoreCase("time")){
						fl_com_time=true;
					}
					else if(localName.equalsIgnoreCase("description")){
						fl_com_txt=true;
					}
				}				
			}//fl_getcomments

			if(localName.equals("postComment")){
				fl_comment_post=true;				
			}

			if(localName.equalsIgnoreCase("deleteAccount")){
				fl_deleteAccount=true;
			}
			
			if(localName.equalsIgnoreCase("getProfile")){
				fl_profile=true;
			}
			if(fl_profile){
				if(localName.equalsIgnoreCase("firstName")){
					fl_firstName=true;
				}
				else if(localName.equalsIgnoreCase("lastName")){
					fl_lastName=true;
				}
				else if(localName.equalsIgnoreCase("gender")){
					fl_gender=true;
				}
				else if(localName.equalsIgnoreCase("age")){
					fl_age=true;
				}
				else if(localName.equalsIgnoreCase("rating")){
					fl_avgRating=true;
				}
				else if(localName.equalsIgnoreCase("numofPosts")){
					fl_numposts=true;
				}
			}//fl_profile
			
			






		}//fl_response=true



	}
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
	throws SAXException {

		/*
		if(localName.equalsIgnoreCase("register")){
			fl_register=false;
		}
		else if(fl_register && localName.equalsIgnoreCase("isRegistered")){
			fl_registerCode=false;
		}
		else if(fl_register && localName.equalsIgnoreCase("Description")){
			fl_registerText=false;
		}

		if(localName.equalsIgnoreCase("login")){
			fl_login=false;
		}
		else if(fl_login && localName.equalsIgnoreCase("isLoggedin")){
			fl_isLoggedin=false;
		}
		else if (fl_login && localName.equalsIgnoreCase("description")) {
			fl_LoginDesc=false;
		}
		else if (fl_login && localName.equalsIgnoreCase("sessionId")) {
			fl_sessionId=false;
		}

		if(localName.equalsIgnoreCase("forgotPassword")){
			fl_forgotPass=false;
		}
		if(localName.equalsIgnoreCase("announce")){
			fl_postAnnouncement=false;
		}

		if(localName.equalsIgnoreCase("Profile")){
			fl_profile=false;
		}
		else if(fl_profile && localName.equalsIgnoreCase("firstName")){
			fl_firstName=false;
		}
		else if(fl_profile && localName.equalsIgnoreCase("lastName")){
			fl_lastName=false;
		}
		else if(fl_profile && localName.equalsIgnoreCase("gender")){
			fl_gender=false;
		}
		else if(fl_profile && localName.equalsIgnoreCase("age")){
			fl_age=false;
		}
		else if(fl_profile && localName.equalsIgnoreCase("rating")){
			fl_avgRating=false;
		}
		else if(fl_profile && localName.equalsIgnoreCase("numOfPosts")){
			fl_numposts=false;
		}//add username.

		if(localName.equalsIgnoreCase("announcements")){
			fl_announcements=false;
		}
		else if(fl_announcements && localName.equalsIgnoreCase("announcement")){
			fl_announcement=false;
			obj_serverResp1.feed.add(a);
			a=new Announcements();
		}
		else if(fl_announcements && fl_announcement && localName.equalsIgnoreCase("id")){
			fl_announcement_id=false;
		}
		else if(fl_announcements && fl_announcement && localName.equalsIgnoreCase("description")){
			fl_announcement_desc=false;
		}
		else if(fl_announcements && fl_announcement && localName.equalsIgnoreCase("announcer")){
			fl_announcement_announcer=false;
		}
		else if(fl_announcements && fl_announcement && localName.equalsIgnoreCase("timestamp")){
			fl_announcement_timestamp=false;
		}
		else if(fl_announcements && fl_announcement && localName.equalsIgnoreCase("averageRating")){
			fl_announcement_avgRating=false;
		}
		else if(fl_announcements && fl_announcement && localName.equalsIgnoreCase("currentUserRating")){
			fl_announcement_currenUserRating=false;
		}
		else if(fl_announcements && fl_announcement && localName.equalsIgnoreCase("Longitude")){
			fl_announcement_longi=false;
		}
		else if(fl_announcements && fl_announcement && localName.equalsIgnoreCase("Latitude")){
			fl_announcement_lati=false;
		}
		else if(fl_announcements && fl_announcement && localName.equalsIgnoreCase("noOfComments")){
			fl_announcement_numOfComments=false;
		}

		if(localName.equalsIgnoreCase("GetComments")){			
			fl_getcomment=false;
		}
		else if(fl_getcomment && localName.equalsIgnoreCase("packet")){
			fl_com_packet=false;
			obj_serverResp1.comments.add(com);
			com=new Classcomment();			
		}
		else if(fl_getcomment && fl_com_packet && localName.equalsIgnoreCase("username")){
			fl_com_user=false;
		}
		else if(fl_getcomment && fl_com_packet && localName.equalsIgnoreCase("time")){
			fl_com_time=false;
		}
		else if(fl_getcomment && fl_com_packet && localName.equalsIgnoreCase("comment")){
			fl_com_txt=false;
		}		
		if(localName.equalsIgnoreCase("rate")){
			fl_rate_resp=false;
		}
		if(localName.equalsIgnoreCase("postComment")){
			fl_post_comResponse=false;
		}
		if(localName.equalsIgnoreCase("logout")){
			fl_logout_resp=false;
		}
		if(localName.equalsIgnoreCase("deleteAccount")){
			fl_delAcc_resp=false;
		}
		if(localName.equalsIgnoreCase("editProfile")){
			fl_editProfile_resp=false;
		}

		if(localName.equalsIgnoreCase("myAnnouncements")){
			fl_myAnn=false;
		}
		else if(fl_myAnn && localName.equalsIgnoreCase("announcement")){
			fl_myAnn_announcement=false;
			obj_serverResp1.feed.add(a);
			a=new Announcements();
		}
		else if(fl_myAnn && fl_myAnn_announcement && localName.equalsIgnoreCase("id")){
			fl_myAnn_id=false;
		}
		else if(fl_myAnn && fl_myAnn_announcement && localName.equalsIgnoreCase("averageRating")){
			fl_myAnn_avgRating=false;
		}
		else if(fl_myAnn && fl_myAnn_announcement && localName.equalsIgnoreCase("description")){
			fl_myAnn_desc=false;
		}
		else if(fl_myAnn && fl_myAnn_announcement && localName.equalsIgnoreCase("latitude")){
			fl_myAnn_lati=false;
		}
		else if(fl_myAnn && fl_myAnn_announcement && localName.equalsIgnoreCase("longitude")){
			fl_myAnn_longi=false;
		}
		else if(fl_myAnn && fl_myAnn_announcement && localName.equalsIgnoreCase("noOfComments")){
			fl_myAnn_numOfComments=false;
		}
		else if(fl_myAnn && fl_myAnn_announcement && localName.equalsIgnoreCase("timestamp")){
			fl_myAnn_timestamp=false;
		}		
		 */


		if(localName.equalsIgnoreCase("response")){
			fl_response=false;			
		}
		if(fl_response){
			if(localName.equalsIgnoreCase("responseCode")){
				fl_responseCode=false;
			}
			else if(localName.equalsIgnoreCase("responseMessage")){
				fl_responseMessage=false;
			}

			if(fl_login){
				if(localName.equalsIgnoreCase("sessionId")){
					fl_sessionId=false;
				}
				else if(localName.equalsIgnoreCase("login")){
					fl_login=false;
				}
			}
			if(fl_getAnnouncements){
				if(localName.equalsIgnoreCase("getAnnouncements")){
					fl_getAnnouncements=false;
				}
				else if(fl_announcement){
					if(localName.equalsIgnoreCase("announcement")){
						fl_announcement=false;
						obj_serverResp1.feed.add(a);
						a=new Announcements();
					}
					else if(localName.equalsIgnoreCase("id")){
						fl_announcement_id=false;
					}
					else if(localName.equalsIgnoreCase("description")){
						fl_announcement_desc=false;
					}
					else if(localName.equalsIgnoreCase("announcer")){
						fl_announcement_announcer=false;
					}
					else if(localName.equalsIgnoreCase("timestamp")){
						fl_announcement_timestamp=false;
					}
					else if(localName.equalsIgnoreCase("averageRating")){
						fl_announcement_avgRating=false;
					}
					else if(localName.equalsIgnoreCase("currentUserRating")){
						fl_announcement_currenUserRating=false;
					}
					else if(localName.equalsIgnoreCase("Longitude")){
						fl_announcement_longi=false;
					}
					else if(localName.equalsIgnoreCase("Latitude")){
						fl_announcement_lati=false;
					}
					else if(localName.equalsIgnoreCase("noOfComments")){
						fl_announcement_numOfComments=false;
					}					
				}//fl_announcement

			}//fl_getAnnouncements

			if(fl_myAnn){
				if(localName.equalsIgnoreCase("myAnnouncements")){
					fl_myAnn=false;
				}
				else if(fl_myAnn_announcement){
					if(localName.equalsIgnoreCase("announcement")){
						fl_myAnn_announcement=false;
						obj_serverResp1.feed.add(a);
						a=new Announcements();
					}
					else if(localName.equalsIgnoreCase("id")){
						fl_myAnn_id=false;
					}
					else if(localName.equalsIgnoreCase("averageRating")){
						fl_myAnn_avgRating=false;
					}
					else if(localName.equalsIgnoreCase("description")){
						fl_myAnn_desc=false;
					}
					else if(localName.equalsIgnoreCase("latitude")){
						fl_myAnn_lati=false;
					}
					else if(localName.equalsIgnoreCase("longitude")){
						fl_myAnn_longi=false;
					}
					else if(localName.equalsIgnoreCase("noOfComments")){
						fl_myAnn_numOfComments=false;
					}
					else if(localName.equalsIgnoreCase("timestamp")){
						fl_myAnn_timestamp=false;
					}						
				}
			}//fl_myAnn

			if(localName.equalsIgnoreCase("postAnnouncement")){
				fl_postAnnouncement=false;
			}

			if(fl_getcomments){
				if(localName.equalsIgnoreCase("getComments")){
					fl_getcomments=false;
				}
				else if(fl_com_comment){
					if(localName.equalsIgnoreCase("comment")){
						fl_com_comment=false;

						obj_serverResp1.comments.add(com);
						com=new Classcomment();	
					}
					if(localName.equalsIgnoreCase("username")){
						fl_com_user=false;
					}
					else if(localName.equalsIgnoreCase("time")){
						fl_com_time=false;
					}
					else if(localName.equalsIgnoreCase("description")){
						fl_com_txt=false;
					}
				}
			}//getComments

			if(localName.equalsIgnoreCase("postComment")){
				fl_comment_post=false;
			}
			if(localName.equalsIgnoreCase("deleteAccount")){
				fl_deleteAccount=false;			
			}
			
			if(fl_profile){
				if(localName.equalsIgnoreCase("getProfile")){
					fl_profile=false;
				}
				else if(localName.equalsIgnoreCase("firstName")){
					fl_firstName=false;
				}
				else if(localName.equalsIgnoreCase("lastName")){
					fl_lastName=false;
				}
				else if(localName.equalsIgnoreCase("gender")){
					fl_gender=false;
				}
				else if(localName.equalsIgnoreCase("age")){
					fl_age=false;
				}
				else if(localName.equalsIgnoreCase("rating")){
					fl_avgRating=false;
				}
				else if(localName.equalsIgnoreCase("numofPosts")){
					fl_numposts=false;
				}
			}//fl_profile



		}//fl_response





	}

	@Override
	public void characters(char ch[], int start, int length) {
		String x= new String(ch,start,length);
		/*
	    	if(fl_register && fl_registerCode){
	    		if(x.equalsIgnoreCase("true") || x.equalsIgnoreCase("1")){
	    			obj_serverResp1.isRegistered=true;
	    		}
	    		else{
	    			obj_serverResp1.isRegistered=false;
	    		}	    		
	    	}
	    	else if(fl_register && fl_registerText){
	    		obj_serverResp1.register_response=x;
	    	}

	    	if(fl_login && fl_isLoggedin){
	    		if(x.equalsIgnoreCase("true") || x.equalsIgnoreCase("1")){
	    			obj_serverResp1.isLoggedin=true;
	    		}
	    		else{
	    			obj_serverResp1.isLoggedin=false;
	    		}   		
	    	}
	    	else if(fl_login && fl_LoginDesc){
	    		obj_serverResp1.login_Error=x;
	    	}
	    	else if(fl_login && fl_sessionId){
	    		obj_serverResp1.session_id=x;
	    	}

	    	if(fl_forgotPass){
	    		obj_serverResp1.ForgotPassNotification=x;
	    	}

	    	if(fl_postAnnouncement){
				obj_serverResp1.PostAnnouncementResponse=x;
			}

	    	if(fl_profile){
	    		if(fl_firstName){
	    			obj_serverResp1.userProfile.firstName=x;
	    		}
	    		else if(fl_lastName){
	    			obj_serverResp1.userProfile.lastName=x;
	    		}
	    		else if(fl_age){
	    			obj_serverResp1.userProfile.age=x;
	    		}
	    		else if(fl_avgRating){
	    			obj_serverResp1.userProfile.averageRating=x;
	    		}
	    		else if(fl_numposts){
	    			obj_serverResp1.userProfile.numofPost=x;
	    		}
	    		else if(fl_gender){
	    			obj_serverResp1.userProfile.gender=x;
	    		}	    		
	    	}

	    	if(fl_announcements){
	    		if(fl_announcement){
	    			if(fl_announcement_id){
	    				a.announcement_id=x;
	    			}
	    			else if(fl_announcement_announcer){
	    				a.announcer=x;
	    			}
	    			else if(fl_announcement_avgRating){
	    				a.averageRating=x;
	    			}
	    			else if(fl_announcement_currenUserRating){
	    				a.currentUserRating=x;
	    			}
	    			else if(fl_announcement_desc){
	    				a.description=x;
	    			}
	    			else if(fl_announcement_lati){
	    				a.latitude=x;
	    			}
	    			else if(fl_announcement_longi){
	    				a.longitude=x;
	    			}
	    			else if(fl_announcement_timestamp){
	    				a.timestamp=x;
	    			}
	    			else if(fl_announcement_numOfComments){
	    				a.noOfComments=x;
	    			}     			
	    		}	    		
	    	}//ann end


	    	if(fl_getcomment){
	    		if(fl_com_packet){
	    			if(fl_com_time){
	    				com.ctime=x;
	    			}
	    			else if(fl_com_txt){
	    				com.ctxt=x;
	    			}
	    			else if(fl_com_user){
	    				com.cuser=x;
	    			}
	    		}
	    	}//end comments

	    	if(fl_rate_resp){
	    		obj_serverResp1.rateResponse=x;	    		
	    	}
	    	if(fl_post_comResponse){
	    		obj_serverResp1.postComResponse=x;	    		
	    	}
	    	if(fl_logout_resp){
				obj_serverResp1.logoutResponse=x;
			}
	    	if(fl_delAcc_resp){
				obj_serverResp1.delAccResponse=x;
			}
	    	if(fl_editProfile_resp){
				obj_serverResp1.editProResponse=x;
			}

	    	if(fl_myAnn){
	    		if(fl_myAnn_announcement){
	    			if(fl_myAnn_id){
	    				a.announcement_id=x;
	    			}
	    			else if(fl_myAnn_avgRating){
	    				a.averageRating=x;
	    			}
	    			else if(fl_myAnn_desc){
	    				a.description=x;
	    			} 
	    			else if(fl_myAnn_lati){
	    				a.latitude=x;
	    			} 
	    			else if(fl_myAnn_longi){
	    				a.longitude=x;
	    			} 
	    			else if(fl_myAnn_numOfComments){
	    				a.noOfComments=x;
	    			} 
	    			else if(fl_myAnn_timestamp){
	    				a.timestamp=x;
	    			} 
	    		}
	    	}
		 */

		if(fl_response){
			if(fl_responseCode){				
				obj_serverResp1.responseCode=x;
			}
			else if(fl_responseMessage){
				obj_serverResp1.responseMessage=x;
			}
			if(fl_login){
				if(fl_sessionId){
					obj_serverResp1.session_id=x;	
				}				
			}
			else if(fl_getAnnouncements){
				if(fl_announcement){
					if(fl_announcement_id){
						a.announcement_id=x;
					}
					else if(fl_announcement_announcer){
						a.announcer=x;
					}
					else if(fl_announcement_avgRating){
						a.averageRating=x;
					}
					else if(fl_announcement_currenUserRating){
						a.currentUserRating=x;
					}
					else if(fl_announcement_desc){
						a.description=x;
					}
					else if(fl_announcement_lati){
						a.latitude=x;
					}
					else if(fl_announcement_longi){
						a.longitude=x;
					}
					else if(fl_announcement_timestamp){
						a.timestamp=x;
					}
					else if(fl_announcement_numOfComments){
						a.noOfComments=x;
					}     			
				}	    		
			}//ann end

			if(fl_myAnn){
				if(fl_myAnn_announcement){
					if(fl_myAnn_id){
						a.announcement_id=x;
					}
					else if(fl_myAnn_avgRating){
						a.averageRating=x;
					}
					else if(fl_myAnn_desc){
						a.description=x;
					} 
					else if(fl_myAnn_lati){
						a.latitude=x;
					} 
					else if(fl_myAnn_longi){
						a.longitude=x;
					} 
					else if(fl_myAnn_numOfComments){
						a.noOfComments=x;
					} 
					else if(fl_myAnn_timestamp){
						a.timestamp=x;
					} 
				}
			}//fl_myAnnouncements

			if(fl_postAnnouncement){
				obj_serverResp1.PostAnnouncementResponse=x;
			}//postAnnouncement


			if(fl_getcomments){
				if(fl_com_comment){
					if(fl_com_time){
						com.ctime=x;
					}
					else if(fl_com_txt){
						com.ctxt=x;
					}
					else if(fl_com_user){
						com.cuser=x;
					}
				}
			}//end comments


			if(fl_comment_post){
				obj_serverResp1.postComResponse=x;				
			}

			if(fl_deleteAccount){
				obj_serverResp1.delAccResponse=x;
			}
			
			if(fl_profile){
	    		if(fl_firstName){
	    			obj_serverResp1.userProfile.firstName=x;
	    		}
	    		else if(fl_lastName){
	    			obj_serverResp1.userProfile.lastName=x;
	    		}
	    		else if(fl_age){
	    			obj_serverResp1.userProfile.age=x;
	    		}
	    		else if(fl_avgRating){
	    			obj_serverResp1.userProfile.averageRating=x;
	    		}
	    		else if(fl_numposts){
	    			obj_serverResp1.userProfile.numofPost=x;
	    		}
	    		else if(fl_gender){
	    			obj_serverResp1.userProfile.gender=x;
	    		}	    		
	    	}//fl_profile



		}		

	}



}
