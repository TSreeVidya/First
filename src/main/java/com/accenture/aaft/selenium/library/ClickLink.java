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
 * Class is used to perform click the link on UI
 *
 * @author vijay.venkatappa
 *
 */
public class ClickLink {

  /**
   * Method is used to click the link on UI
   *
   * @param webDriver - represents WebDriver
   * @param objectName - represents object name
   * @param selector - represents selector type
   * @param extentTest - represents ExtentTest
   * @param controlName - represents control name
   * @return status
   */
  public String clickLink(WebDriver webDriver, String objectName, String selector, ExtentTest extentTest, String controlName) {

	CTLogger.writeToLog("ClickLink", " clickLink() ", " method called");
	PropertyFileReader propertyFileReader = new PropertyFileReader();
	String status = "false";

	try {

	  WaitForObjectExist waitForObjectExist = new WaitForObjectExist();
	  waitForObjectExist.waitForExistance(webDriver, objectName, selector);

	  WebElementDetails webElementDetails = new WebElementDetails();
	  List<WebElement> listElements = webElementDetails.getElements(webDriver, objectName, selector);
	  WebElement txtField = listElements.get(0);

	  if (txtField.isDisplayed()) {

		txtField.click();

		CTLogger.writeToLog("ClickLink", " clickLink() ", " link clicked");
		status = "true";

	  } else {

		CTLogger.writeToLog("ClickLink ", " clickLink()", " Object not found");
		status = "false";
	  }

	  if (status.equals("true")) {
		extentTest.log(LogStatus.PASS, " clicked on " + controlName);
	  } else {
		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
		ExtentTestManager.setThreadStatus("f");
	  }

	} catch (Exception ex) {
	  try {
		ex.printStackTrace();
		CTLogger.writeToLog("Click ", " click()", " Exception occurred");
		String err[] = ex.getMessage().split("\n");
		return "Exception " + err[0].replaceAll("'", "") + " Exception Occurred";
	  } finally {
		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
		webDriver.quit();
		ExtentTestManager.setThreadStatus("f");
	}
	}

	return status;
  }

}
