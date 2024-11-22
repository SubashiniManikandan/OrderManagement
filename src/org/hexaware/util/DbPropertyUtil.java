package org.hexaware.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class DbPropertyUtil {
	public static String getConnectionString()  {
		String url=null;
		Properties props=new Properties();
		try(FileInputStream fis=new FileInputStream("db.properties")) {
			props.load(fis);
			url=props.getProperty("url")+"/"+props.getProperty("database")+"?user="+props.getProperty("user")+"&password="+props.getProperty("password");
	
		}
		catch(IOException e) {
			e.printStackTrace();
			System.out.println("Error");

		}
		
		return url;
	}
		
	}




