package iAnnounce.prototype.version1;


import java.util.zip.Inflater;

import org.xml.sax.SAXException;

import android.R.color;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.Html;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity for displaying the announcements which are done near him
 * @author Awais
 *@version 1
 */
public class NewsFeed extends Activity {
	
	private ProgressDialog pdialog1;
	
	public Messenger mService;
	private int pageNum_int=1;
	
	Messenger mMessenger;
	public static Messenger myMess;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
				
		getApplicationContext().bindService(new Intent(NewsFeed.this, iAnnounceService.class), mConnection, Context.BIND_AUTO_CREATE);

		pdialog1 = ProgressDialog.show(NewsFeed.this,"", 
				"Loading. Please wait...", true);
		
		mMessenger = new Messenger(new IncomingHandler());
		myMess=mMessenger;
		
		l1=new LinearLayout(getBaseContext());
		l1.setOrientation(LinearLayout.VERTICAL);
		
		LayoutParams lp1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		
		l1.setLayoutParams(lp1);
		
//		l1.setBackgroundColor(getResources().getColor(R.color.announcemts_bg));
		
		
//		mainLayout=new LinearLayout(getBaseContext());
		
//		mainLayout.setOrientation(LinearLayout.VERTICAL);
//		setContentView(mainLayout);
		
		v=new ScrollView(NewsFeed.this){

			@Override
			protected void onScrollChanged(int l, int t, int oldl, int oldt) {
				View view = (View) getChildAt(getChildCount()-1);
		        int diff = (view.getBottom()-(getHeight()+getScrollY()));// Calculate the scrolldiff
		        if( diff == 0 ){  
		        	
		        	if(fl_gotPage){
		        		pdialog1.show();
		        		fl_gotPage=false;
		            pageNum_int++;
		            
		            
					getAnnouncementText(Integer.toString(pageNum_int));


					Messenger HomePage_messenger=HomePage.myMess;
					//Messenger toSer_Mess=HomePage.Static_mServer;

					Message m=Message.obtain(null,iAnnounceService.STOP_TIMERTASK);
					m.replyTo = mMessenger;


					try {
//						toSer_Mess.send(m);
						mService.send(m);
					} catch (RemoteException e) {			
						e.printStackTrace();
					}		


					m=Message.obtain(null,iAnnounceService.START_TIMERTASK);
					m.replyTo = mMessenger;
					m.obj=(String)Integer.toString(pageNum_int);

					try {
//						toSer_Mess.send(m);
						mService.send(m);
					} catch (RemoteException e) {			
						e.printStackTrace();
					}
					
		        	}
		            
		            
		        }	
				super.onScrollChanged(l, t, oldl, oldt);
			}
			
		};
		
//		v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		v.setBackgroundColor(getResources().getColor(R.color.announcemts_bg));
		v.addView(l1);
		
//		mainLayout.addView(v);
		
		setContentView(v);
		
		
		/*location update thing goes here now*/
		
		
		String context = Context.LOCATION_SERVICE;
		locationManager = (LocationManager)getSystemService(context);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);


		String provider = locationManager.getBestProvider(criteria, true);
		
		if(provider==null){
			Toast.makeText(getApplicationContext(), "Please Enable your GPS", Toast.LENGTH_LONG).show();
			finish();

		}
		else{
			
			SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
			int distance=Integer.parseInt(settings.getString("distanceMeter","500"));
			int distUpdateTime=60*1000*(Integer.parseInt(settings.getString("distanceTime","1")));
			
			locationManager.requestLocationUpdates(provider, distUpdateTime, distance,
					locationListener);

			/* ifcrash*/

			Location location =
				locationManager.getLastKnownLocation(provider);

			if(location!=null){
				updateLocation(location);
			}
		}
		
		
		
		
		
		
		/*----------------------------------------------*/
		
			
	}
	
