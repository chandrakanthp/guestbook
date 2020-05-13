package com.app.guestbook.service;

import com.app.guestbook.model.GuestNotesDetails;

public interface GuestAppService {

	public int insertNotes(GuestNotesDetails notes);

	public GuestNotesDetails[] viewAllNotes();

	public int approveRejectNotes(int id, String value);

	public GuestNotesDetails getImage(String id);
}
