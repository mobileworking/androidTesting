package com.Example.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Example.constants.Global;
import com.Example.countrycodepicker.CountryPicker;
import com.Example.countrycodepicker.CountryPickerListener;
import com.Example.library.HTTPUtils;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginPhoneActivity extends FragmentActivity {
	
	private static final String TAG = "LoginPhoneActivity";
	
	private Handler mHandler = new Handler();

	private int scrWid;
	private int scrHei;
	private static Dialog dia_error;
	private static Dialog dia_waiting;
	
	Button btn_nav_back;
	RelativeLayout btn_country_code;
	TextView txt_country_code;
	TextView txt_pin;
	EditText edit_phone_number;
	Button btn_next;
	RelativeLayout relativelayout_country_code;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_phone);
        
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
		
        btn_nav_back = (Button)findViewById(R.id.button_nav_back);
        btn_country_code = (RelativeLayout)findViewById(R.id.relativeLayout_country_code);
        txt_country_code = (TextView)findViewById(R.id.textView_country_code);
        txt_pin = (TextView)findViewById(R.id.textView_select_info);
        edit_phone_number = (EditText)findViewById(R.id.editText_phone_number);
        btn_next = (Button)findViewById(R.id.button_next);
        relativelayout_country_code = (RelativeLayout)findViewById(R.id.relativelayout_bottom);   
        
        relativelayout_country_code.setVisibility(View.INVISIBLE);
        upKeyboard();
        
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        CountryPicker picker = new CountryPicker();
        picker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode) {
                
                txt_country_code.setText(dialCode);
            }
        });

        transaction.replace(R.id.linearlayout_picker, picker);
        transaction.commit();
        
        btn_nav_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				finish();
			}
		});
        
        btn_country_code.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				relativelayout_country_code.setVisibility(View.VISIBLE);
		        downKeyboard();
			}
		});
        
        edit_phone_number.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				relativelayout_country_code.setVisibility(View.INVISIBLE);
			}
		});
        
        btn_next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (edit_phone_number.getText().length() == 0) {
					relativelayout_country_code.setVisibility(View.INVISIBLE);
			        upKeyboard();
					return;
				} else {
					
					relativelayout_country_code.setVisibility(View.INVISIBLE);
					
					if (!isNetworkConnected()) {
						showNetworkError();
					} else {
						updateDBSetting(Global.LOCAL_VALUE_SETTINGS_PHONE_NUMBER);
						showLoading();
						onSendPINCode();
					}
				}
			}
		});
        
        edit_phone_number.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
				if (count != 0) {
					btn_next.setBackgroundResource(R.drawable.btn_login_phone);
				} 
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (edit_phone_number.getText().length() == 0) {
					btn_next.setBackgroundResource(R.drawable.roundrect_shape_phone_empty);
				}
			}
		});
        
    }

    public void downKeyboard() {
	   	InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(edit_phone_number.getApplicationWindowToken(), 0);
    }
   
    public void upKeyboard() {
	   	InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
    	imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
   
    
    //================================= Check Country Code =======================================
    
	
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
			((TextView) dia_error.findViewById(R.id.textview_error_title)).setText(getString(R.string.str_error_sending_pin));
			((TextView) dia_error.findViewById(R.id.textView_error_content)).setText(getString(R.string.str_phone_number_invalid));
			dia_error.show();
		}
	};
	
	private void showNetworkError() {
		mHandler.post(showNetworkError);
	}
	private Runnable showNetworkError = new Runnable(){
		@Override
		public void run() {
			((TextView) dia_error.findViewById(R.id.textview_error_title)).setText(getString(R.string.str_error_network_title));
			((TextView) dia_error.findViewById(R.id.textView_error_content)).setText(getString(R.string.str_error_network_content));
			dia_error.show();
		}
	};
	
	//================================= Check Network State =======================================
	
  	private boolean isNetworkConnected() {
  		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
  		NetworkInfo ni = cm.getActiveNetworkInfo();
  		if (ni == null) {
  			// There are no active networks.
  			return false;
  		} else
  			return true;
  	}
  	
  	//================================= Sending PIN Code =======================================
	
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
  	
	public void onSendPINCode() {

		Thread sendPINCodeThread = new Thread(new Runnable() {
			@Override
			public void run() {
				String country_code = txt_country_code.getText().toString();
				String phone_number = edit_phone_number.getText().toString();

				StringBuilder sb = new StringBuilder(country_code);
				sb = sb.deleteCharAt(0);
				country_code = sb.toString();

				Log.d(TAG, "Phone_number: " + phone_number);
				
				String responseSendPINCode = HTTPUtils.HTTPPost(Global.CREATE_URL, Global.locale,
													Global.KEY_PHONE, phone_number,
													Global.KEY_INTERCODE, country_code);
				
				Log.d(TAG, "URL: " + Global.CREATE_URL + ", RESPONSE : " + responseSendPINCode);
				
				processSendPINCode(responseSendPINCode);
			}
		});
		sendPINCodeThread.start();
	}
	
	public void processSendPINCode(String response){
		
		dismissLoading();
		
		if (response == null || response.equals("")) {
			showError();
			return;
		} else {
			
			JSONObject json = null;
			try {
				json = new JSONObject(response);

				String pin = json.getString(Global.KEY_PIN);
				String token = json.getString(Global.KEY_PIN);

				txt_pin.setText(pin);

//				String code = onCheckNull(panel, Global.KEY_CODE);
				
//				if (code.equals("200")) {
//					Intent intent = new Intent(LoginPhoneActivity.this, LoginVerifyActivity.class);
//					intent.putExtra(Global.PHONE_NUMBER, txt_country_code.getText().toString() + " " + edit_phone_number.getText().toString());
//					intent.putExtra(Global.REAL_NUMBER, txt_country_code.getText().toString() + edit_phone_number.getText().toString());
//					intent.putExtra(Global.USER_STATE, Global.NEW_KUGGLE);
//
//					startActivity(intent);
//					Log.d(TAG, "Login Verify");
//
//					return;
//				} else {
//					showError();
//					return;
//				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				showError();
				return;
			}
			
		}
	}
	
	public void updateDBSetting(String info) {
		
		String sql = "update " + Global.LOCAL_TABLE_SETTINGS_INFO + " set "
				+ Global.LOCAL_FIELD_SETTINGS_LOGIN + " = ? , "
				+ Global.LOCAL_FIELD_SETTINGS_LOGOUT + " = ? where " + Global.LOCAL_FIELD_SETTINGS_UPDATE + "=" + Global.LOCAL_VALUE_SETTINGS_UPDATE;
		Object[] args = new Object[] { info, Global.LOCAL_VALUE_SETTINGS_NO };
		Global.dbService.execSQL(sql, args);
	}
   
}
