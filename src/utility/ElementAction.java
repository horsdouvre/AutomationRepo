package utility;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;


/**
 * This class used to access all Element actions of different page objects.
 * @author Prasad Tirunagari
 * Version 1.0
 * Date Modified 02/03/2016
 */
public class ElementAction extends Utils{

	/* This method is used to find the element by xpath	*/
	public boolean isElementPresentByXpath(String xpath)
	{
		try
		{
			driver.findElement(By.xpath(xpath));
		}
		catch(Throwable t)
		{
			Log.error("Element not Found -->"+t.getMessage());
			return false;
		}
		return true;
	}

	/* This method is used to find the element by LinkText	*/
	public boolean isElementPresentByLinkText(String linkText)
	{
		try
		{
			driver.findElement(By.linkText(linkText));
		}
		catch(Throwable t)
		{
			Log.error("Element not Found -->"+t.getMessage());
			return false;
		}
		return true;
	}

	/* This method is used to find the element by ID	*/
	public boolean isElementPresentByID(String ID)
	{
		try
		{
			driver.findElement(By.id(ID));
		}
		catch(Throwable t)
		{
			Log.info("Element not Found -->"+t.getMessage());
			return false;
		}
		return true;
	}

	/* This method is used to find the element by Name	*/
	public boolean isElementPresentByName(String Name)
	{
		try
		{
			driver.findElement(By.name(Name));
		}
		catch(Throwable t)
		{
			Log.info("Element not Found -->"+t.getMessage());
			return false;
		}
		return true;
	}

	/* This method is used to find the element by ClassName	*/
	public boolean isElementPresentByClassName(String className)
	{
		try
		{
			driver.findElement(By.className(className));
		}
		catch(Throwable t)
		{
			Log.info("Element not Found -->"+t.getMessage());
			return false;
		}
		return true;
	}

	/* This method is used to click the Button by ID	*/
	public void clickButtonID(String ID) throws Exception
	{
		if(isElementPresentByID(ID))
		{
			driver.findElement(By.id(ID)).click();

		}
		else
		{
			throw new Exception("clickButtonID() --- >Element Not Present");
		}
	}

	/* This method is used to click the Button by xpath	*/
	public void clickButton(String ObjectxPath) throws Exception
	{
		if(isElementPresentByXpath(ObjectxPath))
		{
			driver.findElement(By.xpath(ObjectxPath)).click();

		}
		else
		{
			throw new Exception("clickButton --- >Element Not Present");
		}
	}

	/* This method is used to click the Link by ID	*/
	public void clickLink(String ObjectxPath) throws Exception
	{
		if(isElementPresentByXpath(ObjectxPath))
		{
			driver.findElement(By.xpath(ObjectxPath)).click();

		}
		else
		{
			throw new Exception("clickLink --- >Element Not Present");
		}
	}

	/* This method is used to click the link by LinkText	*/
	public void clickLinkByLinkText(String linkText) throws Exception
	{
		if(isElementPresentByLinkText(linkText))
		{
			driver.findElement(By.linkText(linkText)).click();;

		}
		else
		{
			throw new Exception("clickLink --- >Element Not Present");
		}
	}
	/* This method is used to clickFirst the Button by xpath	*/
	public boolean clickFirst(String ObjectxPath) throws Exception
	{
		boolean flag=true;
		if(isElementPresentByXpath(ObjectxPath))

		{
			driver.findElement(By.xpath(ObjectxPath)).click();;

		}
		else
			flag=false;
		return flag;
	}

	/* This method is used to input the text in textbox by xpath	*/
	public void inputText(String ObjectxPath,String sValue) throws Exception
	{
		if(isElementPresentByXpath(ObjectxPath))
		{
			driver.findElement(By.xpath(ObjectxPath)).sendKeys(sValue);

		}
		else
		{
			throw new Exception("inputText --- >Element Not Present");
		}
	}

	/* This method is used to input text by ID	*/
	public void inputTextByID(String ID,String sValue) throws Exception
	{
		if(isElementPresentByID(ID))
		{
			driver.findElement(By.id(ID)).sendKeys(sValue);
		}
		else
		{
			throw new Exception("inputText --- >Element Not Present");
		}
	}

