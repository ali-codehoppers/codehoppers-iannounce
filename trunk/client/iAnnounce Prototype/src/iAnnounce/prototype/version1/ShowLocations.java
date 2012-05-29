package iAnnounce.prototype.version1;

import java.util.List;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ShowLocations extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_locations);

		Bundle bundle = this.getIntent().getExtras();
		final String neighbourId = bundle.getString("neighbourId");

		Button buttonNewLocation = (Button) findViewById(R.id.location_new);
		buttonNewLocation.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//Do stuff here
				Intent myIntent = new Intent(getApplicationContext(), NeighbourLocations.class);
				Bundle b= new Bundle();
				b.putString("neighbourId", neighbourId);
				myIntent.putExtras(b);
				startActivity(myIntent);

			}
		});
		TableLayout locationTable = new TableLayout(this);
		
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
			TableRow locationsRow = new TableRow(this);
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
			locationTable.addView(seperator);
		}
		
		ScrollView scrollView = (ScrollView) findViewById(R.id.location_scrollView);
		scrollView.addView(locationTable);
	}


}