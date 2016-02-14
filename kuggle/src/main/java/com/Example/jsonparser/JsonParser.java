package com.Example.jsonparser;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.Example.constants.FBUserInfo;
import com.Example.constants.Global;
import com.Example.constants.UserInfo;

import android.util.Log;

public class JsonParser implements Serializable{
	private static final long 	serialVersionUID = 1L;
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
	
	public static FBUserInfo getFBUserInfo(JSONObject json) {
		
		FBUserInfo info = new FBUserInfo();
		if (json == null)
			return null;
		
		try {
			JSONObject e1 = json.getJSONObject(Global.KEY_FROMFB);
			
			info.setUser_id(onCheckNull(e1, Global.KEY_ID));
			info.setUser_firstname(onCheckNull(e1, Global.KEY_FIRST_NAME));
			info.setUser_birthday(onCheckNull(e1, Global.KEY_BIRTHDAY));
			info.setUser_birthdaystring(onCheckNull(e1, Global.KEY_BIRTHDAYSTRING));
			info.setUser_accesstoken(onCheckNull(e1, Global.KEY_ACCESSTOKEN));
			info.setUser_gender(onCheckNull(e1, Global.KEY_GENDER));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e("log_tag", "Error parsing data "+e.toString());
		}
		return info;
	}
	
	public static UserInfo getUsersInfo(JSONObject json) {
		
		UserInfo info = new UserInfo();
		if (json == null)
			return null;
		
		try {
			JSONObject e1 = json.getJSONObject(Global.KEY_PROFILE);
			
			info.setUser_gender(onCheckNull(e1, Global.KEY_GENDER));
			info.setUser_firstname(onCheckNull(e1, Global.KEY_FIRST_NAME));
			info.setUser_dob(onCheckNull(e1, Global.KEY_DOB));
			info.setUser_invitecode(onCheckNull(e1, Global.KEY_INVITE_CODE));
			info.setUser_profilepic(onCheckNull(e1, Global.KEY_PROFILE_PIC));
			info.setUser_profilepicsmall(onCheckNull(e1, Global.KEY_PROFILE_PIC_SMALL));
			info.setUser_phonenumber(onCheckNull(e1, Global.KEY_PHONE_NUMBER));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e("log_tag", "Error parsing data "+e.toString());
		}
		
		return info;
	}
	

}
