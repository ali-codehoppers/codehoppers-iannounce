package iAnnounce.prototype.version1;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Xml;
import android.view.View;
import android.view.Window;
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
	
	private String mess;
	private final int ERROR_COMMUNICATION = 0;	
	private final int ERROR_SERVER = 1;
	private final int ERROR_SESSION = 2;
	private final int GET_PROFILE=3;

	private Handler msghandler;
	private ProgressDialog pdialog1;
	
	public void onCreate(Bundle savedInstanceState){
		
		pdialog1 = new ProgressDialog(MyProfile.this);
		pdialog1.setMessage("Loading. Please wait...");
		pdialog1.setIndeterminate(true);
		pdialog1.setCancelable(false);
		
		
		
		msghandler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				pdialog1.dismiss();
				
				switch(msg.what){
					
				case ERROR_COMMUNICATION:
					Toast.makeText(getApplicationContext(), (String)msg.obj, Toast.LENGTH_LONG).show();				
					break;

				case ERROR_SERVER:
					Toast.makeText(getApplicationContext(), (String)msg.obj, Toast.LENGTH_LONG).show();
					break;

				case ERROR_SESSION:
					Toast.makeText(getApplicationContext(), (String)msg.obj, Toast.LENGTH_LONG).show();
					Intent resultIntent = new Intent();
					setResult(Activity.RESULT_OK, resultIntent);
					finish();
					break;
				case GET_PROFILE:
					User userPro=(User)msg.obj;
					
					((TextView)findViewById(R.id.mypro_name)).setText(userPro.firstName+" "+userPro.lastName);
					((TextView)findViewById(R.id.mypro_age)).setText(userPro.age);

					((TextView)findViewById(R.id.mypro_numofpost)).setText(userPro.numofPost);
					if((userPro.gender).equalsIgnoreCase("0")){
						((TextView)findViewById(R.id.mypro_gender)).setText("Female");
					}
					else{
						((TextView)findViewById(R.id.mypro_gender)).setText("Male");
					}
					((TextView)findViewById(R.id.mypro_rating)).setText(userPro.averageRating);
					pdialog1.cancel();
					
					break;
				
				}
				super.handleMessage(msg);
			}
			
			
			
			
		};
		
		
		
		super.onCreate(savedInstanceState);		
	}
	
	
	private class ThreadMyProfile extends Thread{
		
		public String username; 

		@Override
		public void run() {
			
			HttpPostRequest ht=new HttpPostRequest();

			SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
					
			ht.getProfile(settings.getString("sessionId", "0"),username);

			if(ht.isError){
				Message msg1=new Message();
				msg1.what=ERROR_COMMUNICATION;
				msg1.obj=ht.xception;
				msghandler.sendMessage(msg1);
			}
			else{
				MyXmlHandler mh=new MyXmlHandler();
				try {
					Xml.parse(ht.xmlStringResponse, mh);
				} catch (SAXException e) {
					e.printStackTrace();
				}

				if(!mh.obj_serverResp1.responseCode.equalsIgnoreCase("0")){
					Message msg1=new Message();
					if(mh.obj_serverResp1.responseCode.equalsIgnoreCase("1")){
						msg1.what=ERROR_SESSION;
					}
					else{
						msg1.what=ERROR_SERVER;
					}
					msg1.obj=mh.obj_serverResp1.responseMessage;
					msghandler.sendMessage(msg1);
				}
				else{
					
					Message m1=new Message();
					m1.what=GET_PROFILE;
					m1.obj=mh.obj_serverResp1.userProfile;
					msghandler.sendMessage(m1);
					
				}
			}
			
			
			super.run();
		}
		
		
		
		
	}

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
						HttpPostRequest ht=new HttpPostRequest();
						ht.deleteProfile(settings.getString("sessionId", "0"), password);

						if(ht.isError){
							Toast.makeText(getApplicationContext(), ht.xception, Toast.LENGTH_LONG).show();							
						}
						else{
							MyXmlHandler myx=new MyXmlHandler();
							try {
								Xml.parse(ht.xmlStringResponse, myx);
							} catch (SAXException e) {
								e.printStackTrace();
							}

							if(!myx.obj_serverResp1.responseCode.equalsIgnoreCase("0")){
								Toast.makeText(getApplicationContext(), myx.obj_serverResp1.responseMessage, Toast.LENGTH_LONG).show();
								if(myx.obj_serverResp1.responseCode.equalsIgnoreCase("1")){
									finish();
								}

							}
							else{
								mess=myx.obj_serverResp1.delAccResponse;
								showDialog(1);
							}

						}

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
						Intent resultIntent = new Intent();
						setResult(Activity.RESULT_OK, resultIntent);
						finish();
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

		
		ThreadMyProfile th=new ThreadMyProfile();
		th.username=b.getString("username");
	th.start();
		
		
//		HttpPostRequest ht=new HttpPostRequest();
//
//		SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
//				
//		ht.getProfile(settings.getString("sessionId", "0"),username);
//
//		if(ht.isError){
//			Toast.makeText(getApplicationContext(), ht.xception, Toast.LENGTH_LONG).show();
//		}
//		else{
//			MyXmlHandler mh=new MyXmlHandler();
//			try {
//				Xml.parse(ht.xmlStringResponse, mh);
//			} catch (SAXException e) {
//				e.printStackTrace();
//			}
//
//			if(!mh.obj_serverResp1.responseCode.equalsIgnoreCase("0")){
//				Toast.makeText(getApplicationContext(), mh.obj_serverResp1.responseMessage, Toast.LENGTH_LONG).show();
//				if(mh.obj_serverResp1.responseCode.equalsIgnoreCase("1")){
//					Intent resultIntent = new Intent();
//					setResult(Activity.RESULT_OK, resultIntent);
//					finish();
//				}
//			}
//			else{
//				((TextView)findViewById(R.id.mypro_name)).setText(mh.obj_serverResp1.userProfile.firstName+" "+mh.obj_serverResp1.userProfile.lastName);
//				((TextView)findViewById(R.id.mypro_age)).setText(mh.obj_serverResp1.userProfile.age);
//
//				((TextView)findViewById(R.id.mypro_numofpost)).setText(mh.obj_serverResp1.userProfile.numofPost);
//				if((mh.obj_serverResp1.userProfile.gender).equalsIgnoreCase("0")){
//					((TextView)findViewById(R.id.mypro_gender)).setText("Female");
//				}
//				else{
//					((TextView)findViewById(R.id.mypro_gender)).setText("Male");
//				}
//				((TextView)findViewById(R.id.mypro_rating)).setText(mh.obj_serverResp1.userProfile.averageRating);
//				
//			}
//		}
	}
}
