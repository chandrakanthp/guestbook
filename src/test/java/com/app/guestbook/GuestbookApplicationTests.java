package com.app.guestbook;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.app.guestbook.dao.GuestAppDao;
import com.app.guestbook.model.GuestNotesDetails;
import com.app.guestbook.service.GuestAppServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class GuestbookApplicationTests {
	
	@Mock
	GuestAppDao appDao;
	
	@InjectMocks
	GuestAppServiceImpl GuestAppService;
	

	@Test
	public void testViewAllNotes() {
		List<GuestNotesDetails> list = new ArrayList<GuestNotesDetails>();
		GuestNotesDetails guestNotesEntry1 = new GuestNotesDetails("Testing Notes","chandra","2020-05-13 10:28:27","",1,null,null,null);
		GuestNotesDetails guestNotesEntry2 = new GuestNotesDetails("Testing Notes1","chandra","2020-05-13 10:29:30","",2,null,null,null);
		list.add(guestNotesEntry1);
		list.add(guestNotesEntry2);
		
		GuestNotesDetails[] notesDetails = new GuestNotesDetails[list.size()];
		notesDetails = list.toArray(notesDetails);
		Mockito.when(appDao.viewAllNotes()).thenReturn(notesDetails);
        
        //test
		GuestNotesDetails[] viewDetails = GuestAppService.viewAllNotes();
         
        assertEquals(2, viewDetails.length);
        Mockito.verify(appDao, Mockito.times(1)).viewAllNotes();
		
	}

	@Test
	public void testInsertNotes() {
		GuestNotesDetails guestNotesEntry1 = new GuestNotesDetails("Testing Notes","chandra","2020-05-13 11:28:27","",3,null,null,null);
		Mockito.when(appDao.insertNotes(guestNotesEntry1)).thenReturn(1);
        assertEquals(1, GuestAppService.insertNotes(guestNotesEntry1));
        Mockito.verify(appDao, Mockito.times(1)).insertNotes(guestNotesEntry1);
		
	}
	
	@Test
	public void testApproveReject() {
		GuestNotesDetails guestNotesEntry = new GuestNotesDetails("Testing Notes","chandra","2020-05-13 11:29:27","",1,null,null,null);
		Mockito.when(appDao.approveRejectNotes(guestNotesEntry.getNotes_details_id(),"A")).thenReturn(1);
        assertEquals(1, GuestAppService.approveRejectNotes(guestNotesEntry.getNotes_details_id(),"A"));
        Mockito.verify(appDao, Mockito.times(1)).approveRejectNotes(1,"A");
	}
	
	@Test
	public void testApproveReject1() {
		GuestNotesDetails guestNotesEntry = new GuestNotesDetails("Testing Notes","chandra","2020-05-13 10:28:27","",1,null,null,null);
		Mockito.when(appDao.approveRejectNotes(guestNotesEntry.getNotes_details_id(),"R")).thenReturn(1);
        assertEquals(1, GuestAppService.approveRejectNotes(1,"R"));
	}
	
}
