package com.accenture.aaft.selenium.library;

import java.util.List;
import java.util.Map;
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
public class Store {

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
  public String store(WebDriver webDriver, String objectName, String selector, String inputValue, ExtentTest extentTest, String controlName,
		  Map<String,String> hmTempStore) {

	CTLogger.writeToLog("Store", "store()", " method called");
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

	  if (webDriverString.toLowerCase().contains("androiddriver") || webDriverString.toLowerCase().contains("iosdriver")) {
		element = webElementDetails.getElement(webDriver, objectName, selector);
	  } else {
		List<WebElement> listElements = webElementDetails.getElements(webDriver, objectName, selector);
		element = listElements.get(0);
	  }

	  if (element.isDisplayed()) {

		if (webDriverString != null) {

		  initText = element.getAttribute("value");
		  if (initText != null && initText.length() > 0) {
			hmTempStore.put(inputValue, initText);
		  }
		  else {
			initText = element.getText();
    		hmTempStore.put(inputValue, initText);
		  }
		}

  	    if (initText != null && initText.length() > 0) {
  		  CTLogger.writeToLog(" successfully stored the value " + initText + " -- path : " + objectName + " --> " + controlName);
		  status = "true";
  	    }
	  }

	  if (status.equals("true")) {
		extentTest.log(LogStatus.PASS, " stored " + initText + " into " + inputValue);
	  } else {
		CTLogger.writeToLog("Store", "store()", " Object not found exception thrown  --> path : " + objectName + " --> " + controlName);
		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
		ExtentTestManager.setThreadStatus("f");
		webDriver.close();
		webDriver.quit();
	  }

	} catch (Exception ex) {
	  try {
		ex.printStackTrace();
		String err[] = ex.getMessage().split("\n");
		CTLogger.writeToLog("Store", "store()", " Object not found exception thrown  --> path : " + objectName + " --> " + controlName);
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
