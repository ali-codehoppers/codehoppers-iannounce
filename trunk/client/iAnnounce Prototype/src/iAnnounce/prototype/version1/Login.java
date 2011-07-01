package iAnnounce.prototype.version1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**
 * First Activity when application is launched for displaying the login screen
 * @author Awais
 *@version 1
 */

public class Login extends Activity {
	
	private CheckBox rem;
	private ProgressDialog pdialog1;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		
		SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
		final SharedPreferences.Editor editor = settings.edit();
		
		rem=(CheckBox)findViewById(R.id.mainCheckboxRememberme);
		String doremember=settings.getString("remember", "-1");
		
		if(doremember.equalsIgnoreCase("1")){
			rem.setChecked(true);
			String us=settings.getString("userName", "-1");
			String pas=settings.getString("passWord", "-1");
			if(!((us.equalsIgnoreCase("-1")) || (pas.equalsIgnoreCase("-1")))){
				((EditText)findViewById(R.id.mainEdittextLogin)).setText(us);
				((EditText)findViewById(R.id.mainEdittextPassword)).setText(pas);
			}
			
		}




		final Button searchButton = (Button) findViewById(R.id.mainButtonLogin);
		searchButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				
				User user1=new User();
				user1.userName=(((EditText)findViewById(R.id.mainEdittextLogin)).getText()).toString();
				if(user1.userName.length()<1 || user1.userName.length()>15 || user1.userName.contains(" ") || user1.userName.contains("/")){
					Toast.makeText(getApplicationContext(),"Invalid username, must be between 5 to 15 characters and cannot contain blanck space", Toast.LENGTH_LONG).show();
					((EditText)findViewById(R.id.mainEdittextLogin)).requestFocus();
					
					return;
				}
				user1.setPassword((((EditText)findViewById(R.id.mainEdittextPassword)).getText()).toString());
				if(user1.getPassword().length()<6 || user1.getPassword().length()>30 || user1.getPassword().contains("/")){
					Toast.makeText(getApplicationContext(),"Invalid password, must be between 6 to 30 characters", Toast.LENGTH_LONG).show();
					((EditText)findViewById(R.id.mainEdittextPassword)).requestFocus();
					return;
				}

				ConnectivityManager manager = (ConnectivityManager)getSystemService(Login.CONNECTIVITY_SERVICE);
				Boolean isMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
				Boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
				
				if(!(isMobile || isWifi)){
					showDialog(3);
				}
				else{
					pdialog1 = ProgressDialog.show(Login.this,"", 
							"Loading. Please wait...", true);
					String s=user1.Login();
					if(user1.getSessionId()==null){
						Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
					}
					else{
						
						Intent myIntent = new Intent(v.getContext(), HomePage.class);
						String context = Context.LOCATION_SERVICE;
						LocationManager locationManager;
						locationManager = (LocationManager)getSystemService(context);
						Criteria criteria = new Criteria();
						criteria.setAccuracy(Criteria.ACCURACY_FINE);
						criteria.setAltitudeRequired(false);
						criteria.setBearingRequired(false);
						criteria.setCostAllowed(true);
						criteria.setPowerRequirement(Criteria.POWER_LOW);
						String provider = locationManager.getBestProvider(criteria, true);
						if(provider==null){
							showDialog(2);
						}else{
							
							if(rem.isChecked()){
								editor.putString("remember", "1");
								editor.putString("passWord", user1.getPassword());								
							}
							else{
								editor.putString("remember", "0");
								editor.putString("passWord","-1");	
							}
							editor.putString("sessionId", s);
							editor.putString("userName", user1.userName);
							editor.commit();
							startActivity(myIntent);
						}

					}
					pdialog1.cancel();
				}

			}// of onclick function
		});
		final TextView registerTextView = (TextView) findViewById(R.id.mainTextviewRegister);
		registerTextView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(), Registeration.class);
				startActivity(myIntent);
			}
		});









		final TextView forgoTextView = (TextView) findViewById(R.id.forgo);
		forgoTextView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {		
				showDialog(0);
			}
		});


	}
	String mess;

	@Override
	protected Dialog onCreateDialog(int id) {

		AlertDialog.Builder b= new AlertDialog.Builder(Login.this);
		switch(id){
		case 0:
			final EditText input = new EditText(this);

			b.setMessage("Enter username");
			b.setTitle("Forgot password?");


			b.setView(input);
			b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					String value = input.getText().toString().trim();
					if(value.length()<5 || value.length()>15 || value.contains(" ") || value.contains("/")){
						Toast.makeText(getApplicationContext(),"Invalid username, must be between 5 to 15 characters and cannot contain blanck space", Toast.LENGTH_LONG).show();
					}
					else{
						User user1=new User();
						user1.userName=value;
						mess=user1.forgetPassword();
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
			b.setMessage(mess);  
			b.setTitle("Notification");  
			b.setCancelable(true)  ;
			b.setNeutralButton(android.R.string.ok,  
					new DialogInterface.OnClickListener() {  
				public void onClick(DialogInterface dialog, int whichButton){}  
			});
			b.show();
			break;

		case 2:			  
			b.setMessage("Please enable your GPS");  
			b.setTitle("Notification");  
			b.setCancelable(true);

			b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
					startActivity(intent);
				}
			});
			b.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {			
					finish();
					dialog.cancel();

				}
			});


			b.show();
			break;

		case 3:			  
			b.setMessage("Please connect to internet");  
			b.setTitle("Notification");  
			b.setCancelable(true);

			b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
					startActivity(intent);
				}
			});
			b.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {			
					finish();
					dialog.cancel();

				}
			});


			b.show();
			break;


		default:
			break;		
		}
		return super.onCreateDialog(id);
	}
	@Override
	protected void onStop() {
		//		String ns = Context.NOTIFICATION_SERVICE;
		//		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		//		mNotificationManager.cancel(123);
		//		getApplicationContext().stopService(new Intent(getApplicationContext(), iAnnounceService.class));	

		super.onStop();
	}
	@Override
	protected void onDestroy() {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		mNotificationManager.cancel(123);
		getApplicationContext().stopService(new Intent(getApplicationContext(), iAnnounceService.class));

		super.onDestroy();
	}


}
