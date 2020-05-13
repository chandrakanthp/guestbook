package com.app.guestbook.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private DataSource dataSource;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	  auth.jdbcAuthentication().dataSource(dataSource)
        .usersByUsernameQuery("select username, password, enabled"
            + " from user_details where username=?")
        .authoritiesByUsernameQuery("select username,roles "
            + "from user_roles where username=?")
        .passwordEncoder(new BCryptPasswordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
	  http.authorizeRequests()
//	  .antMatchers(").permitAll()
	  .antMatchers("/viewAllNotes","/approveReject").hasAnyAuthority("ADMIN")
	  .antMatchers("/guestNotes","/insertNotes","/").hasAnyAuthority("ADMIN", "GUEST")
	  .anyRequest().authenticated()
	  .and()
	  .formLogin().loginPage("/login").usernameParameter("username").passwordParameter("password").permitAll()
	;
  
  }  

}