//	LinearLayout mainLayout;
	
	LocationManager locationManager;
	
	public void updateLocation(Location Loc){
		SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
		SharedPreferences.Editor editor = settings.edit();

		editor.putString("Longitude", Double.toString(Loc.getLongitude()));
		editor.putString("Latitude", Double.toString(Loc.getLatitude()));
		editor.commit();


	}
	private LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			updateLocation(location);
		}
		public void onProviderDisabled(String provider){
			showDialog(2);
		}
		public void onProviderEnabled(String provider){ }
		public void onStatusChanged(String provider, int status,
				Bundle extras){ }
	};
	



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.appmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_Profile:
			Intent in= new Intent(this, MyProfile.class);
						
			Bundle b=new Bundle();
			b.putString("username", settings.getString("userName", "0"));
			in.putExtras(b);
			startActivityForResult(in,3);
			return true;
			
		case R.id.menu_options:
			Intent intent2= new Intent(this, optionAct.class);
			startActivity(intent2);
			return true;
		case R.id.menu_Logout:
			HttpPostRequest ht=new HttpPostRequest();
			ht.logout(settings.getString("sessionId", "0"));
			
			if(ht.isError){
				Toast.makeText(getApplicationContext(), ht.xception, Toast.LENGTH_LONG).show();			
			}
			else{
				MyXmlHandler myhand=new MyXmlHandler();
				try {
					Xml.parse(ht.xmlStringResponse, myhand);
				} catch (SAXException e) {
					e.printStackTrace();
				}
				
				if(!myhand.obj_serverResp1.responseCode.equalsIgnoreCase("0")){
					Toast.makeText(getApplicationContext(), ht.xception, Toast.LENGTH_LONG).show();					
				}
				else{
					SharedPreferences.Editor editor = settings.edit();
					editor.putString("sessionId", "0");
					editor.commit();
					Toast.makeText(getApplicationContext(),myhand.obj_serverResp1.logoutResponse, Toast.LENGTH_LONG).show();				
										
				}
				finish();				
			}
					
			return true;
			
		case R.id.menu_AbtUs:
			Intent intent3= new Intent(this, Act_abt.class);
			startActivity(intent3);
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	
	
	/**
	 * Message handler 
	 * @author Awais
	 *@version 1
	 */

	class IncomingHandler extends Handler {
		
		@Override
		public void handleMessage(Message msg) {
			pdialog1.cancel();
			switch (msg.what) {
			case iAnnounceService.RECIEVE_ANNOUNCEMENTS:
				fl_gotPage=true;				
				generateGUI((ServerResponse)msg.obj,Integer.toString(pageNum_int));				
				break;
			case iAnnounceService.RESPONSE_NETWORK_ERROR:				
				Toast.makeText(getApplicationContext(), (String)msg.obj, Toast.LENGTH_LONG).show();
				break;
			case iAnnounceService.RESPONSE_ERROR_FROM_SERVER:
								
				Toast.makeText(getApplicationContext(), (String)msg.obj, Toast.LENGTH_LONG).show();
				break;
			case iAnnounceService.RESPONSE_ERROR_SESSION:
				Toast.makeText(getApplicationContext(), (String)msg.obj, Toast.LENGTH_LONG).show();
				finish();
				break;
			case iAnnounceService.RECIEVE_TASK_RESPONSE:				
				v.removeView(l1);
				l1=new LinearLayout(NewsFeed.this);
				l1.setOrientation(LinearLayout.VERTICAL);
				v.addView(l1);
				generateGUI((ServerResponse)msg.obj,Integer.toString(pageNum_int));				
				
				Log.e("task respnse", "Got taskkkkkkkkkkkkk responseeeeeeeeeeeeeeee");
				break;
				
			
			default:

			}
			super.handleMessage(msg);
		}
	}
	
	Button bt_Next;
	Button bt_Prev;
	
	boolean fl_gotPage=false;
	
	ScrollView v;
	LinearLayout l1;
	
	boolean fl_refreshGui=false;

