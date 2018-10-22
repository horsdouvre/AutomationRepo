package testCases;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import appModules.LoginTasks;
import config.BaseClass;
import utility.Constant;
import utility.ElementAction;
import utility.ExcelUtils;
import utility.Log;
import utility.TestBase;
import utility.Utils;

@Listeners({utility.Listener.class})
public class Login {

	WebDriver driver;
	String sTestCaseName;
	String sheetName = null;
	String colName = "NeedToExecute";
	boolean browserOpen = false;
	boolean flag = false;
	int rowIndex;	
	ElementAction action = new ElementAction();
	public int count = 0;
	// Update Call Based on the Functionality Task Required
	LoginTasks testStep = null;


	
	@BeforeMethod
	public void beforeClass() throws Exception {
		new BaseClass();
		sheetName = Utils.getClassName(this.toString());
		count = 0;
		DOMConfigurator.configure(BaseClass.bLog);
		ExcelUtils.openExcel(Constant.Path_TestData, sheetName);
		testStep = new LoginTasks();
	}

	@AfterMethod
	public void afterClass() throws Exception {
		Log.endTestCase(sTestCaseName);
		// Closing the opened driver
		if(browserOpen==true){
			driver.quit(); 
		}
		ExcelUtils.closeExcelFile();
	}
	
	public int flagStatus(Boolean flag) throws Exception{
		if(!flag){
			Utils.takeScreenshot(sTestCaseName, Constant.Path_ScreenShot, count);
			count++;
		}
		return count;
	}
	
	@Test
	public void Test1_GSSP_3749() throws Exception {
		try
		{	flag=true;
			
			//put it in xml report
			sTestCaseName = new Exception().getStackTrace()[0].getMethodName();
			
			//Log.info(sTestCaseName);
			Log.startTestCase(sTestCaseName);
			Log.info("Testcase Being Tested --> " + sTestCaseName);
			
			rowIndex = ExcelUtils.getRowIndex(sheetName, sTestCaseName);
			
			//Checking if test case need to be executed or not
			Log.info("Value of NeedToExecute -->" + ExcelUtils.getCellData(sheetName, colName, rowIndex));
			if(ExcelUtils.getCellData(sheetName, colName, rowIndex).equalsIgnoreCase("Yes"))
			{	browserOpen = true;
				driver =  Utils.OpenBrowser(Constant.appName);
				Log.info("Webdriver initialized and GSSP Application Loaded");

					flagStatus(TestBase.verifyCompare(action.getText(".//*[@id='content']/div/div/div/div/div[1]"), "withfgjhfriends"));
					flagStatus(TestBase.verifyEquals("abcdef", "withfgjhfriends"));					
					flagStatus(TestBase.verifyEquals("Abc", "Abc"));
				
				
				
//				if(flag){
//					action.inputText(".//*[@id='u_0_1']", "hello");
//				}
					
				Log.info("Final Result of the test case -->"+flag);
				//Printing the result of the test case in the excel Result Column and TestNG report.
				if(count==0){
					ExcelUtils.setCellData(sheetName, "Result", rowIndex, "Pass", Constant.Path_TestData);
					ExcelUtils.setCellData(sheetName, "Comment", rowIndex, "Test Case Executed Successfully", Constant.Path_TestData);
					Reporter.log(sTestCaseName+" Test Case Pass");
				}else{
				//	Utils.takeScreenshot(sTestCaseName, Constant.Path_ScreenShot);
					ExcelUtils.setCellData(sheetName, "Result", rowIndex, "Fail", Constant.Path_TestData);
					throw new Exception("Test Case Failed because of Verification");
				}
			}
			//If Test Case need not to be executed.
			else
			{
				Log.info("Test Case does not need to be executed");
				//Writing comment on the test data file why this case is skipped from the execution.
				ExcelUtils.setCellData(sheetName, "Result", rowIndex, "Skip", Constant.Path_TestData);
				ExcelUtils.setCellData(sheetName, "Comment", rowIndex, "Test Case Skiped", Constant.Path_TestData);
				throw new SkipException("Skiping This Test as 'Need To Execute' Request is selected as No");
			}
		}
		catch(SkipException e)
		{
			Log.error(e);
		    throw(e);
		}
		catch(Exception e){
			Log.error(e);
			throw(e);
		}
	}



	@Test
	public void Test2_GSSP_3748() throws Exception {
		try
		{	flag=true;
			
			//put it in xml report
			sTestCaseName = new Exception().getStackTrace()[0].getMethodName();
			
			//Log.info(sTestCaseName);
			Log.startTestCase(sTestCaseName);
			Log.info("Testcase Being Tested --> " + sTestCaseName);
			
			rowIndex = ExcelUtils.getRowIndex(sheetName, sTestCaseName);
			
			//Checking if test case need to be executed or not
			Log.info("Value of NeedToExecute -->" + ExcelUtils.getCellData(sheetName, colName, rowIndex));
			if(ExcelUtils.getCellData(sheetName, colName, rowIndex).equalsIgnoreCase("Yes"))
			{	browserOpen = true;
				driver =  Utils.OpenBrowser(Constant.appName);
				Log.info("Webdriver initialized and GSSP Application Loaded");

					flagStatus(TestBase.verifyCompare(action.getText(".//*[@id='content']/div/div/div/div/div[1]"), "with friends"));
					
				
				
//				if(flag){
//					action.inputText(".//*[@id='u_0_1']", "hello");
//				}
					
				Log.info("Final Result of the test case -->"+flag);
				//Printing the result of the test case in the excel Result Column and TestNG report.
				if(count==0){
					ExcelUtils.setCellData(sheetName, "Result", rowIndex, "Pass", Constant.Path_TestData);
					ExcelUtils.setCellData(sheetName, "Comment", rowIndex, "Test Case Executed Successfully", Constant.Path_TestData);
					Reporter.log(sTestCaseName+" Test Case Pass");
				}else{
				//	Utils.takeScreenshot(sTestCaseName, Constant.Path_ScreenShot);
					ExcelUtils.setCellData(sheetName, "Result", rowIndex, "Fail", Constant.Path_TestData);
					throw new Exception("Test Case Failed because of Verification");
				}
			}
			//If Test Case need not to be executed.
			else
			{
				Log.info("Test Case does not need to be executed");
				//Writing comment on the test data file why this case is skipped from the execution.
				ExcelUtils.setCellData(sheetName, "Result", rowIndex, "Skip", Constant.Path_TestData);
				ExcelUtils.setCellData(sheetName, "Comment", rowIndex, "Test Case Skiped", Constant.Path_TestData);
				throw new SkipException("Skiping This Test as 'Need To Execute' Request is selected as No");
			}
		}
		catch(SkipException e)
		{
			Log.error(e);
		    throw(e);
		}
		catch(Exception e){
			Log.error(e);
			throw(e);
		}
	}



}
