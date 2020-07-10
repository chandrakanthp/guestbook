package com.app.guestbook;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.app.guestbook.dao.GuestAppDao;
import com.app.guestbook.dao.GuestAppDaoImpl;
import com.app.guestbook.model.GuestNotesDetails;
import com.app.guestbook.service.GuestAppServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class GuestbookDaoTest {
	
	@Mock
	JdbcTemplate template;   
	
	@Mock
	NamedParameterJdbcTemplate nameTemplate;    
	
	@InjectMocks
	GuestAppDaoImpl appDao;			
	
	
	@Test
	public void testInsertNotes()
	{
		GuestNotesDetails notes = new GuestNotesDetails("Testing Notes","chandra","2020-05-13 11:28:27","",3,null,null,null);		
		Mockito.when(nameTemplate.update(Mockito.any(String.class),Mockito.any(MapSqlParameterSource.class))).thenReturn(1);	
		assertEquals(appDao.insertNotes(notes), 1);		
	}
	
	@Test
	public void testviewAllNotes()
	{
		GuestNotesDetails notes[] = new GuestNotesDetails[1];
		notes[0]=	new GuestNotesDetails("Testing Notes","chandra","2020-05-13 11:28:27","",3,null,null,null);
		List list = new ArrayList();
		list.add(notes[0]);
		Mockito.when(nameTemplate.query(Mockito.any(String.class), Mockito.any(BeanPropertyRowMapper.class))).thenReturn(list);	
		assertEquals(appDao.viewAllNotes(),notes);		
	}
	
	@Test
	public void testApproveNotes()
	{		
		GuestNotesDetails notes = new GuestNotesDetails("Testing Notes","chandra","2020-05-13 11:28:27","",3,null,null,null);		
		Mockito.when(nameTemplate.update(Mockito.any(String.class),Mockito.any(MapSqlParameterSource.class))).thenReturn(1);	
		assertEquals(appDao.approveRejectNotes(notes.getNotes_details_id(),"A"), 1);
	}
	
	@Test
	public void testRejectNotes()
	{		
		GuestNotesDetails notes = new GuestNotesDetails("Testing Notes","chandra","2020-05-13 11:28:27","",3,null,null,null);		
		Mockito.when(nameTemplate.update(Mockito.any(String.class),Mockito.any(MapSqlParameterSource.class))).thenReturn(1);	
		assertEquals(appDao.approveRejectNotes(notes.getNotes_details_id(),"R"), 1);
	}
	
	@Test
	public void testGetImage() throws IOException 
	{
		BufferedImage result = new BufferedImage(600, 400, BufferedImage.TYPE_INT_RGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( result, "jpg", baos );
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		
		GuestNotesDetails notes[] = new GuestNotesDetails[1];
		notes[0]=	new GuestNotesDetails("Testing Notes","chandra","2020-05-13 11:28:27","",3,null,null,imageInByte);
		List list = new ArrayList();
		list.add(notes[0]);
	
		Mockito.when(nameTemplate.query(Mockito.any(String.class),Mockito.any(MapSqlParameterSource.class),Mockito.any(BeanPropertyRowMapper.class))).thenReturn(list);
        assertEquals(appDao.getImage("1"), notes[0]);		
	}	
}
