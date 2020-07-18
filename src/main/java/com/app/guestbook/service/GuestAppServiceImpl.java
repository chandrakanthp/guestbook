package com.app.guestbook.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.guestbook.dao.GuestAppDao;
import com.app.guestbook.model.GuestNotesDetails;

/**
 * 
 * @author CHANDRAKANTH
 * This is the service method where the actual business logic available
 * and calls the repository for the action to be perform the db
 *
 */
@Service
public class GuestAppServiceImpl implements GuestAppService{

	@Autowired
	GuestAppDao appDao;
	
	Logger logger = LogManager.getLogger(this.getClass());
	
	/**
	 * @param GuestNotesDetails
	 * This insertNotes method base upon the user input details it call the insertNotes method of DAO/repository class
	 */
	public int insertNotes(GuestNotesDetails notes)
	{
		logger.debug("Inside insertNotes");
		return appDao.insertNotes(notes);
	}
	
	/**
	 * This viewAllNotes method helps in getting the all the details
	 */
	public GuestNotesDetails[] viewAllNotes()
	{
		logger.debug("Inside viewAllNotes");
		return appDao.viewAllNotes();
	}
	
	/**
	 * This approveRejectNotes method helps in approving/deleting the details of the Notes entered by guest
	 */
	public int approveRejectNotes(int id, String value)
	{
		logger.debug("Inside approveRejectNotes id : {}, value : {}",id,value);
		return appDao.approveRejectNotes(id,value);

	}
	
	/**
	 * This getImage method retrieve the image based on the id
	 */
	public GuestNotesDetails getImage(String id)
	{
		logger.debug("Inside getImage notes id: {}",id);
		return appDao.getImage(id);
	}
}
