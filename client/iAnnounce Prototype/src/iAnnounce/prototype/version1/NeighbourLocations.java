package iAnnounce.prototype.version1;

import java.util.List;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
	    setContentView(R.layout.location);
	    MapView mapView = (MapView) findViewById(R.id.mymap);
	    mapView.setBuiltInZoomControls(true);
	    
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
	    
	    }
	
}