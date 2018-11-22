package com.accenture.aaft.selenium.library;

import java.util.Set;
import org.openqa.selenium.WebDriver;
import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.report.ExtentTestManager;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Class is used to perform select window action
 *
 * @author vijay.venkatappa
 *
 */
public class SelectWindow {

  /**
   * Method is used to perform select window action
   *
   * @param webDriver - represents WebDriver
   * @param title - represents browser title
   * @param extentTest - represents ExtentTest
   * @param controlName - represents control name
   * @return status
   */
  public String selectWindow(WebDriver webDriver, String title, ExtentTest extentTest, String controlName) {

	CTLogger.writeToLog("SelectWindow", "selectWindow() ", "title - " + title);
	PropertyFileReader propertyFileReader = new PropertyFileReader();
	String status = "false";

	try {

	  String parentwindow = webDriver.getWindowHandle();
	  Set<String> availableWindows = webDriver.getWindowHandles();

	  if (!availableWindows.isEmpty()) {

		CTLogger.writeToLog("SelectWindow", "selectWindow() ", "No Of windows available" + availableWindows.size());
		for (String winHandle : availableWindows) {
		  CTLogger.writeToLog("Window Title  " + webDriver.getTitle());
		  String currURL = webDriver.getCurrentUrl();
		  CTLogger.writeToLog("Window getCurrentUrl  " + currURL);

		  if (!parentwindow.equalsIgnoreCase(winHandle)) {
			webDriver.switchTo().window(winHandle);
			CTLogger.writeToLog("SelectWindow", "selectWindow() ", "'" + title + "' =>Document is activated successfully");
			status = "true";
		  }
		}
		if (status.equals("true")) {
		  extentTest.log(LogStatus.PASS, " clicked on " + controlName);
		} else {
		  extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
		  ExtentTestManager.setThreadStatus("f");
		}

	  } else {
		CTLogger.writeToLog("SelectWindow", "selectWindow() ", "Available windows size is zero");
		status = "Available windows size is zero";
		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
		ExtentTestManager.setThreadStatus("f");
		return status;
	  }

	}

	catch (Exception ex) {
	  try {
		ex.printStackTrace();
		CTLogger.writeToLog("SelectWindow", "selectWindow() ", "Exception occurred");

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