/**
 * Function for populating the screen
 * @param s1 parseable response from the serer
 * @param pagenum Page number to tell which page's announcements will be populated
 */

	void generateGUI(ServerResponse sr01,String pagenum){
		/*LayoutParams lp1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT); 
		l1.setLayoutParams(lp1);
		l1.setBackgroundColor(Color.BLACK);	*/	
		
		if(fl_refreshGui){
			l1=new LinearLayout(NewsFeed.this);			
			fl_refreshGui=false;
		}
		
		
//		LinearLayout lbar=new LinearLayout(getBaseContext());
//		lbar.setGravity(Gravity.CENTER_HORIZONTAL);
//
//		lbar.setBackgroundColor(Color.rgb(189, 189, 189));
//		lbar.setPadding(0, 5, 0, 0);
//		final TextView tv_pgnum= new TextView(getBaseContext());
//		tv_pgnum.setText("Page : "+pagenum);
//		tv_pgnum.setTextColor(Color.BLACK);
//		bt_Next=new Button(getBaseContext());
//		bt_Next.setText("Next Page");
//		bt_Next.setOnClickListener(new OnClickListener() {
//
//			public void onClick(View arg0) {
//
//				pageNum_int++;
//				
//				if(pageNum_int<=1){
//					bt_Prev.setEnabled(false);
//				}
//				else{
//					bt_Prev.setEnabled(true);
//				}
//				tv_pgnum.setText("Page : "+Integer.toString(pageNum_int));
//				getAnnouncementText(Integer.toString(pageNum_int));
//
//
//				Messenger HomePage_messenger=HomePage.myMess;
//				Messenger toSer_Mess=HomePage.Static_mServer;
//
//				Message m=Message.obtain(null,iAnnounceService.STOP_TIMERTASK);
//				m.replyTo = HomePage_messenger;
//
//
//				try {
//					toSer_Mess.send(m);
//				} catch (RemoteException e) {			
//					e.printStackTrace();
//				}		
//
//
//				m=Message.obtain(null,iAnnounceService.START_TIMERTASK);
//				m.replyTo = HomePage_messenger;
//				m.obj=(String)Integer.toString(pageNum_int);
//
//				try {
//					toSer_Mess.send(m);
//				} catch (RemoteException e) {			
//					e.printStackTrace();
//				}	
//
//
//			}
//		});
//
//		bt_Prev=new Button(getBaseContext());
//		bt_Prev.setText("Prev Page");
//		
//		bt_Prev.setOnClickListener(new OnClickListener() {
//
//			public void onClick(View arg0) {
//				pageNum_int--;
//				tv_pgnum.setText("Page : "+Integer.toString(pageNum_int));
//				getAnnouncementText(Integer.toString(pageNum_int));					
//
//				Messenger HomePage_messenger=HomePage.myMess;
//				Messenger toSer_Mess=HomePage.Static_mServer;
//
//				Message m=Message.obtain(null,iAnnounceService.STOP_TIMERTASK);
//				m.replyTo = HomePage_messenger;
//
//				try {
//					toSer_Mess.send(m);
//				} catch (RemoteException e) {			
//					e.printStackTrace();
//				}					
//
//				m=Message.obtain(null,iAnnounceService.START_TIMERTASK);
//				m.replyTo = HomePage_messenger;
//				m.obj=(String)Integer.toString(pageNum_int);
//
//				try {
//					toSer_Mess.send(m);
//				} catch (RemoteException e) {			
//					e.printStackTrace();
//				}	
//
//
//			}
//		});
//
//
//		if(pageNum_int<=1){
//			bt_Prev.setEnabled(false);
//		}
//		lbar.addView(bt_Prev);
//		lbar.addView(tv_pgnum);
//		lbar.addView(bt_Next);
//
//		mainLayout.addView(lbar);

		final ServerResponse obj_serRes=sr01;
		
//		v.addView(l1);
		
		

		for(int i=0;i<obj_serRes.feed.size();i++){
			
			LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//			LinearLayout l2=new LinearLayout(getBaseContext());
			
			View v=mInflater.inflate(R.layout.ann_bg,null);
			
			LinearLayout l2=(LinearLayout)v.findViewById(R.id.lay_ann_bg);
			
			
			
			
			
//			l2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));

			TextView ann=new TextView(getBaseContext());
			
			ann.setText((Html.fromHtml("<b>"+obj_serRes.feed.get(i).announcer+"</b>")).toString().toUpperCase());
//			ann.setText(obj_serRes.feed.get(i).announcer);
			ann.setTextColor(getResources().getColor(R.color.ann_announcer));
			ann.setTextSize(26);
			final String s=obj_serRes.feed.get(i).announcer;
			ann.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
					String curruser=settings.getString("userName", "-1");
					if(curruser.equalsIgnoreCase(s)){
						Intent myIntent = new Intent(v.getContext(), MyProfile.class);
						Bundle b=new Bundle();
						b.putString("username",curruser);
						myIntent.putExtras(b);
						startActivityForResult(myIntent,3);
					}else{					
						Intent myIntent = new Intent(v.getContext(), UserProfile.class);
						Bundle b=new Bundle();
						b.putString("username",s);
						
						myIntent.putExtras(b);
						startActivityForResult(myIntent,3);
					}
				}
			});

			l2.addView(ann);			
			TextView Descr=new TextView(getBaseContext());
			Descr.setText(obj_serRes.feed.get(i).description);
			Descr.setTextSize(16);
			Descr.setTextColor(getResources().getColor(R.color.ann_desc));
			
			l2.addView(Descr);

