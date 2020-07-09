package com.app.guestbook.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * 
 * @author CHANDRAKANTH
 * This is configuration class
 * It configures the dataSource based on the application.properties it gets all the details for of MySQL DB
 * configures the bean for dataSource, jdbcTemplate and namedParamJdbcTemplate
 */
@Configuration
public class AppConfig {

  @Autowired
  private Environment env;
  
  Logger logger = Logger.getLogger(this.getClass());

  @Bean(name = "dataSource")
  public DataSource getDataSource() {
	logger.debug("Start of getDataSource method");
    BasicDataSource dataSource = new BasicDataSource();
    dataSource.setDriverClassName(env.getProperty("mysql.driver"));
    dataSource.setUrl(env.getProperty("mysql.jdbcUrl"));
    dataSource.setUsername(env.getProperty("mysql.username"));
    dataSource.setPassword(env.getProperty("mysql.password"));
    logger.debug("End of getDataSource method");
    return dataSource;
  }
  
  @Bean("jdbcTemplate")
  public JdbcTemplate createJdbcTemplate(@Autowired @Qualifier("dataSource") DataSource dataSource){
      return new JdbcTemplate(dataSource);
  }
  
  @Bean("namedParamJdbcTemplate")
  public NamedParameterJdbcTemplate createNamedParamJdbcTemplate(@Autowired @Qualifier("dataSource") DataSource dataSource){
      return new NamedParameterJdbcTemplate(dataSource);
  }
  /*

  @Bean
public InternalResourceViewResolver viewResolver() {
InternalResourceViewResolver resolver = new InternalResourceViewResolver();
resolver.setPrefix("/WEB-INF/jsp/");
resolver.setSuffix(".jsp");
return resolver;
}   */
}