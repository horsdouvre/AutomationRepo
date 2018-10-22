package testCases;

import java.util.ArrayList;

import org.apache.log4j.xml.DOMConfigurator;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import appModules.LoginTasks;
import config.BaseClass;
import utility.Constant;
import utility.JsonUtils;
import utility.Log;


@Listeners({utility.Listener.class})
public class JsonReadData {
	
	String sTestCaseName;
	
	//This will open the Json file and read data
	JSONObject test = JsonUtils.openJson(Constant.Path_Json);
	
	/**
	 * Update Call Based on the Task Required
	 * If the task is related to Register call RegisterTasks
	 * "teststep=null" can stay the same just the class name needs to updated.
	 */
	LoginTasks testStep = null;
	
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
		
			String name = (String) test.get("policyNumber");
	        System.out.println(name);

		
	         String currencyCode = (String) JsonUtils.getJsonArrayValue(test, "contractOwners", "name", "lastName");
	        System.out.println(currencyCode);
	         
	         String payemntMethodCode = (String) JsonUtils.getJsonObjectValue(test, "product", "type");
	        		 System.out.println(payemntMethodCode);
	        		 
	        		 
	        		 String payemntMethodCode1 = (String) JsonUtils.getJsonArrayObjectValue(test, "paymentDetails", "payemntMethodCode");
	        		 System.out.println(payemntMethodCode1);
	        		 
	        		 
	        		 String payemntMethodCode2 = (String) JsonUtils.getJsonObjectObjValue(test, "paymentSummary", "paymentAmountDue", "currencyCode");
	        		 System.out.println(payemntMethodCode2);
	        		 
	        		 
	        		 ArrayList<String> currencyCode1 = JsonUtils.getJsonArrayLoopValue(test, "coverages", "description");
	        		for(String s:currencyCode1) {
	        	         //  System.out.println(s);
	        	        }
	        		 String x = currencyCode1.get(0);
	        		 System.out.println(x);
	        		 String y = currencyCode1.get(1);
	        		 System.out.println(y);
	        
	    
		}
		catch(Exception e){
			Log.error(e);
		}
		}

	

}