	/* This method is used to select checkBox by xpath	*/
	public void selectCheckBox(String ObjectxPath) throws Exception
	{
		if(isElementPresentByXpath(ObjectxPath))
		{
			driver.findElement(By.xpath(ObjectxPath)).click();

		}
		else
		{
			throw new Exception("checkBox --- >Element Not Present");
		}
	}

	/* This method is used to select RadioButton by xpath	*/
	public void selectRadio(String ObjectxPath) throws Exception
	{
		if(isElementPresentByXpath(ObjectxPath))
		{
			driver.findElement(By.xpath(ObjectxPath)).click();
		}
		else
		{
			throw new Exception("checkBox --- >Element Not Present");
		}
	}

	/* This method is used to get text by xpath	*/
	public String getInputTextValue(String ObjectxPath) throws Exception
	{
		if(isElementPresentByXpath(ObjectxPath))
		{
			WebElement inputText = driver.findElement(By.xpath(ObjectxPath));
			return inputText.getText();
		}
		else
		{
			throw new Exception("get Text Value --- >Element Not Present");
		}
	}

	/* This method is used to clear input text by xpath	*/
	public void clearInputTextValue(String ObjectxPath) throws Exception
	{
		if(isElementPresentByXpath(ObjectxPath))
		{
			driver.findElement(By.xpath(ObjectxPath)).clear();

		}
		else
		{
			throw new Exception("inputText --- >Element Not Present");
		}
	}

	/* This method is used to clear input text by name	*/
	public void clearInputTextValueByName(String name) throws Exception
	{
		if(isElementPresentByName(name))
		{
			driver.findElement(By.name(name)).clear();

		}
		else
		{
			throw new Exception("inputText --- >Element Not Present");
		}
	}

	/* This method is used to clear input text and value by xpath	*/
	public void clearAndInputTextValue(String ObjectxPath,String value) throws Exception
	{

		if(isElementPresentByXpath(ObjectxPath))
		{
			WebElement inputText = driver.findElement(By.xpath(ObjectxPath));
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			inputText.clear();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			inputText.sendKeys(value);
		}
		else
		{
			throw new Exception("inputText --- >Element Not Present");
		}
	}

	/* This method is used to select the dropbox by xpath	*/
	public void selectDropBoxValue(String ObjectxPath,String value) throws Exception
	{
		if(isElementPresentByXpath(ObjectxPath))
		{
			WebElement selectDropBox = driver.findElement(By.xpath(ObjectxPath));
			Select select = new Select(selectDropBox);
			select.selectByValue(value);
		}
		else
		{
			throw new Exception("selectDropBoxValue() --- >Element Not Present");
		}

	}
	
	/* This method is used to select the dropbox by ID	*/
	public void selectDropBoxValueByID(String ID,String value) throws Exception
	{
		if(isElementPresentByID(ID))
		{
			WebElement selectDropBox = driver.findElement(By.id(ID));
			Select select = new Select(selectDropBox);
			select.selectByValue(value);
		}
		else
		{
			throw new Exception("selectDropBoxValue() --- >Element Not Present");
		}

	}
	
	/* This method is used to select the dropbox by Name	*/
	public void selectDropBoxValueByName(String Name,String value) throws Exception
	{
		if(isElementPresentByName(Name))
		{
			WebElement selectDropBox = driver.findElement(By.name(Name));
			Select select = new Select(selectDropBox);
			select.selectByValue(value);
		}
		else
		{
			throw new Exception("selectDropBoxValue() --- >Element Not Present");
		}

	}
	
	/* This method is used to select the dropbox by index	*/
	public void selectDropBoxValue(String ObjectxPath,int index) throws Exception
	{
		if(isElementPresentByXpath(ObjectxPath))		{
			WebElement selectDropBox = driver.findElement(By.xpath(ObjectxPath));
			Select select = new Select(selectDropBox);
			select.selectByIndex(index);
		}
		else
		{
			throw new Exception("selectDropBoxValue() --- >Element Not Present");
		}

	}
	
