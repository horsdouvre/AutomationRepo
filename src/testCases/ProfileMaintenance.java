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

import appModules.CommonTasks;
import appModules.LoginTasks;
import appObjects.ProfileMaintenanceObjects;
import appVerification.ProfileMaintenanceVerification;
import config.BaseClass;
import utility.Constant;
import utility.ElementAction;
import utility.ExcelUtils;
import utility.Log;
import utility.TestBase;
import utility.Utils;

@Listeners({utility.Listener.class})
public class ProfileMaintenance {

	/**
	 * This are the required declarations for the file.
	 * You do not need to change any of the declarations
	 */
	
	
	WebDriver driver;
	String sTestCaseName;
	String sheetName = null;
	String colName = "NeedToExecute";
	boolean browserOpen = false;
	boolean flag = false;
	int rowIndex;
	ElementAction action = new ElementAction();
	CommonTasks common = new CommonTasks();

	int count = 0;

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
		ExcelUtils.openExcel(Constant.Path_TestData, Constant.Master_Sheet);
		sheetName = Utils.getClassName(this.toString());
		System.out.println("sheetname@@"+sheetName);
		rowIndex = ExcelUtils.getRowIndex(Constant.Master_Sheet, sheetName);
		if (ExcelUtils.getCellData( Constant.Master_Sheet,colName,rowIndex).equalsIgnoreCase("Yes")){
			flag=true;
			browserOpen = true;
			driver =  Utils.OpenBrowser(Constant.appName);
			Log.info("Webdriver initialized and GSSP Application Loaded");
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
	
	

	/**
	 * This method is used to setup the test 
	 * 
	 */
	@BeforeMethod
	public void beforeTestCase() throws Exception {
		new BaseClass();
		DOMConfigurator.configure(BaseClass.bLog);
		ExcelUtils.openExcel(Constant.Path_TestData, sheetName);
		//testStep = new LoginTasks();
	}

	/**
	 * This method is used once the test is complete 
	 * 	  
	 */
	@AfterMethod
	public void afterTestCase() throws Exception {
		Log.endTestCase(sTestCaseName);
	}
	
	public int flagStatus(Boolean flag) throws Exception{
		if(!flag){
		//	Utils.takeScreenshot(sTestCaseName, Constant.Path_ScreenShot, count);
			count++;
		}
		return count;
	}
	/**
	 * The methods will be used to code test cases.
	 * 
	 */
	@Test
	public void LoginInformation_GSSP_3794() throws Exception {
		try
		{	
			/**No Need to update any of the following code
			 * Next 6 Lines:
			 * * The flag will be used to stop execution and continue to next test case execution.
			 * * The Next 4 lines will read the testcase name based on the method name and log the information appropriately.
			 * * The Next 2 lines rowIndex and rowData are used to find the correct row with the data and fetching it from the excel file.
			 */
			
			//flag=true;
			sTestCaseName = new Exception().getStackTrace()[0].getMethodName();
			Reporter.log("Starting Execution for" + sTestCaseName);
			Log.startTestCase(sTestCaseName);
			Log.info("Testcase Being Tested --> " + sTestCaseName);
			rowIndex = ExcelUtils.getRowIndex(sheetName, sTestCaseName);
			
			/*
			 * This will write a log whether the case will be executed or not based on the Need to execute column
			 */
			Log.info("Value of NeedToExecute -->" + ExcelUtils.getCellData(sheetName, colName, rowIndex));
			
			/*
			 * Need to implement the test case logic inside the following if statement.
			 * If the if statement reads NO from excel file the code block will be skipped, the case will not be executed and marked as skipped.
			 */
			if(ExcelUtils.getCellData(sheetName, colName, rowIndex).equalsIgnoreCase("Yes"))
			{	

									
				//common.loginUser(sheetName,rowIndex);
				//action.clickLink(ProfileMaintenanceObjects.profile_Link);
					Thread.sleep(7000);	
					boolean test;
					test = TestBase.verifyCompare(action.getText(ProfileMaintenanceObjects.profile_VerifyLandPage), ProfileMaintenanceVerification.profile_VerifyCorrectLandPage);
					System.out.println("test    "+test);
				if(TestBase.verifyCompare(action.getText(ProfileMaintenanceObjects.profile_VerifyLandPage), ProfileMaintenanceVerification.login_VerifyCorrectLandPage)){
					
					flagStatus(TestBase.verifyCompare(action.getText(ProfileMaintenanceObjects.LoginInfo_VerifyCard), ProfileMaintenanceVerification.loginInfo_VerifyCard));
					TestBase.verifyCompare(action.getText(ProfileMaintenanceObjects.LoginInfo_Details), ProfileMaintenanceVerification.Profile_Details);
					TestBase.verifyCompare(action.getText(ProfileMaintenanceObjects.LoginInfo_Update), ProfileMaintenanceVerification.Profile_Update);
					TestBase.verifyCompare(action.getText(ProfileMaintenanceObjects.LoginInfo_SecQues), ProfileMaintenanceVerification.loginInfo_SecQues);
					TestBase.verifyCompare(action.getText(ProfileMaintenanceObjects.LoginInfo_LoginUser), ExcelUtils.getCellData(sheetName, "LogInUser", rowIndex));
					TestBase.verifyCompare(action.getText(ProfileMaintenanceObjects.LoginInfo_LoginPwd), ExcelUtils.getCellData(sheetName, "LoginPwd", rowIndex));
				}
				
						
								
				if(flag){
					/**
					 * This will update the Excel file will test result as pass. 
					 * This will update the xml report with the test case pass
					 */
					ExcelUtils.setCellData(sheetName, "Result", rowIndex, "Pass", Constant.Path_TestData);
					ExcelUtils.setCellData(sheetName, "Comment", rowIndex, "Test Case Executed Successfully", Constant.Path_TestData);
					Reporter.log(sTestCaseName+" Test Case Pass");
				}else{
					/**
					 * This will be executed if any of the steps fail
					 * It will take a screenshot of where the failure occurred.
					 */			

					//Utils.takeScreenshot(sTestCaseName, Constant.Path_ScreenShot);

					ExcelUtils.setCellData(sheetName, "Result", rowIndex, "Fail", Constant.Path_TestData);
					ExcelUtils.setCellData(sheetName, "Comment", rowIndex, "Test Case Executtion Failed", Constant.Path_TestData);
					Reporter.log(sTestCaseName+" Test Case Failed");
					throw new Exception("Test Case Failed because of Verification");
				}
			}
			//If Test Case need not to be executed.
			else
			{
				/**
				 * This will be executed if the testcase execution is marked as NO in the excel file.
				 * It will update the excel file with test case skipped status
				 * and update the log file accordingly.
				 */
				Log.info("Test Case does not need to be executed");
				//Writing comment on the test data file why this case is skipped from the execution.
				ExcelUtils.setCellData(sheetName, "Result", rowIndex, "Skip", Constant.Path_TestData);
				ExcelUtils.setCellData(sheetName, "Comment", rowIndex, "Test Case Skiped", Constant.Path_TestData);
				Reporter.log(sTestCaseName+" Test Case Skipped");
				throw new SkipException("Skiping This Test as 'Need To Execute' Request is selected as No");
			}
		}
		catch(SkipException e)
		{
			Log.error(e);
			//throw(e);
		}
		catch(Exception e){
			Log.error(e);
		}
	}

	
	/**
	 * The methods will be used to code test cases.
	 * 
	 */

	@Test
	public void PersonalInformation_GSSP_3794() throws Exception {
		try
		{	
			//flag=true;
			sTestCaseName = new Exception().getStackTrace()[0].getMethodName();
			Reporter.log("Starting Execution for" + sTestCaseName);
			Log.startTestCase(sTestCaseName);
			Log.info("Testcase Being Tested --> " + sTestCaseName);
			rowIndex = ExcelUtils.getRowIndex(sheetName, sTestCaseName);

			/*
			 * This will write a log whether the case will be executed or not based on the Need to execute column
			 */
			Log.info("Value of NeedToExecute -->" + ExcelUtils.getCellData(sheetName, colName, rowIndex));
			
			/*
			 * Need to implement the test case logic inside the following if statement.
			 * If the if statement reads NO from excel file the code block will be skipped, the case will not be executed and marked as skipped.
			 */
			if(ExcelUtils.getCellData(sheetName, colName, rowIndex).equalsIgnoreCase("Yes"))
			{	
								
				//common.loginUser(sheetName,rowIndex);
				//action.clickLink(ProfileMaintenanceObjects.profile_Link);
								
				if(TestBase.verifyCompare(action.getText(ProfileMaintenanceObjects.profile_VerifyLandPage), ProfileMaintenanceVerification.login_VerifyCorrectLandPage)){
					
					TestBase.verifyCompare(action.getText(ProfileMaintenanceObjects.PerInfo_VerifyCard), ProfileMaintenanceVerification.PerInfo_VerifyCard);
					TestBase.verifyCompare(action.getText(ProfileMaintenanceObjects.PerInfo_Details), ProfileMaintenanceVerification.Profile_Details);
					TestBase.verifyCompare(action.getText(ProfileMaintenanceObjects.PerInfo_Update), ProfileMaintenanceVerification.Profile_Update);
					TestBase.verifyCompare(action.getText(ProfileMaintenanceObjects.PerInfo_Name), ExcelUtils.getCellData(sheetName, "LogInUser", rowIndex));
					TestBase.verifyCompare(action.getText(ProfileMaintenanceObjects.PerInfo_Email), ExcelUtils.getCellData(sheetName, "Email", rowIndex));
					TestBase.verifyCompare(action.getText(ProfileMaintenanceObjects.PerInfo_Mobile), ExcelUtils.getCellData(sheetName, "Mobile", rowIndex));
					TestBase.verifyCompare(action.getText(ProfileMaintenanceObjects.PerInfo_Address), ExcelUtils.getCellData(sheetName, "Address", rowIndex));
				}
				
						
								
				if(flag){
					/**
					 * This will update the Excel file will test result as pass. 
					 * This will update the xml report with the test case pass
					 */

					ExcelUtils.setCellData(sheetName, "Result", rowIndex, "Pass", Constant.Path_TestData);
					ExcelUtils.setCellData(sheetName, "Comment", rowIndex, "Test Case Executed Successfully", Constant.Path_TestData);
					Reporter.log(sTestCaseName+" Test Case Pass");
				}else{
					/**
					 * This will be executed if any of the steps fail
					 * It will take a screenshot of where the failure occurred.
					 */			
					//Utils.takeScreenshot(sTestCaseName, Constant.Path_ScreenShot);
					ExcelUtils.setCellData(sheetName, "Result", rowIndex, "Fail", Constant.Path_TestData);
					ExcelUtils.setCellData(sheetName, "Comment", rowIndex, "Test Case Executtion Failed", Constant.Path_TestData);
					Reporter.log(sTestCaseName+" Test Case Failed");
					throw new Exception("Test Case Failed because of Verification");
				}
			}
			//If Test Case need not to be executed.
			else
			{
				/**
				 * This will be executed if the testcase execution is marked as NO in the excel file.
				 * It will update the excel file with test case skipped status
				 * and update the log file accordingly.
				 */
				Log.info("Test Case does not need to be executed");
				//Writing comment on the test data file why this case is skipped from the execution.
				ExcelUtils.setCellData(sheetName, "Result", rowIndex, "Skip", Constant.Path_TestData);
				ExcelUtils.setCellData(sheetName, "Comment", rowIndex, "Test Case Skiped", Constant.Path_TestData);
				Reporter.log(sTestCaseName+" Test Case Skipped");
				throw new SkipException("Skiping This Test as 'Need To Execute' Request is selected as No");
			}
		}
		catch(SkipException e)
		{
			Log.error(e);
			//throw(e);
		}
		catch(Exception e){
			Log.error(e);
		}
	}

	
	/**
	 * The methods will be used to code test cases.
	 * 
	 */

/*	@Test
	public void UserAgreements_GSSP_3794() throws Exception {
		try
		{	
			*//**No Need to update any of the following code
			 * Next 6 Lines:
			 * * The flag will be used to stop execution and continue to next test case execution.
			 * * The Next 4 lines will read the testcase name based on the method name and log the information appropriately.
			 * * The Next 2 lines rowIndex and rowData are used to find the correct row with the data and fetching it from the excel file.

			 *//*
			
			//flag=true;
			sTestCaseName = new Exception().getStackTrace()[0].getMethodName();
			Reporter.log("Starting Execution for" + sTestCaseName);
			Log.startTestCase(sTestCaseName);
			Log.info("Testcase Being Tested --> " + sTestCaseName);
			rowIndex = ExcelUtils.getRowIndex(sheetName, sTestCaseName);
			
			
			 * This will write a log whether the case will be executed or not based on the Need to execute column
			 
			Log.info("Value of NeedToExecute -->" + ExcelUtils.getCellData(sheetName, colName, rowIndex));
			
			
			 * Need to implement the test case logic inside the following if statement.
			 * If the if statement reads NO from excel file the code block will be skipped, the case will not be executed and marked as skipped.
			 
			if(ExcelUtils.getCellData(sheetName, colName, rowIndex).equalsIgnoreCase("Yes"))
			{	
								
				//common.loginUser(sheetName,rowIndex);
				//action.clickLink(ProfileMaintenanceObjects.profile_Link);
								
				if(TestBase.verifyCompare(action.getText(ProfileMaintenanceObjects.profile_VerifyLandPage), ProfileMaintenanceVerification.login_VerifyCorrectLandPage)){
					
					TestBase.verifyCompare(action.getText(ProfileMaintenanceObjects.UserAgree_VerifyCard), ProfileMaintenanceVerification.UserAgree_VerifyCard);
					TestBase.verifyCompare(action.getText(ProfileMaintenanceObjects.UserAgree_Details), ProfileMaintenanceVerification.Profile_Details);
					TestBase.verifyCompare(action.getText(ProfileMaintenanceObjects.UserAgree_Update), ProfileMaintenanceVerification.Profile_Update);
					TestBase.verifyCompare(action.getText(ProfileMaintenanceObjects.UserAgree_VerifyCard), ProfileMaintenanceVerification.UserAgree_TermsofUse);
					TestBase.verifyCompare(action.getText(ProfileMaintenanceObjects.UserAgree_VerifyCard), ProfileMaintenanceVerification.UserAgree_ElecConsent);
				
				}
				
						
								
				if(flag){
					*//**
					 * This will update the Excel file will test result as pass. 
					 * This will update the xml report with the test case pass
					 *//*
					ExcelUtils.setCellData(sheetName, "Result", rowIndex, "Pass", Constant.Path_TestData);
					ExcelUtils.setCellData(sheetName, "Comment", rowIndex, "Test Case Executed Successfully", Constant.Path_TestData);
					Reporter.log(sTestCaseName+" Test Case Pass");
				}else{
					*//**
					 * This will be executed if any of the steps fail
					 * It will take a screenshot of where the failure occurred.
					 *//*			
//					Utils.takeScreenshot(sTestCaseName, Constant.Path_ScreenShot);
					ExcelUtils.setCellData(sheetName, "Result", rowIndex, "Fail", Constant.Path_TestData);
					ExcelUtils.setCellData(sheetName, "Comment", rowIndex, "Test Case Executtion Failed", Constant.Path_TestData);
					Reporter.log(sTestCaseName+" Test Case Failed");
					throw new Exception("Test Case Failed because of Verification");
				}
			}
			//If Test Case need not to be executed.
			else
			{
				*//**
				 * This will be executed if the testcase execution is marked as NO in the excel file.
				 * It will update the excel file with test case skipped status
				 * and update the log file accordingly.
				 *//*
				Log.info("Test Case does not need to be executed");
				//Writing comment on the test data file why this case is skipped from the execution.
				ExcelUtils.setCellData(sheetName, "Result", rowIndex, "Skip", Constant.Path_TestData);
				ExcelUtils.setCellData(sheetName, "Comment", rowIndex, "Test Case Skiped", Constant.Path_TestData);
				Reporter.log(sTestCaseName+" Test Case Skipped");
				throw new SkipException("Skiping This Test as 'Need To Execute' Request is selected as No");
			}
		}
		catch(SkipException e)
		{
			Log.error(e);
			//throw(e);
		}
		catch(Exception e){
			Log.error(e);
		}
	}*/

	
	/**
	 * The methods will be used to code test cases.
	 * 
	 */

/*	@Test
	public void TechDiffculties_GSSP_3794() throws Exception {
		try
		{	
			*//**No Need to update any of the following code

			 * Next 6 Lines:
			 * * The flag will be used to stop execution and continue to next test case execution.
			 * * The Next 4 lines will read the testcase name based on the method name and log the information appropriately.
			 * * The Next 2 lines rowIndex and rowData are used to find the correct row with the data and fetching it from the excel file.

			 *//*
			
			//flag=true;
			sTestCaseName = new Exception().getStackTrace()[0].getMethodName();
			Reporter.log("Starting Execution for" + sTestCaseName);
			Log.startTestCase(sTestCaseName);
			Log.info("Testcase Being Tested --> " + sTestCaseName);
			rowIndex = ExcelUtils.getRowIndex(sheetName, sTestCaseName);
			

			
			 * This will write a log whether the case will be executed or not based on the Need to execute column
			 
			Log.info("Value of NeedToExecute -->" + ExcelUtils.getCellData(sheetName, colName, rowIndex));
			
			
			 * Need to implement the test case logic inside the following if statement.
			 * If the if statement reads NO from excel file the code block will be skipped, the case will not be executed and marked as skipped.
			 

			if(ExcelUtils.getCellData(sheetName, colName, rowIndex).equalsIgnoreCase("Yes"))
			{	
								
				//common.loginUser(sheetName,rowIndex);
				//action.clickLink(ProfileMaintenanceObjects.profile_Link);
								
				if(TestBase.verifyCompare(action.getText(ProfileMaintenanceObjects.profile_VerifyLandPage), ProfileMaintenanceVerification.login_VerifyCorrectLandPage)){
					
					TestBase.verifyCompare(action.getText(ProfileMaintenanceObjects.TechDiff_VerifyCard), ProfileMaintenanceVerification.TechDiff_VerifyCard);
					TestBase.verifyCompare(action.getText(ProfileMaintenanceObjects.TechDiff_VerifyCard), ProfileMaintenanceVerification.TechDiff_Desc);
					TestBase.verifyCompare(action.getText(ProfileMaintenanceObjects.TechDiff_VerifyCard), ProfileMaintenanceVerification.TechDiff_DescTwo);
					flag= action.isElementPresentByXpath(ProfileMaintenanceObjects.TechDiff_ReloadButton);
				}
				
						
								
				if(flag){

					*//**
					 * This will update the Excel file will test result as pass. 
					 * This will update the xml report with the test case pass
					 *//*

					ExcelUtils.setCellData(sheetName, "Result", rowIndex, "Pass", Constant.Path_TestData);
					ExcelUtils.setCellData(sheetName, "Comment", rowIndex, "Test Case Executed Successfully", Constant.Path_TestData);
					Reporter.log(sTestCaseName+" Test Case Pass");
				}else{

					*//**
					 * This will be executed if any of the steps fail
					 * It will take a screenshot of where the failure occurred.
					 *//*			
//					Utils.takeScreenshot(sTestCaseName, Constant.Path_ScreenShot);

					ExcelUtils.setCellData(sheetName, "Result", rowIndex, "Fail", Constant.Path_TestData);
					ExcelUtils.setCellData(sheetName, "Comment", rowIndex, "Test Case Executtion Failed", Constant.Path_TestData);
					Reporter.log(sTestCaseName+" Test Case Failed");
					throw new Exception("Test Case Failed because of Verification");
				}
			}
			//If Test Case need not to be executed.
			else
			{

				*//**
				 * This will be executed if the testcase execution is marked as NO in the excel file.
				 * It will update the excel file with test case skipped status
				 * and update the log file accordingly.
				 *//*

				Log.info("Test Case does not need to be executed");
				//Writing comment on the test data file why this case is skipped from the execution.
				ExcelUtils.setCellData(sheetName, "Result", rowIndex, "Skip", Constant.Path_TestData);
				ExcelUtils.setCellData(sheetName, "Comment", rowIndex, "Test Case Skiped", Constant.Path_TestData);
				Reporter.log(sTestCaseName+" Test Case Skipped");
				throw new SkipException("Skiping This Test as 'Need To Execute' Request is selected as No");
			}
		}
		catch(SkipException e)
		{
			Log.error(e);
			//throw(e);
		}
		catch(Exception e){
			Log.error(e);
		}
	}*/

}
