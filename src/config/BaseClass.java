package config;

import java.io.FileInputStream;
import java.util.Properties;

public class BaseClass {
	public static boolean bResult;
	public static String bLog;
	public static Process process;
	public static String bEnvi;
	
	public  BaseClass() throws Exception{		
		Properties properties = new Properties();
		properties.load(new FileInputStream("./src/config/Config.properties"));
		BaseClass.bResult = true;
		BaseClass.bEnvi = null;
		if(properties.getProperty("LogType").equals("HTML"))
			BaseClass.bLog = "log4jH.xml";
		else if(properties.getProperty("LogType").equals("Text"))
			BaseClass.bLog = "log4j.xml";
	}

}
