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
 * Class is used to perform click action on UI
 *
 * @author vijay.venkatappa
 *
 */
public class ClickCP {

  /**
   * Method is used to perform click action on UI
   *
   * @param webDriver - represents WebDriver
   * @param objectName - represents object name
   * @param selector - represents selector type
   * @param extentTest - represents ExtentTest
   * @param controlName - represents control name
   * @return status
   */
  public String click(WebDriver webDriver, String objectName, String selector, ExtentTest extentTest, String controlName) {

	CTLogger.writeToLog("ClickCP", " click() ", " method called");
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

		CTLogger.writeToLog("ClickCP", " click() ", " button clicked -- path : " + objectName + " --> " + controlName);
		status = "true";
		
	  } else {

		CTLogger.writeToLog("ClickCP ", " click()", " Object not found" + " --> " + controlName);
		status = "false";
	  }

	  if (status.equals("true")) {
		extentTest.log(LogStatus.PASS, " clicked on " + controlName);
		if(controlName.equals("clickOnSpeed") || controlName.equals("clickOnAction")){
			extentTest.log(LogStatus.INFO, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
				
		}
		
	  } else {

		CTLogger.writeToLog("ClickCP", "click()", " Object not found exeption thrown  --> path : " + objectName + " --> " + controlName);
		extentTest.log(LogStatus.FAIL, " failed to click on " + controlName);
		extentTest.log(LogStatus.INFO, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
		//webDriver.quit();
		ExtentTestManager.setThreadStatus("f");
	  }

	} catch (Exception e) {
	  try {
		e.printStackTrace();
		CTLogger.writeToLog("Click", "click()", " Object not found exeption thrown  --> path : " + objectName + " --> " + controlName);
		status = "false";
	  } finally {
		extentTest.log(LogStatus.FAIL, " failed to click on " + controlName);
		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
		//webDriver.quit();
		ExtentTestManager.setThreadStatus("f");
	  }

	}

	return status;
  }

}
