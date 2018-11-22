package com.accenture.aaft.selenium.library;

import java.util.List;

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
 * Class is used to compare count of Xpath element against provided value
 *
 * @author prashant.gurunathan
 *
 */
public class MatchElementCountToValue {

  /**
   * Method is used to compare counts of two Xpath elements
   *
   * @param webDriver - represents WebDriver
   * @param objectName - represents object name
   * @param selector - represents selector type
   * @param inputValue - represents input value
   * @param extentTest - represents ExtentTest
   * @param controlName - represents control name
   * @return status
   */
  public String matchElementCountToValue(WebDriver webDriver, String objectName, String selector, String inputValue, ExtentTest extentTest, String controlName) {

	CTLogger.writeToLog("MatchElementCountToValue", "matchElementCountToValue()", " method called");
	PropertyFileReader propertyFileReader = new PropertyFileReader();
	String status = "false";

	try {
	  WaitForObjectExist waitForObjectExist = new WaitForObjectExist();
	  waitForObjectExist.waitForExistance(webDriver, objectName, selector);

	  WebElementDetails webElementDetails = new WebElementDetails();
	  List<WebElement> listElements = webElementDetails.getElements(webDriver, objectName, selector);
	  int intValue = -1;

	  try {
		intValue = Integer.parseInt(inputValue); 
	    if (listElements.size() == intValue) {
		  status = "true";
		}
	  }
	  catch (NumberFormatException nfe) {
	    CTLogger.writeToLog("MatchElementCountToValue", "matchElementCountToValue()", " provided count does not resolve to a value -- " + inputValue);
	  }

	  if (status.equals("true")) {
		CTLogger.writeToLog("MatchElementCountToValue", "matchElementCountToValue()", "  count of elements match count provided -- " + intValue);
		extentTest.log(LogStatus.PASS, " count of elements match count provided -- " + intValue);
	  } else {
		CTLogger.writeToLog("MatchElementCountToValue", "matchElementCountToValue()", " count of elements does not match count provided -- " + listElements.size() + " & " + inputValue);
		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
		//ExtentTestManager.setThreadStatus("f");
		//webDriver.close();
		//webDriver.quit();
	  }

	} catch (Exception ex) {
	  try {
		ex.printStackTrace();
		String err[] = ex.getMessage().split("\n");
		CTLogger.writeToLog("MatchElementCountToValue", "matchElementCountToValue()", " Objects not found exception thrown  --> path : " + objectName + " --> " + controlName);
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
