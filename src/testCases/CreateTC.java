package testCases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import appModules.LoginTasks;
import utility.Constant;
import utility.ElementAction;
import utility.ExcelUtils;
import utility.Log;
import utility.Utils;

@Listeners({utility.Listener.class})
public class CreateTC {

	WebDriver driver;
	String sTestCaseName;
	String sheetName = "StartHere";
	ElementAction action = new ElementAction();

	LoginTasks testStep = null;


	@BeforeClass
	public void beforeClass() throws Exception{
				driver =  Utils.OpenBrowser(Constant.appName);
		
				action.clickLink(".//*[@id='content']/div/div/section/div/div/p[2]/a");
				action.inputTextByID("login-form-username", "bhavdeep.patel@metlife.com");
				action.inputTextByID("login-form-password", "  ");// Enter your password in the blank here
				action.clickLink(".//*[@id='login-form-submit']");
	}

	@AfterClass
	public void afterClass() throws Exception{   
		driver.quit(); 
		ExcelUtils.closeExcelFile();
	}


	@BeforeMethod
	public void beforeTestCase() throws Exception {
		ExcelUtils.openExcel(Constant.Path_TestData, sheetName);
	}


	@AfterMethod
	public void afterTestCase() throws Exception {
		Log.endTestCase(sTestCaseName);
	}


	@Test
	public void CreateCase() throws Exception {
		try
		{	
			int itr1 = ExcelUtils.getRowCount(sheetName);

			String url ="https://172.26.60.6:8443/secure/CreateIssue.jspa?pid=10000&issuetype=10300";
			String Summary, Description, Label, TestStep, ExpectedResult;

			for(int i = 2; i<itr1;i++){
				String newSheetName = ExcelUtils.getCellData(sheetName, "Story", i);

				if(ExcelUtils.getCellData(sheetName, "Execute", i+2).equalsIgnoreCase("Yes")){
					int itr2 = ExcelUtils.getRowCount(newSheetName);
					for(int j = 2; j<itr2; j++){
						System.out.println(ExcelUtils.getCellData(newSheetName, "Execute", j));
						if(ExcelUtils.getCellData(newSheetName, "Execute", j).equalsIgnoreCase("Yes")){
							Summary = ExcelUtils.getCellData(newSheetName, "TestCaseID", j);
							Description = ExcelUtils.getCellData(newSheetName, "TestDescription", j) + 
									"\n\n\nPre-Condition\n" + ExcelUtils.getCellData(newSheetName, "PreConditions", j);
							Label = ExcelUtils.getCellData(newSheetName, "Label", j);
							TestStep = ExcelUtils.getCellData(newSheetName, "TestSteps", j);
							ExpectedResult = ExcelUtils.getCellData(newSheetName, "ExpectedResult", j);

							driver.get(url);
							action.inputTextByID("summary", Summary);
							action.inputTextByID("description", Description);
							action.inputTextByID("issuelinks-issues-textarea", Label);
							action.inputTextByID("labels-textarea", Label);
							action.clickLink(".//*[@id='issue-create-submit']");

							action.inputText(".//*[@id='project-config-steps-table']/tbody[2]/tr/td[3]/textarea", TestStep);
							action.inputText(".//*[@id='project-config-steps-table']/tbody[2]/tr/td[5]/textarea", ExpectedResult);
							action.clickLink(".//*[@id='project-config-steps-table']/tbody[2]/tr/td[6]/div/input");

							String JiraID = action.getText(".//*[@id='key-val']");
							String currURL = driver.getCurrentUrl();
							ExcelUtils.setCellData(newSheetName, "JiraID", j, JiraID, Constant.Path_TestData);
							ExcelUtils.setCellData(newSheetName, "JiraLink", j, currURL , Constant.Path_TestData);
							ExcelUtils.setCellData(newSheetName, "Execute", j, "No", Constant.Path_TestData);
						}
					}
				}
			}
		}
		catch(Exception e){
			Log.error(e);
		}
	}

}
