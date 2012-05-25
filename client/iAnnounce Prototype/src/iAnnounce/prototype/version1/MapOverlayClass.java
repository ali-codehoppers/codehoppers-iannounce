package iAnnounce.prototype.version1;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

/**
 * This class is user for creating the overlays on the map and it extends ItemizedOverlay class supported by the external library of googles map
 * @author Awais
 *@version 1
 */
@SuppressWarnings("rawtypes")
public class MapOverlayClass extends ItemizedOverlay {
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;

	public MapOverlayClass(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
		
	}

	@Override
	protected OverlayItem createItem(int i) {
		 return mOverlays.get(i);		
	}

	@Override
	public int size() {		
		return mOverlays.size();
	}
	
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}
	
	public MapOverlayClass(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));  
		mContext = context;		  
		}
	
	@Override
	protected boolean onTap(int index) {
	  OverlayItem item = mOverlays.get(index);
	  AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
	  dialog.setTitle(item.getTitle());
	  dialog.setMessage(item.getSnippet());
	  dialog.show();
	  return true;
	}

}
