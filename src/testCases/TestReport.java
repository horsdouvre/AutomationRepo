package testCases;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import appModules.ViewPortfolioSummaryTask;
import appObjects.ViewPortfolioSummaryObjects;
import appVerification.ViewPortfolioSummaryVerification;
import config.BaseClass;
import utility.Constant;
import utility.ElementAction;
import utility.ExcelUtils;
import utility.Log;
import utility.TestBase;
import utility.Utils;

@Listeners({utility.Listener.class})
public class TestReport{

	WebDriver driver;
	String sTestCaseName = null;
	String sheetName = null;
	String colName = "NeedToExecute";
	boolean browserOpen = false;
	boolean flag = false;
	boolean login = true;
	int rowIndex;	
	ElementAction action = new ElementAction();
	int count = 0;
	
	// Update Call Based on the Functionality Task Required
	ViewPortfolioSummaryTask testStep = null;
	ViewPortfolioSummaryObjects obj = null;
	ViewPortfolioSummaryVerification ver = null;

	@BeforeClass
	public void beforeClass() throws Exception{
		ExcelUtils.openExcel(Constant.Path_TestData, Constant.Master_Sheet);
		sheetName = Utils.getClassName(this.toString());		
		rowIndex = ExcelUtils.getRowIndex(Constant.Master_Sheet, sheetName);
		if (ExcelUtils.getCellData(Constant.Master_Sheet,colName,rowIndex).equalsIgnoreCase("Yes")){
			flag=true;
			browserOpen = true;
			driver =  Utils.OpenBrowser(Constant.appName);
			Log.info("Webdriver initialized and GSSP Application Loaded");
			testStep = new ViewPortfolioSummaryTask();
			obj = new ViewPortfolioSummaryObjects();
			ver = new ViewPortfolioSummaryVerification();
		}
		else
		{
			throw new SkipException("Skiping This Test as 'Need To Execute' Request is selected as No");    
		}
	}

	@AfterClass
	public void afterClass() throws Exception{   
		// Closing the opened driver
		if(browserOpen==true){
			driver.quit(); 
		}
		ExcelUtils.closeExcelFile();
	}

	@BeforeMethod
	public void beforeTestCase() throws Exception {
		new BaseClass();
		count =0;
		DOMConfigurator.configure(BaseClass.bLog);
		ExcelUtils.openExcel(Constant.Path_TestData, sheetName);
	}

	@AfterMethod
	public void afterTestCase() throws Exception {
		Log.endTestCase(sTestCaseName);
	}

	public void login() throws Exception
	{
		testStep.loginUser(sTestCaseName, rowIndex);
		login = false;
	}
	
	public int flagStatus(Boolean flag) throws Exception{
		if(!flag){
			Utils.takeScreenshot(sTestCaseName, Constant.Path_ScreenShot, count);
			count++;
		}
		return count;
	}
	
	@Test
	public void TestA_GSSP_3787() throws Exception {
		try
		{	
			sTestCaseName = new Exception().getStackTrace()[0].getMethodName();
			Log.startTestCase(sTestCaseName);
			Log.info("Testcase Being Tested --> " + sTestCaseName);
		
			rowIndex = ExcelUtils.getRowIndex(sheetName, sTestCaseName);
			
			//Checking if test case need to be executed or not
			Log.info("Value of NeedToExecute -->" + ExcelUtils.getCellData(sheetName, colName, rowIndex));
			if(ExcelUtils.getCellData(sheetName, colName, rowIndex).equalsIgnoreCase("Yes"))
			{	
				
				flagStatus(TestBase.verifyCompare("abc", "profile"));
					
				Log.info("Final Result of the test case -->"+flag);
				//Printing the result of the test case in the excel Result Column and TestNG report.
				if(count==0){
					ExcelUtils.setCellData(sheetName, "Result", rowIndex, "Pass", Constant.Path_TestData);
					ExcelUtils.setCellData(sheetName, "Comment", rowIndex, "Test Case Executed Successfully", Constant.Path_TestData);
					Reporter.log(sTestCaseName+" Test Case Pass");
				}else{
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
		catch(SkipException e){	
			Log.error(e);
			throw(e);
		}
		catch(Exception e){
			Log.error(e);
			throw(e);
		}
	}
	
	@Test
	public void TestB_GSSP_3793() throws Exception {
		try
		{	
			sTestCaseName = new Exception().getStackTrace()[0].getMethodName();
			Log.startTestCase(sTestCaseName);
			Log.info("Testcase Being Tested --> " + sTestCaseName);

			rowIndex = ExcelUtils.getRowIndex(sheetName, sTestCaseName);
			
			//Checking if test case need to be executed or not
			Log.info("Value of NeedToExecute -->" + ExcelUtils.getCellData(sheetName, colName, rowIndex));
			if(ExcelUtils.getCellData(sheetName, colName, rowIndex).equalsIgnoreCase("Yes"))
			{	
				
				flagStatus(TestBase.verifyCompare("Hello World", "Hello"));
				flagStatus(TestBase.verifyCompare("Pass this test", "Pass"));
				flagStatus(TestBase.verifyEquals("verify", "verify"));
				
				Log.info("Final Result of the test case -->"+flag);
				//Printing the result of the test case in the excel Result Column and TestNG report.
				if(count==0){
					ExcelUtils.setCellData(sheetName, "Result", rowIndex, "Pass", Constant.Path_TestData);
					ExcelUtils.setCellData(sheetName, "Comment", rowIndex, "Test Case Executed Successfully", Constant.Path_TestData);
					Reporter.log(sTestCaseName+" Test Case Pass");
				}else{
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
		catch(SkipException e){	
			Log.error(e);
			throw(e);
		}
		catch(Exception e){
			Log.error(e);
			throw(e);
		}
	}

	@Test
	public void TestC_GSSP_3796() throws Exception {
		try
		{	
			sTestCaseName = new Exception().getStackTrace()[0].getMethodName();
			Log.startTestCase(sTestCaseName);
			Log.info("Testcase Being Tested --> " + sTestCaseName);

			rowIndex = ExcelUtils.getRowIndex(sheetName, sTestCaseName);
			
			//Checking if test case need to be executed or not
			Log.info("Value of NeedToExecute -->" + ExcelUtils.getCellData(sheetName, colName, rowIndex));
			if(ExcelUtils.getCellData(sheetName, colName, rowIndex).equalsIgnoreCase("Yes"))
			{	
				
				flagStatus(TestBase.verifyCompare("Hello World", "Hello Fail"));

				flagStatus(TestBase.verifyCompare("Pass this test", "Pass "));

				flagStatus(TestBase.verifyEquals("verify", "verify"));
				flagStatus(TestBase.verifyEquals("verify", "verify"));
				flagStatus(TestBase.verifyCompare("Pass this test ", "Pass this test"));
				flagStatus(TestBase.verifyCompare("this test", "this"));
				flagStatus(TestBase.verifyEquals("fail", "pass"));
				
				
				Log.info("Final Result of the test case -->"+flag);
				//Printing the result of the test case in the excel Result Column and TestNG report.
				if(count==0){
					ExcelUtils.setCellData(sheetName, "Result", rowIndex, "Pass", Constant.Path_TestData);
					ExcelUtils.setCellData(sheetName, "Comment", rowIndex, "Test Case Executed Successfully", Constant.Path_TestData);
					Reporter.log(sTestCaseName+" Test Case Pass");
				}else{
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
		catch(SkipException e){	
			Log.error(e);
			throw(e);
		}
		catch(Exception e){
			Log.error(e);
			throw(e);
		}
	}

	

}
