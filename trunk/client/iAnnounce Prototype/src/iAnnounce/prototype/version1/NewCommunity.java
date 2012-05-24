package iAnnounce.prototype.version1;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class NewCommunity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		setContentView(R.layout.new_community);

		Button buttonReturn = (Button) findViewById(R.id.btn_return);
		buttonReturn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//Do stuff here
				finish();
			}
		});

		Button buttonCreate = (Button) findViewById(R.id.btn_create);
		buttonCreate.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//Do stuff here
				HttpPostRequest http=new HttpPostRequest();
				SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);

				String title = (((EditText)findViewById(R.id.communityNewName)).getText()).toString();
				String description = (((EditText)findViewById(R.id.communityNewDesc)).getText()).toString();
				CheckBox checkBox = (CheckBox)findViewById(R.id.ck1);
				boolean isPrivate = checkBox.isChecked();
				if(title.length()==0){
					Toast.makeText(getApplicationContext(),"Title cannot be empty.", Toast.LENGTH_LONG).show();
					return;
				}
				if(description.length()==0){
					Toast.makeText(getApplicationContext(),"Please Describe your community.", Toast.LENGTH_LONG).show();
					return;
				}

				http.createCommunity(settings.getString("sessionId","0"), title, description, isPrivate);
				if(http.isError){
					Toast.makeText(getApplicationContext(),http.xception, Toast.LENGTH_LONG).show();
				}
				MyXmlHandler myhandler=new MyXmlHandler();
				try {
					Xml.parse(http.xmlStringResponse, myhandler);
				} catch (SAXException e) {
					e.printStackTrace();
				}
				
				Intent myIntent = new Intent(getApplicationContext(), CommunityHome.class);
		    	Bundle b= new Bundle();
		    	b.putString("neighbourId", myhandler.obj_serverResp1.neigbhours.get(0).id);
		    	myIntent.putExtras(b);
                startActivity(myIntent);
			}
		});

	}
}
