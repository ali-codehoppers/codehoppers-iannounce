package iAnnounce.prototype.version1;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
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
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_announcments);
		getAnnouncementText(1);

	}
	
	@Override
	protected void onResume() {
		getAnnouncementText(pageNum_int);
		super.onResume();
	}
	
	/**
	 * For getting announcement of a page number and populating the screen
	 * @param pg Pagenumber of type int
	 */

	void getAnnouncementText(int pg){
		ProgressDialog pdialog1;
		pdialog1 = ProgressDialog.show(MyAnnouncments.this, "", 
				"Loading. Please wait...", true);
		HttpPostRequest htreq=new HttpPostRequest();
		SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
		String resp=htreq.getMyAnnouncements(settings.getString("sessionId", "0"),Integer.toString(pg));
		generateGUI(resp);
		pdialog1.cancel();
	}
	
	/**
	 * Function for populating the GUI when xml parseable string is passed in parameters.
	 * @param resp resp is parseable string from the server
	 */

	void generateGUI(String resp){
		ScrollView v=new ScrollView(getBaseContext());
		LinearLayout mainLayout=new LinearLayout(getBaseContext());
		mainLayout.setOrientation(LinearLayout.VERTICAL);
		setContentView(mainLayout);	


		LinearLayout lbar=new LinearLayout(getBaseContext());

		lbar.setGravity(Gravity.CENTER_HORIZONTAL);
		lbar.setBackgroundColor(Color.rgb(189, 189, 189));
		lbar.setPadding(0, 5, 0, 0);

		final TextView tv_pgnum= new TextView(getBaseContext());
		tv_pgnum.setText("Page : "+Integer.toString(pageNum_int));
		tv_pgnum.setTextColor(Color.BLACK);


		Button bt_Next=new Button(getBaseContext());
		bt_Next.setText("Next Page");
		bt_Next.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				pageNum_int++;
				tv_pgnum.setText("Page : "+Integer.toString(pageNum_int));
				getAnnouncementText(pageNum_int);
			}
		});

		Button bt_Prev=new Button(getBaseContext());
		bt_Prev.setText("Prev Page");
		bt_Prev.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				pageNum_int--;
				tv_pgnum.setText("Page : "+Integer.toString(pageNum_int));
				getAnnouncementText(pageNum_int);
			}
		});


		if(pageNum_int<=1){
			bt_Prev.setEnabled(false);
		}
		lbar.addView(bt_Prev);
		lbar.addView(tv_pgnum);
		lbar.addView(bt_Next);

		mainLayout.addView(lbar);



		MyXmlHandler myhandler=new MyXmlHandler();

		try {
			Xml.parse(resp, myhandler);
		} catch (SAXException e) {
			e.printStackTrace();
		}

		final ServerResponse obj_serRes=myhandler.obj_serverResp1;
		LinearLayout l1=new LinearLayout(getBaseContext());
		v.addView(l1);
		l1.setOrientation(LinearLayout.VERTICAL);

		if(obj_serRes.forceLogin){						
			finish();			
		}		

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

		mainLayout.addView(v);


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
			startActivity(in);
			return true;
		case R.id.menu_options:
			Intent intent2= new Intent(this, optionAct.class);
			startActivity(intent2);
			return true;
		case R.id.menu_Logout:
			SharedPreferences settings = getSharedPreferences("iAnnounceVars", 0);
			HttpPostRequest ht=new HttpPostRequest();
			String x=ht.logout(settings.getString("sessionId", "0"));
			MyXmlHandler myhand=new MyXmlHandler();
			try {
				Xml.parse(x, myhand);
			} catch (SAXException e) {
				e.printStackTrace();
			}

			SharedPreferences.Editor editor = settings.edit();
			editor.putString("sessionId", "0");
			editor.commit();
			Toast.makeText(getApplicationContext(),myhand.obj_serverResp1.logoutResponse, Toast.LENGTH_LONG).show();				
			finish();		
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}