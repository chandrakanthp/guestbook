package com.app.guestbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * @author CHANDRAKANTH
 *  
 */
@SpringBootApplication
public class GuestbookApplication {
		
	public static final String JASYPT_SECRET_KEY = "guestbook";
	
	/**
	 * JASYPT_SECRET_KEY is the secret key for encrypt the password, default is "guestbook"
	 * @param args[0] we can pass the secret key and need to change the encrypted value of "db.pwd.key" in application properties file
	 */
	public static void main(String[] args) {
		if(args != null && args.length >0 && args[0] != null && !args[0].isEmpty())
		{
			 System.setProperty("jasypt.encryptor.password",args[0]);
		}
		else
		{
			System.setProperty("jasypt.encryptor.password",JASYPT_SECRET_KEY);
		}

		SpringApplication.run(GuestbookApplication.class, args);
	}
}
