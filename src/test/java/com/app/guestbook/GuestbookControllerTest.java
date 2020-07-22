package com.app.guestbook;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import  org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.app.guestbook.controller.GuestAppController;
import com.app.guestbook.model.GuestNotesDetails;
import com.app.guestbook.service.GuestAppService;

@RunWith(MockitoJUnitRunner.class)
public class GuestbookControllerTest {
	
	@Mock
	GuestAppService appService;    
	
	@InjectMocks
	GuestAppController appController;			
	
    private MockMvc mockMvc;
	
	  @Before
      public void onSetUp() {
          // add principal object to SecurityContextHolder
          User user = new User();
          user.setName("chandra");
          user.setPassword("guest@123");
          List<String> roles = new ArrayList<String>();
          roles.add("ADMIN");
          roles.add("GUEST");
          user.setRoles(roles);  
          Authentication auth = new UsernamePasswordAuthenticationToken(user,null);
          SecurityContextHolder.getContext().setAuthentication(auth);
          
          MockitoAnnotations.initMocks(this);
          this.mockMvc = MockMvcBuilders.standaloneSetup(appController).build(); 
      }
	
	@Test
	public void testHomePage() throws Exception
	{
		 mockMvc.perform(get("/")).andExpect(MockMvcResultMatchers.redirectedUrl("/guestNotes"));
	}	
	
	@Test
	public void testGuestNotes() throws Exception
	{      
		 mockMvc.perform(get("/guestNotes")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}
		
	@Test
	public void testInsertNotes() throws Exception
	{
		GuestNotesDetails notes = new GuestNotesDetails("Testing Notes","chandra",new Date(),"N",3,null,null,null);	
		Mockito.when(appService.insertNotes(Mockito.any(GuestNotesDetails.class))).thenReturn(1);	
		mockMvc.perform(post("/insertNotes").flashAttr("guestNotesDetails", notes)).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}
	
		
	@Test
	public void testViewAllNotes() throws Exception
	{
		List<GuestNotesDetails> notes = new ArrayList();
		GuestNotesDetails note=new GuestNotesDetails("Testing Notes","chandra",new Date(),"N",3,null,null,null);	
		notes.add(note);
		Mockito.when(appService.viewAllNotes()).thenReturn(notes);	
		mockMvc.perform(get("/viewAllNotes")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}
	

	@Test
	public void testApprove() throws Exception
	{
		GuestNotesDetails notes = new GuestNotesDetails("Testing Notes","chandra",new Date(),"N",3,null,null,null);
		Mockito.when(appService.approveRejectNotes(notes.getNotes_details_id(),"A")).thenReturn(1);	
		mockMvc.perform(get("/approveReject").param("status", "approve").param("id",notes.getNotes_details_id()+"" ))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}
	

	@Test
	public void testReject() throws Exception
	{
		GuestNotesDetails notes = new GuestNotesDetails("Testing Notes","chandra",new Date(),"N",3,null,null,null);
		Mockito.when(appService.approveRejectNotes(notes.getNotes_details_id(),"R")).thenReturn(1);	
		mockMvc.perform(get("/approveReject").param("status", "remove").param("id",notes.getNotes_details_id()+"" ))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}
	
	@Test
	public void testNegativeApprove() throws Exception
	{
		GuestNotesDetails notes = new GuestNotesDetails("Testing Notes","chandra",new Date(),"N",3,null,null,null);
		mockMvc.perform(get("/approveReject").param("status", "abc").param("id",notes.getNotes_details_id()+"" ))
		.andExpect(MockMvcResultMatchers.request().attribute("status", "Error"));
	}
		

	@Test
	public void testNegativeApprove2() throws Exception
	{
		GuestNotesDetails notes = new GuestNotesDetails("Testing Notes","chandra",new Date(),"N",3,null,null,null);
		Mockito.when(appService.approveRejectNotes(notes.getNotes_details_id(),"R")).thenReturn(0);
		mockMvc.perform(get("/approveReject").param("status", "remove").param("id",notes.getNotes_details_id()+"" ))
		.andExpect(MockMvcResultMatchers.request().attribute("status", "Error"));
	}
	
	@Test
	public void testViewImage() throws Exception
	{		
		BufferedImage result = new BufferedImage(600, 400, BufferedImage.TYPE_INT_RGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( result, "jpg", baos );
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		GuestNotesDetails guestNotesEntry = new GuestNotesDetails("Testing Notes","chandra",new Date(),"N",1,null,null,imageInByte);
		Mockito.when(appService.getImage("1")).thenReturn(guestNotesEntry);
		
		mockMvc.perform(get("/viewImage").param("id",guestNotesEntry.getNotes_details_id()+"" ))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}
	
	@Test
	public void testRoles() throws Exception
	{
		mockMvc.perform(get("/roles")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}
	
}
