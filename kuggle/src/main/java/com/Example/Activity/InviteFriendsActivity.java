package com.Example.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.Example.constants.Global;

import org.json.JSONObject;


public class InviteFriendsActivity extends Activity {
	
	private static final String TAG = "InviteFriendsActivity";
	
	Button btn_menu;
	Button btn_watch_ad;
	TextView txt_hello;
	TextView txt_profile_percent;
	ImageView img_user_state;
	Button btn_invite_friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_invite_friends);
        
        Intent intent = getIntent();
		String user_name = intent.getStringExtra(Global.USER_NAME);
		
		btn_menu = (Button)findViewById(R.id.button_nav_menu);
		btn_watch_ad = (Button)findViewById(R.id.button_nav_watch_ad);
		txt_hello = (TextView)findViewById(R.id.textView_hello);
		txt_profile_percent = (TextView)findViewById(R.id.textView_profile_percent);
		img_user_state = (ImageView)findViewById(R.id.imageView_user_state);
		btn_invite_friends = (Button)findViewById(R.id.button_invite_friends);
		
		txt_hello.setText(getString(R.string.str_hi) + " " + user_name + ",");
		
		btn_menu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(InviteFriendsActivity.this, MenuActivity.class);
				startActivity(intent);
				Log.d(TAG, "Menu!");
			}
		});
		

		btn_invite_friends.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent emailIntent = new Intent(Intent.ACTION_SEND);
				emailIntent.setType("text/plain");
				emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
				emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
				emailIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.str_invite_content));
				startActivity(Intent.createChooser(emailIntent, "Send mail..."));
			}
		});
		
		WaitThread waitThread = new WaitThread();
		waitThread.execute("");
        
    }
    
    class WaitThread extends AsyncTask<String, Void, JSONObject> {
		@Override
		protected JSONObject doInBackground(String... urls) {
			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				
			}
			return null;
		}

		@Override
		protected void onPostExecute(JSONObject result) {

			txt_profile_percent.setText("48%");
			img_user_state.setImageResource(R.drawable.progress_logo_middle);	
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
   
}
