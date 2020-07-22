<%@page import="com.app.guestbook.model.GuestNotesDetails,java.io.*" %>
<%@ page trimDirectiveWhitespaces="true"%>
<%
	GuestNotesDetails data = (GuestNotesDetails)pageContext.findAttribute("guestNotesDetails");
	response.setContentType("image/jpeg");
	response.setHeader( "Pragma", "public" );
	response.setContentLength(data.getImage().length);
	response.setHeader("Content-Disposition", "attachment; filename=\"" + data.getImage_file_name() + "\"");
	
	BufferedOutputStream output = null;
	
	try {
	    output = new BufferedOutputStream(response.getOutputStream());
	        output.write(data.getImage());
	} finally {
	    if (output != null) try { output.close(); } catch (IOException logOrIgnore) {}
	}
%>