	/* This method is used to select the dropbox defaultvalue by xpath	*/
	public void selectDropBoxDefaultValue(String ObjectxPath) throws Exception
	{
		if(isElementPresentByXpath(ObjectxPath))
		{
			WebElement selectDropBox = driver.findElement(By.xpath(ObjectxPath));
			Select select = new Select(selectDropBox);
			select.selectByIndex(0);
		}
		else
		{
			throw new Exception("selectDropBoxDefaultValue() --- >Element Not Present");
		}

	}

	/* This method is used to get the dropbox default value by xpath	*/
	public String getDropBoxDefaultValue(String ObjectxPath) throws Exception
	{
		if(isElementPresentByXpath(ObjectxPath))
		{
			WebElement selectDropBox = driver.findElement(By.xpath(ObjectxPath));
			Select select = new Select(selectDropBox);
			return select.getFirstSelectedOption().getText();
		}
		else
		{
			throw new Exception("getDropBoxDefaultValue() --- >Element Not Present");
		}
	}

	/* This method is used to get the dropbox value by index	*/
	public String getDropBoxSelectedValue(String ObjectxPath,int index) throws Exception
	{
		if(isElementPresentByXpath(ObjectxPath))
		{
			WebElement selectDropBox = driver.findElement(By.xpath(ObjectxPath));
			Select select = new Select(selectDropBox);
			return select.getOptions().get(index).getText();
		}
		else
		{
			throw new Exception("getDropBoxSelectedValue() --- >Element Not Present");
		}
	}
	
	/* This method is used to get the dropbox size by xpath	*/
	public int getDropBoxSize(String ObjectxPath) throws Exception
	{
		if(isElementPresentByXpath(ObjectxPath))
		{
			WebElement selectDropBox = driver.findElement(By.xpath(ObjectxPath));
			Select select = new Select(selectDropBox);
			return select.getOptions().size();
		}
		else
		{
			throw new Exception("getDropBoxSize() --- >Element Not Present");
		}
	}

	/* This method is used to get the dropbox value by xpath	*/
	public String[] getDropBoxValue(String ObjectxPath) throws Exception
	{
		if(isElementPresentByXpath(ObjectxPath))
		{
			WebElement selectDropBox = driver.findElement(By.xpath(ObjectxPath));
			Select select =new Select(selectDropBox);
			List<WebElement> optionValue = select.getOptions();
			String[] value = new String[optionValue.size()];
			for(int i =0;i<optionValue.size();i++)
				value[i] = optionValue.get(i).getText();
			return value;
		}
		else
		{
			throw new Exception("selectDropBoxValue() --- >Element Not Present");
		}

	}

	/* This method is used to get the total table cell by xpath	*/
	public int getTotalTableCell(String ObjectxPath) throws Exception
	{
		if(isElementPresentByXpath(ObjectxPath))
		{
			return driver.findElements(By.xpath(ObjectxPath)).size();
		}
		else
		{
			throw new Exception("getTotalTableCell() --- >Element Not Present");
		}
	}

	/* This method is used to get total elements size by xpath	*/
	public int getElementsSize(String ObjectxPath) throws Exception
	{
		if(isElementPresentByXpath(ObjectxPath))
		{
			return driver.findElements(By.xpath(ObjectxPath)).size();
		}
		else
		{
			throw new Exception("getElementsSize() --- >Element Not Present");
		}
	}

	/* This method is used to get the element count by Classname	*/
	public int getElementCount(String className)
	{
		int count=0;

		if(isElementPresentByClassName(className))
		{
			count = driver.findElements(By.className(className)).size();
		}
		return count;
	}

	/* This method is used to verify the displayed element	*/
	public boolean isElementDisplay(String xpath)
	{
		boolean flag=false;
		try
		{
			if(driver.findElement(By.xpath(xpath)).isDisplayed())
				flag = true;
			else
				flag=true;
		}
		catch(Throwable e)
		{
			flag = false;
		}
		return flag;

	}

