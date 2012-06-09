package iAnnounce.prototype.version1;

import java.util.zip.Inflater;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Comment activity , used for displaying the comments of an announcement.
 * @author Awais
 *
 */
public class AnnouncementComments extends Activity{

	private EditText et_comment;
	private Bundle b;
	private int CHAR_LIMIT=100;
	private TextView commentLen;

	private final int ERROR_COMMUNICATION = 0;	
	private final int ERROR_SERVER = 1;
	private final int ERROR_SESSION = 2;
	private final int COMMENT_POST=3;
	private final int COMMENT_GET=4;

	private Handler msghandler;
	private ProgressDialog pdialog1;
	private ScrollView l1;

	private class ThreadGetComment extends Thread{
		
		public String aid;
		public Context context;

		@Override
		public void run() {
			
			Message msg=new Message();

			//ScrollView sc= new ScrollView(AnnouncementComments.this);
			//ScrollView sc= (ScrollView)findViewById(R.id.main_scroll);
			LinearLayout LM=new LinearLayout(AnnouncementComments.this);
			LM.setOrientation(LinearLayout.VERTICAL);
			
			SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);

			HttpPostRequest ht=new HttpPostRequest();
			ht.getComments(settings.getString("sessionId","0"), aid);
			if(ht.isError){
				msg.what=ERROR_COMMUNICATION;
				msg.obj=ht.xception;
				msghandler.sendMessage(msg);
			}
			else{
				MyXmlHandler mh=new MyXmlHandler();
				try {
					Xml.parse(ht.xmlStringResponse, mh);
				} catch (SAXException e) {
					e.printStackTrace();
				}
				if(!mh.obj_serverResp1.responseCode.equalsIgnoreCase("0")){					
					if(mh.obj_serverResp1.responseCode.equalsIgnoreCase("1")){
						msg.what=ERROR_SESSION;
					}
					else{
						msg.what=ERROR_SERVER;
					}
					msg.obj=mh.obj_serverResp1.responseMessage;
					msghandler.sendMessage(msg);
				}
				else{

					//		ArrayList<String> data=new ArrayList<String>();
					for(int i=0;i<mh.obj_serverResp1.comments.size();i++){
						String s=mh.obj_serverResp1.comments.get(i).ctxt+" ("+ (mh.obj_serverResp1.comments.get(i).ctime).substring(0,(mh.obj_serverResp1.comments.get(i).ctime).length()-2)+" )";
						
						LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
						View comm_view = mInflater.inflate(R.layout.comm_detail,null);

						RelativeLayout comm_detail = (RelativeLayout) comm_view.findViewById(R.id.comm_detail);
						
						TextView tv= (TextView) comm_view.findViewById(R.id.comm_name);
						tv.setText(mh.obj_serverResp1.comments.get(i).cuser);
						final String userSn=mh.obj_serverResp1.comments.get(i).cuser;
						tv.setOnClickListener(new OnClickListener() {
							public void onClick(View v) {
								Intent myIntent = new Intent(v.getContext(), UserProfile.class);
								Bundle b=new Bundle();

								b.putString("username",userSn);
								myIntent.putExtras(b);
								startActivityForResult(myIntent,3);
							}
						});

						TextView tv2= (TextView) comm_view.findViewById(R.id.comm_text);
						tv2.setText(mh.obj_serverResp1.comments.get(i).ctxt);
						LM.addView(comm_detail);

					}
					//sc.addView(LM);				
				}
			}
			Message m1=new Message();
			m1.what=COMMENT_GET;
			m1.obj=LM;
			msghandler.sendMessage(m1);

			super.run();
		}

	}

	private class ThreadPostComment extends Thread{

		@Override
		public void run(){
			Message msg1=new Message();

			HttpPostRequest ht=new HttpPostRequest();
			SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
			ht.postComment(settings.getString("sessionId","0"), et_comment.getText().toString(), b.getString("aid"));

			if(ht.isError){
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
					msg1.what=COMMENT_POST;
					msg1.obj=mh.obj_serverResp1.postComResponse;
					msghandler.sendMessage(msg1);
				}
			}					
			super.run();
		}
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		if(customTitleSupported){
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.titlebar);
			TextView tv_title= (TextView)findViewById(R.id.tv_titlebar);        	
			SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
			tv_title.setText((settings.getString("userName", "iAnnounce")).toUpperCase());	
		}
		pdialog1 = new ProgressDialog(AnnouncementComments.this);
		pdialog1.setMessage("Loading. Please wait...");
		pdialog1.setIndeterminate(true);
		pdialog1.setCancelable(false);
		
		
		msghandler= new Handler(){
			
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

				case COMMENT_POST:
					Toast.makeText(getApplicationContext(), (String)msg.obj, Toast.LENGTH_LONG).show();
					genGUI();
					break;
					
				case COMMENT_GET:
					l1.addView((LinearLayout)msg.obj);

				default:
					
				}

				super.handleMessage(msg);
			}
		};
		
		
		genGUI();		

	}

	private void genGUI(){
		
		pdialog1.show();
		setContentView(R.layout.comlist);
		l1= (ScrollView)findViewById(R.id.main_scroll);
		et_comment = (EditText) findViewById(R.id.ann_comment); 
		commentLen= (TextView) findViewById(R.id.ann_char_left);

		et_comment.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				int len=et_comment.getText().toString().length();
				commentLen.setText(CHAR_LIMIT-len+"");

			}

			public void afterTextChanged(Editable s) {
				int len=et_comment.getText().toString().length();
				commentLen.setText(CHAR_LIMIT-len+"");

			}

			public void beforeTextChanged(CharSequence s, int start,
					int count, int after) {
				int len=et_comment.getText().toString().length();
				commentLen.setText(CHAR_LIMIT-len+"");
			}

		});

		ImageView bt_comment= (ImageView) findViewById(R.id.ann_submit);
		bt_comment.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(et_comment.getText().length()==0){
					Toast.makeText(getApplicationContext(), "Comment cannot be empty", Toast.LENGTH_LONG).show();
				}
				else if(et_comment.getText().length()<=CHAR_LIMIT){
					ThreadPostComment th=new ThreadPostComment();
					th.start();				
				}else{
					Toast.makeText(getApplicationContext(),"Character Limit Reached", Toast.LENGTH_LONG).show();
					et_comment.requestFocus();
				}

			}
		});
		b=this.getIntent().getExtras();

		TextView comm_name = (TextView) findViewById(R.id.ann_name);
		TextView comm_text = (TextView) findViewById(R.id.ann_text);
		
		comm_name.setText(b.getString("name"));
		comm_text.setText(b.getString("desc"));
		
		b=this.getIntent().getExtras();		
		ThreadGetComment th= new ThreadGetComment();
		th.aid=b.getString("aid");
		th.context=getApplicationContext();
		th.start();
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
