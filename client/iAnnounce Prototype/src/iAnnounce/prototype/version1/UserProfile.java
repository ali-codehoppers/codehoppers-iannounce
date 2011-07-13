package iAnnounce.prototype.version1;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Xml;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Activity for the displaying the profile of anyother user.
 * @author Awais
 *@version 1 
 */

public class UserProfile extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.user_profile);
		super.onCreate(savedInstanceState);

		Bundle b=this.getIntent().getExtras();
		String username=b.getString("username");
		HttpPostRequest ht=new HttpPostRequest();

		SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);

		ht.getProfile(settings.getString("sessionId", "0"),username);
		
		
		if(ht.isError){
			Toast.makeText(getApplicationContext(), ht.xception, Toast.LENGTH_LONG).show();
		}
		else{
			MyXmlHandler mh=new MyXmlHandler();
			try {
				Xml.parse(ht.xmlStringResponse, mh);
			} catch (SAXException e) {
				e.printStackTrace();
			}

			if(!mh.obj_serverResp1.responseCode.equalsIgnoreCase("0")){
				Toast.makeText(getApplicationContext(), mh.obj_serverResp1.responseMessage, Toast.LENGTH_LONG).show();
				if(mh.obj_serverResp1.responseCode.equalsIgnoreCase("1")){
					Intent resultIntent = new Intent();
					setResult(Activity.RESULT_OK, resultIntent);
					finish();
				}
			}
			else{
				((TextView)findViewById(R.id.pro_name)).setText(mh.obj_serverResp1.userProfile.firstName+" "+mh.obj_serverResp1.userProfile.lastName);
				((TextView)findViewById(R.id.pro_age)).setText(mh.obj_serverResp1.userProfile.age);
				((TextView)findViewById(R.id.pro_avg_rate)).setText(mh.obj_serverResp1.userProfile.averageRating);
				if((mh.obj_serverResp1.userProfile.gender).equalsIgnoreCase("0")){
					((TextView)findViewById(R.id.pro_gender)).setText("Female");
				}
				else{
					((TextView)findViewById(R.id.pro_gender)).setText("Male");
				}

				((TextView)findViewById(R.id.pro_noofposts)).setText(mh.obj_serverResp1.userProfile.numofPost);
				((TextView)findViewById(R.id.pro_uname)).setText(username);
			}
		}

	}

}