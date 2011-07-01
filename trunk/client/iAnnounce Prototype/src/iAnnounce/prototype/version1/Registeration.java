package iAnnounce.prototype.version1;

import java.util.Date;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
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

	static final int DATE_DIALOG_ID = 0;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.registration);

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
				user1.firstName=(((EditText)findViewById(R.id.registrationEdittextName)).getText()).toString();
				user1.lastName=(((EditText)findViewById(R.id.registrationEdittextLastName)).getText()).toString();
				user1.lastName=(((EditText)findViewById(R.id.registrationEdittextLastName)).getText()).toString();
				user1.userName=(((EditText)findViewById(R.id.registrationEdittextUsername)).getText()).toString();
				user1.setPassword((((EditText)findViewById(R.id.registrationEdittextPassword)).getText()).toString());
				user1.email=(((EditText)findViewById(R.id.registrationEdittextEmail)).getText()).toString();
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
						pdialog1 = ProgressDialog.show(Registeration.this,"", 
								"Loading. Please wait...", true);
						res=user1.register();
						pdialog1.cancel();
						String []bo=res.split(":");
						if(bo[0].equalsIgnoreCase("true")){
							Toast.makeText(getApplicationContext(), bo[1], Toast.LENGTH_LONG).show();
							finish();
						}
						else{
							Toast.makeText(getApplicationContext(), bo[1], Toast.LENGTH_LONG).show();
							
						}

					}
				}

			}
		});



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

	@Override
	protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder b= new AlertDialog.Builder(Registeration.this);
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this,mDateSetListener,mYear, mMonth, mDay);
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