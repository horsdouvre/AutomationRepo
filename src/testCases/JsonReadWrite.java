package testCases;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import org.apache.log4j.xml.DOMConfigurator;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import appModules.LoginTasks;
import config.BaseClass;
import utility.Constant;
import utility.JsonUtils;
import utility.Log;


@Listeners({utility.Listener.class})
public class JsonReadWrite {
	
	String sTestCaseName;
	
	//This will open the Json file and read data
	//JSONObject test = JsonUtils.openJson(Constant.Path_Json);
	
	/**
	 * Update Call Based on the Task Required
	 * If the task is related to Register call RegisterTasks
	 * "teststep=null" can stay the same just the class name needs to updated.
	 */
	//LoginTasks testStep = null;
	
	/**
	 * The beforeclass will open the excel file and read the master sheet information
	 * The sheetName will be updated with the class name.
	 * The getRowindex searches the excel file for the correct row we are searching 
	 * We update the flag accordingly based on the if statement.
	 * If the class has NO in the excel file the entire test will be skipped.
	 */
	@BeforeClass
	public void beforeClass() throws Exception{
	
	}

	@AfterClass
	public void afterClass() throws Exception{   
		
	}
	
	

	/**
	 * This method is used to setup the test 
	 * 
	 */
	@BeforeMethod
	public void beforeTestCase() throws Exception {
		new BaseClass();
		DOMConfigurator.configure(BaseClass.bLog);
		/*ExcelUtils.openExcel(Constant.Path_TestData, sheetName);
		testStep = new LoginTasks();*/
		
	}

	/**
	 * This method is used once the test is complete 
	 * 	  
	 */
	@AfterMethod
	public void afterTestCase() throws Exception {
		Log.endTestCase(sTestCaseName);
	}

	/**
	 * The methods will be used to code test cases.
	 * 
	 */
	@Test
	public void JsonreadSample() throws Exception {
		
		
		try
		{	
		
			JSONObject countryObj = new JSONObject();  
	        countryObj.put("Name", "USA");  
	        countryObj.put("Population", new Integer(2000000));  
	  
	        JSONArray listOfStates = new JSONArray();  
	        listOfStates.add("Cary");  
	        listOfStates.add("Raleigh");  
	        listOfStates.add("Morrisville");  
	  
	        countryObj.put("States", listOfStates);  
	  
	        try {  
	              
	            // Writing to a file  
	            File file=new File("C:\\Users\\ptirunagari1\\CountryJSONFile1.json");  
	            file.createNewFile();  
	            FileWriter fileWriter = new FileWriter(file);  
	            System.out.println("Writing JSON object to file");  
	            System.out.println("-----------------------");  
	            System.out.print(countryObj);  
	  
	            fileWriter.write(countryObj.toJSONString());  
	            fileWriter.flush();  
	            fileWriter.close();  
     
	    
		}
		catch(Exception e){
			Log.error(e);
		}
	       
		}
		 catch(Exception f){
				Log.error(f);}
		}
	
	@Test
	public void dbConnect() throws Exception {
		
		
		try
		{	MongoClient client = new MongoClient("localhost",27017); //with default server and port adress
		MongoDatabase db = client.getDatabase("your_db_name");
		MongoCollection<Document> collection = db.getCollection("Your_Collection_Name");
		
	       
		}
		 catch(Exception f){
				Log.error(f);}
		}
	
}
