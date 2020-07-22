package com.app.guestbook.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

/**
 * 
 * @author CHANDRAKANTH
 * This is configuration class
 * It configures the dataSource based on the application.properties it gets all the details for of MySQL DB
 * configures the bean for dataSource, jdbcTemplate and namedParamJdbcTemplate
 */
@Configuration
@EnableEncryptableProperties
public class AppConfig {
  
  @Value("${spring.datasource.driver-class-name}")
  private String driverClassName;
  
  @Value("${spring.datasource.url}")
  private String datasourceUrl;
  
  @Value("${db.usr.key}")
  private String datasourceUserName;
  
  @Value("${db.pwd.key}")
  private String datasourcePwd;  
  
  Logger logger = LogManager.getLogger(this.getClass());
  
  
  @Bean(name = "dataSource")
  public DataSource getDataSource() {
	logger.info("Start of getDataSource method:");
    BasicDataSource dataSource = new BasicDataSource();
    dataSource.setDriverClassName(driverClassName);
    dataSource.setUrl(datasourceUrl);
    dataSource.setUsername(datasourceUserName);
    dataSource.setPassword(datasourcePwd);
    logger.debug("End of getDataSource method mysql.driver : {} mysql.jdbcUrl : {} ",dataSource.getDriverClassName(), dataSource.getUrl());
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
}
