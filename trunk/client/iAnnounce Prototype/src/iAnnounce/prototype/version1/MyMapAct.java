package iAnnounce.prototype.version1;



import java.util.List;

import android.content.Intent;
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

/**
 * For displaying the Google Maps along the the announcement and user's current location on the map
 * @author Awais
 * @version 1
 */

public class MyMapAct extends MapActivity{
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
		Drawable d = this.getResources().getDrawable(R.drawable.map_user);
		MapOverlayClass itemizedoverlay = new MapOverlayClass(d,MyMapAct.this);

		
		MapController mc=mv.getController();

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
			Toast.makeText(MyMapAct.this, "Unable to parse your Location", Toast.LENGTH_LONG).show();
		}

		if(longitu!=0 && latitu!=0){
			GeoPoint mypoint = new GeoPoint(latitu,longitu);
			OverlayItem overlayitem2 = new OverlayItem(mypoint,null, "This is your Position");
//			itemizedoverlay.setFocus(overlayitem2);
//			overlayitem2.setMarker(this.getResources().getDrawable(R.drawable.map_user));
			itemizedoverlay.addOverlay(overlayitem2);
//			mc.animateTo(mypoint);
			mc.setZoom(16);
			
		}
		
		
		MapOverlayClass announcementOverlayer = new MapOverlayClass(this.getResources().getDrawable(R.drawable.comment),MyMapAct.this);
		Intent i=this.getIntent();
		Bundle b=i.getExtras();
		String an_longi=b.getString("longitude");
		String an_lati=b.getString("latitude");
		String an_desc=b.getString("desc");
		
		
		
		Double an_la=Double.parseDouble(an_lati)*1e6;
		Double an_lo=Double.parseDouble(an_longi)*1e6;
		GeoPoint an_point = new GeoPoint(an_la.intValue(),an_lo.intValue());
		OverlayItem anOveritem = new OverlayItem(an_point,"Announcement", an_desc);
		
		mc.animateTo(an_point);
		
		announcementOverlayer.addOverlay(anOveritem);
		
		mapOverlays.add(itemizedoverlay);
		mapOverlays.add(announcementOverlayer);
		
		super.onResume();
	}
	
	
	

	//for wasae pc= 0IeOHCKQmyicBZgpYqianrl2TNrExd86nwqbVHg

	//for my pc= 0IeOHCKQmyid9qINCMmBH1YXm520PC_wZwuAtVA
	
	//for atom=  0IeOHCKQmyid2JkYzexd6P8vuyaZkSH9Qjx0Yjw

	//for my n5110   0p9ZLv0IImCJwXBeU_3hfm6Q79yzHhJH73W7_hQ


}
