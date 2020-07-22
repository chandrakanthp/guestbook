package com.app.guestbook.service;

import java.util.List;

import com.app.guestbook.model.GuestNotesDetails;

public interface GuestAppService {

	public int insertNotes(GuestNotesDetails notes);

	public List<GuestNotesDetails> viewAllNotes();
	
	public int approveRejectNotes(int id, String value);

	public GuestNotesDetails getImage(String id);
}
