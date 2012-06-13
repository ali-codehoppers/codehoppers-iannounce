package iAnnounce.prototype.version1;

import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class NeighbourLocations extends MapActivity {
	private List<Overlay> mapOverlays;

	@Override
	protected boolean isRouteDisplayed(){
		return false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.location);
		
		if(customTitleSupported){
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.titlebar);
			TextView tv_title= (TextView)findViewById(R.id.tv_titlebar);        	
			SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
			tv_title.setText((settings.getString("userName", "iAnnounce")).toUpperCase());	
		}
		MapView mapView = (MapView) findViewById(R.id.mymap);
		mapView.setBuiltInZoomControls(true);

		Bundle bundle = this.getIntent().getExtras();
		final String neighbourId = bundle.getString("neighbourId");
		
		mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.location);
		MapOverlayClass mapoverlay = new MapOverlayClass(drawable, this);
		SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
		String longi=settings.getString("Longitude", "0");
		String lati=settings.getString("Latitude", "0");
		int latitu=0;
		int longitu=0;
		try {
			Double la=Double.parseDouble(lati)*1e6;
			Double lo=Double.parseDouble(longi)*1e6;
			latitu=la.intValue();
			longitu=lo.intValue();
		} catch (Exception e) {
			Toast.makeText(this, "Unable to parse your Location", Toast.LENGTH_LONG).show();
		}
		GeoPoint point = new GeoPoint(latitu,longitu);
		OverlayItem overlayitem = new OverlayItem(point, "You", "You current location");

		mapoverlay.addOverlay(overlayitem);
		mapOverlays.add(mapoverlay);
		MapController mc=mapView.getController();
		mc.animateTo(point);
		mc.setZoom(16);

		TextView latText = (TextView) findViewById(R.id.text_latitude);
		latText.setText(lati);
		TextView longText = (TextView) findViewById(R.id.text_longitude);
		longText.setText(longi);

		Button buttonAddLocation = (Button) findViewById(R.id.location_add);
		buttonAddLocation.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//Do stuff here
				String lati = ((TextView) findViewById(R.id.text_latitude)).getText().toString();
				String longi = ((TextView) findViewById(R.id.text_longitude)).getText().toString();
				String name = ((TextView) findViewById(R.id.location_name)).getText().toString();
				String desc = ((TextView) findViewById(R.id.location_desc)).getText().toString();
				HttpPostRequest http=new HttpPostRequest();
				SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
				http.addLocation(settings.getString("sessionId","0"), name, desc, neighbourId, lati, longi);
				Intent myIntent = new Intent(getApplicationContext(), ShowLocations.class);
				Bundle b= new Bundle();
				b.putString("neighbourId", neighbourId);
				myIntent.putExtras(b);
				startActivity(myIntent);
			}
		});

	}

}