package com.app.guestbook.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.app.guestbook.model.GuestNotesDetails;

@Repository
public class GuestAppDaoImpl implements GuestAppDao {
	
	@Qualifier("jdbcTemplate")
	@Autowired
	JdbcTemplate template;   
	
	@Qualifier("namedParamJdbcTemplate")
	@Autowired
	NamedParameterJdbcTemplate nameTemplate;    
	
	Logger logger = Logger.getLogger(this.getClass());

	
	public int insertNotes(GuestNotesDetails notes)
	{
		 String sql="insert into guest_notes_details(username,notes) values(:name,:notes)";  
		 MapSqlParameterSource params = new MapSqlParameterSource();
		 params.addValue("notes", notes.getNotes())
		 .addValue("name",notes.getUsername());
    return nameTemplate.update(sql, params);    	
	}
	
	@SuppressWarnings("unchecked")
	public GuestNotesDetails[] viewAllNotes() {
		String sql = "select "
				+ " notes_details_id  as notes_details_id, "
				+ "username  as username, " + 
				"	modified_time  as dateTime, " + 
				"    notes as notes, " +  
				"     approveStatus as approveStatus from guest_notes_details ";
		
		List details = nameTemplate.query(sql, new BeanPropertyRowMapper(GuestNotesDetails.class));
		if(details != null)
		{
			return (GuestNotesDetails[]) details.toArray(new GuestNotesDetails[details.size()]);
		}
		
		return null;
	}
	
	public int approveRejectNotes(int id, String value)
	{
		String sql = "update guest_notes_details "
				+ " set approveStatus=:approveStatus  where notes_details_id=:notes_details_id ";
		if(value.equalsIgnoreCase("R"))
			sql = "delete from guest_notes_details "
					+ " where notes_details_id=:notes_details_id ";
		 MapSqlParameterSource params = new MapSqlParameterSource();
		 params.addValue("approveStatus", value)
		 .addValue("notes_details_id",id);
	return nameTemplate.update(sql, params);
	}

}
