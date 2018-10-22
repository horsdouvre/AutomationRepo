package appModules;

import appObjects.LoginObjects;
import utility.ElementAction;

public class LoginTasks extends CommonTasks{
	
	ElementAction action = new ElementAction();

	public void ConsentPage() throws Exception {
		action.selectRadio(LoginObjects.login_ConsentRadioYes);		
		action.clickButton(LoginObjects.login_ContinueButton);
	}
}
