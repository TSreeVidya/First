package com.accenture.aaft.selenium.library;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.report.ExtentTestManager;
import com.accenture.aaft.selenium.library.utility.WaitForObjectExist;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Class is used to perform click on particular item on UI
 *
 * @author vijay.venkatappa
 *
 */
public class ClickItem {

  /**
   * Method is used to perform click action
   *
   * @param webDriver - represents WebDriver
   * @param objectName - represents object name
   * @param selector - represents selector type
   * @param extentTest - represents ExtentTest
   * @param controlName - represents control name
   * @return status
   */
  public String click(WebDriver webDriver, String objectName, String selector, ExtentTest extentTest, String controlName) {

	CTLogger.writeToLog("Click", " click() ", " method called");
	PropertyFileReader propertyFileReader = new PropertyFileReader();
	String status = "false";

	try {
	  WaitForObjectExist waitForObjectExist = new WaitForObjectExist();
	  waitForObjectExist.waitForExistance(webDriver, objectName, selector);
	  ((JavascriptExecutor) webDriver).executeScript("document.getElementById('" + objectName + "').click()");
	  status = "true";

	  if (status.equals("true")) {
		extentTest.log(LogStatus.PASS, " clicked on " + controlName);
	  } else {
		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
		ExtentTestManager.setThreadStatus("f");
	  }
	} catch (NoSuchElementException exception) {
	  exception.printStackTrace();
	  CTLogger.writeToLog("Click ", " click()", " Object not found exeption thrown");
	  ExtentTestManager.setThreadStatus("f");
	  return "false";

	} catch (Exception ex) {
	  ex.printStackTrace();
	  CTLogger.writeToLog("Click ", " click()", " Exception occurred");
	  String err[] = ex.getMessage().split("\n");
	  ExtentTestManager.setThreadStatus("f");
	  return "Exception " + err[0].replaceAll("'", "") + " Occurred";
	}
	return status;
  }

}
