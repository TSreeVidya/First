package com.accenture.aaft.selenium.library;

import java.util.List;
import java.util.Map;

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
public class VerifyNotExists {

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
  public String verifyNotExists(WebDriver webDriver, String objectName, String selector, String inputValue, ExtentTest extentTest, String controlName) {

	CTLogger.writeToLog("VerifyNotExists", "verifyNotExists()", " method called");
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

	  if (webDriverString.toLowerCase().contains("androiddriver") || webDriverString.toLowerCase().contains("iosdriver")) {
		element = webElementDetails.getElement(webDriver, objectName, selector, 5);
		if (element == null ) {
		  status = "true";
		}
	  } else if (webDriverString != null){
		List<WebElement> listElements = webElementDetails.getElements(webDriver, objectName, selector, 5);
		if (listElements.size() == 0) {
		  status = "true";
		}
	  }

	  if (status.equals("true")) {
		extentTest.log(LogStatus.PASS, " verified " + controlName + " does not exist");
	  } else {
		CTLogger.writeToLog("VerifyNotExists", "verifyNotExists()", " Object found --> path : " + objectName + " --> " + controlName);
		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
		ExtentTestManager.setThreadStatus("f");
		webDriver.close();
		webDriver.quit();
	  }

	} catch (Exception ex) {
	  try {
		ex.printStackTrace();
		String err[] = ex.getMessage().split("\n");
		CTLogger.writeToLog("VerifyNotExists", "verifyNotExists()", " Exception thrown  --> path : " + objectName + " --> " + controlName);
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
