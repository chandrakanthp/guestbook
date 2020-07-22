package com.app.guestbook;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

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
public class GuestbookServiceTests {
	
	@Mock
	GuestAppDao appDao;
	
	@InjectMocks
	GuestAppServiceImpl GuestAppService;
	

	@Test
	public void testViewAllNotes() {
		List<GuestNotesDetails> list = new ArrayList<GuestNotesDetails>();
		GuestNotesDetails guestNotesEntry1 = new GuestNotesDetails("Testing Notes","chandra",new Date(),"N",1,null,null,null);
		GuestNotesDetails guestNotesEntry2 = new GuestNotesDetails("Testing Notes1","chandra",new Date(),"N",2,null,null,null);
		list.add(guestNotesEntry1);
		list.add(guestNotesEntry2);
		
		GuestNotesDetails[] notesDetails = new GuestNotesDetails[list.size()];
		notesDetails = list.toArray(notesDetails);
		Mockito.when(appDao.viewAllNotes()).thenReturn(list);
        
        //test
		List<GuestNotesDetails> viewDetails = GuestAppService.viewAllNotes();
         
        assertEquals(2, viewDetails.size());
        Mockito.verify(appDao, Mockito.times(1)).viewAllNotes();
		
	}

	@Test
	public void testInsertNotes() {
		GuestNotesDetails guestNotesEntry = new GuestNotesDetails("Testing Notes","chandra",new Date(),"N",3,null,null,null);
		Mockito.when(appDao.insertNotes(guestNotesEntry)).thenReturn(1);
        assertEquals(1, GuestAppService.insertNotes(guestNotesEntry));
        Mockito.verify(appDao, Mockito.times(1)).insertNotes(guestNotesEntry);
		
	}
	
	@Test
	public void testApproveNotes() {
		GuestNotesDetails guestNotesEntry = new GuestNotesDetails("Testing Notes","chandra",new Date(),"N",1,null,null,null);
		Mockito.when(appDao.approveRejectNotes(guestNotesEntry.getNotes_details_id(),"A")).thenReturn(1);
        assertEquals(1, GuestAppService.approveRejectNotes(guestNotesEntry.getNotes_details_id(),"A"));
        Mockito.verify(appDao, Mockito.times(1)).approveRejectNotes(1,"A");
	}
	
	@Test
	public void testRejectNotes() {
		GuestNotesDetails guestNotesEntry = new GuestNotesDetails("Testing Notes","chandra",new Date(),"N",1,null,null,null);
		Mockito.when(appDao.approveRejectNotes(guestNotesEntry.getNotes_details_id(),"R")).thenReturn(1);
        assertEquals(1, GuestAppService.approveRejectNotes(1,"R"));
	}
	
	@Test
	public void testViewImage() throws IOException {
		//appDao.getImage(id);
		BufferedImage result = new BufferedImage(600, 400, BufferedImage.TYPE_INT_RGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( result, "jpg", baos );
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		GuestNotesDetails guestNotesEntry = new GuestNotesDetails("Testing Notes","chandra",new Date(),"N",1,null,null,imageInByte);
		Mockito.when(appDao.getImage("1")).thenReturn(guestNotesEntry);
        assertEquals(imageInByte, GuestAppService.getImage("1").getImage());		
	}
	
	@Test
	public void testNegativeInsertNotes() {
		//username should not be null
		GuestNotesDetails guestNotesEntry = new GuestNotesDetails("Testing please Ignore",null,new Date(),"N",3,null,null,null);
		Mockito.when(appDao.insertNotes(guestNotesEntry)).thenReturn(0);
        assertEquals(0, GuestAppService.insertNotes(guestNotesEntry));
        Mockito.verify(appDao, Mockito.times(1)).insertNotes(guestNotesEntry);
		
	}
	
}
