package iAnnounce.prototype.version1;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity for displaying user his announcements
 * @author Awais
 *@version 1
 */
public class MyAnnouncments extends Activity {

	private int pageNum_int=1;

	private Handler msgHandler;
	private final int ERROR_COMMUNICATION = 0;	
	private final int ERROR_SERVER = 1;
	private final int ERROR_SESSION = 2;
	private final int GUI_READY = 3;

	private ProgressDialog pdialog1;

	private LinearLayout mlay;
	private boolean fl_gotPage;
	ScrollView sv;

	private class myAnnouncementThread extends Thread{

		@Override
		public void run() {

			HttpPostRequest htreq=new HttpPostRequest();
			SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
			htreq.getMyAnnouncements(settings.getString("sessionId", "0"),Integer.toString(pageNum_int));
			if(htreq.isError){
				Message msg1=new Message();
				msg1.what=ERROR_COMMUNICATION;
				msg1.obj=htreq.xception;				
				msgHandler.sendMessage(msg1);

			}else{
				generateGUI(htreq.xmlStringResponse);				
			}

			super.run();
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_announcments);
		
		fl_gotPage=true;
		
		//		getAnnouncementText(pageNum_int);
		pdialog1= new ProgressDialog(MyAnnouncments.this);
		pdialog1.setTitle("");
		pdialog1.setMessage("Loading. Please wait...");
		
		

		sv =new ScrollView(MyAnnouncments.this){

			@Override
			protected void onScrollChanged(int l, int t, int oldl, int oldt) {
				Log.e("errrrrrrrrrr", "crap its working");
				View view = (View) getChildAt(getChildCount()-1);
				int diff = (view.getBottom()-(getHeight()+getScrollY()));// Calculate the scrolldiff
				
				
				if( diff == 0 ){  

					if(fl_gotPage){
						pdialog1.show();
						fl_gotPage=false;
						pageNum_int++;
						getAnnouncementText();
					}
				}
				super.onScrollChanged(l, t, oldl, oldt);
			}


		};
		
//		mlay=new LinearLayout(MyAnnouncments.this);
//		mlay.setOrientation(LinearLayout.VERTICAL);
//		sv.addView(mlay);
		
		setContentView(sv);



