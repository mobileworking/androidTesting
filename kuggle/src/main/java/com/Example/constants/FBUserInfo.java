package com.Example.constants;

import java.io.Serializable;

public class FBUserInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String user_id = "";
	private String user_firstname = "";
	private String user_birthday = "";
	private String user_birthdaystring = "";
	private String user_accesstoken = "";
	private String user_gender = "";
	
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_firstname() {
		return user_firstname;
	}
	public void setUser_firstname(String user_firstname) {
		this.user_firstname = user_firstname;
	}
	public String getUser_birthday() {
		return user_birthday;
	}
	public void setUser_birthday(String user_birthday) {
		this.user_birthday = user_birthday;
	}
	public String getUser_birthdaystring() {
		return user_birthdaystring;
	}
	public void setUser_birthdaystring(String user_birthdaystring) {
		this.user_birthdaystring = user_birthdaystring;
	}
	public String getUser_accesstoken() {
		return user_accesstoken;
	}
	public void setUser_accesstoken(String user_accesstoken) {
		this.user_accesstoken = user_accesstoken;
	}
	public String getUser_gender() {
		return user_gender;
	}
	public void setUser_gender(String user_gender) {
		this.user_gender = user_gender;
	}
	
	
	
}
