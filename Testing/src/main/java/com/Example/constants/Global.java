package com.Example.constants;

import com.Example.library.DBService;


public class Global
{
	public static UserInfo userinfo 								= null;
	public static FBUserInfo fbuserinfo 							= null;
	
	public static final String USER_NAME							= "user_name";

	/*
	 * API URL.	
	 */
	public static final String SERVER_URL 							= "http://bebetrack.com/api";
	public static final String REGISTER_URL 						= String.format("%s/register", SERVER_URL);
	public static final String CREATE_URL 							= String.format("%s/create", SERVER_URL);

	/*
	 * API Key.	
	 */
	
	public static final String KEY_FBTOKEN 							= "fbtoken";
	public static final String KEY_PHONE_NUMBER						= "phonenumber";
	public static final String KEY_PHONE							= "phone";
	public static final String KEY_INTERCODE						= "internationalCode";
	public static final String KEY_PIN	 							= "pin";
	public static final String KEY_FIRST_NAME						= "firstname";
	public static final String KEY_DOB								= "dob";
	public static final String KEY_GENDER 							= "gender";
	public static final String KEY_PURPOSE 							= "purpose";
	public static final String KEY_LAT 								= "lat";
	public static final String KEY_LNG 								= "lng";
	public static final String KEY_TYPE 							= "type";
	
	public static final String VALUE_NEED_HELP						= "Need Help with studies!";
	public static final String VALUE_HELP 							= "help";
	
	public static final String KEY_META 							= "meta";
	public static final String KEY_CODE 							= "code";
	public static final String KEY_MSG	 							= "msg";
	public static final String KEY_TOKEN 							= "token";
	public static final String KEY_METHOD 							= "method";
	
	public static final String KEY_PROFILE_CREATED					= "profilecreated";
	public static final String KEY_PROFILE_LINKED					= "profilelinked";
	public static final String KEY_PROFILE 							= "profile";
	public static final String KEY_INVITE_CODE						= "invitecode";
	public static final String KEY_PROFILE_PIC						= "profilepic";
	public static final String KEY_PROFILE_PIC_SMALL				= "profilepicsmall";
	
	public static final String KEY_FROMFB							= "fromfb";
	public static final String KEY_ID								= "id";
	public static final String KEY_BIRTHDAY							= "birthday";
	public static final String KEY_BIRTHDAYSTRING					= "birthdaystring";
	public static final String KEY_ACCESSTOKEN						= "accesstoken";
	
	/*
	 * Local DB.	
	 */
	public static DBService dbService 								= null;
	
	public static final String LOCAL_DB_NAME 						= "sample.db";
	public static final String LOCAL_TABLE_FACEBOOK_INFO 			= "sample_facebook_user_info_table";
	public static final String LOCAL_TABLE_USER_INFO 				= "sample_user_info_table";
	public static final String LOCAL_TABLE_SETTINGS_INFO 			= "sample_settings_info_table";
	
	public static final String LOCAL_FIELD_FACEBOOK_TOKEN 			= "access_token";
	public static final String LOCAL_FIELD_FACEBOOK_NAME 			= "facebook_name";
	
	public static final String LOCAL_FIELD_NAME						= "g_name";
	public static final String LOCAL_FIELD_GENDER					= "kuggle_gender";
	public static final String LOCAL_FIELD_BIRTHDAY 				= "kuggle_birthday";
	public static final String LOCAL_FIELD_TOKEN 					= "g_token";
	
	public static final String LOCAL_FIELD_SETTINGS_LOGIN 			= "login_state";
	public static final String LOCAL_FIELD_SETTINGS_LOGOUT 			= "logout_state";
	public static final String LOCAL_FIELD_SETTINGS_UPDATE 			= "settings";
	
	public static final String LOCAL_VALUE_SETTINGS_YES 			= "yes";
	public static final String LOCAL_VALUE_SETTINGS_NO 				= "no";
	public static final String LOCAL_VALUE_SETTINGS_FACEBOOK 		= "facebook";
	public static final String LOCAL_VALUE_SETTINGS_PHONE 			= "phone_number";
	public static final String LOCAL_VALUE_SETTINGS_PHONE_NUMBER	= "phone";
	public static final String LOCAL_VALUE_SETTINGS_UPDATE 			= "settings";
	public static final String LOCAL_VALUE_SETTINGS_INFO 			= "info";
	
	public static final String GENDER_MALE				 			= "Male";
	public static final String GENDER_FEMALE			 			= "Female";
	public static final String GENDER_OTHER				 			= "Other";
	
	/*
	 * 	
	 */
	
	public static String login_state								= "";
	public static String logout_state								= "";
	public static String g_token = "";
	public static String g_name = "";
	public static String facebook_name								= "";
	public static String facebook_token								= "";
	public static String locale										= "";
	
	/*
	 * Network error codes.	
	 */
	public static final int OK 										= 200;
	public static final int CONNECTION_ERROR 						= 201;
	public static final int SYSTEM_ERROR 							= 202;
	public static final int CONNECT_TIMEOUT 						= 203;
	public static final int READ_TIMEOUT 							= 204;
	public static final int IO_ERROR 								= 205;	
	public static final int CLIENT_PROTOCOL_ERROR 					= 206;
}
