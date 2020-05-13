package com.app.guestbook.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.guestbook.dao.GuestAppDao;
import com.app.guestbook.model.GuestNotesDetails;

@Service
public class GuestAppServiceImpl implements GuestAppService{

	@Autowired
	GuestAppDao appDao;
	
	Logger logger = Logger.getLogger(this.getClass());

	public int insertNotes(GuestNotesDetails notes)
	{
		return appDao.insertNotes(notes);
	}
	
	
	public GuestNotesDetails[] viewAllNotes()
	{
		return appDao.viewAllNotes();
	}
	
	public int approveRejectNotes(int id, String value)
	{
		return appDao.approveRejectNotes(id,value);

	}
	
	public GuestNotesDetails getImage(String id)
	{
		return appDao.approveRejectNotes(id);
	}
}
