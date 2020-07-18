package com.app.guestbook.config;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 
 * @author CHANDRAKANTH
 * This is class is enabled with Web Security
 * It had override the two configure methods 
 *
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private DataSource dataSource;
  
	Logger logger = LogManager.getLogger(this.getClass());

  /**
   * @param AuthenticationManagerBuild
   * @throws Exception
   * It will configure and set the user details and roles on connecting with database
   * It also performs the password encoder which is encoded with BCryptPasswordEncoder.
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	  logger.debug("Start of configure method of AuthenticationManager Builder");
	  auth.jdbcAuthentication().dataSource(dataSource)
        .usersByUsernameQuery("select username, password, enabled"
            + " from user_details where username=?")
        .authoritiesByUsernameQuery("select username,roles "
            + "from user_roles where username=?")
        .passwordEncoder(new BCryptPasswordEncoder());
	  logger.debug("End of configure method of AuthenticationManager Builder");
  }

  /**
   * @param http
   * This configure method helps to authorize the user based on the role
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
	  logger.debug("Start of configure method of HttpSecurity authorizeRequests : {}",http.authorizeRequests());
	  http.authorizeRequests()
	  .antMatchers("/viewAllNotes","/approveReject").hasAnyAuthority("ADMIN")
	  .antMatchers("/guestNotes","/insertNotes","/").hasAnyAuthority("ADMIN", "GUEST")
	  .anyRequest().authenticated()
	  .and()
	  .formLogin().loginPage("/login").usernameParameter("username").passwordParameter("password").permitAll()
	  .and()
	  .logout().permitAll();
	  logger.debug("End of configure method of HttpSecurity");
  
  }  

}