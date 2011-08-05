package iAnnounce.prototype.version1;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class AnnouncementDetail extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.ann_detail);
		
		 if(customTitleSupported){
	        	
	        	getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.titlebar);
	        	TextView tv_title= (TextView)findViewById(R.id.tv_titlebar);        	
	        	SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
	        	tv_title.setText((settings.getString("userName", "iAnnounce")).toUpperCase());
	        }
		
		
		super.onCreate(savedInstanceState);
	}
	
}
