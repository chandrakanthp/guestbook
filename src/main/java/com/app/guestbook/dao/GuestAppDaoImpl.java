package com.app.guestbook.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.app.guestbook.model.GuestNotesDetails;

/**
 * 
 * @author CHANDRAKANTH
 * This is repository class which help to connect with MySQL database
 * It uses the SpringJDBC module with jdbc templates
 * 
 */
@Repository
public class GuestAppDaoImpl implements GuestAppDao {
	
	@Qualifier("jdbcTemplate")
	@Autowired
	JdbcTemplate template;   
	
	@Qualifier("namedParamJdbcTemplate")
	@Autowired
	NamedParameterJdbcTemplate nameTemplate;    
	
	Logger logger = LogManager.getLogger(this.getClass());

	/**
	 * @param GuestNotesDetails
	 * This insertNotes method helps to add a new guest notes
	 * @return int value [0-Failure;1-Success]
	 * 
	 */
	public int insertNotes(GuestNotesDetails notes)
	{
		logger.debug("Start of insertNotes method");
		String sql="insert into guest_notes_details(username,notes,image,image_file_name) "
		 		+ " values(:name,:notes,:image,:image_file_name)"; 
		logger.debug("insert sql : {} ",sql);
		byte[] image = null;
		String image_file_name = null;
		 try
		 {
			 image = notes.getImageFile().getBytes();
			 image_file_name = notes.getImageFile().getOriginalFilename();
		 }catch(Exception e)
		 {
			 logger.error("Error while inserting notes : {} ",e.getMessage());
		 }
		 
		 MapSqlParameterSource params = new MapSqlParameterSource();
		 params.addValue("notes", notes.getNotes()!=null?notes.getNotes():"")
		 .addValue("image", image)
		 .addValue("image_file_name", image_file_name)
		 .addValue("name",notes.getUsername());
		 logger.debug("End of insertNotes method");
		 return nameTemplate.update(sql, params);    	
	}
	
	/**
	 * This viewAllNotes method will retrieve all the notes of guest entered
	 * @return int value [0-Failure;1-Success]
	 * 
	 */
	@SuppressWarnings("unchecked")
	public GuestNotesDetails[] viewAllNotes() {
		logger.debug("Start of viewAllNotes method");
		String sql = "select "
				+ " notes_details_id  as notes_details_id, "
				+ "username  as username, " + 
				"	modified_time  as dateTime, " + 
				"    notes as notes, " + 
				"  image_file_name as image_file_name, "+
				"     approveStatus as approveStatus from guest_notes_details ";
		logger.debug("viewAllNotes sql : {} ",sql);
		List details = nameTemplate.query(sql, new BeanPropertyRowMapper(GuestNotesDetails.class));
		if(details != null)
		{
			return (GuestNotesDetails[]) details.toArray(new GuestNotesDetails[details.size()]);
		}
		logger.debug("End of viewAllNotes method");
		return null;
	}
	
	/**
	 * @param id and value
	 * id - notes_details_id and values which help to identify which action to perform
	 * if the values is R then it will delete the entry else it will approve
	 */
	public int approveRejectNotes(int id, String value)
	{
		logger.debug("Start of approveRejectNotes method");
		String sql = "update guest_notes_details "
				+ " set approveStatus=:approveStatus  where notes_details_id=:notes_details_id ";
		if(value.equalsIgnoreCase("R"))
			sql = "delete from guest_notes_details "
					+ " where notes_details_id=:notes_details_id ";
		
		logger.debug("approveRejectNotes sql : {} ",sql);
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("approveStatus", value)
		 .addValue("notes_details_id",id);
		logger.debug("End of approveRejectNotes method");
		return nameTemplate.update(sql, params);
	}

	/**
	 * @param id
	 * Base on the notes_details_id it will retrieve the image for download
	 */
	public GuestNotesDetails getImage(String id)
	{
		logger.debug("Start of getImage method");
		String sql = "select "
				+ " notes_details_id  as notes_details_id, " +
				"  image_file_name as image_file_name, image as image "+
				"     from guest_notes_details where notes_details_id=:notes_details_id ";
		
		logger.debug("sql : {} ",sql); 
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("notes_details_id", id);
		List details = nameTemplate.query(sql,params, new BeanPropertyRowMapper(GuestNotesDetails.class));
			if(details != null)
			{
				return (GuestNotesDetails)details.get(0);
			}
		
		logger.debug("End of getImage method");
		return null;
	}
}
