package iAnnounce.prototype.version1;

import java.util.List;

import org.xml.sax.SAXException;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Xml;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class LocationOnMap extends MapActivity {
	private List<Overlay> mapOverlays;


	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	protected void onCreate(Bundle icicle) {

		super.onCreate(icicle);

		setContentView(R.layout.mapsview);	
		MapView mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
	}

	@Override
	protected void onResume() {
		MapView mv=(MapView)findViewById(R.id.mapview);
		mapOverlays = mv.getOverlays();
		Drawable d = this.getResources().getDrawable(R.drawable.marker_pink);
		MapOverlayClass itemizedoverlay = new MapOverlayClass(d,LocationOnMap.this);
		Bundle bundle = this.getIntent().getExtras();
		final String locationId = bundle.getString("locationId");
		HttpPostRequest http=new HttpPostRequest();
		SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
		http.getLocationById(settings.getString("sessionId","0"), locationId);
		MyXmlHandler myhandler=new MyXmlHandler();
		try {
			Xml.parse(http.xmlStringResponse, myhandler);
		} catch (SAXException e) {
			e.printStackTrace();
		}
		MapController mc=mv.getController();
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
			Toast.makeText(LocationOnMap.this, "Unable to parse your Location", Toast.LENGTH_LONG).show();
		}

		if(longitu!=0 && latitu!=0){
			GeoPoint mypoint = new GeoPoint(latitu,longitu);
			OverlayItem overlayitem2 = new OverlayItem(mypoint,null, "This is your Position");
			itemizedoverlay.addOverlay(overlayitem2);
			mc.setZoom(16);

		}
		mapOverlays.add(itemizedoverlay);
		List<Location> locations = myhandler.obj_serverResp1.locations; 
		for(int i=0;i<locations.size();i++){
			MapOverlayClass announcementOverlayer = new MapOverlayClass(this.getResources().getDrawable(R.drawable.marker_blue),LocationOnMap.this);
			Double an_la=Double.parseDouble(locations.get(i).latitude)*1e6;
			Double an_lo=Double.parseDouble(locations.get(i).longitude)*1e6;
			GeoPoint an_point = new GeoPoint(an_la.intValue(),an_lo.intValue());
			OverlayItem anOveritem = new OverlayItem(an_point,locations.get(i).name, locations.get(i).description);
			//mc.animateTo(an_point);

			announcementOverlayer.addOverlay(anOveritem);			
			mapOverlays.add(announcementOverlayer);
		}
		super.onResume();

	}


}
