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
 * Class is used to compare UI control value against temporary storage
 *
 * @author prashant.gurunathan
 *
 */
public class CompareToStore {

  /**
   * Method is used to compare UI control value against temporary storage
   *
   * @param webDriver - represents WebDriver
   * @param objectName - represents object name
   * @param selector - represents selector type
   * @param inputValue - represents input value
   * @param extentTest - represents ExtentTest
   * @param controlName - represents control name
   * @return status
   */
  public String compareToStore(WebDriver webDriver, String objectName, String selector, String inputValue, ExtentTest extentTest, String controlName,
		  Map<String,String> hmTempStore) {

	CTLogger.writeToLog("CompareToStore", "compareToStore()", " method called");
	PropertyFileReader propertyFileReader = new PropertyFileReader();
	String status = "false";
	String webDriverString;

	WebElement element = null;

	try {

	  webDriverString = webDriver.toString();

	  WaitForObjectExist waitForObjectExist = new WaitForObjectExist();
	  waitForObjectExist.waitForExistance(webDriver, objectName, selector);

	  WebElementDetails webElementDetails = new WebElementDetails();
	  String initText = null;
	  String storeValue = null;

	  if (webDriverString.toLowerCase().contains("androiddriver") || webDriverString.toLowerCase().contains("iosdriver")) {
		element = webElementDetails.getElement(webDriver, objectName, selector);
	  } else {
		List<WebElement> listElements = webElementDetails.getElements(webDriver, objectName, selector);
		element = listElements.get(0);
	  }

	  if (element != null && element.isDisplayed()) {

		if (webDriverString != null) {

		  initText = element.getAttribute("value");
		  if (initText == null || initText.length() == 0) {
			initText = element.getText();
		  }

		  if (hmTempStore.containsKey(inputValue)) {
		    storeValue = hmTempStore.get(inputValue);

  	      if (initText != null && initText.length() > 0 && storeValue != null && storeValue.length() > 0 && initText.equals(storeValue)) {
		    status = "true";
  	      }
  	      else {
		    CTLogger.writeToLog(" compared values do not match - " + storeValue + " & " + initText);
  	      }
	    }
	  }
	 }

	  if (status.equals("true")) {
		extentTest.log(LogStatus.PASS, " compared values match - " + storeValue + " & " + initText);
	  } else {
		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
	  }

	} catch (Exception ex) {
	  try {
		ex.printStackTrace();
		String err[] = ex.getMessage().split("\n");
		CTLogger.writeToLog("CompareToStore", "compareToStore()", " Object not found exception thrown  --> path : " + objectName + " --> " + controlName);
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
