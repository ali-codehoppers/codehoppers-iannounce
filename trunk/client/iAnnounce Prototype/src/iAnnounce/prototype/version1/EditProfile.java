package iAnnounce.prototype.version1;

import java.util.Date;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Xml;
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
 * Activity for  generating user interface for an user
 * @author Awais
 *@version 1.0
 */
public class EditProfile extends Activity {
	private Button mPickDate;
	private int mYear;
	private int mMonth;
	private int mDay;

	static final int DATE_DIALOG_ID = 0;
	private ProgressDialog pdialog1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_profile);

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

		pdialog1 = ProgressDialog.show(EditProfile.this,"", 
				"Loading. Please wait...", true);
		HttpPostRequest ht=new HttpPostRequest();

		SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);

		ht.getProfile(settings.getString("sessionId", "0"),settings.getString("userName", "0"));

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
				((TextView)findViewById(R.id.editProfileEdittextName)).setText(mh.obj_serverResp1.userProfile.firstName);
				((TextView)findViewById(R.id.editProfileEdittextLastName)).setText(mh.obj_serverResp1.userProfile.lastName);
				if((mh.obj_serverResp1.userProfile.gender).equalsIgnoreCase("0")){
					((RadioButton)findViewById(R.id.radio_female)).toggle();				
				}
				else{
					((RadioButton)findViewById(R.id.radio_male)).toggle();
				}



				String []str_dob=mh.obj_serverResp1.userProfile.dob.split("-");



				mYear= Integer.parseInt(str_dob[0]);
				mMonth= Integer.parseInt(str_dob[1])-1;
				mDay= Integer.parseInt(str_dob[2]);

				((TextView)findViewById(R.id.editProfileTextviewDOB2)).setText(mDay+"/"+(mMonth+1)+"/"+mYear);
				pdialog1.cancel();
			}			
		}


		Button bt_submit=(Button)findViewById(R.id.editProfileButtonRegister);
		bt_submit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			
				int id1;
				id1=(((RadioGroup)findViewById(R.id.editProfileRadiogroupGender)).getCheckedRadioButtonId());
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
				

				((EditText)findViewById(R.id.editProfileEdittextNewPassword)).setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
				((EditText)findViewById(R.id.editProfileEdittextPassword)).setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());

				String sNewPass=((EditText)findViewById(R.id.editProfileEdittextNewPassword)).getText().toString();
				String sPass=((EditText)findViewById(R.id.editProfileEdittextPassword)).getText().toString();

				if(((EditText)findViewById(R.id.editProfileEdittextName)).getText().length()<1 || ((EditText)findViewById(R.id.editProfileEdittextName)).getText().length()>15 || ((EditText)findViewById(R.id.editProfileEdittextName)).getText().toString().contains("/")){
					Toast.makeText(getApplicationContext(), "Invalid First name, must be between 1 to 15 characters", Toast.LENGTH_LONG).show();
					((EditText)findViewById(R.id.editProfileEdittextName)).requestFocus();

				}
				else if(((EditText)findViewById(R.id.editProfileEdittextLastName)).getText().length()<1 || ((EditText)findViewById(R.id.editProfileEdittextLastName)).getText().length()>15 ||((EditText)findViewById(R.id.editProfileEdittextLastName)).getText().toString().contains("/")){
					Toast.makeText(getApplicationContext(), "Invalid Last name, must be between 1 to 15 characters", Toast.LENGTH_LONG).show();
					((EditText)findViewById(R.id.editProfileEdittextLastName)).requestFocus();					
				}				
				else if( !(sPass.length()==0) && (sNewPass.length()==0)  ){
					Toast.makeText(getApplicationContext(), "Please check passwords", Toast.LENGTH_LONG).show();
					((EditText)findViewById(R.id.editProfileEdittextNewPassword)).requestFocus();					
				}
				else if((sPass.length()==0) && !(sNewPass.length()==0)){
					Toast.makeText(getApplicationContext(), "Please check passwords", Toast.LENGTH_LONG).show();
					((EditText)findViewById(R.id.editProfileEdittextNewPassword)).requestFocus();
				}
				else if( (sPass.length()!=0) && (sNewPass.length()!=0) ){
					if(((EditText)findViewById(R.id.editProfileEdittextNewPassword)).getText().length()<6||((EditText)findViewById(R.id.editProfileEdittextNewPassword)).getText().length()>30 || ((EditText)findViewById(R.id.editProfileEdittextNewPassword)).getText().toString().contains("/")){
						Toast.makeText(getApplicationContext(), "Invalid New Password, must be between 6 to 30 characters", Toast.LENGTH_LONG).show();
						((EditText)findViewById(R.id.editProfileEdittextNewPassword)).requestFocus();					
					}
					else if(((EditText)findViewById(R.id.editProfileEdittextPassword)).getText().length()<6||((EditText)findViewById(R.id.editProfileEdittextPassword)).getText().length()>30 || ((EditText)findViewById(R.id.editProfileEdittextPassword)).getText().toString().contains("/")){
						Toast.makeText(getApplicationContext(), "Invalid Old Password, must be between 6 to 30 characters", Toast.LENGTH_LONG).show();
						((EditText)findViewById(R.id.editProfileEdittextPassword)).requestFocus();					
					}
					else{
						myFunc(gender);
					}

				}
				else{
					myFunc(gender);
				}
			}
		});




	}

	private void myFunc(String gender){
		HttpPostRequest ht=new HttpPostRequest();
		SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
		ht.editProfile(settings.getString("sessionId", "0"),((EditText)findViewById(R.id.editProfileEdittextPassword)).getText().toString(), ((EditText)findViewById(R.id.editProfileEdittextNewPassword)).getText().toString(), gender, ((EditText)findViewById(R.id.editProfileEdittextName)).getText().toString(), ((EditText)findViewById(R.id.editProfileEdittextLastName)).getText().toString(),mYear+"/"+(mMonth+1)+"/"+mDay);
		
		if(ht.isError){
			Toast.makeText(getApplicationContext(), ht.xception, Toast.LENGTH_LONG).show();
		}
		else{
			MyXmlHandler mhand=new MyXmlHandler();
			try {
				Xml.parse(ht.xmlStringResponse, mhand);
			} catch (SAXException e) {
				e.printStackTrace();
			}
			if(!mhand.obj_serverResp1.responseCode.equalsIgnoreCase("0")){
				Toast.makeText(getApplicationContext(), mhand.obj_serverResp1.responseMessage, Toast.LENGTH_LONG).show();
				if(mhand.obj_serverResp1.responseCode.equalsIgnoreCase("1")){
					Intent resultIntent = new Intent();
					setResult(Activity.RESULT_OK, resultIntent);
					finish();					
				}
			}
			else{
				Toast.makeText(getApplicationContext(),mhand.obj_serverResp1.editProResponse, Toast.LENGTH_LONG).show();
				finish();
			}	
		}
	}
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

			((TextView)findViewById(R.id.editProfileTextviewDOB2)).setText(mDay+"/"+(mMonth+1)+"/"+mYear);

		}
	};

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this,mDateSetListener,mYear, mMonth, mDay);
		}
		return null;
	}
}