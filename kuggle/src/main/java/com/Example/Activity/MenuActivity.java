package com.Example.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;


public class MenuActivity extends Activity {
	
	private static final String TAG = "MenuActivity";
	
	Button btn_nav_close;
	RelativeLayout btn_purpose;
	RelativeLayout btn_purchases;
	RelativeLayout btn_find_place;
	RelativeLayout btn_preferences;
	Button btn_spend;
	Button btn_share_facebook;
	Button btn_share_twitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);
        
        
        btn_nav_close = (Button)findViewById(R.id.button_nav_close);
        btn_purpose = (RelativeLayout)findViewById(R.id.relativeLayout_menu_purpose);
        btn_purchases = (RelativeLayout)findViewById(R.id.relativeLayout_menu_purchases);
        btn_find_place = (RelativeLayout)findViewById(R.id.relativeLayout_menu_find_place);
        btn_preferences = (RelativeLayout)findViewById(R.id.relativeLayout_menu_preferences);
        btn_spend = (Button)findViewById(R.id.button_spend);
        btn_share_facebook = (Button)findViewById(R.id.button_share_facebook);
        btn_share_twitter = (Button)findViewById(R.id.button_share_twitter);
        
        
        
        btn_nav_close.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				finish();
			}
		});
        
        btn_purpose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				
			}
		});
        
        btn_purchases.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				
			}
		});
        
        btn_find_place.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				
			}
		});
        

        
        btn_spend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
        
        btn_share_facebook.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				
			}
		});
        
        btn_share_twitter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				
			}
		});
        
        
    }


   
}
