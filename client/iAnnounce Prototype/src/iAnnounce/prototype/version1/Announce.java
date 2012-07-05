package iAnnounce.prototype.version1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
	private Map<String, Integer> neighbourMap = new HashMap<String, Integer>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.announce);

		RadioGroup range1=(RadioGroup)findViewById(R.id.RG_range);
		range1.check(R.id.radio_loud);
		HttpPostRequest http=new HttpPostRequest();
		SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
		http.getNeighboursByPerson(settings.getString("sessionId","0"));
		MyXmlHandler myhandler=new MyXmlHandler();
		try {
			Xml.parse(http.xmlStringResponse, myhandler);
		} catch (SAXException e) {
			e.printStackTrace();
		}
		List<Neigbhours> neighbours = myhandler.obj_serverResp1.neigbhours;
		String neighboursArray[] = new String[neighbours.size()+1];
		neighboursArray[0] = "Open";
		neighbourMap.put("Open", 0);
		String neighbourId = "0";
		int selectedPosition = 0;
		Bundle bundle = this.getIntent().getExtras();
		if(bundle != null && bundle.getString("neighbourId") != null){
			neighbourId = bundle.getString("neighbourId");
		}else{
			selectedPosition=0;
		}
		
		for(int i=0;i<neighbours.size();i++){
			neighboursArray[i+1]=neighbours.get(i).title;
			neighbourMap.put(neighbours.get(i).title, Integer.parseInt(neighbours.get(i).id));
			if(neighbourId.equals(neighbours.get(i).id)){
				selectedPosition = i+1;
			}
		}
		Spinner spinner = (Spinner) findViewById(R.id.spinner);
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, neighboursArray);
		spinner.setAdapter(spinnerArrayAdapter);
		spinner.setSelection(selectedPosition);
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
				Spinner spinner = (Spinner) findViewById(R.id.spinner);
				String selected = spinner.getSelectedItem().toString();
				int neighbourId = neighbourMap.get(selected);
				int duration = 0;
				CheckBox lifeCheckBox = (CheckBox) findViewById(R.id.life_check_box);
				EditText lifeSpan = (EditText) findViewById(R.id.life_span);
				if (lifeCheckBox.isChecked()) {
					try{
						duration = Integer.parseInt(lifeSpan.getText().toString());
					}catch(Exception e)
					{
						Toast.makeText(getApplicationContext(),"Enter a valid number for life span", Toast.LENGTH_LONG).show();
						return;
					}
				}
				http.PostAnnouncement(settings.getString("sessionId","0"), (range.getText()).toString(),announcement,duration ,""+neighbourId, settings.getString("Longitude","0"), settings.getString("Latitude","0"));

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

		CheckBox lifeCheckBox = (CheckBox) findViewById(R.id.life_check_box);
		lifeCheckBox.setOnClickListener(new CheckBox.OnClickListener() {
			public void onClick(View v) {
				TextView lifeText1 = (TextView) findViewById(R.id.life_text1);
				TextView lifeText2 = (TextView) findViewById(R.id.life_text2);
				EditText lifeSpan = (EditText) findViewById(R.id.life_span);
				if (((CheckBox) v).isChecked()) {
					//Toast.makeText(Announce.this,"Checked", Toast.LENGTH_LONG).show();
					((CheckBox) v).setTextColor(getResources().getColor(R.color.announce_labels));
					lifeText1.setTextColor(getResources().getColor(R.color.announce_labels));
					lifeText2.setTextColor(getResources().getColor(R.color.announce_labels));
					lifeSpan.setFocusable(true);
					lifeSpan.setFocusableInTouchMode(true);
				}else
				{
//					Toast.makeText(Announce.this,"Unchecked", Toast.LENGTH_LONG).show();
					((CheckBox) v).setTextColor(getResources().getColor(R.color.announce_labels_fade));
					lifeText1.setTextColor(getResources().getColor(R.color.announce_labels_fade));
					lifeText2.setTextColor(getResources().getColor(R.color.announce_labels_fade));
					lifeSpan.setFocusable(false);
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
			ht.logout(settings.getString("sessionId", "0"));

			if(ht.isError){
				Toast.makeText(getApplicationContext(), ht.xception, Toast.LENGTH_LONG).show();			
			}
			else{
				MyXmlHandler myhand=new MyXmlHandler();
				try {
					Xml.parse(ht.xmlStringResponse, myhand);
				} catch (SAXException e) {
					e.printStackTrace();
				}

				if(!myhand.obj_serverResp1.responseCode.equalsIgnoreCase("0")){
					Toast.makeText(getApplicationContext(), ht.xception, Toast.LENGTH_LONG).show();					
				}
				else{
					SharedPreferences.Editor editor = settings.edit();
					editor.putString("sessionId", "0");
					editor.commit();
					Toast.makeText(getApplicationContext(),myhand.obj_serverResp1.logoutResponse, Toast.LENGTH_LONG).show();				

				}
				finish();				
			}

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
				ht.logout(settings.getString("sessionId", "0"));
				MyXmlHandler myhand=new MyXmlHandler();
				try {
					Xml.parse(ht.xmlStringResponse, myhand);
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