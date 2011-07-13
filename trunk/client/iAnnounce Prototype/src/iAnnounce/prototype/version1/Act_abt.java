package iAnnounce.prototype.version1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class Act_abt extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		setContentView(R.layout.aboutus);
		
		ImageView imgV01=(ImageView)findViewById(R.id.abt_imgView01);
		
		imgV01.setImageDrawable(this.getResources().getDrawable(R.drawable.ch));
		
		super.onCreate(savedInstanceState);
	}

}
