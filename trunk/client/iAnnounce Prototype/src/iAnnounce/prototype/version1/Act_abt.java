package iAnnounce.prototype.version1;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
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
