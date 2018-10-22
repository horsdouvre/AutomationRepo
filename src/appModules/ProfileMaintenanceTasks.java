package appModules;

import org.openqa.selenium.WebDriver;

import appObjects.LoginObjects;
import utility.ElementAction;

public class ProfileMaintenanceTasks extends CommonTasks{
	
	ElementAction action = new ElementAction();
	boolean flag;
	
	public boolean ConsentPage(WebDriver driver, String sTestCaseName) throws Exception {
		flag = false;
		
		action.selectRadio(LoginObjects.login_ConsentRadioYes);		
		action.clickButton(LoginObjects.login_ContinueButton);
	
		flag=true;
		return flag;
	}
}
