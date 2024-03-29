package iAnnounce.prototype.version1;




import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
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
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

/**
 * User interface which comes after a user login. Landing page is consists of tab view. First tab is Announcements. Second tab consists of GUI for announcing and third for announcements which are announced by current logged in user
 * Moreover There is option bar.    
 * @author Awais
 *
 */
public class HomePage extends TabActivity {
	LocationManager locationManager;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);




		//check if custom title is supported BEFORE setting the content view!
		boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.home_page);

		if(customTitleSupported){
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.titlebar);
			TextView tv_title= (TextView)findViewById(R.id.tv_titlebar);        	
			SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
			tv_title.setText((settings.getString("userName", "iAnnounce")).toUpperCase());	
		}



		/*Location work*/

		/*Location location =
			locationManager.getLastKnownLocation(provider);

		if(location!=null){
		updateLocation(location);
		}*/


		getApplication().startService(new Intent(this, iAnnounceService.class));
		//		getApplicationContext().bindService(new Intent(HomePage.this, iAnnounceService.class), mConnection, Context.BIND_AUTO_CREATE);



		int timeFreq=1;
		int distFreq=500;



		/* Application variables  */

		SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
		SharedPreferences.Editor editor = settings.edit();

		if(settings.getString("timeInterval","-1").equalsIgnoreCase("-1")){   	
			editor.putString("timeInterval", Integer.toString(timeFreq));
			editor.commit();
		}
		else{

			timeFreq=Integer.parseInt(settings.getString("timeInterval","1"));
		}
		if(settings.getString("distanceMeter","-1").equalsIgnoreCase("-1")){   	
			editor.putString("distanceMeter", "500");
			editor.commit();
		}
		else{
			distFreq=Integer.parseInt(settings.getString("distanceMeter","500"));						
		}
		/*adding location listener*/

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
			//			locationManager.requestLocationUpdates(provider, 1*60*1000, distFreq,
			//					locationListener);
			//
			//			/* ifcrash*/
			//
			//			Location location =
			//				locationManager.getLastKnownLocation(provider);
			//
			//			if(location!=null){
			//				updateLocation(location);
			//			}
			/*----*/

			//			TabHost tabHost = getTabHost();  // The activity TabHost
			//			TabHost.TabSpec spec;  // Reusable TabSpec for each tab
			//			Intent intent;  // Reusable Intent for each tab
			//			TabHost.TabSpec spec1;
			//			// Create an Intent to launch an Activity for the tab (to be reused)
			//			intent = new Intent().setClass(this, NewsFeed.class);
			//
			//			// Initialize a TabSpec for each tab and add it to the TabHost
			//			spec1 = tabHost.newTabSpec("news").setIndicator("Announcement Feed",getResources().getDrawable(R.drawable.tabs_annoucements)).setContent(intent);
			//			tabHost.addTab(spec1);
			//
			//			// Do the same for the other tabs
			//			intent = new Intent().setClass(this, Announce.class);
			//			spec = tabHost.newTabSpec("post").setIndicator("Announce").setContent(intent);
			//			tabHost.addTab(spec);
			//
			//
			//
			//
			//			intent = new Intent().setClass(this, MyAnnouncments.class);
			//			spec = tabHost.newTabSpec("mypost").setIndicator("My Announcments").setContent(intent);
			//			tabHost.addTab(spec);
			//
			//			tabHost.setCurrentTab(0);


			TabHost tabHost= getTabHost();
			String tabId = "0";
			//			tabHost.setBackgroundColor(Color.BLACK);
			Bundle bundle = this.getIntent().getExtras();
			if(bundle != null && bundle.getString("tabId") != null){
				tabId = bundle.getString("tabId");
			}
			View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tabs_indicator, getTabWidget(), false);
			//tabIndicator.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_indicator_selector_ann));
			ImageView icon = (ImageView)tabIndicator.findViewById(R.id.icon);
			icon.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_indicator_selector_ann));
			TextView title = (TextView) tabIndicator.findViewById(R.id.title);
			title.setText("Announcements");
			TabHost.TabSpec spec = tabHost.newTabSpec("tab1");			
			spec.setIndicator(tabIndicator);
			spec.setContent(new Intent(HomePage.this, NewsFeed.class));
			tabHost.addTab(spec);



			View tabIndicator1 = LayoutInflater.from(this).inflate(R.layout.tabs_indicator, getTabWidget(), false);
			ImageView icon1 = (ImageView)tabIndicator1.findViewById(R.id.icon);
			icon1.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_indicator_selector_pann));
			//tabIndicator1.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_indicator_selector_pann));
			TextView title1 = (TextView) tabIndicator1.findViewById(R.id.title);
			title1.setText("Announce");
			TabHost.TabSpec spec2 = tabHost.newTabSpec("tab2");			
			spec2.setIndicator(tabIndicator1);
			Intent myIntent = new Intent(HomePage.this, Announce.class);
			if(tabId.equals("1")){
				Bundle b = new Bundle();
				b.putString("neighbourId", bundle.getString("neighbourId"));
				myIntent.putExtras(b);
			}
			spec2.setContent(myIntent);
			tabHost.addTab(spec2);


			View tabIndicator2 = LayoutInflater.from(this).inflate(R.layout.tabs_indicator, getTabWidget(), false);
			//tabIndicator2.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_indicator_selector_myann));
			ImageView icon3 = (ImageView)tabIndicator2.findViewById(R.id.icon);
			icon3.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_indicator_selector_myann));
			TextView title2 = (TextView) tabIndicator2.findViewById(R.id.title);
			title2.setText("My Announcements");
			TabHost.TabSpec spec3 = tabHost.newTabSpec("tab3");			 
			spec3.setIndicator(tabIndicator2);
			spec3.setContent(new Intent(HomePage.this,MyAnnouncments.class));
			tabHost.addTab(spec3);

			View tabIndicator3 = LayoutInflater.from(this).inflate(R.layout.tabs_indicator, getTabWidget(), false);
			//tabIndicator3.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_indicator_selector_ngbr));
			ImageView icon4 = (ImageView)tabIndicator3.findViewById(R.id.icon);
			icon4.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_indicator_selector_ngbr));
			TextView title3 = (TextView) tabIndicator3.findViewById(R.id.title);
			title3.setText("Neighbourhoods");
			TabHost.TabSpec spec4 = tabHost.newTabSpec("tab4");			 
			spec4.setIndicator(tabIndicator3);
			spec4.setContent(new Intent(HomePage.this,TabNeighbourhoodActivity.class));
			tabHost.addTab(spec4);			
			tabHost.setCurrentTab(Integer.parseInt(tabId));




			//			addTab("Announce", R.drawable.tab_indicator_selector_ann, new Intent(HomePage.this, Announce.class));
			//			addTab("My Announcements", R.drawable.tab_indicator_selector_ann, new Intent(HomePage.this, MyAnnouncments.class));




			/*Message m=Message.obtain(null,iAnnounceService.GET_ANNOUNCEMENTS);
			m.replyTo = mMessenger;
			try {
				mService.send(m);
			} catch (RemoteException e) {			
				e.printStackTrace();
			}*/

			myMess=mMessenger;

		}

	}



	public void updateLocation(Location Loc){
		SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
		SharedPreferences.Editor editor = settings.edit();

		editor.putString("Longitude", Double.toString(Loc.getLongitude()));
		editor.putString("Latitude", Double.toString(Loc.getLatitude()));
		editor.commit();


	}
	private final LocationListener locationListener = new LocationListener() {
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


	public Messenger mService;

	public static Messenger myMess;
	public static Messenger Static_mServer;

	public Messenger getMessenger(){
		return mMessenger;	
	}

	final Messenger mMessenger = new Messenger(new IncomingHandler());

	class IncomingHandler extends Handler {
		Messenger NewsFeed_messenger=NewsFeed.myMess;

		Message m;

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case iAnnounceService.RECIEVE_ANNOUNCEMENTS:

				break;

			case iAnnounceService.RECIEVE_TASK_RESPONSE:
				m=Message.obtain(null,iAnnounceService.RECIEVE_ANNOUNCEMENTS);
				m.replyTo = mMessenger;
				m.obj=msg.obj;
				if(NewsFeed_messenger!=null){			
					try {
						NewsFeed_messenger.send(m);
					} catch (RemoteException e) {			
						e.printStackTrace();
					}		
				}
				break;

			case iAnnounceService.RESPONSE_NETWORK_ERROR:
				m=Message.obtain(null,iAnnounceService.RESPONSE_NETWORK_ERROR);
				m.replyTo = mMessenger;
				m.obj=msg.obj;
				if(NewsFeed_messenger!=null){	
					try {
						NewsFeed_messenger.send(m);
					} catch (RemoteException e) {			
						e.printStackTrace();
					}	
				}
				break;
			case  iAnnounceService.RESPONSE_ERROR_FROM_SERVER:
				m=Message.obtain(null,iAnnounceService.RESPONSE_ERROR_FROM_SERVER);
				m.replyTo = mMessenger;
				m.obj=msg.obj;
				if(NewsFeed_messenger!=null){	
					try {
						NewsFeed_messenger.send(m);
					} catch (RemoteException e) {			
						e.printStackTrace();
					}	
				}
				break;
			case iAnnounceService.RESPONSE_ERROR_SESSION:
				m=Message.obtain(null,iAnnounceService.RESPONSE_ERROR_FROM_SERVER);
				m.replyTo = mMessenger;
				m.obj=msg.obj;
				if(NewsFeed_messenger!=null){	
					try {
						NewsFeed_messenger.send(m);
					} catch (RemoteException e) {			
						e.printStackTrace();
					}
				}
				break;


			default:

			}
			super.handleMessage(msg);
		}
	}
	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className,
				IBinder service) {
			mService = new Messenger(service);
			Static_mServer=mService;
			Message m=Message.obtain(null,iAnnounceService.START_TIMERTASK);
			m.replyTo = mMessenger;

			try {
				mService.send(m);
			} catch (RemoteException e) {			
				e.printStackTrace();
			}

		}

		public void onServiceDisconnected(ComponentName className) {

			mService = null;				
			Toast.makeText(getBaseContext(),"Disconnected from Service",Toast.LENGTH_SHORT).show();

		}
	};


	protected void onResume() {
		//		
		//		//		Message m=Message.obtain(null,iAnnounceService.GET_ANNOUNCEMENTS);
		//		//		m.replyTo = NewsFeed.myMess;
		//		//		if(mService!=null){
		//		//			try {
		//		//				mService.send(m);
		//		//			} catch (RemoteException e) {			
		//		//				e.printStackTrace();
		//		//			}
		//		//		}
		//		if(mService!=null){
		//			Message m1=Message.obtain(null,iAnnounceService.STOP_TIMERTASK);
		//			m1.replyTo = mMessenger;
		//
		//			try {
		//				mService.send(m1);
		//			} catch (RemoteException e) {			
		//				e.printStackTrace();
		//			}
		//
		//		}
		//
		//		Message m=Message.obtain(null,iAnnounceService.START_TIMERTASK);	
		//		m.replyTo = mMessenger;
		//		if(mService!=null){
		//			try {
		//				mService.send(m);
		//			} catch (RemoteException e) {			
		//				e.printStackTrace();
		//			}
		//		}
		super.onResume();

	}

	@Override
	protected void onPause() {

		//		Message m=Message.obtain(null,iAnnounceService.STOP_TIMERTASK);
		//		m.replyTo = mMessenger;
		//		if(mService!=null){
		//			try {
		//				mService.send(m);
		//			} catch (RemoteException e) {			
		//				e.printStackTrace();
		//			}
		//
		//		}
		//
		super.onPause();		
	}

	@Override
	protected void onStop() {

		//		locationManager.removeUpdates(locationListener);
		//		getApplicationContext().unbindService(mConnection);

		getApplication().stopService(new Intent(this, iAnnounceService.class));

		super.onStop();
	}

	@Override
	protected Dialog onCreateDialog(int id) {

		//		Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG);
		AlertDialog.Builder b= new AlertDialog.Builder(HomePage.this);
		switch(id){

		case 2:			  
			b.setMessage("Please enable your GPS");  
			b.setTitle("Notification");  
			b.setCancelable(true);

			b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
					startActivity(intent);
				}
			});
			b.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {			
					finish();
					dialog.cancel();

				}
			});

			b.show();
			break;


		default:
			break;		
		}
		return super.onCreateDialog(id);
	}





}




