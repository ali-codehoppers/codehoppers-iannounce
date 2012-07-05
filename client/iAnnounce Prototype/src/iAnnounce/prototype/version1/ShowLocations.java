package iAnnounce.prototype.version1;

import java.text.DecimalFormat;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ShowLocations extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.show_locations);
		if(customTitleSupported){
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.titlebar);
			TextView tv_title= (TextView)findViewById(R.id.tv_titlebar);        	
			SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
			tv_title.setText((settings.getString("userName", "iAnnounce")).toUpperCase());	
		}
		
		Bundle bundle = this.getIntent().getExtras();
		final String neighbourId = bundle.getString("neighbourId");
		TextView allLocations = (TextView) findViewById(R.id.all_locations);
		allLocations.setOnClickListener(new TextView.OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(getApplicationContext(), AllLocations.class);
				Bundle b= new Bundle();
				b.putString("neighbourId", neighbourId);
				myIntent.putExtras(b);
				startActivity(myIntent);
				finish();
			}
		});
		ImageView buttonNewLocation = (ImageView) findViewById(R.id.location_new);
		buttonNewLocation.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//Do stuff here
				Intent myIntent = new Intent(getApplicationContext(), NeighbourLocations.class);
				Bundle b= new Bundle();
				b.putString("neighbourId", neighbourId);
				myIntent.putExtras(b);
				startActivity(myIntent);
				finish();

			}
		});
		//TableLayout locationTable = new TableLayout(this);
		ScrollView scrollView = (ScrollView) findViewById(R.id.location_scrollView);
		LinearLayout outerLinearLayout = new LinearLayout(this);
		scrollView.addView(outerLinearLayout);
		outerLinearLayout.setOrientation(LinearLayout.VERTICAL);
		HttpPostRequest http=new HttpPostRequest();
		SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
		http.getNearByLocations(settings.getString("sessionId","0"), neighbourId);
		MyXmlHandler myhandler=new MyXmlHandler();
		try {
			Xml.parse(http.xmlStringResponse, myhandler);
		} catch (SAXException e) {
			e.printStackTrace();
		}
		List<Location> locations = myhandler.obj_serverResp1.locations; 
		for(int i=0;i<locations.size();i++){
			/*TableRow locationsRow = new TableRow(this);
			TextView text = new TextView(this);
			
			text.setText(locations.get(i).name);
			text.setTextSize(25);
			text.setTextColor(R.color.announce_txt);
			locationsRow.addView(text);
			locationTable.addView(locationsRow);
			TableRow seperator = new TableRow(this);
			seperator.setBackgroundColor(Color.parseColor("#FFFFFF"));
			TextView textView = new TextView(this);
			textView.setWidth(200);
			textView.setHeight(5);
			seperator.addView(textView);
			locationTable.addView(seperator);*/
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = layoutInflater.inflate(R.layout.locationinfo, null);
			RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.loc_main);
			TextView name = (TextView) view.findViewById(R.id.loc_name);
			TextView dist = (TextView) view.findViewById(R.id.loc_dist); 
			TextView desc = (TextView) view.findViewById(R.id.loc_desc);
			
			DecimalFormat twoDForm = new DecimalFormat("#.##");
	        Double distance =  Double.valueOf(twoDForm.format(Double.parseDouble(locations.get(i).distance)));
	        
			name.setText(locations.get(i).name);
			dist.setText(distance+" km");
			desc.setText(locations.get(i).description);
			
			LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
			rlp.setMargins(10, 0, 10, 0);
			relativeLayout.setLayoutParams(rlp);
			
			outerLinearLayout.addView(relativeLayout);
		}
		

	}


}