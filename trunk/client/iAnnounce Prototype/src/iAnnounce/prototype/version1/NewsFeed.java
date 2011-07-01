package iAnnounce.prototype.version1;



import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.Html;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
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
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getApplicationContext().bindService(new Intent(NewsFeed.this, iAnnounceService.class), mConnection, Context.BIND_AUTO_CREATE);

		pdialog1 = ProgressDialog.show(NewsFeed.this,"", 
				"Loading. Please wait...", true);

		myMess=mMessenger;
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.appmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_Profile:
			Intent in= new Intent(this, MyProfile.class);
			SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);			
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
			SharedPreferences settings1 = getSharedPreferences("iAnnounceVars", 0);
			HttpPostRequest ht=new HttpPostRequest();
			String x=ht.logout(settings1.getString("sessionId", "0"));
			MyXmlHandler myhand=new MyXmlHandler();
			try {
				Xml.parse(x, myhand);
			} catch (SAXException e) {
				e.printStackTrace();
			}

			SharedPreferences.Editor editor = settings1.edit();
			editor.putString("sessionId", "0");
			editor.commit();
			Toast.makeText(getApplicationContext(),myhand.obj_serverResp1.logoutResponse, Toast.LENGTH_LONG).show();				
			finish();		
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
	 * Service messenger which will be available after this activity is binded with the iAnnounce service
	 */
	public Messenger mService;
	private int pageNum_int=1;
	
	/**
	 * Messenger of this activity
	 */
	public static Messenger myMess;
	
	final Messenger mMessenger = new Messenger(new IncomingHandler());
	
	/**
	 * Message handler 
	 * @author Awais
	 *@version 1
	 */

	class IncomingHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case iAnnounceService.RECIEVE_ANNOUNCEMENTS:
				pdialog1.cancel();
//				Log.e("gaga", (String)msg.obj);
				generateGUI((String)msg.obj,Integer.toString(pageNum_int));
				break;

			default:

			}
			super.handleMessage(msg);
		}
	}

