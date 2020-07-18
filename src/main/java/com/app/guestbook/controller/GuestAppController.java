package com.app.guestbook.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.app.guestbook.model.GuestNotesDetails;
import com.app.guestbook.model.UserLoginInfo;
import com.app.guestbook.service.GuestAppService;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

/**
 * 
 * @author CHANDRAKANTH
 * It is the main controller class which help in routing based on the path
 *
 */
@Controller
public class GuestAppController {

	@Autowired
	GuestAppService appService;
	
	Logger logger = LogManager.getLogger(this.getClass());
	
	/**
	 * 
	 * @param model Spring Attribute
	 * @param principal Spring Attribute
	 * @param userLoginInfo Model/POM attribute
	 * @return Based upon the role, 
	 * if the role contains ADMIN then its redirect to viewAllNotes page
	 * else its redirects to guestNotes page
	 */
	@GetMapping("/")
	public RedirectView homePage(Model model, Principal principal,@ModelAttribute UserLoginInfo userLoginInfo) {
		logger.debug("Start of homePage");
		getLogedUser();
		Collection<SimpleGrantedAuthority> roles = getRoles();
		if(roles.stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN")))
		{
			logger.debug("Admin role, so redirecting to viewAllNotes page");
			return new RedirectView("/viewAllNotes");
		}
		logger.debug("End of homePage");
		return new RedirectView("/guestNotes");
	 }
	
	
	/**
	 * 
	 * @param model Spring Attribute
	 * @param principal Spring Attribute
	 * @param userLoginInfo Model/POM attribute
	 * @return login page
	 */
	@GetMapping("/login")
	public String index(Model model, Principal principal,@ModelAttribute UserLoginInfo userLoginInfo) {
		logger.debug("start of index method of Login path user: {}",userLoginInfo.getUsername());
		return "login";
	}
  
	/**
	 * 
	 * @param model Spring Attribute
	 * @param guestNotesDetails Model/POM attribute
	 * @return guestEntry page where user can input the text (or) upload image to add
	 */
	@GetMapping("/guestNotes")
	public String guestNotes(Model model,@ModelAttribute GuestNotesDetails guestNotesDetails) {
		logger.debug("Start of guestNotes method GuestNotesDetails: {} ",guestNotesDetails.getNotes());
		model.addAttribute("message", "You are logged in as ");
		logger.debug("End of guestNotes method");
		return "guestEntry";
	}

  
	/**
	 * 
	 * @param request Spring Attribute
	 * @param model Spring Attribute
	 * @param guestNotesDetails ModelAttribute
	 * @return guestEntry page
	 * It takes the input text (or/and) image file, if it inserted into DB then it will send success message or Error
	 */
	@PostMapping("/insertNotes")
	public String insertNotes(HttpServletRequest request, Model model,@ModelAttribute GuestNotesDetails guestNotesDetails) {
		logger.debug("Start of insertNotes method");
		guestNotesDetails.setUsername(getLogedUser());
		int resStatus = appService.insertNotes(guestNotesDetails);
		logger.debug("resStatus : {} ",resStatus);
		request.setAttribute("resStatus", (resStatus==1?"Added Successfully":"Error while adding"));
		logger.debug("End of insertNotes method");
		return "guestEntry";
	}
  
	/**
	 * 
	 * @param request Spring Attribute
	 * @param model Spring Attribute
	 * @return viewNotes page
	 * It will get the all the guest notes, this functionality is for ADMIN role
	 */
	@GetMapping("/viewAllNotes")
	public String viewAllNotes(HttpServletRequest request, Model model) {
		logger.debug("Start of viewAllNotes method");
		GuestNotesDetails[] guestNotesDetails =  appService.viewAllNotes();
		//request.setAttribute("guestNotesDetails", guestNotesDetails);
		//request.setAttribute("status", request.getParameter("status"));	
		model.addAttribute("guestNotesDetails", guestNotesDetails);
		model.addAttribute("status", request.getParameter("status") !=null?request.getParameter("status"):"");

		logger.debug("End of viewAllNotes method");
		return "viewNotes";
	}
  
	/**
	 * 
	 * @param request Spring Attribute
	 * @param response Spring Attribute
	 * @param model Spring Attribute
	 * @return redirects to viewAllNotes page
	 * @throws IOException
	 * This method will call from ViewAllNotes page, when we perform Approve/Reject the guest details, this functionality for ADMIN role
	 */
	@GetMapping("/approveReject")
	public RedirectView approveReject(HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
		logger.debug("Start of approveReject method");
		logger.debug("ID: {} ",request.getParameter("id"));
		logger.debug("status: {} ",request.getParameter("status"));
		String status="N";
		if(request.getParameter("status").equalsIgnoreCase("approve"))
			status="A";
		else  if(request.getParameter("status").equalsIgnoreCase("remove"))
			status="R";
		else
		{
			request.setAttribute("status", "Error");
			return new RedirectView("/viewAllNotes?status=Error");
		}
		
		int returnSts =  appService.approveRejectNotes(Integer.parseInt(request.getParameter("id")),status);
		logger.debug("End of approveReject method : returnSts : "+returnSts);
		if(returnSts ==0)
		{
			request.setAttribute("status", "Error");
		}
		return new RedirectView("/viewAllNotes?status="+(returnSts==1?"Updated Successfully":"Error"));   
	}
  
	/**
	 * 
	 * @return index page
	 * This method is written just for testing the role, no business functionality 
	 */
	@GetMapping("/roles")
	public String roles() {
		logger.debug("Start of roles method");
		getRoles();
		logger.debug("End of roles method");
		return "index";
	}
  
	/**
	 * 
	 * @param request Spring Attribute
	 * @param id pass the image id to download the image
	 * @return the image contain to downloadFile page, where it will download and show the image
	 */
	@GetMapping("/viewImage")
	public String viewImage(HttpServletRequest request,@RequestParam String id,Model model) {
		logger.debug("Start of viewImage method notes id : {} ",id);
		GuestNotesDetails data = appService.getImage(id);
		model.addAttribute("guestNotesDetails", data);
		logger.debug("End of viewImage method");
		return "downloadFile";
	}

	/**
	 * 
	 * @return all the roles for logged in user
	 */
	Collection<SimpleGrantedAuthority> getRoles()
	{
		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		logger.debug("authorities : {}",authorities);
		return authorities;
	}
  
	/**
	 * 
	 * @return logged in user id
	 */
	String getLogedUser()
	{
		Object principal = SecurityContextHolder.getContext(). getAuthentication(). getPrincipal();
		if (principal instanceof UserDetails) {
			String username = ((UserDetails)principal). getUsername();
			logger.debug("User:{}", username);
			return username;
		}
		return "default";
	}
}
