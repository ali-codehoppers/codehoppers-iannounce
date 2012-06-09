package iAnnounce.prototype.version1;

import java.util.List;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class CommunityHome extends Activity {

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
		final String neighbourId = bundle.getString("neighbourId");

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
		http.getAnnouncementsByNeighbourhood(settings.getString("sessionId","0"),neighbourId,"1");
		myhandler=new MyXmlHandler();
		try {
			Xml.parse(http.xmlStringResponse, myhandler);
		} catch (SAXException e) {
			e.printStackTrace();
		}
		List<Announcements> announcements = myhandler.obj_serverResp1.feed;
		ScrollView scrollview = (ScrollView) findViewById(R.id.scroll_announcements);	
		LinearLayout outerLinearLayout = new LinearLayout(this);
		scrollview.addView(outerLinearLayout);
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
			ann.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
			    /*	Intent myIntent = new Intent(getApplicationContext(), CommunityHome.class);
			    	//myIntent.putExtra("neighbourId", neighbourId);
			    	
			    	Bundle b= new Bundle();
			    	b.putString("neighbourId", n);
			    	myIntent.putExtras(b);
	                startActivity(myIntent);*/
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
			
			TextView tv_like=(TextView) view2.findViewById(R.id.tv_ann_info_likes);
			TextView tv_dislike=(TextView) view2.findViewById(R.id.tv_ann_info_dislikes);
			TextView tv_locate=(TextView) view2.findViewById(R.id.tv_ann_info_locate);
			TextView tv_comment=(TextView) view2.findViewById(R.id.tv_ann_info_comments);
			
			tv_like.setText("(0)");
			tv_dislike.setText("(0)");
			tv_locate.setText("(0km)");			
			tv_comment.setText("(0)");
			
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


	}

}
