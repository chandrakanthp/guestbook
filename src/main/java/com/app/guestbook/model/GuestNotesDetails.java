package com.app.guestbook.model;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author CHANDRAKANTH
 * This is DTO class, where getter/setter are implemented
 *
 */
public class GuestNotesDetails {
	
	private String notes;
	private String username;
	private Date dateTime;
	private String approveStatus;
	private int notes_details_id ;
	private MultipartFile imageFile;
	private String image_file_name;
	private byte[] image;	
	
	public GuestNotesDetails() {
		
	}
	
	public GuestNotesDetails(String notes, String username, Date dateTime, String approveStatus, int notes_details_id,
			MultipartFile imageFile, String image_file_name, byte[] image) {
		super();
		this.notes = notes;
		this.username = username;
		this.dateTime = dateTime;
		this.approveStatus = approveStatus;
		this.notes_details_id = notes_details_id;
		this.imageFile = imageFile;
		this.image_file_name = image_file_name;
		this.image = image;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getImage_file_name() {
		return image_file_name;
	}

	public void setImage_file_name(String image_file_name) {
		this.image_file_name = image_file_name;
	}

	public MultipartFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}

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
	public Date getDateTime() {
		return dateTime;
	}

	public String toDateFormat() {
	
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY HH:mm:ss");
			return sdf.format(getDateTime());
		}
		catch(Exception e) {}
		return "";
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	public String toString() {
		return "GuestNotesDetails [notes=" + notes + ", username=" + username + ", dateTime=" + dateTime
				+ ", approveStatus=" + approveStatus + ", notes_details_id=" + notes_details_id + ", imageFile="
				+ imageFile + ", image_file_name=" + image_file_name + "]";
	}
}