//			TextView date=new TextView(getBaseContext());
//			
//			
//			String []tempst=(obj_serRes.feed.get(i).timestamp).split(" ");
//			String date_txt=tempst[0];
//			String time=tempst[1];
//			time=time.substring(0, time.length()-2);
//
//
//			date.setText(Html.fromHtml("<b>Date: </b>"+date_txt));			
//			date.setTextSize(16);		
//
//			TextView tim=new TextView(getBaseContext());
//			tim.setText(Html.fromHtml("<b>Time: </b>"+time));			
//			tim.setTextSize(16);
//
//			l2.addView(tim);
//			l2.addView(date);
//
//
//			TextView ratin=new TextView(getBaseContext());
//			ratin.setText(Html.fromHtml("<b>Rating:</b> "+obj_serRes.feed.get(i).averageRating));
//			ratin.setTextSize(16);						
//			l2.addView(ratin);
			
			
			View v2=mInflater.inflate(R.layout.ann_info_bar, null);
			
			RelativeLayout l3=(RelativeLayout)v2.findViewById(R.id.ann_info_bar_RL);

//			LinearLayout l3=new LinearLayout(getBaseContext());
			


//			Button bt_locate=new Button(getBaseContext());
//			//			bt_locate.setText("Locate");
//			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//
//			bt_locate.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.locate));
//
//			final String longi=obj_serRes.feed.get(i).longitude;
//			final String lati=obj_serRes.feed.get(i).latitude;
//			final String mapDesc=obj_serRes.feed.get(i).announcer+" :  "+obj_serRes.feed.get(i).description+"("+(obj_serRes.feed.get(i).timestamp).substring(0,(obj_serRes.feed.get(i).timestamp).length())+")";
//			bt_locate.setOnClickListener(new OnClickListener() {				
//
//				public void onClick(View arg0) {
//					Bundle b=new Bundle();
//					b.putString("longitude", longi);
//					b.putString("latitude", lati);
//					b.putString("desc", mapDesc);
//					Intent myIntent = new Intent(getBaseContext(), MyMapAct.class);	
//					myIntent.putExtras(b);
//					startActivity(myIntent);					
//				}
//			});
//
//
//			Button bt_tu=new Button(getBaseContext());
//			//			bt_tu.setText("Rate UP");
//			if((obj_serRes.feed.get(i).currentUserRating).equalsIgnoreCase("0")){
//				bt_tu.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.bt_r1));
//			}
//			else if((obj_serRes.feed.get(i).currentUserRating).equalsIgnoreCase("-1")){
//				bt_tu.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.bt_r1));
//				bt_tu.setEnabled(false);
//			}
//			else{
//				bt_tu.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.rtu));
//				bt_tu.setEnabled(false);
//			}
//
//
//			Button bt_td=new Button(getBaseContext());			
//
//			if((obj_serRes.feed.get(i).currentUserRating).equalsIgnoreCase("0")){
//				bt_td.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.bt_r2));
//			}
//			else if((obj_serRes.feed.get(i).currentUserRating).equalsIgnoreCase("-1")){
//				bt_td.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.rtd));
//				bt_td.setEnabled(false);
//			}
//			else{
//				bt_td.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.bt_r2));
//				bt_td.setEnabled(false);
//			}
//
//
//
//
//			l3.addView(bt_tu);
//			l3.addView(bt_td);
//			//			LinearLayout l4=new LinearLayout(getBaseContext());
//			l3.addView(bt_locate);
//			Button bt_comment=new Button(getBaseContext());
//			bt_comment.setText(obj_serRes.feed.get(i).noOfComments);
//			bt_comment.setTextSize(18);
//
//
//			bt_comment.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.comment));
//			lp.setMargins(5, 0, 0, 0);
//			bt_comment.setLayoutParams(lp);
//			final int k=i;
//			bt_comment.setOnClickListener(new OnClickListener() {
//
//				public void onClick(View v) {
//					Intent in=new Intent(getBaseContext(),CommentActivity.class);
//					Bundle b=new Bundle();
//					b.putString("desc",obj_serRes.feed.get(k).description);
//					b.putString("aid",obj_serRes.feed.get(k).announcement_id);
//					in.putExtras(b);
//					startActivityForResult(in,3);
//				}
//			});
//
//			bt_td.setOnClickListener(new OnClickListener() {
//				public void onClick(View v) {
//					
//					v.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.rtd));
//					v.setEnabled(false);
//					
//					HttpPostRequest ht=new HttpPostRequest();
//					SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);					
//					
//					ht.rateAnnouncement(settings.getString("sessionId","0"), obj_serRes.feed.get(k).announcement_id, "0");
//					myFunc(ht);
//					
//				}
//			});
//
//			bt_tu.setOnClickListener(new OnClickListener() {
//
//				public void onClick(View v) {
//					v.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.rtu));
//					v.setEnabled(false);
//					
//					HttpPostRequest ht=new HttpPostRequest();
//					
//					SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
//					
//					ht.rateAnnouncement(settings.getString("sessionId","0"), obj_serRes.feed.get(k).announcement_id, "1");
//					
//					myFunc(ht);
//					
//				}
//			});
//			l3.addView(bt_comment);
			
			
//			l2.addView(l3);
			
