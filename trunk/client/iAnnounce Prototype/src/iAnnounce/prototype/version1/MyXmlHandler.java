package iAnnounce.prototype.version1;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

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
	private Neigbhours n;
	private Location l;







	/*---modified--*/

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
	private boolean fl_announcement_likes;
	private boolean fl_announcement_dislikes;
	private boolean fl_announcement_distance;


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
	private boolean fl_dob;

	private boolean fl_getNeighbourhood;
	private boolean fl_neighbour;
	private boolean fl_nb_id;
	private boolean fl_nb_title;
	private boolean fl_nb_description;
	private boolean fl_nb_private; 
	private boolean fl_nb_owner;
	private boolean fl_nb_member;
	private boolean fl_nb_membersNear;

	private boolean fl_locations;
	private boolean fl_location;
	private boolean fl_loc_id;
	private boolean fl_loc_name;
	private boolean fl_loc_description;
	private boolean fl_loc_lat;
	private boolean fl_loc_long;
	private boolean fl_loc_distance;



	private boolean fl_editProfile;

	private boolean fl_forgotPass;

	private boolean fl_logout;

	private boolean fl_ratePost;

	private boolean fl_register;




	public MyXmlHandler() {
		super();

		obj_serverResp1=new ServerResponse();
		a=new Announcements();
		n = new Neigbhours();
		com=new Classcomment();
		l=new Location();

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
		fl_announcement_likes=false;
		fl_announcement_dislikes=false;
		fl_announcement_distance=false;



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

		fl_getNeighbourhood=false;
		fl_neighbour = false;
		fl_nb_id=false;
		fl_nb_title=false;
		fl_nb_description=false ;
		fl_nb_private=false; 
		fl_nb_owner=false;
		fl_nb_member=false;
		fl_nb_membersNear=false;

		fl_locations=false;
		fl_location=false;
		fl_loc_id=false;
		fl_loc_name=false;
		fl_loc_description=false;
		fl_loc_lat=false;
		fl_loc_long=false;
		fl_loc_distance=false;

		fl_comment_post=false;

		fl_deleteAccount=false;

		fl_profile=false;
		fl_firstName=false;
		fl_lastName=false;
		fl_gender=false;
		fl_age=false;
		fl_avgRating=false;
		fl_numposts=false;

		fl_editProfile=false;

		fl_forgotPass=false;

		fl_logout=false;

		fl_ratePost=false;

		fl_register=false;

	}
	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {


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
					else if(localName.equalsIgnoreCase("likes")){
						fl_announcement_likes=true;
					}
					else if(localName.equalsIgnoreCase("dislikes")){
						fl_announcement_dislikes=true;
					}
					else if(localName.equalsIgnoreCase("distance")){
						fl_announcement_distance=true;
					}
				}				
			}//get announcement

			if(localName.equalsIgnoreCase("getNeighbourhood")){
				fl_getNeighbourhood=true;
			}
			if(fl_getNeighbourhood){
				if(localName.equalsIgnoreCase("neighbour")){
					fl_neighbour=true;
				}
				if(fl_neighbour){
					if(localName.equalsIgnoreCase("id")){
						fl_nb_id=true;
					}else if(localName.equalsIgnoreCase("title")){
						fl_nb_title=true;
					}else if(localName.equalsIgnoreCase("description")){
						fl_nb_description=true;
					}else if(localName.equalsIgnoreCase("isPrivate")){
						fl_nb_private=true;
					}else if(localName.equalsIgnoreCase("owner")){
						fl_nb_owner=true;
					}else if(localName.equalsIgnoreCase("isMember")){
						fl_nb_member=true;
					}else if(localName.equalsIgnoreCase("membersNear")){
						fl_nb_membersNear=true;
					}
				}
			} // get Neighbours

			if(localName.equalsIgnoreCase("locations")){
				fl_locations=true;
			}
			if(fl_locations){
				if(localName.equalsIgnoreCase("location")){
					fl_location=true;
				}
				if(fl_location){
					if(localName.equalsIgnoreCase("id")){
						fl_loc_id=true;
					}else if(localName.equalsIgnoreCase("name")){
						fl_loc_name=true;
					}else if(localName.equalsIgnoreCase("description")){
						fl_loc_description=true;
					}else if(localName.equalsIgnoreCase("latitude")){
						fl_loc_lat=true;
					}else if(localName.equalsIgnoreCase("longitude")){
						fl_loc_long=true;
					}else if(localName.equalsIgnoreCase("distance")){
						fl_loc_distance=true;
					}
				}
			} // get Location

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
				else if(localName.equalsIgnoreCase("dob")){
					fl_dob=true;
				}
			}//fl_profile

			if(localName.equalsIgnoreCase("editProfile")){
				fl_editProfile=true;
			}
			else if(localName.equalsIgnoreCase("forgetPassword")){
				fl_forgotPass=true;
			}
			else if(localName.equalsIgnoreCase("logOut")){
				fl_logout=true;
			}
			else if(localName.equalsIgnoreCase("ratingPost")){
				fl_ratePost=true;
			}
			else if(localName.equalsIgnoreCase("register")){
				fl_register=true;
			}



		}//fl_response=true



	}
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
	throws SAXException {



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
					else if(localName.equalsIgnoreCase("likes")){
						fl_announcement_likes=false;
					}
					else if(localName.equalsIgnoreCase("dislikes")){
						fl_announcement_dislikes=false;
					}
					else if(localName.equalsIgnoreCase("distance")){
						fl_announcement_distance=false;
					}

				}//fl_announcement

			}//fl_getAnnouncements

			if(localName.equalsIgnoreCase("getNeighbourhood")){
				fl_getNeighbourhood=false;
			}
			if(fl_getNeighbourhood){
				if(localName.equalsIgnoreCase("neighbour")){
					fl_neighbour=false;
					obj_serverResp1.neigbhours.add(n);
					n=new Neigbhours();
				}
				if(fl_neighbour){
					if(localName.equalsIgnoreCase("id")){
						fl_nb_id=false;
					}else if(localName.equalsIgnoreCase("title")){
						fl_nb_title=false;
					}else if(localName.equalsIgnoreCase("description")){
						fl_nb_description=false;
					}else if(localName.equalsIgnoreCase("isPrivate")){
						fl_nb_private=false;
					}else if(localName.equalsIgnoreCase("owner")){
						fl_nb_owner=false;
					}else if(localName.equalsIgnoreCase("isMember")){
						fl_nb_member=false;
					}else if(localName.equalsIgnoreCase("membersNear")){
						fl_nb_membersNear=false;
					}		
				}
			} // fl_get_Neighbours

			if(localName.equalsIgnoreCase("locations")){
				fl_locations=false;
			}
			if(fl_locations){
				if(localName.equalsIgnoreCase("location")){
					fl_location=false;
					obj_serverResp1.locations.add(l);
					l=new Location();
				}
				if(fl_location){
					if(localName.equalsIgnoreCase("id")){
						fl_loc_id=false;
					}else if(localName.equalsIgnoreCase("name")){
						fl_loc_name=false;
					}else if(localName.equalsIgnoreCase("description")){
						fl_loc_description=false;
					}else if(localName.equalsIgnoreCase("latitude")){
						fl_loc_lat=false;
					}else if(localName.equalsIgnoreCase("longitude")){
						fl_loc_long=false;
					}else if(localName.equalsIgnoreCase("distance")){
						fl_loc_distance=false;
					}
				}
			} // get fl_Location

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
				else if(localName.equalsIgnoreCase("dob")){
					fl_dob=false;
				}
			}//fl_profile

			if(localName.equalsIgnoreCase("editProfile")){
				fl_editProfile=false;
			}
			else if(localName.equalsIgnoreCase("forgetPassword")){
				fl_forgotPass=false;
			}
			else if(localName.equalsIgnoreCase("logOut")){
				fl_logout=false;
			}
			else if(localName.equalsIgnoreCase("ratingPost")){
				fl_ratePost=false;
			}
			else if(localName.equalsIgnoreCase("register")){
				fl_register=false;
			}
		}//fl_response





	}

	@Override
	public void characters(char ch[], int start, int length) {
		String x= new String(ch,start,length);


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
					else if(fl_announcement_likes){
						a.likes=x;
					}
					else if(fl_announcement_dislikes){
						a.dislikes=x;
					}
					else if(fl_announcement_distance){
						a.distance=x;
					}
				}	    		
			}//ann end
			if((fl_getNeighbourhood)){
				if(fl_neighbour){
					if(fl_nb_id){
						n.id=x;
					}else if(fl_nb_title){
						n.title=x;
					}else if(fl_nb_description){
						n.description=x;
					}else if(fl_nb_private){
						n.isPrivate=x;
					}else if(fl_nb_owner){
						n.owner=x;
					}else if(fl_nb_member){
						n.isMember=x;	
					}else if(fl_nb_membersNear){
						n.membersNear=x;
					}
				}
			} // fl_get_Neighbours

			if(fl_locations){
				if(fl_location){
					if(	fl_loc_id){
						l.id=x;						
					}else if(fl_loc_name){
						l.name=x;
					}else if(fl_loc_description){
						l.description=x;
					}else if(fl_loc_lat){
						l.latitude=x;
					}else if(fl_loc_long){
						l.longitude=x;
					}else if(fl_loc_distance){
						l.distance=x;
					}
				}
			} // get fl_Location
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
				else if(fl_dob){
					obj_serverResp1.userProfile.dob=x;
				}
			}//fl_profile

			if(fl_editProfile){
				obj_serverResp1.editProResponse=x;
			}
			else if(fl_forgotPass){
				obj_serverResp1.forgotPassResponse=x;
			}
			else if(fl_logout){
				obj_serverResp1.logoutResponse=x;
			}
			else if(fl_ratePost){
				obj_serverResp1.rateResponse=x;
			}
			else if(fl_register){
				obj_serverResp1.register_response=x;
			}


		}	//fl_response	

	}



}
