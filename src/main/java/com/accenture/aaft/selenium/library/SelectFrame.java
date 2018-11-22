package com.accenture.aaft.selenium.library;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
 * Class is used to perform select frame action in browser
 *
 * @author vijay.venkatappa
 *
 */
public class SelectFrame {

  /**
   * Method is used to select frame in browser
   *
   * @param webDriver - represents WebDriver
   * @param objectName - represents object name
   * @param selector - represents selector type
   * @param extentTest - represents ExtentTest
   * @param controlName - represents control name
   * @return status
   */
  public String selectFrame(WebDriver webDriver, String objectName, String selector, ExtentTest extentTest, String controlName) {

	CTLogger.writeToLog("SelectFrame", "selectFrame() ", "method called");
	PropertyFileReader propertyFileReader = new PropertyFileReader();
	String status = "false";
	try {
	  WaitForObjectExist waitForObjectExist = new WaitForObjectExist();
	  waitForObjectExist.waitForExistance(webDriver, objectName, selector);

	  WebElementDetails webElementDetails = new WebElementDetails();
	  webDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	  List<WebElement> listElements = webElementDetails.getElements(webDriver, objectName, selector);

	  WebElement frame = listElements.get(0);
	  webDriver.switchTo().frame(frame);
	  status = "true";
	  if (status.equals("true")) {
		extentTest.log(LogStatus.PASS, " clicked on " + controlName);
	  } else {
		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
		ExtentTestManager.setThreadStatus("f");
	  }

	} catch (Exception ex) {
	  try {
		ex.printStackTrace();
		CTLogger.writeToLog("SelectFrame", "selectFrame() ", "Exception occurred");

		String err[] = ex.getMessage().split("\n");
		status = "Exception " + err[0].replaceAll("'", "") + " Occurred";
		return status;

	  } finally {

		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
		ExtentTestManager.setThreadStatus("f");
		webDriver.quit();

	  }
	}

	return status;
  }

}
