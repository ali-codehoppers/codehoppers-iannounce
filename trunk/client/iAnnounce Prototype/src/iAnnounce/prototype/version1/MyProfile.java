package iAnnounce.prototype.version1;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Userinterface for MyProfile usecase
 * @author Awais
 *@version 1
 */
public class MyProfile extends Activity{
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		/*setContentView(R.layout.view_profile);


		Button editButton = (Button) findViewById(R.id.EditProfileButton);
		editButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(), EditProfile.class);
				startActivityForResult(myIntent,3);
			}
		});

		Button bt_del=(Button)findViewById(R.id.DeleteProfileButton);
		bt_del.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				showDialog(0);
			}
		});

		Bundle b=this.getIntent().getExtras();
		String username=b.getString("username");
		HttpPostRequest ht=new HttpPostRequest();

		SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);

		String resp=ht.getProfile(settings.getString("sessionId", "0"),username);
		MyXmlHandler mh=new MyXmlHandler();
		try {
			Xml.parse(resp, mh);
		} catch (SAXException e) {
			e.printStackTrace();
		}
		if(!mh.obj_serverResp1.forceLogin){
			((TextView)findViewById(R.id.mypro_name)).setText(mh.obj_serverResp1.userProfile.firstName+" "+mh.obj_serverResp1.userProfile.lastName);
			((TextView)findViewById(R.id.mypro_age)).setText(mh.obj_serverResp1.userProfile.age);
			((TextView)findViewById(R.id.mypro_rating)).setText(mh.obj_serverResp1.userProfile.averageRating);
			if((mh.obj_serverResp1.userProfile.gender).equalsIgnoreCase("0")){
				((TextView)findViewById(R.id.mypro_gender)).setText("Female");
			}
			else{
				((TextView)findViewById(R.id.mypro_gender)).setText("Male");
			}

			((TextView)findViewById(R.id.mypro_numofpost)).setText(mh.obj_serverResp1.userProfile.numofPost);

		}
		else{
			Intent resultIntent = new Intent();
			setResult(Activity.RESULT_OK, resultIntent);
			finish();
		}*/
	}
	private String mess;

	@Override
	protected Dialog onCreateDialog(int id) {

		//		Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG);

		AlertDialog.Builder b= new AlertDialog.Builder(this);
		switch(id){
		case 0:
			final EditText input = new EditText(this);

			b.setMessage("To delete account enter password");
			b.setTitle("Deleting Account?");
			input.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());


			b.setView(input);
			b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
					String password = input.getText().toString().trim();
					if(password.length()<6 || password.length()>30 || password.contains("/"))
						Toast.makeText(getApplicationContext(),"Invalid password, must be between 6 to 30 characters", Toast.LENGTH_LONG).show();
					
					else{
						HttpPostRequest h=new HttpPostRequest();
						String resp=h.deleteProfile(settings.getString("sessionId", "0"), password);
						MyXmlHandler myx=new MyXmlHandler();
						try {
						Xml.parse(resp, myx);
						} catch (SAXException e) {
						e.printStackTrace();
						}
						mess=myx.obj_serverResp1.delAccResponse;

						showDialog(1);
					}
				}
			});
			b.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.cancel();
				}
			});
			b.show();
			break;
		case 1:
			if(mess.equalsIgnoreCase("true")){
				b.setMessage("Your account is deleted");				  
				b.setCancelable(true);
				b.setNeutralButton("Ok",  
						new DialogInterface.OnClickListener() {  
					public void onClick(DialogInterface dialog, int whichButton){
						Intent resultIntent = new Intent();
						setResult(Activity.RESULT_OK, resultIntent);
						finish();
					}  

				});
			}
			else{
				
				b.setMessage(mess);				  
				b.setCancelable(false)  ;
				b.setNeutralButton("Ok",  
						new DialogInterface.OnClickListener() {  
					public void onClick(DialogInterface dialog, int whichButton){
						
					}  

				});
				
			}
			b.show();
			break;
		default:
			break;
		}
		return super.onCreateDialog(id);
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		setContentView(R.layout.view_profile);
		Button editButton = (Button) findViewById(R.id.EditProfileButton);
		editButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(), EditProfile.class);
				startActivityForResult(myIntent,3);
			}
		});

		Button bt_del=(Button)findViewById(R.id.DeleteProfileButton);
		bt_del.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				showDialog(0);
			}
		});

		Bundle b=this.getIntent().getExtras();
		String username=b.getString("username");
		HttpPostRequest ht=new HttpPostRequest();

		SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);

		String resp=ht.getProfile(settings.getString("sessionId", "0"),username);
		MyXmlHandler mh=new MyXmlHandler();
		try {
			Xml.parse(resp, mh);
		} catch (SAXException e) {
			e.printStackTrace();
		}
		if(!mh.obj_serverResp1.forceLogin){
			((TextView)findViewById(R.id.mypro_name)).setText(mh.obj_serverResp1.userProfile.firstName+" "+mh.obj_serverResp1.userProfile.lastName);
			((TextView)findViewById(R.id.mypro_age)).setText(mh.obj_serverResp1.userProfile.age);
			((TextView)findViewById(R.id.mypro_rating)).setText(mh.obj_serverResp1.userProfile.averageRating);
			if((mh.obj_serverResp1.userProfile.gender).equalsIgnoreCase("0")){
				((TextView)findViewById(R.id.mypro_gender)).setText("Female");
			}
			else{
				((TextView)findViewById(R.id.mypro_gender)).setText("Male");
			}

			((TextView)findViewById(R.id.mypro_numofpost)).setText(mh.obj_serverResp1.userProfile.numofPost);

		}
		else{
			Intent resultIntent = new Intent();
			setResult(Activity.RESULT_OK, resultIntent);
			finish();
		}
	}

}
