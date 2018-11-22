package com.accenture.aaft.selenium.library;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import com.accenture.aaft.selenium.library.utility.WaitForObjectExist;
import com.accenture.aaft.selenium.library.utility.WebElementDetails;
import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.report.ExtentTestManager;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Class is used to perform select from list action
 *
 * @author vijay.venkatappa
 *
 */
public class SelectFromList {

  /**
   * Method is used to perform select from list action
   *
   * @param webDriver - represents WebDriver
   * @param objectName - represents object name
   * @param selector - represents selector type
   * @param input - represents input value
   * @param selection - represents selection type
   * @param extentTest - represents ExtentTest
   * @param controlName - represents control name
   * @return status
   */
  public String selectFromList(WebDriver webDriver, String objectName, String selector, String inputValue, String selection, ExtentTest extentTest, String controlName) {

	CTLogger.writeToLog("SelectFromList", "selectFromList() ", " objectName - " + objectName + ".. input - " + inputValue + ".. selection - " + selection);

	System.out.println("objectName------>:" + objectName + "\n"+"inputValue----->:" + inputValue +"\n"+ "selector---->:"+selector+"\n"+"selection---->:" + selection);

	
	PropertyFileReader propertyFileReader = new PropertyFileReader();
	String status = "false";
	String[] inputArray = inputValue.split(",");
	
	String input  = inputArray[0];
	selection  = inputArray[1];
	
	CTLogger.writeToLog("SelectFromList", "selectFromList() ", " objectName - " + objectName + ".. input - " + inputValue + ".. selection - " + selection);
	try {

	  WaitForObjectExist waitForObjectExist = new WaitForObjectExist();
	  waitForObjectExist.waitForExistance(webDriver, objectName, selector);

	  WebElementDetails webElementDetails = new WebElementDetails();
	  List<WebElement> listElements = webElementDetails.getElements(webDriver, objectName, selector);

	  WebElement selectElement = listElements.get(0);

	  if (selectElement.isDisplayed()) {

		// return tag of Html whether is it input/select/span/value
		CTLogger.writeToLog("select Element isDisplayed " + selectElement.getTagName());

		if ((selectElement.getTagName().equalsIgnoreCase("input")) || (selectElement.getTagName().equalsIgnoreCase("span"))) {

		  CTLogger.writeToLog("SeleniumKeywordslib", "select FromList()--- TagName", selectElement.getTagName());
		  selectElement.clear();
		  selectElement.sendKeys(input);
		  selectElement.sendKeys(Keys.RETURN);
		  CTLogger.writeToLog("SeleniumKeywordslib", "select FromList() ", input + "=> Item was selected successfully from the" + "'" + objectName + "'" + "ListBox");
		  status = "true";
		  extentTest.log(LogStatus.PASS, "select " + input + " option from " + objectName + " dropdown");
		  return status;

		} else if ((selectElement.getTagName().equalsIgnoreCase("select"))) {

		  Select select = new Select(selectElement);
		  if (selection.equalsIgnoreCase("value")) {
			CTLogger.writeToLog("SeleniumKeywordslib", "select FromList() ", "by option value");

			select.selectByValue(input);
			webDriver.manage().timeouts().pageLoadTimeout(1, TimeUnit.MINUTES);
			CTLogger.writeToLog("SeleniumKeywordslib", "select FromList() ", input + "=> Item was selected successfully from the" + "'" + objectName + "'" + "ListBox");
			status = "true";
			extentTest.log(LogStatus.PASS, "select " + input + " option from " + objectName + " dropdown");
			return status;

		  } else if (selection.equalsIgnoreCase("text")) {
			CTLogger.writeToLog("SeleniumKeywordslib", "select FromList() ", "by label");
			// select.selectByValue(input.trim());
			select.selectByVisibleText(input.trim());
			CTLogger.writeToLog("SeleniumKeywordslib", "select FromList() ", input + "=> Item was selected successfully from the" + "'" + objectName + "'" + "ListBox");
			status = "true";
			extentTest.log(LogStatus.PASS, "select " + input + " option from " + objectName + " dropdown");
			return status;

		  } else if (selection.equalsIgnoreCase("index")) {
			CTLogger.writeToLog("SeleniumKeywordslib", "select FromList() ", "by option index");
			List<WebElement> oPtionsList = new ArrayList<WebElement>();
			oPtionsList = select.getOptions();
			for (int i = 0; i < oPtionsList.size(); i++) {

			  if (oPtionsList.get(i).getText().trim().replaceAll("\\s+", " ").equalsIgnoreCase(input.trim().replaceAll("\\s+", " "))) {
				select.selectByIndex(i);
				status = "true";
				extentTest.log(LogStatus.PASS, "select " + input + "from list dropdown" + objectName);
				CTLogger.writeToLog("SeleniumKeywordslib", "select FromList() ", input + "=> Item was selected successfully from the" + "'" + objectName + "'" + "ListBox");
				return status;
			  } else {
				status = "false";
			  }
			}

		  }

		} else {
		  CTLogger.writeToLog("SeleniumKeywordslib", "select FromList()--- TagName is not input neither select", selectElement.getTagName());
		  status = "false";
		  extentTest.log(LogStatus.FAIL, "select " + input + " option from " + objectName + " dropdown");
		  ExtentTestManager.setThreadStatus("f");
		  return status;
		}

	  } else if (!selectElement.isDisplayed()) {

		CTLogger.writeToLog("select Element isNotDisplayed " + selectElement.getTagName());

		if ((selectElement.getTagName().equalsIgnoreCase("input")) || (selectElement.getTagName().equalsIgnoreCase("span"))) {
		  selectElement.clear();
		  selectElement.sendKeys(input);
		  selectElement.sendKeys(Keys.RETURN);
		  status = "true";
		  extentTest.log(LogStatus.PASS, "select " + input + "from list dropdown" + objectName);
		  CTLogger.writeToLog("SeleniumKeywordslib", "select FromList() ", input + "=> Item was selected successfully from the" + "'" + objectName + "'" + "ListBox");
		} else {
		  Select select = new Select(selectElement);
		  if (selection.equalsIgnoreCase("value")) {
			CTLogger.writeToLog("SeleniumKeywordslib", "select FromList() ", "by option value");

			select.selectByValue(input);
			status = "true";
			extentTest.log(LogStatus.PASS, "select " + input + "from list dropdown" + objectName);
			CTLogger.writeToLog("SeleniumKeywordslib", "select FromList() ", input + "=> Item was selected successfully from the" + "'" + objectName + "'" + "ListBox");
		  } else if (selection.equalsIgnoreCase("text")) {
			CTLogger.writeToLog("SeleniumKeywordslib", "select FromList() ", "by label");
			select.selectByVisibleText(input.trim());
			status = "true";
			extentTest.log(LogStatus.PASS, "select " + input + " option from " + objectName + " dropdown");
			CTLogger.writeToLog("SeleniumKeywordslib", "select FromList() ", input + "=> Item was selected successfully from the" + "'" + objectName + "'" + "ListBox");
		  } else if (selection.equalsIgnoreCase("index")) {
			CTLogger.writeToLog("SeleniumKeywordslib", "select FromList() ", "by option index");
			select.selectByIndex(Integer.parseInt(input));
			status = "true";
			extentTest.log(LogStatus.PASS, "select " + input + " option from " + objectName + " dropdown");
			CTLogger.writeToLog("SeleniumKeywordslib", "select FromList() ", input + "=> Item was selected successfully from the" + "'" + objectName + "'" + "ListBox");
		  }
		}

	  } else {
		CTLogger.writeToLog("SeleniumKeywordslib", "select FromList() ", "'" + objectName + "' list was not visible");
		status = "false";
		extentTest.log(LogStatus.FAIL, "select " + input + " option from " + objectName + " dropdown");
		ExtentTestManager.setThreadStatus("f");
		return status;
	  }

	} catch (Exception ex) {
	  try {
		ex.printStackTrace();
		String err[] = ex.getMessage().split("\n");
		CTLogger.writeToLog("SelectFromList", "selectFromList()", " Object not found exeption thrown  --> path : " + objectName);
		status = "Exception " + err[0].replaceAll("'", "") + " Occurred";
	  } finally {
		extentTest.log(LogStatus.FAIL, "Unable to select " + input + " option from " + objectName + " dropdown");
		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH"))));
		ExtentTestManager.setThreadStatus("f");
		webDriver.close();
		webDriver.quit();

	  }
	}
	return status;

  }

}