//			l2.addView(l4);
			
			TextView tv_like=(TextView) v2.findViewById(R.id.tv_ann_info_likes);
			TextView tv_dislike=(TextView) v2.findViewById(R.id.tv_ann_info_dislikes);
			TextView tv_locate=(TextView) v2.findViewById(R.id.tv_ann_info_locate);
			
			tv_like.setText(getResources().getString(R.string.like)+" ( "+obj_serRes.feed.get(i).likes+" ) ");
			tv_dislike.setText(getResources().getString(R.string.dislike)+" ( "+obj_serRes.feed.get(i).dislikes+" ) ");
			tv_locate.setText(getResources().getString(R.string.locate)+" ( "+obj_serRes.feed.get(i).distance+" km ) ");
			
			
			TextView tv_comment=(TextView) v2.findViewById(R.id.tv_ann_info_comments);
			tv_comment.setText(getResources().getString(R.string.comments)+" ( "+obj_serRes.feed.get(i).noOfComments+" ) ");
			
			LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
			lp2.setMargins(10, 10, 10, 0);
			
			l2.setLayoutParams(lp2);			
			
			
			LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
			rlp.setMargins(10, 0, 10, 0);
			l3.setLayoutParams(rlp);
			
			v.setPadding(15, 15, 15, 15);
			
			v2.setPadding(30, 10,30, 10);
			
			l1.addView(l2);			
			l1.addView(l3);
			
			
		}

