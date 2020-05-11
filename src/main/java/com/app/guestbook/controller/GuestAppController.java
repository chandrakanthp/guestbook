package com.app.guestbook.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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


@Controller
public class GuestAppController {

	@Autowired
	GuestAppService appService;
	
	Logger logger = Logger.getLogger(this.getClass());
	
	@GetMapping("/")
	public RedirectView homePage(Model model, Principal principal,@ModelAttribute UserLoginInfo userLoginInfo) {
		logger.debug("in homepage");
		getLogedUser();
		Collection<SimpleGrantedAuthority> roles = getRoles();
		if(roles.stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN")))
		{
			return new RedirectView("/viewAllNotes");
		}
		return new RedirectView("/guestNotes");
	 }
	
	@GetMapping("/login")
	public String index(Model model, Principal principal,@ModelAttribute UserLoginInfo userLoginInfo) {
		logger.debug("in Login");
		//  model.addAttribute("message", "You are logged in as " + principal.getName());
		return "login";
	}
  

	@GetMapping("/test")
	public String test(Model model) {
		logger.debug("in Test");
		model.addAttribute("message", "You are logged in as ");
		return "index";
	}

	@GetMapping("/guestNotes")
	public String guestNotes(Model model,@ModelAttribute GuestNotesDetails guestNotesDetails) {
		logger.debug("in guestNotes");
		model.addAttribute("message", "You are logged in as ");
		return "guestEntry";
	}

  
  @PostMapping("/insertNotes")
  public String insertNotes(HttpServletRequest request, Model model,@ModelAttribute GuestNotesDetails guestNotesDetails) {
    logger.debug("in insertNotes:"+guestNotesDetails.getNotes());
    guestNotesDetails.setUsername(getLogedUser());
    int resStatus = appService.insertNotes(guestNotesDetails);
    request.setAttribute("resStatus", (resStatus==1?"Added Successfully":"Error while adding"));
    return "guestEntry";
  }
  
  
  
	@GetMapping("/viewAllNotes")
	public String viewAllNotes(HttpServletRequest request, Model model) {
	    
		GuestNotesDetails[] guestNotesDetails =  appService.viewAllNotes();
		request.setAttribute("guestNotesDetails", guestNotesDetails);
		request.setAttribute("status", request.getParameter("status"));
	
		logger.debug("in viewAllNotes");
		return "viewNotes";
	}
  
	@GetMapping("/approveReject")
	public RedirectView approveReject(HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
		logger.debug("ID:"+request.getParameter("id"));
		logger.debug("status:"+request.getParameter("status"));
		String status="N";
		if(request.getParameter("status").equalsIgnoreCase("approve"))
			status="A";
		else  if(request.getParameter("status").equalsIgnoreCase("remove"))
			status="R";
		int returnSts =  appService.approveRejectNotes(Integer.parseInt(request.getParameter("id")),status);
		logger.debug("in viewAllNotes");
		return new RedirectView("/viewAllNotes?status="+(returnSts==1?"Updated Successfully":"Error"));   
	}
  

	@GetMapping("/roles")
	public String roles() {
		getRoles();
		return "index";
	}
  
	Collection<SimpleGrantedAuthority> getRoles()
	{
		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		logger.debug(authorities);
		return authorities;
	}
  
	String getLogedUser()
	{
		Object principal = SecurityContextHolder.getContext(). getAuthentication(). getPrincipal();
		if (principal instanceof UserDetails) {
			String username = ((UserDetails)principal). getUsername();
			logger.debug("User:"+username);
			return username;
		}
		return "default";
	}
}
