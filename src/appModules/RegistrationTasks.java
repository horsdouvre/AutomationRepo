package appModules;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import appObjects.RegistrationObjects;
import utility.ElementAction;
import utility.ExcelUtils;
import utility.Utils;

public class RegistrationTasks extends CommonTasks{
	
	ElementAction action = new ElementAction();
	
	public void registrationCustomer(String functionality,int rowIndex) throws Exception {		
		action.inputText(RegistrationObjects.register_FirstName, ExcelUtils.getCellData(functionality,"FirstName", rowIndex));
		action.inputText(RegistrationObjects.register_LastName, ExcelUtils.getCellData(functionality,"LastName", rowIndex));
		action.inputText(RegistrationObjects.register_citizenId, ExcelUtils.getCellData(functionality,"CitizenID", rowIndex));
		action.inputText(RegistrationObjects.register_dateOfBirth, ExcelUtils.getCellData(functionality,"DOB", rowIndex));
		action.inputText(RegistrationObjects.register_postalCode, ExcelUtils.getCellData(functionality,"ZipCode", rowIndex));
		action.inputText(RegistrationObjects.register_emailAddress, ExcelUtils.getCellData(functionality,"Email", rowIndex));
		action.inputText(RegistrationObjects.register_confirmEmailAddress, ExcelUtils.getCellData(functionality,"ConfirmEmail", rowIndex));
		action.inputText(RegistrationObjects.register_mobilePhone, ExcelUtils.getCellData(functionality,"MobileNumber", rowIndex));
	}

	public void verifyRegistration() throws Exception {
		action.clickButton(RegistrationObjects.nextButton);
	}

	//This function is used for waiting till the captcha not selected manually 
	public void verifyCaptcha(WebDriver driver) throws Exception {
		action.clickButton(RegistrationObjects.captchaUnChecked);
		JOptionPane.showMessageDialog(null, "Please select captcha manually");
		while(action.isElementPresentByXpath(RegistrationObjects.captchaCheckedSign)){
			Utils.waitForElement(driver.findElement(By.xpath(RegistrationObjects.captchaCheckedSign)));
		}
	}

}
