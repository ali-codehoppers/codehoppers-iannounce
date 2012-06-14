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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
public class Community extends Activity {

	public String neighbourId;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.community);		
		ImageView new_ngbr = (ImageView) findViewById(R.id.new_ngbr);
		new_ngbr.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//Do stuff here
				Intent newNeighbour = new Intent(getParent(), NewCommunity.class);
				TabGroupActivity parentActivity = (TabGroupActivity)getParent();
				parentActivity.startChildActivity("NewNeighbour", newNeighbour);
						//Intent myIntent = new Intent(getApplicationContext(), NewCommunity.class);
						//startActivity(myIntent);
			}
		});
				/*LinearLayout buttonLayout = new LinearLayout(this);
		//buttonLayout.setGravity(Gravity.CENTER_HORIZONTAL);
		buttonLayout.addView(new_ngbr);
		mainLayout.addView(buttonLayout);
		ScrollView scrollview = new ScrollView(this);
		scrollview.setBackgroundColor(getResources().getColor(R.color.announcemts_bg));
		mainLayout.addView(scrollview);
		mainLayout.setBackgroundColor(getResources().getColor(R.color.announcemts_bg));
		mainLayout.setOrientation(LinearLayout.VERTICAL);*/
				ScrollView scrollview = (ScrollView) findViewById(R.id.main_scroll);
				LinearLayout outerLinearLayout = new LinearLayout(this);
				scrollview.addView(outerLinearLayout);
				outerLinearLayout.setOrientation(LinearLayout.VERTICAL);

				HttpPostRequest http=new HttpPostRequest();
				SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
				http.getAllNeighbours(settings.getString("sessionId","0"),"1");
				MyXmlHandler myhandler=new MyXmlHandler();
				try {
					Xml.parse(http.xmlStringResponse, myhandler);
				} catch (SAXException e) {
					e.printStackTrace();
				}

				List<Neigbhours> neighbours =  myhandler.obj_serverResp1.neigbhours;
				for(int i=0;i<neighbours.size();i++){
					LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					View view = layoutInflater.inflate(R.layout.ann_bg, null);
					LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.lay_ann_bg);
					TextView title = new TextView(getBaseContext());
					title.setText(neighbours.get(i).title);
					title.setTextColor(getResources().getColor(R.color.ann_announcer));
					title.setTextSize(26);
					final String n = neighbours.get(i).id;
					title.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							Intent myIntent = new Intent(getApplicationContext(), CommunityHome.class);
							//myIntent.putExtra("neighbourId", neighbourId);

							Bundle b= new Bundle();
							b.putString("neighbourId", n);
							myIntent.putExtras(b);
							startActivity(myIntent);
						}
					});
					linearLayout.addView(title);
					TextView desc = new TextView(getBaseContext());
					desc.setText(neighbours.get(i).description);
					desc.setTextSize(16);
					desc.setTextColor(getResources().getColor(R.color.ann_desc));
					linearLayout.addView(desc);

					View view2 = layoutInflater.inflate(R.layout.ngbr_info_bar, null);
					RelativeLayout relativeLayout = (RelativeLayout) view2.findViewById(R.id.ann_info_bar_RL);

					TextView tv_members=(TextView) view2.findViewById(R.id.tv_ngbr_info_members);
					TextView tv_ann=(TextView) view2.findViewById(R.id.tv_ngbr_info_announcements);
					TextView tv_locations=(TextView) view2.findViewById(R.id.tv_ngbr_info_locations);


					tv_members.setText("("+neighbours.get(i).membersNear+")");
					tv_ann.setText("("+neighbours.get(i).announcements+")");
					tv_locations.setText("("+neighbours.get(i).locationsNear+")");
					LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
					lp2.setMargins(10, 10, 10, 0);

					linearLayout.setLayoutParams(lp2);			


					LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
					rlp.setMargins(10, 0, 10, 0);
					relativeLayout.setLayoutParams(rlp);

					view.setPadding(15, 15, 15, 15);
					view2.setPadding(15, 10,15, 10);
					/*LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
			layoutParams.setMargins(10,10,10,0);
			linearLayout.setLayoutParams(layoutParams);*/
					outerLinearLayout.addView(linearLayout);
					outerLinearLayout.addView(relativeLayout);
				}
				//setContentView();
	}
}
