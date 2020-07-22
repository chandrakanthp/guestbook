package com.app.guestbook.dao;

import java.util.List;

import com.app.guestbook.model.GuestNotesDetails;

public interface GuestAppDao {

	public int insertNotes(GuestNotesDetails notes);

	public List<GuestNotesDetails> viewAllNotes();

	public int approveRejectNotes(int id, String value);

	public GuestNotesDetails getImage(String id);

}
