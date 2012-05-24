package iAnnounce.prototype.version1;

import org.xml.sax.SAXException;

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
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Xml;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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
	private SharedPreferences settings;
	private Handler msgHandler;


	private final int ERROR_COMMUNICATION = 0;
	private final int INVALID_USERNAME = 1;
	private final int INVALID_PASSWORD = 2;
	private final int ERROR_SERVER = 3;
	private final int ERROR_CONNECTIVITY=4; //wifi is off :O
	private final int ERROR_GPS=5;







	private class loginThread extends Thread{
		//		private volatile Thread blinker;
		//		
		//		@Override
		//		public void stop(){
		//			blinker=null;
		//		}

		@Override
		public void run() {
			SharedPreferences.Editor editor = settings.edit();
			Message msg=new Message();

			User user1=new User();
			user1.userName=(((EditText)findViewById(R.id.mainEdittextLogin)).getText()).toString();
			if(user1.userName.length()<1 || user1.userName.length()>15 || user1.userName.contains(" ") || user1.userName.contains("/")){
				msg.what=INVALID_USERNAME;				
				msgHandler.sendMessage(msg);				
				return;
			}
			user1.setPassword((((EditText)findViewById(R.id.mainEdittextPassword)).getText()).toString());
			if(user1.getPassword().length()<6 || user1.getPassword().length()>30 || user1.getPassword().contains("/")){
				msg.what=INVALID_PASSWORD;				
				msgHandler.sendMessage(msg);
				return;
			}

			ConnectivityManager manager = (ConnectivityManager)getSystemService(Login.CONNECTIVITY_SERVICE);
			Boolean isMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
			Boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

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



			if(!(isMobile || isWifi)){

				msg.what=ERROR_CONNECTIVITY;
				msgHandler.sendMessage(msg);

			}
			else{
				HttpPostRequest ht1=new HttpPostRequest();
				ht1.login(user1.userName,user1.getPassword());					
				if(!ht1.isError){
					MyXmlHandler myhandler=new MyXmlHandler();
					try {
						Xml.parse(ht1.xmlStringResponse, myhandler);
					} catch (SAXException e) {
						e.printStackTrace();
					}												
					if(myhandler.obj_serverResp1.responseCode.equalsIgnoreCase("0")){


						Intent myIntent = new Intent(getApplicationContext(), HomePage.class);
						if(provider==null){
							msg.what=ERROR_GPS;
							msgHandler.sendMessage(msg);
						}else{
							if(rem.isChecked()){
								editor.putString("remember", "1");
								editor.putString("passWord", user1.getPassword());								
							}
							else{
								editor.putString("remember", "0");
								editor.putString("passWord","-1");	
							}
							editor.putString("sessionId", myhandler.obj_serverResp1.session_id);
							editor.putString("userName", user1.userName);
							editor.commit();		
							pdialog1.cancel();
							startActivity(myIntent);
						}
					}
					else{
						//toast error message
						msg.what=ERROR_SERVER;
						msg.obj=(Object)myhandler.obj_serverResp1.responseMessage;
						msgHandler.sendMessage(msg);
					}
				}
				else{
					//communication error.

					msg.what=ERROR_COMMUNICATION;
					msg.obj=(Object)ht1.xception;
					msgHandler.sendMessage(msg);
				}
			}//else of is mobilenetwork or wifi :D

			super.run();
		}

	}




	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.main);

		if(customTitleSupported){

			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.titlebar);

			TextView tv_title= (TextView)findViewById(R.id.tv_titlebar);
			tv_title.setText("iAnnounce");
		}


		settings = getSharedPreferences("iAnnounceVars", 0);
		//		final SharedPreferences.Editor editor = settings.edit();

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

		msgHandler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				pdialog1.cancel();				
				switch (msg.what){
				case ERROR_COMMUNICATION:					
					Toast.makeText(getApplicationContext(),(String) msg.obj, Toast.LENGTH_LONG).show();
					break;
				case INVALID_USERNAME:
					Toast.makeText(getApplicationContext(),"Invalid username, must be between 5 to 15 characters and cannot contain blanck space", Toast.LENGTH_LONG).show();
					((EditText)findViewById(R.id.mainEdittextLogin)).requestFocus();					
					break;
				case INVALID_PASSWORD:
					Toast.makeText(getApplicationContext(),"Invalid password, must be between 6 to 30 characters", Toast.LENGTH_LONG).show();
					((EditText)findViewById(R.id.mainEdittextPassword)).requestFocus();					
					break;
				case ERROR_CONNECTIVITY:					
					showDialog(3);
					break;
				case ERROR_SERVER:
					mess=(String)msg.obj;
					showDialog(1);
					break;
				case ERROR_GPS:
					showDialog(2);
					break;
				default:
				}
				super.handleMessage(msg);
			}

		};

		pdialog1= new ProgressDialog(Login.this);
		pdialog1.setIndeterminate(false);
		pdialog1.setTitle("");
		pdialog1.setMessage("Loading. Please wait...");





		final Button searchButton = (Button) findViewById(R.id.mainButtonLogin);

		searchButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				pdialog1.show();

				loginThread th=new loginThread();
				th.start();

				//				User user1=new User();
				//				user1.userName=(((EditText)findViewById(R.id.mainEdittextLogin)).getText()).toString();
				//				if(user1.userName.length()<1 || user1.userName.length()>15 || user1.userName.contains(" ") || user1.userName.contains("/")){
				//					Toast.makeText(getApplicationContext(),"Invalid username, must be between 5 to 15 characters and cannot contain blanck space", Toast.LENGTH_LONG).show();
				//					((EditText)findViewById(R.id.mainEdittextLogin)).requestFocus();
				//
				//					return;
				//				}
				//				user1.setPassword((((EditText)findViewById(R.id.mainEdittextPassword)).getText()).toString());
				//				if(user1.getPassword().length()<6 || user1.getPassword().length()>30 || user1.getPassword().contains("/")){
				//					Toast.makeText(getApplicationContext(),"Invalid password, must be between 6 to 30 characters", Toast.LENGTH_LONG).show();
				//					((EditText)findViewById(R.id.mainEdittextPassword)).requestFocus();
				//					return;
				//				}
				//
				//				ConnectivityManager manager = (ConnectivityManager)getSystemService(Login.CONNECTIVITY_SERVICE);
				//				Boolean isMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
				//				Boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
				//
				//				if(!(isMobile || isWifi)){
				//					showDialog(3);
				//				}
				//				else{
				//					
				//
				//
				//					/*addition starts here :D*/
				//
				//					HttpPostRequest ht1=new HttpPostRequest();
				//					
				//					pdialog1 = ProgressDialog.show(Login.this,"","Loading. Please wait...", false);
				//					
				//					
				//					ht1.login(user1.userName,user1.getPassword());					
				//					if(!ht1.isError){
				//						
				//						MyXmlHandler myhandler=new MyXmlHandler();
				//
				//						try {
				//							Xml.parse(ht1.xmlStringResponse, myhandler);
				//						} catch (SAXException e) {
				//							e.printStackTrace();
				//						}												
				//						if(myhandler.obj_serverResp1.responseCode.equalsIgnoreCase("0")){
				//							Intent myIntent = new Intent(getApplicationContext(), HomePage.class);
				//							String context = Context.LOCATION_SERVICE;
				//							LocationManager locationManager;
				//							locationManager = (LocationManager)getSystemService(context);
				//							Criteria criteria = new Criteria();
				//							criteria.setAccuracy(Criteria.ACCURACY_FINE);
				//							criteria.setAltitudeRequired(false);
				//							criteria.setBearingRequired(false);
				//							criteria.setCostAllowed(true);
				//							criteria.setPowerRequirement(Criteria.POWER_LOW);
				//							String provider = locationManager.getBestProvider(criteria, true);
				//							
				//							pdialog1.dismiss();
				//							
				//							if(provider==null){
				//								showDialog(2);
				//							}else{
				//								if(rem.isChecked()){
				//									editor.putString("remember", "1");
				//									editor.putString("passWord", user1.getPassword());								
				//								}
				//								else{
				//									editor.putString("remember", "0");
				//									editor.putString("passWord","-1");	
				//								}
				//								editor.putString("sessionId", myhandler.obj_serverResp1.session_id);
				//								editor.putString("userName", user1.userName);
				//								editor.commit();								
				//								startActivity(myIntent);
				//							}
				//						}
				//						else{
				//							//toast error message
				//							pdialog1.cancel();
				//							mess=myhandler.obj_serverResp1.responseMessage;
				//							showDialog(1);
				//						}
				//					}
				//					else{
				//						//communication error.
				//						
				//						Toast.makeText(getApplicationContext(), ht1.xception, Toast.LENGTH_LONG).show();
				//						pdialog1.cancel();
				//					}
				//				}//else of is mobilenetwork or wifi :D

			}// of onclick function
		});


		final TextView registerTextView = (TextView) findViewById(R.id.mainTextviewRegister);
		registerTextView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(), Registeration.class);
				startActivity(myIntent);
			}
		});

		ImageView im=(ImageView)findViewById(R.id.register_img);
		im.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(), Registeration.class);
				startActivity(myIntent);
			}
		});

		TextView tv_regis2=(TextView)findViewById(R.id.main_register);
		
		tv_regis2.setOnClickListener(new OnClickListener() {
			
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
		case 0: //for forget password?
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
						String username=value;

						HttpPostRequest ht=new HttpPostRequest();
						ht.forgetPassword(username);

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
							}
							else{
								mess=myx.obj_serverResp1.forgotPassResponse;
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
		case 1:			  //for showing up messsage

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
	protected void onDestroy() {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		mNotificationManager.cancel(123);
		getApplicationContext().stopService(new Intent(getApplicationContext(), iAnnounceService.class));
		super.onDestroy();
	}


}
