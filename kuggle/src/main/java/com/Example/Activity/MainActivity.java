package com.Example.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Example.constants.Global;
import com.Example.jsonparser.JsonParser;
import com.Example.library.HTTPUtils;
import com.Example.library.NetworkHelper;
import com.Example.library.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class MainActivity extends Activity {
	
	private static final String TAG = "MainActivity";
	
	RelativeLayout btn_watch_ad;
	LoginButton btn_login_facebook;
	Button btn_login_phone;
	private UiLifecycleHelper uiHelper;
	
	public String facebook_accessToken;
	public String facebook_username;
	
	private Handler mHandler = new Handler();

	private int scrWid;
	private int scrHei;
	private static Dialog dia_error;
	private static Dialog dia_waiting;
	
	public String temp_token;
	public String temp_name;
	public String error_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
        
        btn_login_facebook = (LoginButton)findViewById(R.id.button_login_facebook);
		btn_login_facebook.setBackgroundResource(R.drawable.btn_login_facebook);
		btn_login_facebook.setTextColor(getResources().getColor(R.color.white));
		btn_login_facebook.setTextSize(15);
		btn_login_facebook.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
		btn_login_facebook.setReadPermissions(Arrays.asList("email","public_profile","user_friends","user_birthday"));
        
		error_msg = getString(R.string.str_error_login_content);
		
		DisplayMetrics mec = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(mec);
		scrWid = mec.widthPixels;
		scrHei = mec.heightPixels;

		dia_error = new Dialog(this, R.style.CustomTheme);
		dia_error.setContentView(R.layout.dialog_phone_verify);
		Window drawWin = dia_error.getWindow();
		WindowManager.LayoutParams diaParam = drawWin.getAttributes();
		diaParam.gravity = Gravity.CENTER;
		drawWin.setAttributes(diaParam);
		dia_error.getWindow().setLayout(scrWid, scrHei  / 10 * 20 / 7);
		dia_error.getWindow().getAttributes().windowAnimations = R.drawable.slide_up;
		
		((RelativeLayout) dia_error.findViewById(R.id.relativelayout_alertok)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dia_error.dismiss();
			}
		});
		
		dia_waiting = new Dialog(this, R.style.CustomTheme);
		dia_waiting.setContentView(R.layout.dialog_wait);
		Window drawWin_waiting = dia_waiting.getWindow();
		WindowManager.LayoutParams diaParam_waiting = drawWin_waiting.getAttributes();
		diaParam_waiting.gravity = Gravity.CENTER;
		drawWin_waiting.setAttributes(diaParam_waiting);
		dia_waiting.getWindow().setLayout(scrWid, scrHei / 10 * 20 / 5);
		dia_waiting.getWindow().getAttributes().windowAnimations = R.drawable.slide_up;
        
        btn_watch_ad = (RelativeLayout)findViewById(R.id.relativelayout_video);
        btn_login_phone = (Button)findViewById(R.id.button_login_phone);
        
//        btn_watch_ad.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//
//				String videoId = Global.AD_VIDEO_ID;
//
//		        if (videoId == null || videoId.trim().equals("")) {
//		            return;
//		        }
//
//		        Intent lVideoIntent = new Intent(null, Uri.parse("ytv://" + videoId), MainActivity.this, OpenYouTubePlayerActivity.class);
//		        startActivity(lVideoIntent);
//			}
//		});
        
        
        btn_login_phone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(MainActivity.this, LoginPhoneActivity.class);
				startActivity(intent);
				Log.d(TAG, "Login with Phone");
				
			}
		});
        

    }

