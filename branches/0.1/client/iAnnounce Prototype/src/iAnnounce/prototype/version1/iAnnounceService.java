package iAnnounce.prototype.version1;

import java.util.Timer;
import java.util.TimerTask;

import org.xml.sax.SAXException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.util.Xml;


/**
 * A service for communicating asynchronously with the server. This consists of timer task which are after specified period of time fetches data from the server and returns it to the activity. This service runs in background for fetching data and it is multithreaded one listens for the commands from the activity and other thread do process request.
 * @author Awais
 *@version 1
 */

public class iAnnounceService extends Service{
	/**
	 * This is type of command which is used for getting an announcements. Get_announcements  is  is sync   
	 */
	static final int GET_ANNOUNCEMENTS = 1;
	/**
	 * This is int value for recieving the response of Get_announcement from service.
	 */
	static final int RECIEVE_ANNOUNCEMENTS = 2;

	/**
	 * This is int value for starting the timer task 
	 */
	static final int START_TIMERTASK=3;
	/**
	 * This is int value for stopping the already running timer task.
	 */
	static final int STOP_TIMERTASK=4;

	/**
	 * This is int value for recieving the response of the timer task from service to activity.
	 */

	static final int RECIEVE_TASK_RESPONSE=5;


	static final int RESPONSE_ERROR_FROM_SERVER=6;

	static final int RESPONSE_NETWORK_ERROR=7;

	static final int RESPONSE_ERROR_SESSION=8;


	/**
	 * Messenger type object of activity which sent message for starting the timertask 
	 */
	Messenger mClient;	
	/**
	 * Messenger type object of activity which sent message for getting the announcement
	 */
	Messenger mClient_getAnn;
	/**
	 * Messenger of this service
	 */
	final Messenger mMessenger=new Messenger(new IncomingHandler());
	/**
	 * Class which extends handler and is for the handling the messages from the activites, communication between activity and service is done with this.
	 * @author Awais
	 *
	 */
	class IncomingHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {			
			case GET_ANNOUNCEMENTS:
				mClient_getAnn=msg.replyTo;
				pagenum=(String)msg.obj;
				getAnnouncements();				
				break;
			case START_TIMERTASK:
				mClient=msg.replyTo;
				start_timerTask();
				break;
			case STOP_TIMERTASK:
				if(tim!=null)
				{	
					tim.cancel();
				}

				break;
			default:

			}
			super.handleMessage(msg);
		}
	}


	@Override
	public IBinder onBind(Intent intent) {
		return mMessenger.getBinder();
	}

	@Override
	public void onCreate() {

		super.onCreate();
	}

	private String pagenum="1";
	/**
	 * function for getting the annoucement in response to the Activity request of getannoucements
	 */
	void getAnnouncements(){

		Thread th=new Thread(new Runnable() {

			public void run() {
				try{
					HttpPostRequest ht=new HttpPostRequest();
					SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
					Message m;
					ht.getAnnoucnements(settings.getString("sessionId", "0"), settings.getString("Latitude", "0"), settings.getString("Longitude", "0"), pagenum);
					Log.e("XML=", ht.xmlStringResponse);

					if(ht.isError){
						m=Message.obtain(null,RESPONSE_NETWORK_ERROR,(Object)ht.xception);
						mClient_getAnn.send(m);
					}else{
						MyXmlHandler myhandler=new MyXmlHandler();
						Xml.parse(ht.xmlStringResponse, myhandler);
						if(myhandler.obj_serverResp1.responseCode.equalsIgnoreCase("0")){						
							m=Message.obtain(null,RECIEVE_ANNOUNCEMENTS,(Object)myhandler.obj_serverResp1);
							mClient_getAnn.send(m);
						}else{
							if(myhandler.obj_serverResp1.responseCode.equalsIgnoreCase("1")){
								m=Message.obtain(null,RESPONSE_ERROR_SESSION,(Object)myhandler.obj_serverResp1.responseMessage);
							}
							else{
								m=Message.obtain(null,RESPONSE_ERROR_FROM_SERVER,(Object)myhandler.obj_serverResp1.responseMessage);	
							}
							mClient_getAnn.send(m);
						}
					}
				}catch(RemoteException e){
					e.printStackTrace();
					Log.e("iAnnounceCommunicationException", e.getMessage());
				}
				catch (SAXException e) {				
					e.printStackTrace();				
				}
			}
		});

		th.start();
	}

	private TimerTask tTask;
	private Timer tim;

	/**
	 * Function for starting the timer task on user specified time and it keeps fetching the announcements after time delay.  
	 */
	void start_timerTask(){

		tTask= new TimerTask() {

			@Override
			public void run() {

				try{
					HttpPostRequest ht=new HttpPostRequest();
					SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
					ht.getAnnoucnements(settings.getString("sessionId", "0"), settings.getString("Latitude", "0"), settings.getString("Longitude", "0"), "1"); // fetching that page in background 
					Message m;
					if(ht.isError){
						m=Message.obtain(null,RESPONSE_NETWORK_ERROR,(Object)ht.xception);
						mClient.send(m);
					}else{
						MyXmlHandler myhandler=new MyXmlHandler();
						Xml.parse(ht.xmlStringResponse, myhandler);
						if(myhandler.obj_serverResp1.responseCode.equalsIgnoreCase("0")){						
							m=Message.obtain(null,RECIEVE_TASK_RESPONSE,(Object)myhandler.obj_serverResp1);
							mClient_getAnn.send(m);
						}else{
							if(myhandler.obj_serverResp1.responseCode.equalsIgnoreCase("1")){
								m=Message.obtain(null,RESPONSE_ERROR_SESSION,(Object)myhandler.obj_serverResp1.responseMessage);
							}
							else{
								m=Message.obtain(null,RESPONSE_ERROR_FROM_SERVER,(Object)myhandler.obj_serverResp1.responseMessage);	
							}
							mClient_getAnn.send(m);
						}
					}





					//				Message m=Message.obtain(null,RECIEVE_TASK_RESPONSE,(Object)resp);
					//				try {
					//					mClient.send(m);
					//				} catch (RemoteException e) {
					//					e.printStackTrace();
					//				}

				}catch(RemoteException e){
					e.printStackTrace();
					Log.e("iAnnounceCommunicationException", e.getMessage());
				}
				catch (SAXException e) {				
					e.printStackTrace();				
				}
			}
		};

		SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);

		String freq=settings.getString("timeInterval", "1");
		int ifreq= Integer.parseInt(freq);
		ifreq=ifreq*60*1000;
		if(tim!=null){
			tim.cancel();
		}
		
		tim= new Timer();
		tim.scheduleAtFixedRate(tTask,ifreq,ifreq);
		
		
	}

	@Override
	public void onDestroy() {
		if(tim!=null){
			tim.cancel();			
		}
		super.onDestroy();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
	}
	
	
	

	
	@Override
	public void onStart(Intent intent, int startId) {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		int icon = R.drawable.iann1;
		CharSequence tickerText = "IAnounnce Service,Click for Details";
		long when = System.currentTimeMillis();

		Notification notification = new Notification(icon, tickerText, when);
		Context context = getApplicationContext();
		CharSequence contentTitle = "iAnnounce App info";
		CharSequence contentText = "Running iAnnounce Service";
		Intent notificationIntent = new Intent(this, optionAct.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);		
		mNotificationManager.notify(123, notification);
		super.onStart(intent, startId);
	}


}
