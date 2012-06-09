package iAnnounce.prototype.version1;

import java.util.Date;
import java.util.regex.Pattern;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.InputType;
import android.util.Xml;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Activity for the registration screen 
 * @author Awais
 *
 */
public class Registeration extends Activity {
	private Button mPickDate;
	private int mYear;
	private int mMonth;
	private int mDay;
	private ProgressDialog pdialog1;
	
	private EditText et_fname;
	private EditText et_lname;
	private EditText et_username;
	private EditText et_email;
	private EditText et_password;
	
	private Handler msgHandler;
	
	private final int ERROR_COMMUNICATION = 0;
	private final int REGISTER_SUCCESS = 1;
	
	private final int ERROR_SERVER = 3;
	private final int ERROR_CONNECTIVITY=4; //wifi is off :O
	private final int ERROR_GPS=5;

	

	static final int DATE_DIALOG_ID = 0;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.registration);
		if(customTitleSupported){
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.titlebar);
			TextView tv_title= (TextView)findViewById(R.id.tv_titlebar);        	
			tv_title.setText("iAnnounce");	
		}
		mPickDate = (Button) findViewById(R.id.pickDate);
		mYear = 1980;
		mMonth = 0;
		mDay=1;

		// add a click listener to the button
		mPickDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});

		final User user1=new User();
		pdialog1=new ProgressDialog(Registeration.this);		
		pdialog1.setMessage("Loading. Please wait...");
		pdialog1.setIndeterminate(true);
		
		msgHandler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				pdialog1.cancel();				
				switch (msg.what){
				case ERROR_COMMUNICATION:					
					Toast.makeText(getApplicationContext(),(String) msg.obj, Toast.LENGTH_LONG).show();
					break;
				
				case REGISTER_SUCCESS:
					mess=(String)msg.obj;
					showDialog(1);				
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
		
		
		
		
		
		et_fname=(EditText)findViewById(R.id.registrationEdittextName);
		et_lname=(EditText)findViewById(R.id.registrationEdittextLastName);
		et_username=(EditText)findViewById(R.id.registrationEdittextUsername);
		et_password=(EditText)findViewById(R.id.registrationEdittextPassword);
		et_email=(EditText)findViewById(R.id.registrationEdittextEmail);
		
		
		et_email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

		Button bt_register=(Button)findViewById(R.id.bt_register);

		bt_register.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {


				int id1;
				id1=(((RadioGroup)findViewById(R.id.registrationRadiogroupGender)).getCheckedRadioButtonId());
				RadioButton gen=(RadioButton)findViewById(id1);
				String gender="";
				if(id1>0){
					gender=(gen.getText()).toString();
					if(gender.equalsIgnoreCase("male")){
						gender="true";
					}
					else{
						gender="false";
					}
				}
				String res="";
				user1.firstName=et_fname.getText().toString();
//				user1.lastName=(((EditText)findViewById(R.id.registrationEdittextLastName)).getText()).toString();
				user1.lastName=et_lname.getText().toString();
				user1.userName=et_username.getText().toString();
				user1.setPassword(et_password.getText().toString());
				user1.email=et_email.getText().toString();
				user1.gender=gender;
				user1.dob=mYear+"/"+mMonth+"/"+mDay;
				if(validate(user1))
				{

					ConnectivityManager manager = (ConnectivityManager)getSystemService(Login.CONNECTIVITY_SERVICE);
					Boolean isMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
					Boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

					if(!(isMobile || isWifi)){
						showDialog(3);
					}
					else{
						
						pdialog1.show();
						
						threadRegister th=new threadRegister();
						
						th.user1=user1;
						
						th.start();
						
						
						
					}
				}

			}
		});



	}
	
	private class threadRegister extends Thread{
		public User user1;

		@Override
		public void run() {
			

			HttpPostRequest ht=new HttpPostRequest();
								
			try {
				ht.register(user1.firstName, user1.lastName, user1.userName, user1.getPassword(), user1.email,user1.gender, user1.dob);
			} catch (Exception e1) {
				Message msg=new Message();
				msg.what=ERROR_COMMUNICATION;
				msg.obj=e1.toString();	
				msgHandler.sendMessage(msg);
				e1.printStackTrace();
			}
			
			
			if(ht.isError){
//				Toast.makeText(getApplicationContext(), ht.xception, Toast.LENGTH_LONG).show();
				Message msg=new Message();
				msg.what=ERROR_COMMUNICATION;
				msg.obj=ht.xception;	
				msgHandler.sendMessage(msg);
			}
			else{
				MyXmlHandler myhandler=new MyXmlHandler();
				try {
					Xml.parse(ht.xmlStringResponse, myhandler);
				} catch (SAXException e) {
					Message msg=new Message();
					msg.what=ERROR_COMMUNICATION;
					msg.obj=e.toString();	
					msgHandler.sendMessage(msg);
					e.printStackTrace();
				}
				
				if(!myhandler.obj_serverResp1.responseCode.equalsIgnoreCase("0")){
					Message msg=new Message();
					msg.obj=myhandler.obj_serverResp1.responseMessage;
					msg.what=ERROR_SERVER;
					msgHandler.sendMessage(msg);
//					Toast.makeText(getApplicationContext(),myhandler.obj_serverResp1.responseMessage, Toast.LENGTH_LONG).show();								
				}
				else{
					Message msg=new Message();
					msg.obj=myhandler.obj_serverResp1.register_response;
					msg.what=REGISTER_SUCCESS;
					msgHandler.sendMessage(msg);					
				}
				
			}
			
			super.run();
		}
		
	}
	
	
	
	
	
	/**
	 * DatePicker Dialog
	 */
	
	private DatePickerDialog.OnDateSetListener mDateSetListener =
		new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, 
				int monthOfYear, int dayOfMonth) {
			mYear=year;
			mMonth=monthOfYear;
			mDay=dayOfMonth;

			Date selected = new Date(year-1900,monthOfYear,dayOfMonth);

			if(selected.compareTo(new Date())>0)
			{
				Toast.makeText(getApplicationContext(), "Invalid date, cannot be a date after the currunt date.", Toast.LENGTH_LONG).show();
				return;
			}

			((TextView)findViewById(R.id.registrationTextviewDOB2)).setText(mDay+"/"+(mMonth+1)+"/"+mYear);

		}
	};
	private String mess;
	@Override
	protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder b= new AlertDialog.Builder(Registeration.this);
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this,mDateSetListener,mYear, mMonth, mDay);
			
		case 1:			  //for showing up messsage
			b.setMessage(mess);  
			b.setTitle("Notification");  
			b.setCancelable(true)  ;
			b.setNeutralButton(android.R.string.ok,  
					new DialogInterface.OnClickListener() {  
				public void onClick(DialogInterface dialog, int whichButton){
					finish();
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
		}
		return null;
	}

	/**
	 * Function is for validation of data entered in all the edittext fields i.e 5<username characters<=15 and email in proper format. Same character limit for the firstname and lastname. 
	 * @param u user object
	 * @return boolean true or false
	 */
	
	boolean validate(User u)
	{
		final Pattern emailPattern = Pattern.compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");

		if(u.firstName.length()<1 || u.firstName.length()>15 || u.firstName.contains("/")){
			Toast.makeText(getApplicationContext(), "Invalid First name, must be between 1 to 15 characters", Toast.LENGTH_LONG).show();
			((EditText)findViewById(R.id.registrationEdittextName)).requestFocus();
			return false;
		}
		else if(u.lastName.length()<1 || u.lastName.length()>15 || u.lastName.contains("/")){
			Toast.makeText(getApplicationContext(), "Invalid Last name, , must be between 1 to 15 characters", Toast.LENGTH_LONG).show();
			((EditText)findViewById(R.id.registrationEdittextLastName)).requestFocus();
			return false;
		}
		else if(u.userName.length()<5 || u.userName.length()>15 || u.userName.contains("/") || u.userName.contains(" ")){
			Toast.makeText(getApplicationContext(), "Invalid Username, must be between 5 to 15 characters and cannot contain blanck space", Toast.LENGTH_LONG).show();
			((EditText)findViewById(R.id.registrationEdittextUsername)).requestFocus();
			return false;
		}
		else if(u.getPassword().length()<6 || u.getPassword().length()>30 || u.getPassword().contains("/")){
			Toast.makeText(getApplicationContext(), "Invalid Password, must be between 6 to 30 characters", Toast.LENGTH_LONG).show();
			((EditText)findViewById(R.id.registrationEdittextPassword)).requestFocus();
			return false;
		}
		else if(!emailPattern.matcher(u.email).matches()){
			Toast.makeText(getApplicationContext(), "Invalid Email address", Toast.LENGTH_LONG).show();
			((EditText)findViewById(R.id.registrationEdittextEmail)).requestFocus();
			return false;
		}



		return true;
	}



}