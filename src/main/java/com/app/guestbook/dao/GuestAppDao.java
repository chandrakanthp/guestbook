package com.app.guestbook.dao;

import com.app.guestbook.model.GuestNotesDetails;

public interface GuestAppDao {

	public int insertNotes(GuestNotesDetails notes);

	public GuestNotesDetails[] viewAllNotes();

	public int approveRejectNotes(int id, String value);

}
