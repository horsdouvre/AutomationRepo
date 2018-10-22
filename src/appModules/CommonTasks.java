package appModules;

import appObjects.LoginObjects;
import utility.ElementAction;
import utility.ExcelUtils;
import utility.Utils;

public class CommonTasks extends Utils{

	ElementAction action = new ElementAction();
	
	public void clickLink(String link) throws Exception {
		action.clickLink(link);
	}
	
	public void loginUser(String functionality,int rowIndex)throws Exception {
		action.inputText(LoginObjects.login_UserName, ExcelUtils.getCellData(functionality,"UserName", rowIndex));
		action.inputText(LoginObjects.login_Password, ExcelUtils.getCellData(functionality,"Password", rowIndex));
		action.clickButton(LoginObjects.login_LoginButton);
	}
	
	
}
