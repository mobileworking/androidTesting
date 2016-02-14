package com.Example.constants;

import java.io.Serializable;

public class UserInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String user_gender = "";
	private String user_firstname = "";
	private String user_dob = "";
	private String user_invitecode = "";
	private String user_profilepic = "";
	private String user_profilepicsmall = "";
	private String user_phonenumber = "";
	
	
	public String getUser_gender() {
		return user_gender;
	}
	public void setUser_gender(String user_gender) {
		this.user_gender = user_gender;
	}
	public String getUser_firstname() {
		return user_firstname;
	}
	public void setUser_firstname(String user_firstname) {
		this.user_firstname = user_firstname;
	}
	public String getUser_dob() {
		return user_dob;
	}
	public void setUser_dob(String user_dob) {
		this.user_dob = user_dob;
	}
	public String getUser_invitecode() {
		return user_invitecode;
	}
	public void setUser_invitecode(String user_invitecode) {
		this.user_invitecode = user_invitecode;
	}
	public String getUser_profilepic() {
		return user_profilepic;
	}
	public void setUser_profilepic(String user_profilepic) {
		this.user_profilepic = user_profilepic;
	}
	public String getUser_profilepicsmall() {
		return user_profilepicsmall;
	}
	public void setUser_profilepicsmall(String user_profilepicsmall) {
		this.user_profilepicsmall = user_profilepicsmall;
	}
	public String getUser_phonenumber() {
		return user_phonenumber;
	}
	public void setUser_phonenumber(String user_phonenumber) {
		this.user_phonenumber = user_phonenumber;
	}
	
}
