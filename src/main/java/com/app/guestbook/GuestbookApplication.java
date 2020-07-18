package com.app.guestbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class GuestbookApplication {
	


	public static void main(String[] args) {
		if(args != null && args.length >0 && args[0] != null)
		{
			 System.setProperty("jasypt.encryptor.password",args[0]);
		}
		else
		{
		  System.setProperty("jasypt.encryptor.password","guestbook");
		}

		SpringApplication.run(GuestbookApplication.class, args);
	}

}
