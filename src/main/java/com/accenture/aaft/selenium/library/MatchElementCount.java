package com.accenture.aaft.selenium.library;

import java.util.List;

import org.openqa.selenium.By;
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
 * Class is used to compare counts of two Xpath elements
 *
 * @author prashant.gurunathan
 *
 */
public class MatchElementCount {

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
  public String matchElementCount(WebDriver webDriver, String objectName, String selector, String inputValue, ExtentTest extentTest, String controlName) {

	CTLogger.writeToLog("MatchElementCount", "matchElementCount()", " method called");
	PropertyFileReader propertyFileReader = new PropertyFileReader();
	String status = "false";
//	String webDriverString;
	String[] objectNames = objectName.split("!--SPLIT--!");

	try {
//	  webDriverString = webDriver.toString();

	  List<WebElement> lstElement1 = webDriver.findElements(By.xpath(objectNames[0]));
	  List<WebElement> lstElement2 = webDriver.findElements(By.xpath(objectNames[1]));
/*
	  WaitForObjectExist waitForObjectExist = new WaitForObjectExist();
	  waitForObjectExist.waitForExistance(webDriver, objectNames[0], selector);
	  waitForObjectExist.waitForExistance(webDriver, objectNames[1], selector);

	  WebElementDetails webElementDetails = new WebElementDetails();

	  if (webDriverString.toLowerCase().contains("androiddriver") || webDriverString.toLowerCase().contains("iosdriver")) {
		txtField = webElementDetails.getElement(webDriver, objectName, selector);
	  } else {
		List<WebElement> listElements = webElementDetails.getElements(webDriver, objectName, selector);
		txtField = listElements.get(0);
	  }
*/	  

	  if (lstElement1.size() == lstElement2.size()) {
		status = "true";
	  }

	  if (status.equals("true")) {
		extentTest.log(LogStatus.PASS, " count of elements tallied -- " + lstElement1.size());
	  } else {
		CTLogger.writeToLog("MatchElementCount", "matchElementCount()", " Objects not found exception thrown  --> path : " + objectNames[0] + " (or) " + objectNames[1] + " --> " + controlName);
		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
		ExtentTestManager.setThreadStatus("f");
		webDriver.close();
		webDriver.quit();
	  }

	} catch (Exception ex) {
	  try {
		ex.printStackTrace();
		String err[] = ex.getMessage().split("\n");
		CTLogger.writeToLog("MatchElementCount", "matchElementCount()", " Objects not found exception thrown  --> path : " + objectNames[0] + " (or) " + objectNames[1] + " --> " + controlName);
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
