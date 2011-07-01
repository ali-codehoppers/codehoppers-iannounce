package iAnnounce.prototype.version1;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Xml;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Comment activity , used for displaying the comments of an announcement.
 * @author Awais
 *
 */
public class CommentActivity extends Activity{

	private EditText et_comment;
	private Bundle b;
	private int CHAR_LIMIT=100;
	private TextView commentLen;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		genGUI();		
	}

	private void genGUI(){
		ProgressDialog pdialog1;
		pdialog1 = ProgressDialog.show(CommentActivity.this, "", 
				"Loading. Please wait...", true);


		LinearLayout l1=new LinearLayout(this);


		setContentView(l1);

		l1.setOrientation(LinearLayout.VERTICAL);		
		et_comment= new EditText(this);
		et_comment.setWidth(200);		
		et_comment.setHeight(60);
		LinearLayout combar=new LinearLayout(this);
		combar.setOrientation(LinearLayout.HORIZONTAL);
		commentLen= new TextView(this);
		commentLen.setText(CHAR_LIMIT+"\t Characters left");
		commentLen.setPadding(0, 0, 10, 0);

		et_comment.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				int len=et_comment.getText().toString().length();
				commentLen.setText(CHAR_LIMIT-len+"\t Characters left");

			}

			public void afterTextChanged(Editable s) {
				int len=et_comment.getText().toString().length();
				commentLen.setText(CHAR_LIMIT-len+"\t Characters left");

			}

			public void beforeTextChanged(CharSequence s, int start,
					int count, int after) {
				int len=et_comment.getText().toString().length();
				commentLen.setText(CHAR_LIMIT-len+"\t Characters left");

			}

		});


		combar.addView(commentLen);



		l1.addView(et_comment);


		Button bt_comment=new Button(this);
		bt_comment.setText("Submit");

		bt_comment.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if(et_comment.getText().length()==0){
					Toast.makeText(getApplicationContext(), "Comment cannot be empty", Toast.LENGTH_LONG).show();
				}
				else if(et_comment.getText().length()<=CHAR_LIMIT){
					HttpPostRequest ht=new HttpPostRequest();
					SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
					String s=ht.postComment(settings.getString("sessionId","0"), et_comment.getText().toString(), b.getString("aid"));
					MyXmlHandler mh=new MyXmlHandler();
					try {
						Xml.parse(s, mh);
					} catch (SAXException e) {
						e.printStackTrace();
					}
					if(mh.obj_serverResp1.forceLogin){
						Intent resultIntent = new Intent();
						setResult(Activity.RESULT_OK, resultIntent);
						finish();
					}
					else{
						Toast.makeText(getApplicationContext(), mh.obj_serverResp1.postComResponse, Toast.LENGTH_LONG).show();
						genGUI();
					}
				}else{
					Toast.makeText(getApplicationContext(),"Character Limit Reached", Toast.LENGTH_LONG).show();
					et_comment.requestFocus();
				}

			}
		});
		bt_comment.setWidth(100);
		combar.setGravity(Gravity.CENTER_HORIZONTAL);

		combar.addView(bt_comment);
		l1.addView(combar);
		b=this.getIntent().getExtras();


		TextView tv_comments=new TextView(this);
		tv_comments.setText("Comments");
		tv_comments.setGravity(Gravity.CENTER_HORIZONTAL);
		tv_comments.setWidth(LayoutParams.FILL_PARENT);
		tv_comments.setTextSize(20);

		l1.addView(tv_comments);
		TextView tv_comOn=new TextView(this);
		tv_comOn.setText(Html.fromHtml("<b>Announcement: </b>"+b.getString("desc")));
		tv_comOn.setMaxWidth(320);
		tv_comOn.setTextSize(18);
		l1.addView(tv_comOn);




		ScrollView sc=new ScrollView(this);
		LinearLayout LM=new LinearLayout(this);
		LM.setOrientation(LinearLayout.VERTICAL);

		b=this.getIntent().getExtras();


		String aid= b.getString("aid");
		SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);

		HttpPostRequest ht=new HttpPostRequest();
		String resp=ht.getComments(settings.getString("sessionId","0"), aid);

		MyXmlHandler myhandler=new MyXmlHandler();
		try {
			Xml.parse(resp, myhandler);
		} catch (SAXException e) {
			e.printStackTrace();
		}
		ServerResponse obj_serRes=myhandler.obj_serverResp1;
		if(obj_serRes.forceLogin){
			Intent resultIntent = new Intent();
			setResult(Activity.RESULT_OK, resultIntent);
			finish();
		}
		else{
			//		ArrayList<String> data=new ArrayList<String>();
			for(int i=0;i<obj_serRes.comments.size();i++){
				String s=obj_serRes.comments.get(i).ctxt+" ("+ (obj_serRes.comments.get(i).ctime).substring(0,(obj_serRes.comments.get(i).ctime).length()-2)+" )";
				LinearLayout templay=new LinearLayout(this);
				templay.setOrientation(LinearLayout.HORIZONTAL);
				templay.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT ));

				TextView tv=new TextView(this);



				tv.setText(Html.fromHtml("<b>"+obj_serRes.comments.get(i).cuser+"</b>"));
				tv.setTextSize(20);

				final String userSn=obj_serRes.comments.get(i).cuser;

				tv.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						Intent myIntent = new Intent(v.getContext(), UserProfile.class);
						Bundle b=new Bundle();

						b.putString("username",userSn);
						myIntent.putExtras(b);
						startActivityForResult(myIntent,3);

					}
				});


				TextView tv2=new TextView(this);
				tv2.setText(s);
				tv2.setTextSize(18);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
				lp.setMargins(10, 0, 0, 0);
				tv2.setLayoutParams(lp);


				templay.addView(tv);
				templay.addView(tv2);

				LM.addView(templay);

			}
			sc.addView(LM);



		}



		l1.addView(sc);


		pdialog1.cancel();
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
		switch (item.getItemId()) {
		case R.id.menu_Profile:
			Intent in= new Intent(this, MyProfile.class);
			SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);			
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
			Intent resultIntent = new Intent();
			setResult(Activity.RESULT_OK, resultIntent);
			finish();			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==3){
			if (resultCode == Activity.RESULT_OK) {
				Intent resultIntent = new Intent();
				setResult(Activity.RESULT_OK, resultIntent);
				finish();		               
			}
		}

	}



}
