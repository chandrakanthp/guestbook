package com.app.guestbook.model;

public class GuestNotesDetails {
	
	private String notes;
	private String username;
	private String dateTime;
	private String approveStatus;
	private int notes_details_id ;	

	
	public int getNotes_details_id() {
		return notes_details_id;
	}

	public void setNotes_details_id(int notes_details_id) {
		this.notes_details_id = notes_details_id;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	
	

}
