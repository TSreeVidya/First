package com.accenture.aaft.selenium.library;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.report.ExtentTestManager;
//import com.accenture.aaft.selenium.driver.ExecuteScript;
import com.accenture.aaft.selenium.library.utility.WaitForObjectExist;
import com.accenture.aaft.selenium.library.utility.WebElementDetails;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Class is used to perform the input action on UI
 *
 * @author vijay.venkatappa
 *
 */
public class Input {

  /**
   * Method is used to enter the text on UI
   *
   * @param webDriver - represents WebDriver
   * @param objectName - represents object name
   * @param selector - represents selector type
   * @param inputValue - represents input value
   * @param extentTest - represents ExtentTest
   * @param controlName - represents control name
   * @return status
   */
  public String enterText(WebDriver webDriver, String objectName, String selector, String inputValue, ExtentTest extentTest, String controlName) {

	CTLogger.writeToLog("Input", "input()", " method called");
	PropertyFileReader propertyFileReader = new PropertyFileReader();
	String status = "false";
	String webDriverString;

	WebElement txtField = null;

	try {

	  webDriverString = webDriver.toString();

	  WaitForObjectExist waitForObjectExist = new WaitForObjectExist();
	  waitForObjectExist.waitForExistance(webDriver, objectName, selector);

	  WebElementDetails webElementDetails = new WebElementDetails();

	  if (webDriverString.toLowerCase().contains("androiddriver") || webDriverString.toLowerCase().contains("iosdriver")) {
		txtField = webElementDetails.getElement(webDriver, objectName, selector);
	  } else {
		List<WebElement> listElements = webElementDetails.getElements(webDriver, objectName, selector);
		txtField = listElements.get(0);
	  }

	  if (txtField.isDisplayed()) {

		if (webDriverString != null && webDriverString.toLowerCase().indexOf("androiddriver") < 0 && webDriverString.toLowerCase().indexOf("iosdriver") < 0) {
		  txtField.clear();
		  //txtField.click();
		  String jScript = "var element = arguments[0]; element.click();"; 
		  JavascriptExecutor executor = (JavascriptExecutor) webDriver;
		  executor.executeScript(jScript, txtField);
		  String initText = txtField.getAttribute("value");

		  if (initText != null && initText.length() > 0) {
			txtField.sendKeys(Keys.END, Keys.SHIFT, Keys.HOME, Keys.DELETE);

			for (int i = 1; i <= initText.length(); i++)
			  txtField.sendKeys(Keys.DELETE);
			CTLogger.writeToLog("Input", "input() ", "after deleting the text");
		  }
		}
		txtField.sendKeys(inputValue);
		CTLogger.writeToLog(" edit box successfully accepted the value  -- path : " + objectName + " --> " + controlName);
		status = "true";
	  }

	  if (status.equals("true")) {
		/*
		if (ExecuteScript.captureScreenPass) {
		  if (controlName.toLowerCase().contains("password") || controlName.toLowerCase().contains("pwd")) {
			extentTest.log(LogStatus.PASS, " entered password ******** into " + controlName + " " + extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
		  } else {
			extentTest.log(LogStatus.PASS, " entered " + inputValue + " into " + controlName + " " + extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
		  }
		} else {
		  if (controlName.toLowerCase().contains("password") || controlName.toLowerCase().contains("pwd")) {
			extentTest.log(LogStatus.PASS, " entered password ******** into " + controlName);
		  } else {
			extentTest.log(LogStatus.PASS, " entered " + inputValue + " into " + controlName);
		  }
		}
		*/
		if (controlName.toLowerCase().contains("password") || controlName.toLowerCase().contains("pwd")) {
		  extentTest.log(LogStatus.PASS, " entered password ******** into " + controlName);
		} else {
	      extentTest.log(LogStatus.PASS, " entered " + inputValue + " into " + controlName);
		}
	  } else {
		CTLogger.writeToLog("Input", "input()", " Object not found exeption thrown  --> path : " + objectName + " --> " + controlName);
		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
		ExtentTestManager.setThreadStatus("f");
		webDriver.close();
		webDriver.quit();
	  }

	} catch (Exception ex) {
	  try {
		ex.printStackTrace();
		String err[] = ex.getMessage().split("\n");
		CTLogger.writeToLog("Input", "input()", " Object not found exeption thrown  --> path : " + objectName + " --> " + controlName);
		status = "Exception " + err[0].replaceAll("'", "") + " Occurred";
	  } finally {

		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
		ExtentTestManager.setThreadStatus("f");
		webDriver.quit();

	  }

	}

	return status;

  }

}