//================================ get Facebook User Info ================================
	
	
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };
 
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
    	if (session != null && session.isOpened()) {
    		Log.d("DEBUG", "facebook session is open ");
    		
    		facebook_accessToken = session.getAccessToken();
			Log.d(TAG, "Facebook access token : " + facebook_accessToken);
			
			updateDBFacebookToken(facebook_accessToken);
			
			showLoading();
        	onRegisterFacebook();
    	} 
    }
    
    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
    }
 
    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }
 
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }
 
    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }
 
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }
    
    public static void callFacebookLogout(Context context) {
        Session session = Session.getActiveSession();
        if (session != null) {

            if (!session.isClosed()) {
                session.closeAndClearTokenInformation();
                //clear your preferences if saved
            }
        } else {

            session = new Session(context);
            Session.setActiveSession(session);

            session.closeAndClearTokenInformation();
                //clear your preferences if saved

        }

    }
    
    
    //============================ Check Error ==========================================
    
    private void showLoading() {
		mHandler.post(showLoading);
	}
	private Runnable showLoading = new Runnable(){
		@Override
		public void run() {
			dia_waiting.show();
		}
	};
	
	private void dismissLoading() {
		mHandler.post(dismissLoading);
	}
	private Runnable dismissLoading = new Runnable(){
		@Override
		public void run() {
			dia_waiting.dismiss();
		}
	};
	
	//================================= Check Errors =======================================
	
	private void showError() {
		mHandler.post(showError);
	}
	private Runnable showError = new Runnable(){
		@Override
		public void run() {
			((TextView) dia_error.findViewById(R.id.textview_error_title)).setText(getString(R.string.str_error_facebook_title));
			((TextView) dia_error.findViewById(R.id.textView_error_content)).setText(getString(R.string.str_error_facebook_content));
			dia_error.show();
		}
	};
	
	private void showLoginError() {
		mHandler.post(showLoginError);
	}
	private Runnable showLoginError = new Runnable(){
		@Override
		public void run() {
			((TextView) dia_error.findViewById(R.id.textview_error_title)).setText(getString(R.string.str_error_login_title));
			((TextView) dia_error.findViewById(R.id.textView_error_content)).setText(error_msg);
			dia_error.show();
		}
	};
	
  	//================================= Register with Facebook =======================================
	
  	public static String onCheckNull( JSONObject e, String Key) {
		try {
			if (e.getString(Key) == null){
				return "";
			} else if ((e.getString(Key)).equals("")){
				return "";
			} else {
				return e.getString(Key);
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			return "";
		}
	}
  	
	public void onRegisterFacebook() {

		Thread registerFacebookThread = new Thread(new Runnable() {
			@Override
			public void run() {
				
				String token = facebook_accessToken;
				Log.d(TAG, "Facebook access token : " + token);
				
				String responseregisterFacebook = HTTPUtils.HTTPPost(Global.REGISTER_URL, Global.locale,
													Global.KEY_FBTOKEN, token);
				
				Log.d(TAG, "URL: " + Global.REGISTER_URL + ", RESPONSE : " + responseregisterFacebook);
				
				processRegisterFacebook(responseregisterFacebook);
			}
		});
		registerFacebookThread.start();
	}
	
	public void processRegisterFacebook(String response){
		
		dismissLoading();
		
		if (response == null || response.equals("")) {
			showLoginError();
			callFacebookLogout(this);
			return;
		} else {
			
			JSONObject json = null;
			try {
				json = new JSONObject(response);
				
				JSONObject panel = json.getJSONObject(Global.KEY_META);
				
				String code = onCheckNull(panel, Global.KEY_CODE);
				String token = onCheckNull(json, Global.KEY_TOKEN);
				
				Log.d(TAG, "Kuggle token : " + token);
				
				if (code.equals("200")) {
					
					Global.fbuserinfo = JsonParser.getFBUserInfo(json);
					
					updateDBSetting(Global.LOCAL_VALUE_SETTINGS_FACEBOOK);
					updateDBFacebook(token);
					
					Global.login_state = Global.LOCAL_VALUE_SETTINGS_FACEBOOK;
					
					Intent intent = new Intent(MainActivity.this, InviteFriendsActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    				intent.putExtra(Global.USER_NAME, Global.fbuserinfo.getUser_firstname());
    				startActivity(intent);
    				finish();
					
					return;
				} else {
					showLoginError();
					callFacebookLogout(this);
					return;
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				showLoginError();
				callFacebookLogout(this);
				return;
			}
			
		}
	}
	
	//================================= Login to kuggle ==========================================
	

	// async task to make network requests in separate thread
	public class HttpGetTask extends AsyncTask<String, Void, Response> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// display a ProgressDialog with message
		}

		@Override
		protected Response doInBackground(String... params) {
			// call method to initiate HTTP request

			String token = temp_token;
			Log.d(TAG, "Token : " + token);
			return NetworkHelper.doGet(params[0], Global.locale, token, getApplicationContext());
		}

		@Override
		protected void onPostExecute(Response result) {
			super.onPostExecute(result);
			// show the result
			if (result.getStatusCode() == Global.OK) {
				// showToast(result.getResponseText());

				String responseData = result.getResponseText();

				Log.d(TAG, "RESPONSE: " + responseData);

				processLoginKuggle(responseData);

			} else {
				String responseData = "";
				processLoginKuggle(responseData);
			}

		}

	}
	
	public void processLoginKuggle(String response){
		
		dismissLoading();
		
		if (response == null || response.equals("")) {
			showLoginError();
			return;
		} else {
			
			JSONObject json = null;
			try {
				json = new JSONObject(response);
				
				JSONObject panel = json.getJSONObject(Global.KEY_META);
				
				String code = onCheckNull(panel, Global.KEY_CODE);
//				String msg = onCheckNull(panel, Global.KEY_MSG);
				
				if (code.equals("200")) {
					
					Global.userinfo = JsonParser.getUsersInfo(json);
					
					Intent intent = new Intent(MainActivity.this, InviteFriendsActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    				intent.putExtra(Global.USER_NAME, Global.userinfo.getUser_firstname());
    				startActivity(intent);
    				finish();
					
					return;
				} else {
//					error_msg = msg;
					showLoginError();
					return;
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				showLoginError();
				return;
			}
			
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if (keyCode == KeyEvent.KEYCODE_BACK) {
	        moveTaskToBack(true);
	        return true;
	    }
		
		return super.onKeyDown(keyCode, event);
	}
	
	//============================== Check Local DB =====================================
	
	public void updateDBSetting(String info) {
		
		String sql = "update " + Global.LOCAL_TABLE_SETTINGS_INFO + " set "
				+ Global.LOCAL_FIELD_SETTINGS_LOGIN + " = ? , "
				+ Global.LOCAL_FIELD_SETTINGS_LOGOUT + " = ? where " + Global.LOCAL_FIELD_SETTINGS_UPDATE + "=" + Global.LOCAL_VALUE_SETTINGS_UPDATE;
		Object[] args = new Object[] { info, Global.LOCAL_VALUE_SETTINGS_NO };
		Global.dbService.execSQL(sql, args);
	}
	
	public void updateDBFacebookToken(String token) {
		
		String sql = "update " + Global.LOCAL_TABLE_FACEBOOK_INFO + " set "
				+ Global.LOCAL_FIELD_FACEBOOK_TOKEN + " = ? where " + Global.LOCAL_FIELD_SETTINGS_UPDATE + "=" + Global.LOCAL_VALUE_SETTINGS_UPDATE;
		Object[] args = new Object[] { token };
		Global.dbService.execSQL(sql, args);
		
	}
	
	public void updateDBFacebook(String token) {
		
		String sql = "update " + Global.LOCAL_TABLE_FACEBOOK_INFO + " set "
				+ Global.LOCAL_FIELD_FACEBOOK_TOKEN + " = ? , "
				+ Global.LOCAL_FIELD_FACEBOOK_NAME + " = ? , "
				+ Global.LOCAL_FIELD_TOKEN + " = ? where " + Global.LOCAL_FIELD_SETTINGS_UPDATE + "=" + Global.LOCAL_VALUE_SETTINGS_UPDATE;
		Object[] args = new Object[] { facebook_accessToken, Global.fbuserinfo.getUser_firstname(), token };
		Global.dbService.execSQL(sql, args);
	}
	
}
