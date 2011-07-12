package iAnnounce.prototype.version1;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This Activity is GUI of the posting/announcing an announment.
 * @author Awais
 *
 */

public class Announce extends Activity {
    private int CHAR_LIMIT=250;
    private TabActivity taba;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	  super.onCreate(savedInstanceState);
    	  setContentView(R.layout.announce);

    	 RadioGroup range1=(RadioGroup)findViewById(R.id.RG_range);
    	 range1.check(R.id.radio_loud);


    	 
    	 final EditText announcementText = (EditText) findViewById(R.id.et_announcement);
    	 final TextView announcementLength = (TextView) findViewById(R.id.announcement_length);
    	 announcementLength.setText(CHAR_LIMIT+"\t Characters left");
    	 announcementText.addTextChangedListener(new TextWatcher() {
    		 
    		    public void onTextChanged(CharSequence s, int start, int before,
    		            int count) {
    		    	int len=announcementText.getText().toString().length();
    		    	announcementLength.setText(CHAR_LIMIT-len+"\t Characters left");
    		 
    		    }

				public void afterTextChanged(Editable s) {
					int len=announcementText.getText().toString().length();
    		    	announcementLength.setText(CHAR_LIMIT-len+"\t Characters left");
					
				}

				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					int len=announcementText.getText().toString().length();
    		    	announcementLength.setText(CHAR_LIMIT-len+"\t Characters left");
					
				}
    		 
    		});
    	 taba=(TabActivity) getParent();
    	 Button bt=(Button)findViewById(R.id.bt_announce_submit);
    	 
    	 bt.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {

				
		    	 HttpPostRequest http=new HttpPostRequest();
		    	 SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
		    	 
		    	int id1;
				id1=(((RadioGroup)findViewById(R.id.RG_range)).getCheckedRadioButtonId());
				RadioButton range=(RadioButton)findViewById(id1);
				
				
				String announcement=(((EditText)findViewById(R.id.et_announcement)).getText()).toString();
				if(announcement.length()==0)
				{
					Toast.makeText(getApplicationContext(),"Announcmement cannot be empty.", Toast.LENGTH_LONG).show();
					return;
				}
				else if(announcement.length()>CHAR_LIMIT)
				{
					Toast.makeText(getApplicationContext(),"Announcmement cannot be greater than "+CHAR_LIMIT+" characters.", Toast.LENGTH_LONG).show();
					return;
				}
				
				
				http.PostAnnouncement(settings.getString("sessionId","0"), (range.getText()).toString(),announcement , settings.getString("Longitude","0"), settings.getString("Latitude","0"));
				
				if(http.isError){
					Toast.makeText(getApplicationContext(),http.xception, Toast.LENGTH_LONG).show();
				}
				else{
					MyXmlHandler myhandler=new MyXmlHandler();
					
					try {
						Xml.parse(http.xmlStringResponse, myhandler);
					} catch (SAXException e) {
						e.printStackTrace();
					}
					
					if(myhandler.obj_serverResp1.responseCode.equalsIgnoreCase("0")){
						Toast.makeText(getApplicationContext(),myhandler.obj_serverResp1.PostAnnouncementResponse, Toast.LENGTH_LONG).show();
						((EditText)findViewById(R.id.et_announcement)).setText("");
						TabHost th= taba.getTabHost();
						th.setCurrentTab(0);
					}
					else{
						Toast.makeText(getApplicationContext(),myhandler.obj_serverResp1.responseMessage, Toast.LENGTH_LONG).show();
						if(myhandler.obj_serverResp1.responseCode.equalsIgnoreCase("1")){
							finish();
						}
					}
				}	
			}
		});
    	 
    	 
    	 
    	}
        
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.appmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
		switch (item.getItemId()) {
		case R.id.menu_Profile:
			Intent in= new Intent(this, MyProfile.class);
						
			Bundle b=new Bundle();
			b.putString("username", settings.getString("userName", "0"));
			in.putExtras(b);
			startActivityForResult(in,3);
			return true;
		case R.id.menu_options:
			Intent intent2= new Intent(this, optionAct.class);
			startActivity(intent2);
			return true;
		case R.id.menu_Logout:
			HttpPostRequest ht=new HttpPostRequest();
			String x=ht.logout(settings.getString("sessionId", "0"));
			MyXmlHandler myhand=new MyXmlHandler();
			try {
				Xml.parse(x, myhand);
			} catch (SAXException e) {
				e.printStackTrace();
			}
			
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("sessionId", "0");
			editor.commit();
			Toast.makeText(getApplicationContext(),myhand.obj_serverResp1.logoutResponse, Toast.LENGTH_LONG).show();				
			finish();		
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==3){
			if (resultCode == Activity.RESULT_OK) {
				SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
				HttpPostRequest ht=new HttpPostRequest();
				String x=ht.logout(settings.getString("sessionId", "0"));
				MyXmlHandler myhand=new MyXmlHandler();
				try {
					Xml.parse(x, myhand);
				} catch (SAXException e) {
					e.printStackTrace();
				}
				
				SharedPreferences.Editor editor = settings.edit();
				editor.putString("sessionId", "0");
				editor.commit();
				Toast.makeText(getApplicationContext(),myhand.obj_serverResp1.logoutResponse, Toast.LENGTH_LONG).show();				
				finish();				               
            }
		}
		
	}
    
}