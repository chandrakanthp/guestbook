package com.app.guestbook.model;

/**
 * 
 * @author CHANDRAKANTH
 * This class had the information of user details
 *
 */
public class UserLoginInfo {

	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "UserLoginInfo [username=" + username + ", password=" + password + "]";
	}	
}
