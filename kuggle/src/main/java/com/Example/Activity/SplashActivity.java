package com.Example.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.Example.constants.Global;
import com.Example.library.DBService;

import org.json.JSONObject;

import java.util.Locale;

public class SplashActivity extends Activity {
	
	private static final String TAG = "SplashActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		
		Global.dbService = new DBService(this);
		
		Global.logout_state = Global.LOCAL_VALUE_SETTINGS_YES;
		Global.login_state = Global.LOCAL_VALUE_SETTINGS_PHONE_NUMBER;
		
		Locale current = getResources().getConfiguration().locale;
		
		Global.locale = current.getLanguage();
		
		Log.d(TAG, "=======================================================================");
		Log.d(TAG, "Locale : " + Global.locale);
		
		onCheckSettings();
		onCheckFacebook();
		onCheckKuggle();
		
		WaitThread waitThread = new WaitThread();
		waitThread.execute("");
		
	}
	
	class WaitThread extends AsyncTask<String, Void, JSONObject> {
		@Override
		protected JSONObject doInBackground(String... urls) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				
			}
			return null;
		}

		@Override
		protected void onPostExecute(JSONObject result) {

			Intent intent = new Intent(SplashActivity.this, MainActivity.class);
			startActivity(intent);
			Log.d(TAG, "Start!");
			finish();
				
		}

	}
	
	@SuppressWarnings("deprecation")
	public void onCheckSettings() {
    	
    	String sql = "select * from " + Global.LOCAL_TABLE_SETTINGS_INFO;
		Cursor cursor = Global.dbService.query(sql, null);
		startManagingCursor(cursor);
		if (cursor == null) {

			String sql_settings = "insert into " + Global.LOCAL_TABLE_SETTINGS_INFO + " ("
					+ Global.LOCAL_FIELD_SETTINGS_LOGIN + ", "
					+ Global.LOCAL_FIELD_SETTINGS_LOGOUT + ", "
					+ Global.LOCAL_FIELD_SETTINGS_UPDATE
					+ ") values(?,?,?)";
			Object[] args = new Object[] { Global.LOCAL_VALUE_SETTINGS_NO, Global.LOCAL_VALUE_SETTINGS_NO, Global.LOCAL_VALUE_SETTINGS_UPDATE };
			Global.dbService.execSQL(sql_settings, args);
			return;
		} else {
			int nums = cursor.getCount();
			if (nums == 0) {
				stopManagingCursor(cursor);
				cursor.close();
				
				String sql_settings = "insert into " + Global.LOCAL_TABLE_SETTINGS_INFO + " ("
						+ Global.LOCAL_FIELD_SETTINGS_LOGIN + ", "
						+ Global.LOCAL_FIELD_SETTINGS_LOGOUT + ", "
						+ Global.LOCAL_FIELD_SETTINGS_UPDATE
						+ ") values(?,?,?)";
				Object[] args = new Object[] { Global.LOCAL_VALUE_SETTINGS_NO, Global.LOCAL_VALUE_SETTINGS_NO, Global.LOCAL_VALUE_SETTINGS_UPDATE };
				Global.dbService.execSQL(sql_settings, args);

				return;
			} else {
				while (cursor.moveToNext()) {
					Global.login_state = cursor.getString(1);
					Global.logout_state = cursor.getString(2);
				}
				
				stopManagingCursor(cursor);
				cursor.close();
				return;
			}
		}
    }
	
	@SuppressWarnings("deprecation")
	public void onCheckFacebook() {
    	
		String access_token = "", name = "", token = "";

		String sql = "select * from " + Global.LOCAL_TABLE_FACEBOOK_INFO;
		Cursor cursor = Global.dbService.query(sql, null);
		startManagingCursor(cursor);
		if (cursor == null) {
			
			String sql_settings = "insert into " + Global.LOCAL_TABLE_FACEBOOK_INFO + " ("
					+ Global.LOCAL_FIELD_FACEBOOK_TOKEN + ", "
					+ Global.LOCAL_FIELD_FACEBOOK_NAME + ", "
					+ Global.LOCAL_FIELD_TOKEN + ", "
					+ Global.LOCAL_FIELD_SETTINGS_UPDATE
					+ ") values(?,?,?,?)";
			Object[] args = new Object[] { Global.LOCAL_VALUE_SETTINGS_NO, Global.LOCAL_VALUE_SETTINGS_NO, Global.LOCAL_VALUE_SETTINGS_NO, Global.LOCAL_VALUE_SETTINGS_UPDATE };
			Global.dbService.execSQL(sql_settings, args);
			
			return;
		} else {
			int nums = cursor.getCount();
			if (nums == 0) {
				stopManagingCursor(cursor);
				cursor.close();
				
				String sql_settings = "insert into " + Global.LOCAL_TABLE_FACEBOOK_INFO + " ("
						+ Global.LOCAL_FIELD_FACEBOOK_TOKEN + ", "
						+ Global.LOCAL_FIELD_FACEBOOK_NAME + ", "
						+ Global.LOCAL_FIELD_TOKEN + ", "
						+ Global.LOCAL_FIELD_SETTINGS_UPDATE
						+ ") values(?,?,?,?)";
				Object[] args = new Object[] { Global.LOCAL_VALUE_SETTINGS_NO, Global.LOCAL_VALUE_SETTINGS_NO, 
												Global.LOCAL_VALUE_SETTINGS_NO, Global.LOCAL_VALUE_SETTINGS_UPDATE };
				Global.dbService.execSQL(sql_settings, args);
				
				return;
			} else {
				while (cursor.moveToNext()) {

					access_token = cursor.getString(1);
					name = cursor.getString(2);
					token = cursor.getString(3);

					Global.facebook_name = name;
					Global.facebook_token = token;

				}
				stopManagingCursor(cursor);
				cursor.close();
				// dbService.close();
				return;
			}
		}
    }
	
	@SuppressWarnings("deprecation")
	public void onCheckKuggle() {
    	
		@SuppressWarnings("unused")
		String name = "", gender = "", birthday = "", token="";

		String sql = "select * from " + Global.LOCAL_TABLE_USER_INFO;
		Cursor cursor = Global.dbService.query(sql, null);
		startManagingCursor(cursor);
		if (cursor == null) {
			
			String sql_settings = "insert into " + Global.LOCAL_TABLE_USER_INFO + " ("
					+ Global.LOCAL_FIELD_NAME + ", "
					+ Global.LOCAL_FIELD_GENDER + ", "
					+ Global.LOCAL_FIELD_BIRTHDAY + ", "
					+ Global.LOCAL_FIELD_TOKEN + ", "
					+ Global.LOCAL_FIELD_SETTINGS_UPDATE
					+ ") values(?,?,?,?,?)";
			Object[] args = new Object[] { Global.LOCAL_VALUE_SETTINGS_NO, Global.LOCAL_VALUE_SETTINGS_NO, 
											Global.LOCAL_VALUE_SETTINGS_NO, Global.LOCAL_VALUE_SETTINGS_NO, 
											Global.LOCAL_VALUE_SETTINGS_UPDATE };
			Global.dbService.execSQL(sql_settings, args);
			
			return;
		} else {
			int nums = cursor.getCount();
			if (nums == 0) {
				stopManagingCursor(cursor);
				cursor.close();
				
				String sql_settings = "insert into " + Global.LOCAL_TABLE_USER_INFO + " ("
						+ Global.LOCAL_FIELD_NAME + ", "
						+ Global.LOCAL_FIELD_GENDER + ", "
						+ Global.LOCAL_FIELD_BIRTHDAY + ", "
						+ Global.LOCAL_FIELD_TOKEN + ", "
						+ Global.LOCAL_FIELD_SETTINGS_UPDATE
						+ ") values(?,?,?,?,?)";
				Object[] args = new Object[] { Global.LOCAL_VALUE_SETTINGS_NO, Global.LOCAL_VALUE_SETTINGS_NO, 
												Global.LOCAL_VALUE_SETTINGS_NO, Global.LOCAL_VALUE_SETTINGS_NO, 
												Global.LOCAL_VALUE_SETTINGS_UPDATE };
				Global.dbService.execSQL(sql_settings, args);
				
				return;
			} else {
				while (cursor.moveToNext()) {

					name = cursor.getString(1);
					gender = cursor.getString(2);
					birthday = cursor.getString(3);
					token = cursor.getString(4);

					Global.g_name = name;
					Global.g_token = token;

				}
				stopManagingCursor(cursor);
				cursor.close();
				// dbService.close();
				return;
			}
		}
    }
	
}
