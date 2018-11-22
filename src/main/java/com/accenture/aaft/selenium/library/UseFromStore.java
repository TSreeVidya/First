package com.accenture.aaft.selenium.library;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.report.ExtentTestManager;
import com.accenture.aaft.selenium.library.utility.WaitForObjectExist;
import com.accenture.aaft.selenium.library.utility.WebElementDetails;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Class is used to store a temporary value
 *
 * @author prashant.gurunathan
 *
 */
public class UseFromStore {

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
  public String useFromStore(WebDriver webDriver, String objectName, String selector, String inputValue, ExtentTest extentTest, String controlName,
		  Map<String,String> hmTempStore) {

	CTLogger.writeToLog("UseFromStore", "useFromStore()", " method called");
	PropertyFileReader propertyFileReader = new PropertyFileReader();
	String status = "false";
	String webDriverString;

	WebElement element = null;

	try {

	  webDriverString = webDriver.toString();
/*
	  WaitForObjectExist waitForObjectExist = new WaitForObjectExist();
	  waitForObjectExist.waitForExistance(webDriver, objectName, selector);
*/
	  WebElementDetails webElementDetails = new WebElementDetails();
	  String initText = null;
	  String storeValue = null;

	  if (webDriverString.toLowerCase().contains("androiddriver") || webDriverString.toLowerCase().contains("iosdriver")) {
		element = webElementDetails.getElement(webDriver, objectName, selector);
	  } else {
		List<WebElement> listElements = webElementDetails.getElements(webDriver, objectName, selector);
		element = listElements.get(0);
	  }

	  if (element.isDisplayed()) {

		if (webDriverString != null && webDriverString.toLowerCase().indexOf("androiddriver") < 0 && webDriverString.toLowerCase().indexOf("iosdriver") < 0) {
		  element.clear();
		  element.click();
		  initText = element.getAttribute("value");

		  if (initText != null && initText.length() > 0) {
			element.sendKeys(Keys.END, Keys.SHIFT, Keys.HOME, Keys.DELETE);

			for (int i = 1; i <= initText.length(); i++)
			  element.sendKeys(Keys.DELETE);
			CTLogger.writeToLog("UseFromStore", "useFromStore() ", "after deleting the text");
		  }
		}

		if (webDriverString != null) {

		  if (hmTempStore.containsKey(inputValue)) {
		    storeValue = hmTempStore.get(inputValue);
			initText = element.getAttribute("value");
		    if (initText != null) {
		      if (initText.length() > 0) {
			    element.sendKeys(Keys.END, Keys.SHIFT, Keys.HOME, Keys.DELETE);

			    for (int i = 1; i <= initText.length(); i++)
			      element.sendKeys(Keys.DELETE);
			    CTLogger.writeToLog("UseFromStore", "useFromStore() ", "after deleting the text");
		      }

			  element.sendKeys(storeValue);
			}
			else {
			  String jScript = "var element = arguments[0]; element.innerText = '" + storeValue + "';"; 
			  JavascriptExecutor executor = (JavascriptExecutor) webDriver;
			  executor.executeScript(jScript, element);
			}
		  }

  	      if (storeValue != null && storeValue.length() > 0) {
  		    CTLogger.writeToLog(" successfully fetched and used the value " + storeValue + " -- path : " + objectName + " --> " + controlName);
		    status = "true";
  	      }
	    }
	  }

	  if (status.equals("true")) {
		extentTest.log(LogStatus.PASS, " fetched and used " + storeValue + " into control " + inputValue);
	  } else {
		CTLogger.writeToLog("UseFromStore", "useFromStore()", " Object not found exception thrown  --> path : " + objectName + " --> " + controlName);
		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
		ExtentTestManager.setThreadStatus("f");
		webDriver.close();
		webDriver.quit();
	  }

	} catch (Exception ex) {
	  try {
		ex.printStackTrace();
		String err[] = ex.getMessage().split("\n");
		CTLogger.writeToLog("UseFromStore", "useFromStore()", " Object not found exception thrown  --> path : " + objectName + " --> " + controlName);
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