		msgHandler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				pdialog1.cancel();				
				switch (msg.what){
				case ERROR_COMMUNICATION:					
					Toast.makeText(getApplicationContext(),(String) msg.obj, Toast.LENGTH_LONG).show();
					break;
				case ERROR_SERVER:
					Toast.makeText(getApplicationContext(),(String) msg.obj, Toast.LENGTH_LONG).show();
					break;
				case ERROR_SESSION:
					Toast.makeText(getApplicationContext(),(String) msg.obj, Toast.LENGTH_LONG).show();
					finish();					
					break;
				case GUI_READY:
					fl_gotPage=true;
					mlay.addView((LinearLayout)msg.obj);
					break;
				default:

				}
				super.handleMessage(msg);
			}
		};


	}

	@Override
	protected void onResume() {
		sv.removeAllViews();
		
		pageNum_int=1;
		mlay=new LinearLayout(MyAnnouncments.this);
		mlay.setOrientation(LinearLayout.VERTICAL);
		sv.addView(mlay);
		getAnnouncementText();
		super.onResume();
	}

	/**
	 * For getting announcement of a page number and populating the screen
	 * @param pg Pagenumber of type int
	 */

	void getAnnouncementText(){

		pdialog1.show();

		myAnnouncementThread th=new myAnnouncementThread();

		th.start();




		//		HttpPostRequest htreq=new HttpPostRequest();
		//		SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
		//		htreq.getMyAnnouncements(settings.getString("sessionId", "0"),Integer.toString(pg));
		//		if(htreq.isError){
		//			Toast.makeText(getApplicationContext(), htreq.xception, Toast.LENGTH_LONG).show();
		//		}else{
		//			generateGUI(htreq.xmlStringResponse);
		//			pdialog1.cancel();
		//		}
	}

	/**
	 * Function for populating the GUI when xml parseable string is passed in parameters.
	 * @param resp resp is parseable string from the server
	 */

	void generateGUI(String resp){

		//		LinearLayout mainLayout=new LinearLayout(getBaseContext());
		//		mainLayout.setOrientation(LinearLayout.VERTICAL);
		//
		//
		//
		//		LinearLayout lbar=new LinearLayout(getBaseContext());
		//
		//		lbar.setGravity(Gravity.CENTER_HORIZONTAL);
		//		lbar.setBackgroundColor(Color.rgb(189, 189, 189));
		//		lbar.setPadding(0, 5, 0, 0);
		//
		//		final TextView tv_pgnum= new TextView(getBaseContext());
		//		tv_pgnum.setText("Page : "+Integer.toString(pageNum_int));
		//		tv_pgnum.setTextColor(Color.BLACK);
		//
		//
		//		Button bt_Next=new Button(getBaseContext());
		//		bt_Next.setText("Next Page");
		//		bt_Next.setOnClickListener(new OnClickListener() {
		//
		//			public void onClick(View arg0) {
		//				
		//				pageNum_int++;
		//				tv_pgnum.setText("Page : "+Integer.toString(pageNum_int));
		//				getAnnouncementText(pageNum_int);
		//			}
		//		});
		//
		//		Button bt_Prev=new Button(getBaseContext());
		//		bt_Prev.setText("Prev Page");
		//		bt_Prev.setOnClickListener(new OnClickListener() {
		//
		//			public void onClick(View arg0) {
		//				pageNum_int--;
		//				tv_pgnum.setText("Page : "+Integer.toString(pageNum_int));
		//				getAnnouncementText(pageNum_int);
		//			}
		//		});
		//
		//
		//		if(pageNum_int<=1){
		//			bt_Prev.setEnabled(false);
		//		}
		//		lbar.addView(bt_Prev);
		//		lbar.addView(tv_pgnum);
		//		lbar.addView(bt_Next);
		//
		//		mainLayout.addView(lbar);



		MyXmlHandler myhandler=new MyXmlHandler();

		try {
			Xml.parse(resp, myhandler);
		} catch (SAXException e) {
			e.printStackTrace();
		}

		final ServerResponse obj_serRes=myhandler.obj_serverResp1;
		Message msg=new Message();
		if(!obj_serRes.responseCode.equalsIgnoreCase("0")){
			msg.obj=obj_serRes.responseMessage;			
			if(obj_serRes.responseCode.equalsIgnoreCase("1")){
				msg.what=ERROR_SESSION;				
			}
			else{
				msg.what=ERROR_SERVER;
			}
			msgHandler.sendMessage(msg);
		}
		else{
			LinearLayout l1=new LinearLayout(getBaseContext());			
			l1.setOrientation(LinearLayout.VERTICAL);
			for(int i=0;i<obj_serRes.feed.size();i++){

				LinearLayout l2=new LinearLayout(getBaseContext());
				l2.setOrientation(LinearLayout.VERTICAL);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
				lp.setMargins(0,10, 0,10);
				l2.setLayoutParams(lp);

				TextView Descr=new TextView(getBaseContext());
				Descr.setText(Html.fromHtml("<b>Description:</b> "+obj_serRes.feed.get(i).description));
				Descr.setTextSize(20);

				l2.addView(Descr);

				TextView date=new TextView(getBaseContext());
				date.setText(Html.fromHtml("<b>Time:</b> "+obj_serRes.feed.get(i).timestamp));
				date.setTextSize(16);

				l2.addView(date);

				TextView ratin=new TextView(getBaseContext());
				ratin.setText(Html.fromHtml("<b>Rating:</b> "+obj_serRes.feed.get(i).averageRating));
				ratin.setTextSize(16);						
				l2.addView(ratin);

				LinearLayout l3=new LinearLayout(getBaseContext());

				Button bt_locate=new Button(getBaseContext());

				final String longi=obj_serRes.feed.get(i).longitude;
				final String lati=obj_serRes.feed.get(i).latitude;
				final String mapDesc=obj_serRes.feed.get(i).announcer+" :  "+obj_serRes.feed.get(i).description+"("+(obj_serRes.feed.get(i).timestamp).substring(0,(obj_serRes.feed.get(i).timestamp).length())+")";

				bt_locate.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.locate));
				bt_locate.setOnClickListener(new OnClickListener() {

					public void onClick(View arg0) {
						Bundle b=new Bundle();
						b.putString("longitude", longi);
						b.putString("latitude", lati);
						b.putString("desc", mapDesc);
						Intent myIntent = new Intent(getBaseContext(), MyMapAct.class);	
						myIntent.putExtras(b);
						startActivity(myIntent);					
					}
				});

				LinearLayout l4=new LinearLayout(getBaseContext());
				l4.addView(bt_locate);
				Button bt_comment=new Button(getBaseContext());
				bt_comment.setText(obj_serRes.feed.get(i).noOfComments);
				bt_comment.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.comment));
				final int k=i;
				bt_comment.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						Intent in=new Intent(getBaseContext(),CommentActivity.class);

						Bundle b=new Bundle();
						b.putString("desc",obj_serRes.feed.get(k).description);
						b.putString("aid",obj_serRes.feed.get(k).announcement_id);
						in.putExtras(b);
						startActivityForResult(in,3);

					}
				});

				l4.addView(bt_comment);

				l2.addView(l3);
				l2.addView(l4);
				l1.addView(l2);
			}


			msg.what=GUI_READY;
			msg.obj=l1;
			msgHandler.sendMessage(msg);

		}

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.appmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
		// Handle item selection		
		switch (item.getItemId()) {
		case R.id.menu_Profile:
			Intent in= new Intent(this, MyProfile.class);
			startActivity(in);
			return true;
		case R.id.menu_options:
			Intent intent2= new Intent(this, optionAct.class);
			startActivity(intent2);
			return true;
		case R.id.menu_Logout:
			HttpPostRequest ht=new HttpPostRequest();
			ht.logout(settings.getString("sessionId", "0"));

			if(ht.isError){
				Toast.makeText(getApplicationContext(), ht.xception, Toast.LENGTH_LONG).show();			
			}
			else{
				MyXmlHandler myhand=new MyXmlHandler();
				try {
					Xml.parse(ht.xmlStringResponse, myhand);
				} catch (SAXException e) {
					e.printStackTrace();
				}

				if(!myhand.obj_serverResp1.responseCode.equalsIgnoreCase("0")){
					Toast.makeText(getApplicationContext(), ht.xception, Toast.LENGTH_LONG).show();					
				}
				else{
					SharedPreferences.Editor editor = settings.edit();
					editor.putString("sessionId", "0");
					editor.commit();
					Toast.makeText(getApplicationContext(),myhand.obj_serverResp1.logoutResponse, Toast.LENGTH_LONG).show();
				}
				finish();				
			}

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}