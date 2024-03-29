package iAnnounce.prototype.version1;


import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity for showing and saving the settings of the application
 * @author Awais
 *@version 1
 */


public class optionAct extends Activity{



	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.options);
		
		
        
        if(customTitleSupported){        	
        	getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.titlebar);
        	TextView tv_title= (TextView)findViewById(R.id.tv_titlebar);        	        	
        	tv_title.setText("Options");
        	
        }
		
		SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
		final SharedPreferences.Editor editor = settings.edit();

		final EditText et1=(EditText)findViewById(R.id.et_LocFreq);
		et1.setText(settings.getString("timeInterval","1"));
		
		et1.setInputType(InputType.TYPE_CLASS_NUMBER);

		final EditText et_dist=(EditText)findViewById(R.id.et_distance);
		et_dist.setText(settings.getString("distanceMeter","500"));
		
		et_dist.setInputType(InputType.TYPE_CLASS_NUMBER);
		
		

		Button bt_save= (Button)findViewById(R.id.bt_option_save);
		Button bt_cancel= (Button)findViewById(R.id.bt_option_cancel);
		
		final EditText et_dtime=(EditText)findViewById(R.id.et_distanceTime);
		
		et_dtime.setText(settings.getString("distanceTime","1"));
		et_dtime.setInputType(InputType.TYPE_CLASS_NUMBER);
		
		
		

		bt_save.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				try {
					Integer.parseInt(et1.getText().toString());
					Integer.parseInt(et_dist.getText().toString());
					Integer.parseInt(et_dtime.getText().toString());
					editor.putString("timeInterval", (et1.getText()).toString());
					editor.putString("distanceMeter", (et_dist.getText()).toString());
					editor.putString("distanceTime", (et_dtime.getText()).toString());
					editor.commit();
					Toast.makeText(getApplicationContext(), "Setting Saved", Toast.LENGTH_LONG).show();
					finish();
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(),"Please Enter Integer", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		
		bt_cancel.setOnClickListener(new OnClickListener() {			
			public void onClick(View arg0) {				
				finish();	
			}
		});

	}
	
	private Messenger mService;
	
	/**
	 * Messenger for this class
	 */
	
final Messenger mMessenger = new Messenger(new IncomingHandler());
	
	class IncomingHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case iAnnounceService.RECIEVE_ANNOUNCEMENTS:
				
				Toast.makeText(getBaseContext(), (String)msg.obj, Toast.LENGTH_LONG).show();
				
				break;
			
			case iAnnounceService.RECIEVE_TASK_RESPONSE:
				Toast.makeText(getBaseContext(), (String)msg.obj, Toast.LENGTH_LONG).show();
				break;
				
			default:
				
			}
			super.handleMessage(msg);
		}
	}
    private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className,
				IBinder service) {
			mService = new Messenger(service);


			
			Message m=Message.obtain(null,iAnnounceService.STOP_TIMERTASK);
			m.replyTo = mMessenger;
	
			try {
				mService.send(m);
			} catch (RemoteException e) {			
				e.printStackTrace();
			}
			
			
		}

		public void onServiceDisconnected(ComponentName className) {
			
			mService = null;				
			Toast.makeText(getBaseContext(),"Disconnected from Service",Toast.LENGTH_SHORT).show();

			
		}
	};
	
	
}