/**
 * Function for populating the screen
 * @param s1 parseable response from the serer
 * @param pagenum Page number to tell which page's announcements will be populated
 */

	void generateGUI(String s1,String pagenum){



		LinearLayout mainLayout=new LinearLayout(getBaseContext());
		mainLayout.setOrientation(LinearLayout.VERTICAL);
		setContentView(mainLayout);
		LinearLayout lbar=new LinearLayout(getBaseContext());
		lbar.setGravity(Gravity.CENTER_HORIZONTAL);

		lbar.setBackgroundColor(Color.rgb(189, 189, 189));
		lbar.setPadding(0, 5, 0, 0);
		final TextView tv_pgnum= new TextView(getBaseContext());
		tv_pgnum.setText("Page : "+pagenum);
		tv_pgnum.setTextColor(Color.BLACK);
		Button bt_Next=new Button(getBaseContext());
		bt_Next.setText("Next Page");
		bt_Next.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {

				pageNum_int++;
				tv_pgnum.setText("Page : "+Integer.toString(pageNum_int));
				getAnnouncementText(Integer.toString(pageNum_int));


				Messenger HomePage_messenger=HomePage.myMess;
				Messenger toSer_Mess=HomePage.Static_mServer;

				Message m=Message.obtain(null,iAnnounceService.STOP_TIMERTASK);
				m.replyTo = HomePage_messenger;


				try {
					toSer_Mess.send(m);
				} catch (RemoteException e) {			
					e.printStackTrace();
				}		


				m=Message.obtain(null,iAnnounceService.START_TIMERTASK);
				m.replyTo = HomePage_messenger;
				m.obj=(String)Integer.toString(pageNum_int);

				try {
					toSer_Mess.send(m);
				} catch (RemoteException e) {			
					e.printStackTrace();
				}	


			}
		});

		Button bt_Prev=new Button(getBaseContext());
		bt_Prev.setText("Prev Page");
		bt_Prev.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				pageNum_int--;
				tv_pgnum.setText("Page : "+Integer.toString(pageNum_int));
				getAnnouncementText(Integer.toString(pageNum_int));					

				Messenger HomePage_messenger=HomePage.myMess;
				Messenger toSer_Mess=HomePage.Static_mServer;

				Message m=Message.obtain(null,iAnnounceService.STOP_TIMERTASK);
				m.replyTo = HomePage_messenger;

				try {
					toSer_Mess.send(m);
				} catch (RemoteException e) {			
					e.printStackTrace();
				}					

				m=Message.obtain(null,iAnnounceService.START_TIMERTASK);
				m.replyTo = HomePage_messenger;
				m.obj=(String)Integer.toString(pageNum_int);

				try {
					toSer_Mess.send(m);
				} catch (RemoteException e) {			
					e.printStackTrace();
				}	


			}
		});


		if(pageNum_int<=1){
			bt_Prev.setEnabled(false);
		}
		lbar.addView(bt_Prev);
		lbar.addView(tv_pgnum);
		lbar.addView(bt_Next);

		mainLayout.addView(lbar);




		ScrollView v=new ScrollView(getBaseContext());

		MyXmlHandler myhandler=new MyXmlHandler();

		try {
			Xml.parse(s1, myhandler);
		} catch (SAXException e) {
			e.printStackTrace();
		}

		final ServerResponse obj_serRes=myhandler.obj_serverResp1;
		LinearLayout l1=new LinearLayout(getBaseContext());
		v.addView(l1);
		l1.setOrientation(LinearLayout.VERTICAL);

		if(obj_serRes.forceLogin){						
			finish();			
		}		

		for(int i=0;i<obj_serRes.feed.size();i++){

			LinearLayout l2=new LinearLayout(getBaseContext());
			l2.setOrientation(LinearLayout.VERTICAL);
			l2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));

			TextView ann=new TextView(getBaseContext());
			ann.setText(Html.fromHtml("<b>"+obj_serRes.feed.get(i).announcer+"</b>"));			
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
			Descr.setTextSize(20);

			l2.addView(Descr);

			TextView date=new TextView(getBaseContext());

			String []tempst=(obj_serRes.feed.get(i).timestamp).split(" ");
			String date_txt=tempst[0];
			String time=tempst[1];
			time=time.substring(0, time.length()-2);


			date.setText(Html.fromHtml("<b>Date: </b>"+date_txt));			
			date.setTextSize(16);		

			TextView tim=new TextView(getBaseContext());
			tim.setText(Html.fromHtml("<b>Time: </b>"+time));			
			tim.setTextSize(16);

			l2.addView(tim);
			l2.addView(date);


			TextView ratin=new TextView(getBaseContext());
			ratin.setText(Html.fromHtml("<b>Rating:</b> "+obj_serRes.feed.get(i).averageRating));
			ratin.setTextSize(16);						
			l2.addView(ratin);


			LinearLayout l3=new LinearLayout(getBaseContext());


			Button bt_locate=new Button(getBaseContext());
			//			bt_locate.setText("Locate");
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


			bt_locate.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.locate));

			final String longi=obj_serRes.feed.get(i).longitude;
			final String lati=obj_serRes.feed.get(i).latitude;
			final String mapDesc=obj_serRes.feed.get(i).announcer+" :  "+obj_serRes.feed.get(i).description+"("+(obj_serRes.feed.get(i).timestamp).substring(0,(obj_serRes.feed.get(i).timestamp).length())+")";
			bt_locate.setOnClickListener(new OnClickListener() {				

				public void onClick(View arg0) {
					Bundle b=new Bundle();
					b.putString("longitude", longi);
					b.putString("latitude", lati);
					b.putString("desc", mapDesc);
					Intent myIntent = new Intent(getBaseContext(), MyMapAct.class);	
					myIntent.putExtras(b);
					startActivity(myIntent);					
				}
			});


			Button bt_tu=new Button(getBaseContext());
			//			bt_tu.setText("Rate UP");
			if((obj_serRes.feed.get(i).currentUserRating).equalsIgnoreCase("0")){
				bt_tu.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.bt_r1));
			}
			else if((obj_serRes.feed.get(i).currentUserRating).equalsIgnoreCase("-1")){
				bt_tu.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.bt_r1));
				bt_tu.setEnabled(false);
			}
			else{
				bt_tu.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.rtu));
				bt_tu.setEnabled(false);
			}


			Button bt_td=new Button(getBaseContext());			

			if((obj_serRes.feed.get(i).currentUserRating).equalsIgnoreCase("0")){
				bt_td.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.bt_r2));
			}
			else if((obj_serRes.feed.get(i).currentUserRating).equalsIgnoreCase("-1")){
				bt_td.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.rtd));
				bt_td.setEnabled(false);
			}
			else{
				bt_td.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.bt_r2));
				bt_td.setEnabled(false);
			}




			l3.addView(bt_tu);
			l3.addView(bt_td);
			//			LinearLayout l4=new LinearLayout(getBaseContext());
			l3.addView(bt_locate);
			Button bt_comment=new Button(getBaseContext());
			bt_comment.setText(obj_serRes.feed.get(i).noOfComments);
			bt_comment.setTextSize(18);


			bt_comment.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.comment));
			lp.setMargins(5, 0, 0, 0);
			bt_comment.setLayoutParams(lp);
			final int k=i;
			bt_comment.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					Intent in=new Intent(getBaseContext(),CommentActivity.class);
					Bundle b=new Bundle();
					b.putString("desc",obj_serRes.feed.get(k).description);
					b.putString("aid",obj_serRes.feed.get(k).announcement_id);
					in.putExtras(b);
					startActivityForResult(in,3);
				}
			});

			bt_td.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					HttpPostRequest ht=new HttpPostRequest();
					SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);					
					String x=ht.rateAnnouncement(settings.getString("sessionId","0"), obj_serRes.feed.get(k).announcement_id, "0");

					MyXmlHandler myhandler=new MyXmlHandler();
					try {
						Xml.parse(x, myhandler);
					} catch (SAXException e) {
						e.printStackTrace();
					}
					if(myhandler.obj_serverResp1.forceLogin){
						finish();
					}
					else{
						Toast.makeText(getApplicationContext(), myhandler.obj_serverResp1.rateResponse, Toast.LENGTH_LONG).show();

						getAnnouncementText(Integer.toString(pageNum_int));
					}
				}
			});

			bt_tu.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					HttpPostRequest ht=new HttpPostRequest();
					SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);					
					String x=ht.rateAnnouncement(settings.getString("sessionId","0"), obj_serRes.feed.get(k).announcement_id, "1");

					MyXmlHandler myhandler=new MyXmlHandler();
					try {
						Xml.parse(x, myhandler);
					} catch (SAXException e) {
						e.printStackTrace();
					}
					if(myhandler.obj_serverResp1.forceLogin){
						SharedPreferences.Editor editor = settings.edit();
						editor.putString("sessionId", "0");
						editor.commit();
						finish();
					}
					else{
						Toast.makeText(getApplicationContext(), myhandler.obj_serverResp1.rateResponse, Toast.LENGTH_LONG).show();

						getAnnouncementText(Integer.toString(pageNum_int));

					}
				}
			});



			l3.addView(bt_comment);



			l2.addView(l3);
			//			l2.addView(l4);


			lp.setMargins(0, 5, 0, 5);
			l2.setLayoutParams(lp);
			l1.addView(l2);
		}

		mainLayout.addView(v);


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
				HttpPostRequest ht=new HttpPostRequest();
				String x=ht.logout(settings.getString("sessionId", "0"));
				MyXmlHandler myhand=new MyXmlHandler();
				try {
					Xml.parse(x, myhand);
				} catch (SAXException e) {
					e.printStackTrace();
				}

				SharedPreferences.Editor editor = settings.edit();
				editor.putString("sessionId", "0");
				editor.commit();
				Toast.makeText(getApplicationContext(),myhand.obj_serverResp1.logoutResponse, Toast.LENGTH_LONG).show();				
				finish();				               
			}
		}

	}


	//	@Override
	//	protected void onResume() {
	//		getAnnouncementText(Integer.toString(pageNum_int));
	//		super.onResume();
	//	}






}