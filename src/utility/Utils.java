package utility;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

/**
 * This class used for different util methods for all functionalities.
 * @author Prasad Tirunagari
 * Version 1.0
 * Date Modified 02/03/2016
 */
public class Utils {

	public static WebDriver driver = null;
	public static String URL=null;

	/* This method is used to open the Browser	*/
	public static WebDriver OpenBrowser(String envi) throws Exception{
		String sBrowserName;
		String environment=null;
		try{
			Properties properties = new Properties();
			properties.load(new FileInputStream("./src/config/Config.properties"));
			sBrowserName = properties.getProperty("Browser");
			environment = properties.getProperty("Environment");


			if(envi.equals("MetLifeGSSP"))
			{
				if(environment.equals("SIT"))
					URL = utility.Constant.URL_appName_SIT;
				else if(environment.equals("UAT"))
					URL = utility.Constant.URL_appName_UAT;
			}

			if(sBrowserName.equals("Mozilla")){
				try
				{
					//					Log.info("");
					driver = new FirefoxDriver();
					//					Log.info("New driver instantiated");
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					//					Log.info("Implicit wait applied on the driver for 10 seconds");
					driver.manage().window().maximize();
					driver.get(URL);
				}
				catch (Exception e)
				{
					Log.error("Class Utils | Method OpenBrowser Inside Catch Exception | Exception: "+e.getMessage());
					Log.info("Inside catch Exception to open the firfox");
					File pathToFirefoxBinary = new File(properties.getProperty("Browser_URL"));
					FirefoxBinary firefoxbin = new FirefoxBinary(pathToFirefoxBinary);
					driver = new FirefoxDriver(firefoxbin,null);
					//					Log.info("New driver instantiated");
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					//					Log.info("Implicit wait applied on the driver for 10 seconds");
					driver.manage().window().maximize();
					driver.get(URL);
					//					Log.info("Web application launched successfully");
				}
			}

			if(sBrowserName.equals("Chrome"))
			{
				try
				{
					System.setProperty("webdriver.chrome.driver", ".//Drivers//chromedriver.exe");
					driver = new ChromeDriver(); 
					//					Log.info("New Google Chrome driver instantiated");
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					//					Log.info("Implicit wait applied on the driver for 10 seconds");
					driver.manage().window().maximize();
					driver.get(URL);
				}
				catch (Exception e)
				{
					Log.error("Class Utils | Method OpenBrowser Inside Catch Exception | Exception: "+e.getMessage());
					Log.info("Inside catch Exception to open the Chrome");
					System.setProperty("webdriver.chrome.driver", ".//Drivers//chromedriver.exe");
					driver = new ChromeDriver(); 
					//					Log.info("New Google Chrome driver instantiated");
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					//					Log.info("Implicit wait applied on the driver for 10 seconds");
					driver.manage().window().maximize();
					driver.get(URL);
					//					Log.info("Web application launched successfully");
				}
			}

			if(sBrowserName.equals("InternetExplorer"))
			{
				try
				{
					DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
					//					Log.info(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS);
					cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
					cap.setJavascriptEnabled(true);			
					//File file = new File(".//lib//IEDriverServer.exe");
					System.setProperty("webdriver.ie.driver", ".//Drivers//IEDriverServer.exe");
					//Log.info(file.getAbsolutePath());
					driver = new InternetExplorerDriver(cap);
					//					Log.info("New IE driver instantiated");
					driver.manage().window().maximize();
					driver.get(URL);
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					//					Log.info("Implicit wait applied on the driver for 10 seconds");
				}
				catch (Exception e)
				{
					Log.error("Class Utils | Method OpenBrowser Inside Catch Exception | Exception desc"+e.getMessage());
					Log.info("Inside catch Exception to open the IE");
					DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
					cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
					cap.setJavascriptEnabled(true);		
					System.setProperty("webdriver.ie.driver", ".//Drivers//IEDriverServer.exe");
					//					Log.info(file.getAbsolutePath());
					driver = new InternetExplorerDriver(cap);
					//					Log.info("New IE driver instantiated");
					driver.manage().window().maximize();
					driver.get(URL);
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				}
			}
		}catch (Exception e){
			Log.error("Class Utils | Method OpenBrowser | Exception: "+e.getMessage());
		}

		return driver;
	}

	/* This method is used to get testcase Name	*/
	public static String getClassName(String sTestCase)throws Exception{
		String value = sTestCase;
		try{
			int posi = value.indexOf("@");
			value = value.substring(0, posi);
			posi = value.lastIndexOf(".");	
			value = value.substring(posi + 1);
			return value;
		}catch (Exception e){
			Log.error("Class Utils | Method getTestCaseName | Exception: "+e.getMessage());
			throw (e);
		}
	}

	/* This method is used to wait for Element	*/
	public static void waitForElement(WebElement element){
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	/* This method is used to take screenshot	*/
	public static void takeScreenshot(String sTestCaseName, String path, int count) throws Exception{
		try{
			if(driver!=null)
			{
				count++;
				String random = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
				File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				String fileName = path+sTestCaseName+"-Failure-"+count+"-"+random+".jpg";
				FileUtils.copyFile(scrFile, new File(fileName));
				//				Log.info(fileName+"-->"+"<br> <img alt=\"Error Screen\" src=\\"+fileName+"/> <br>");
				Reporter.log("Step: " + count + " Failed", true);
				Reporter.log("<br> <img alt=\"Error Screen\" src=\\"+fileName+"\"\\/> <br>");
			}
		} catch (Exception e){
			Log.error("Class Utils | Method takeScreenshot | Exception occured while capturing ScreenShot: "+e.getMessage());
			throw new Exception();
		}
	}

	/* This method is used to explicit wait	*/
	public  void explicitWait(By by)  
	{  
		WebDriverWait wait = new WebDriverWait(driver, 300);  
		wait.until(ExpectedConditions.presenceOfElementLocated(by));  
	}
}