//		mainLayout.addView(v);


	}
	
	void myFunc(HttpPostRequest ht){

		if(ht.isError){
			Toast.makeText(getApplicationContext(), ht.xception, Toast.LENGTH_LONG).show();
		}
		else{
			MyXmlHandler myhandler=new MyXmlHandler();
			try {
				Xml.parse(ht.xmlStringResponse, myhandler);
			} catch (SAXException e) {
				e.printStackTrace();
			}
			
			if(!myhandler.obj_serverResp1.responseCode.equalsIgnoreCase("0")){
				
				Toast.makeText(getApplicationContext(),myhandler.obj_serverResp1.responseCode, Toast.LENGTH_LONG).show();
				if(myhandler.obj_serverResp1.responseCode.equalsIgnoreCase("1")){
					finish();
				}
			}
			else{
				
				Toast.makeText(getApplicationContext(), myhandler.obj_serverResp1.rateResponse, Toast.LENGTH_LONG).show();				
//				getAnnouncementText(Integer.toString(pageNum_int));
			}
			
			
		}
	}
	
	
	
/**
 * For getting the announcement with respect to the page number
 * @param pgnum
 */

	void getAnnouncementText(String pgnum){
		pdialog1.show();
		Message m=Message.obtain(null,iAnnounceService.GET_ANNOUNCEMENTS,(Object)pgnum);
		m.replyTo = mMessenger;
		try {
			mService.send(m);
		} catch (RemoteException e) {			
			e.printStackTrace();
		}
	}
	
	

	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className,
				IBinder service) {
			mService = new Messenger(service);
			Message m=Message.obtain(null,iAnnounceService.GET_ANNOUNCEMENTS,(Object)"1");
			m.replyTo = mMessenger;
			try {
				mService.send(m);
			} catch (RemoteException e) {			
				e.printStackTrace();
			}
			Message m1=new Message();
			m1.what=iAnnounceService.START_TIMERTASK;
			
			try {
				mService.send(m1);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
			
		}

		public void onServiceDisconnected(ComponentName className) {
			mService = null;				
			Toast.makeText(getBaseContext(),"Disconnected from Service",Toast.LENGTH_SHORT).show();
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==3){
			if (resultCode == Activity.RESULT_OK) {
				SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
//				HttpPostRequest ht=new HttpPostRequest();
//				String x=ht.logout(settings.getString("sessionId", "0"));
//				MyXmlHandler myhand=new MyXmlHandler();
//				try {
//					Xml.parse(x, myhand);
//				} catch (SAXException e) {
//					e.printStackTrace();
//				}

				SharedPreferences.Editor editor = settings.edit();
				editor.putString("sessionId", "0");
				editor.commit();
//				Toast.makeText(getApplicationContext(),myhand.obj_serverResp1.logoutResponse, Toast.LENGTH_LONG).show();				
				finish();				               
			}
			
		}

	}
	
	@Override
	protected void onStop() {
		
		locationManager.removeUpdates(locationListener);
		if(mConnection!=null){			
			if(mService!=null){
				Message m=new Message();
				m.what=iAnnounceService.STOP_TIMERTASK;
				m.replyTo=mMessenger;
				 try {
					mService.send(m);
				} catch (RemoteException e) {					
					e.printStackTrace();
				}
			}						
		}
		
		
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		getApplicationContext().unbindService(mConnection);
		super.onDestroy();
	}


		@Override
		protected void onResume() {
//			if(mService!=null){
//			
//			getAnnouncementText(Integer.toString(pageNum_int));
//			}
			super.onResume();
		}
		
		@Override
		protected void onRestart() {
			if(mConnection!=null){			
				if(mService!=null){
					Message m=new Message();
					m.what=iAnnounceService.START_TIMERTASK;
					m.replyTo=mMessenger;
					 try {
						mService.send(m);
					} catch (RemoteException e) {					
						e.printStackTrace();
					}
				}						
			}			
			super.onRestart();
		}






}