	/* This method is used to action on mouse over elements	*/
	public void mouseHoverAction(String mainElementXP, String subElementXP,String subSubElementXP) throws InterruptedException{

		Actions action = new Actions(driver);
		WebElement mainElement = driver.findElement(By.xpath(mainElementXP));
		action.moveToElement(mainElement).perform();
		WebElement subElement = driver.findElement(By.xpath(subElementXP));
		action.moveToElement(subElement).perform();
		WebElement subSubElement = driver.findElement(By.xpath(subSubElementXP));
		action.moveToElement(subSubElement).click().perform();
		Log.info("Click action is performed on the selected Product Type");
	}

	/* This method is used to action on mouse over elements	*/
	public void mouseHoverAction(String mainElementXP, String subElementXP){

		Actions action = new Actions(driver);
		WebElement mainElement = driver.findElement(By.xpath(mainElementXP));
		action.moveToElement(mainElement).perform();
		WebElement subElement = driver.findElement(By.xpath(subElementXP));
		action.moveToElement(subElement).click().perform();
		Log.info("Click action is performed on the selected Product Type");
	}

	/* This method is used to action on mouse over elements	*/
	public void mouseHoverAction(String mainElementXP){

		Actions action = new Actions(driver);
		WebElement mainElement = driver.findElement(By.xpath(mainElementXP));
		action.moveToElement(mainElement).clickAndHold().build().perform();
		action.release().perform();
		//action.perform();
		Log.info("Click action is performed on the selected Product Type");
	}

	/* This method is used to action on mouse over elements	*/
	public void mouseHoverAction(String mainElementXP, String subElementXP,String subSubElementXP, String subBesideElementXP) throws InterruptedException{

		Actions action = new Actions(driver);
		WebElement mainElement = driver.findElement(By.xpath(mainElementXP));
		action.moveToElement(mainElement).perform();
		WebElement subElement = driver.findElement(By.xpath(subElementXP));
		action.moveToElement(subElement).perform();
		WebElement subSubElement = driver.findElement(By.xpath(subSubElementXP));
		action.moveToElement(subSubElement).perform();
		WebElement subBesideElement = driver.findElement(By.xpath(subBesideElementXP));
		action.moveToElement(subBesideElement).perform();
		action.click();
		action.perform();
		Log.info("Click action is performed on the selected Product Type");
	}

	/* This method is used to selectClass with parent and element to select	*/
	public void selectclass(WebElement parent,String elementToSelect){

		Select dropdown = new Select(parent);
		dropdown.selectByVisibleText(elementToSelect);

	}

	/* This method is used to get window Handles for brower windows	*/
	public void getWindowHandle(String title) {
		Set<String> handles = driver.getWindowHandles();

		String[] browser =	handles.toArray(new String[0]);
		System.out.println("Number of broiwsers opened are"
				+ browser.length);
		for (int i=0; i<handles.size();i++) {

			driver.switchTo().window(browser[i]);
			if(driver.getTitle().equals(title)){
				driver.getWindowHandle();

			}

		}

	} 

	/* This method is used to get the table data	*/
	public void getTableData(String xpath)
	{
		// Grab the table 
		WebElement table = driver.findElement(By.xpath(xpath)); 

		// Now get all the TR elements from the table 
		List<WebElement> allRows = table.findElements(By.tagName("tr")); 

		// And iterate over them, getting the cells 
		for (WebElement row : allRows) { 
			List<WebElement> cells = row.findElements(By.tagName("td")); 

			// Print the contents of each cell
			for (WebElement cell : cells) { 
				System.out.println(cell.getText());
			}
		}
	}

	/* This method is used to double click any element	*/
	public void doubleClick(WebElement myElemment, String xpath) throws Exception
	{
		if(isElementPresentByXpath(xpath))
		{
			Actions action = new Actions(driver);
			action.moveToElement(myElemment);
			Thread.sleep(2000);
			action.doubleClick();
			//action.doubleClick(myElemment);
			action.build().perform();
		}
		else
		{
			throw new Exception("doubleClick() --- >Element Not Present");
		}
	}

	/* This method is used to getText from the page */
	public String getText(String ObjectxPath) throws Exception
	{
		if(isElementPresentByXpath(ObjectxPath))
		{
			return driver.findElement(By.xpath(ObjectxPath)).getText().toLowerCase();
	
		}
		else
		{
			throw new Exception("getText() --- >Element Not Present");
		}
	}
}
