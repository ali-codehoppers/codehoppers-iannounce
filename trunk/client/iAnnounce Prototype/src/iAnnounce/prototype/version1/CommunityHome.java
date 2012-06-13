package iAnnounce.prototype.version1;

import java.util.List;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class CommunityHome extends Activity {
	
	private int pageNum_int=1;
	private String neighbourId;

	private Handler msgHandler;
	private final int ERROR_COMMUNICATION = 0;	
	private final int ERROR_SERVER = 1;
	private final int ERROR_SESSION = 2;
	private final int GUI_READY = 3;

	private ProgressDialog pdialog1;

	private LinearLayout mlay;
	private boolean fl_gotPage;
	ScrollView sv;
	
	private class myAnnouncementThread extends Thread{

		@Override
		public void run() {

			HttpPostRequest htreq=new HttpPostRequest();
			SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
			htreq.getAnnouncementsByNeighbourhood(settings.getString("sessionId", "0"),neighbourId,Integer.toString(pageNum_int));
			if(htreq.isError){
				Message msg1=new Message();
				msg1.what=ERROR_COMMUNICATION;
				msg1.obj=htreq.xception;				
				msgHandler.sendMessage(msg1);

			}else{
				generateGUI(htreq.xmlStringResponse);				
			}

			super.run();
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.community_home);
		if(customTitleSupported){
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.titlebar);
			TextView tv_title= (TextView)findViewById(R.id.tv_titlebar);        	
			SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
			tv_title.setText((settings.getString("userName", "iAnnounce")).toUpperCase());	
		}
		RelativeLayout main_layout = (RelativeLayout) findViewById(R.id.scroll_announcements);
		fl_gotPage=true;
		
		//		getAnnouncementText(pageNum_int);
		pdialog1= new ProgressDialog(this);
		pdialog1.setTitle("");
		pdialog1.setMessage("Loading. Please wait...");
		sv =new ScrollView(this){

			@Override
			protected void onScrollChanged(int l, int t, int oldl, int oldt) {
				View view = (View) getChildAt(getChildCount()-1);
				int diff = (view.getBottom()-(getHeight()+getScrollY()));// Calculate the scrolldiff
				
				
				if( diff == 0 ){  

					if(fl_gotPage){
						pdialog1.show();
						fl_gotPage=false;
						pageNum_int++;
						getAnnouncementText();
					}
				}
				super.onScrollChanged(l, t, oldl, oldt);
			}


		};
		
		main_layout.addView(sv);
		msgHandler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				pdialog1.cancel();				
				switch (msg.what){
				case ERROR_COMMUNICATION:					
					Toast.makeText(getApplicationContext(),(String) msg.obj, Toast.LENGTH_LONG).show();
					break;
				case ERROR_SERVER:
					Toast.makeText(getApplicationContext(),(String) msg.obj, Toast.LENGTH_LONG).show();
					break;
				case ERROR_SESSION:
					Toast.makeText(getApplicationContext(),(String) msg.obj, Toast.LENGTH_LONG).show();
					finish();					
					break;
				case GUI_READY:
					fl_gotPage=true;
					mlay.addView((LinearLayout)msg.obj);
					break;
				default:

				}
				super.handleMessage(msg);
			}
		};

		ImageView buttonReturn = (ImageView) findViewById(R.id.btn_return);
		buttonReturn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//Do stuff here
				//	finish();
				Intent myIntent = new Intent(getApplicationContext(), HomePage.class);
				Bundle b= new Bundle();
				b.putString("tabId", "3");
				myIntent.putExtras(b);
				startActivity(myIntent);
			}
		});
		Bundle bundle = this.getIntent().getExtras();
		neighbourId = bundle.getString("neighbourId");

		HttpPostRequest http=new HttpPostRequest();
		SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
		http.getNeighbourById(settings.getString("sessionId","0"),neighbourId,"1");
		MyXmlHandler myhandler=new MyXmlHandler();
		try {
			Xml.parse(http.xmlStringResponse, myhandler);
		} catch (SAXException e) {
			e.printStackTrace();
		}

		TextView title = (TextView) findViewById(R.id.text_title);
		title.setText(myhandler.obj_serverResp1.neigbhours.get(0).title.toUpperCase());

		ImageView buttonJoin = (ImageView) findViewById(R.id.btn_join);
		buttonJoin.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//Do stuff here
				HttpPostRequest http=new HttpPostRequest();
				SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
				http.JoinNeighbourhood(settings.getString("sessionId","0"),neighbourId);
				Intent intent = getIntent();
				finish();
				startActivity(intent);
			}
		});
		ImageView buttonAnnounce = (ImageView) findViewById(R.id.btn_announce);
		buttonAnnounce.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//Do stuff here
				Intent myIntent = new Intent(getApplicationContext(), HomePage.class);
				Bundle b= new Bundle();
				b.putString("tabId", "1");
				b.putString("neighbourId", neighbourId);
				myIntent.putExtras(b);
				startActivity(myIntent);
			}
		});
		ImageView buttonLocation = (ImageView) findViewById(R.id.btn_location);
		buttonLocation.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//Do stuff here
				Intent myIntent = new Intent(getApplicationContext(), ShowLocations.class);
				Bundle b= new Bundle();
				b.putString("neighbourId", neighbourId);
				myIntent.putExtras(b);
				startActivity(myIntent);
			}
		});
		if(myhandler.obj_serverResp1.neigbhours.get(0).isMember.equalsIgnoreCase("true")){
			buttonJoin.setVisibility(View.GONE);
			buttonAnnounce.setVisibility(View.VISIBLE);
			buttonLocation.setVisibility(View.VISIBLE);
		}
	

	}
	@Override
	protected void onResume() {
		sv.removeAllViews();
		
		pageNum_int=1;
		mlay=new LinearLayout(this);
		mlay.setOrientation(LinearLayout.VERTICAL);
		sv.addView(mlay);
		getAnnouncementText();
		super.onResume();
	}
	
	void getAnnouncementText(){

		//pdialog1.show();
		myAnnouncementThread th=new myAnnouncementThread();
		th.start();

	}
	
	void generateGUI(String resp){

		MyXmlHandler myhandler=new MyXmlHandler();
		try {
			Xml.parse(resp, myhandler);
		} catch (SAXException e) {
			e.printStackTrace();
		}
		final ServerResponse obj_serRes=myhandler.obj_serverResp1;
		Message msg=new Message();
		if(!obj_serRes.responseCode.equalsIgnoreCase("0")){
			msg.obj=obj_serRes.responseMessage;			
			if(obj_serRes.responseCode.equalsIgnoreCase("1")){
				msg.what=ERROR_SESSION;				
			}
			else{
				msg.what=ERROR_SERVER;
			}
			msgHandler.sendMessage(msg);
		}
		else{
			
			List<Announcements> announcements = myhandler.obj_serverResp1.feed;
		//	ScrollView scrollview = (ScrollView) findViewById(R.id.scroll_announcements);	
			LinearLayout outerLinearLayout = new LinearLayout(this);
		//	scrollview.addView(outerLinearLayout);
			outerLinearLayout.setOrientation(LinearLayout.VERTICAL);
			for(int i=0;i<announcements.size();i++){
				Announcements announcement = announcements.get(i);
				LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = layoutInflater.inflate(R.layout.ann_bg, null);
				LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.lay_ann_bg);
				TextView ann = new TextView(getBaseContext());
				ann.setText(announcement.announcer.toUpperCase());
				ann.setTextColor(getResources().getColor(R.color.ann_announcer));
				ann.setTextSize(26);
				//final String n = neighbours.get(i).id;
				final String s= announcement.announcer;
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
				linearLayout.addView(ann);
				TextView desc = new TextView(getBaseContext());
				desc.setText(announcement.description);
				desc.setTextSize(16);
				desc.setTextColor(getResources().getColor(R.color.ann_desc));
				linearLayout.addView(desc);
				
				View view2 = layoutInflater.inflate(R.layout.ann_info_bar, null);
				RelativeLayout relativeLayout = (RelativeLayout) view2.findViewById(R.id.ann_info_bar_RL);
				
				ImageView bt_locate = (ImageView) view2.findViewById(R.id.tv_ann_image_locate); 
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

				ImageView bt_tu= (ImageView) view2.findViewById(R.id.tv_ann_image_likes);
				if((announcement.currentUserRating).equalsIgnoreCase("0")){
			
				}
				else if((announcement.currentUserRating).equalsIgnoreCase("-1")){
				
					bt_tu.setEnabled(false);
				}
				else{
		
					bt_tu.setEnabled(false);
				}


				ImageView bt_td= (ImageView) view2.findViewById(R.id.tv_ann_image_dislikes);
				if((announcement.currentUserRating).equalsIgnoreCase("0")){
			
				}
				else if((announcement.currentUserRating).equalsIgnoreCase("-1")){
			
					bt_td.setEnabled(false);
				}
				else{
			
					bt_td.setEnabled(false);
				}

				ImageView bt_comment= (ImageView) view2.findViewById(R.id.tv_ann_image_comments);

				final Announcements announce = announcement;			
				bt_comment.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						
						Intent in=new Intent(getBaseContext(),AnnouncementComments.class);
						Bundle b=new Bundle();
						b.putString("desc",announce.description);
						b.putString("name",announce.announcer);
						b.putString("aid",announce.announcement_id);
						in.putExtras(b);
						startActivity(in);
					}
				});

				bt_td.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						v.setEnabled(false);
						HttpPostRequest ht=new HttpPostRequest();
						SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);					
						ht.rateAnnouncement(settings.getString("sessionId","0"), announce.announcement_id, "0");
						myFunc(ht);
					}
				});

				bt_tu.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						v.setEnabled(false);
						HttpPostRequest ht=new HttpPostRequest();
						SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
						ht.rateAnnouncement(settings.getString("sessionId","0"), announce.announcement_id, "1");
						myFunc(ht);
						
					}
				});
				
				
				TextView tv_like=(TextView) view2.findViewById(R.id.tv_ann_info_likes);
				TextView tv_dislike=(TextView) view2.findViewById(R.id.tv_ann_info_dislikes);
				TextView tv_locate=(TextView) view2.findViewById(R.id.tv_ann_info_locate);
				TextView tv_comment=(TextView) view2.findViewById(R.id.tv_ann_info_comments);
				
				tv_like.setText("( "+obj_serRes.feed.get(i).likes+" ) ");
				tv_dislike.setText("( "+obj_serRes.feed.get(i).dislikes+" ) ");
				tv_locate.setText("( "+obj_serRes.feed.get(i).distance+" km ) ");
				tv_comment.setText("( "+obj_serRes.feed.get(i).noOfComments+" ) ");
				
				LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
				lp2.setMargins(10, 10, 10, 0);
				
				linearLayout.setLayoutParams(lp2);			
				
				
				LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
				rlp.setMargins(10, 0, 10, 0);
				relativeLayout.setLayoutParams(rlp);
				
				view.setPadding(15, 15, 15, 15);
				view2.setPadding(10, 0,10, 0);
				/*LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
				layoutParams.setMargins(10,10,10,0);
				linearLayout.setLayoutParams(layoutParams);*/
				outerLinearLayout.addView(linearLayout);
				outerLinearLayout.addView(relativeLayout);
			}

			msg.what=GUI_READY;
			msg.obj=outerLinearLayout;
			msgHandler.sendMessage(msg);

		}

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

}
