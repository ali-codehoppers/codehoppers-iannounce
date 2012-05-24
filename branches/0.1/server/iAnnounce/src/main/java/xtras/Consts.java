/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package xtras;

/**
 *
 * @author CodeHopper
 */
public class Consts {

    public static String responseCodes[]={

      /*0*/  "", // 0 is for success;
      /*1*/  "Your sessions is expired. Please login again to continue.",  //  error 1 if session is expired. have to change manually on /results/invalid_session.jsp
      /*2*/  "Invalid username or password.",                              // if logins fails
      /*3*/  "Username already exists. Please try another one.",           //while registration if username already exists.
      /*4*/  "Your account is disabled, contact support.",                  
      /*5*/  "Your account is removed, you can't login.",                  //if account with that username has been removed.
      /*6*/  "No such user exists.", //in forgetpassword if username is not found
      /*7*/  "Premium accounts are not accessible from device application", //while login
      /*8*/  "This account has been deactivated.",                          //while login
      /*9*/  "Please verify account before logging in.",    //while login
     /*10*/  "Unable to generate session",  //while login
     /*11*/  "Error posting your announcement. Please try again.", //while posting announcement
     /*12*/  "Error posting you comment, Please try again", //while posting comment
     /*13*/  "Invalid password. Unable to complete the request",    //in edit  profile :D
     /*14*/  "Invalid old password. Unable to comeplete the request",
     /*15*/  "Error occured while rating. Please try again.",
     /*16*/  "Some Error occured while registering please try again", //while registeration
     /*17*/  "No announcements to display", //while getting the announcements :D xD ;)
     /*18*/  "No comment yet on this announcement be the first to comment", //while getting comments, if there are no comments
     /*19*/  "Unable to find the user", // in getProfile
     /*20*/  

              
              
    };

    public static String REGISTERATION_SUCCESS="You have been successfully registered. An email has been sent on your ID for verification. Please verify before login";
    public static String RATING_SUCCESS="Successfully rated";
    public static String LOGOUT_SUCCESS="Successfully logged out.";
    public static String FORGETPASS_SUCCESS="An email with your password has been sent to you email address "; //dont disclose email id here.
    public static String EDITPROFILE_SUCCESS="Information updated. The updated information has been sent on your email address";
    public static String DELETEACCOUNT_SUCCESS="Your account is disabled. Your posts and username will still appear in service";
    public static String COMMENTPOST_SUCCESS="Comment successfully posted";
    public static String ANNOUNCEMENTPOST_SUCCESS="Announcement successfully posted. Will be shortly available";



}
