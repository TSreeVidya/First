package com.accenture.aaft.selenium.library;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.accenture.aaft.selenium.library.utility.WaitForObjectExist;
import com.accenture.aaft.selenium.library.utility.WebElementDetails;
import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.report.ExtentTestManager;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Class is used to read the text value for the web element
 *
 * @author Vivek SR
 *
 */
public class RetrunText {

  /**
   * Method is used to return text of an element 
   *
   * @param webDriver - represents WebDriver
   * @param objectName - represents object name
   * @param selector - represents selector type
   * @param extentTest - represents ExtentTest
   * @param controlName - represents control name
   * @return status
   */
  public String returnText(WebDriver webDriver, String objectName, String selector, ExtentTest extentTest, String controlName) {

	CTLogger.writeToLog("RetrunText", "returnText()", " method called");
	PropertyFileReader propertyFileReader = new PropertyFileReader();
	String status = "false";
	String value = "";
	try {
	  WaitForObjectExist waitForObjectExist = new WaitForObjectExist();
	  waitForObjectExist.waitForExistance(webDriver, objectName, selector);
	  

	  WebElementDetails webElementDetails = new WebElementDetails();
	  List<WebElement> listElements = webElementDetails.getElements(webDriver, objectName, selector);

	  WebElement element = null;
	  

	  if (listElements != null && listElements.size() > 0) {
		element = listElements.get(0);
		if ("input".equalsIgnoreCase(element.getTagName())) {
		  value = element.getAttribute("value");
		} else {
			status="true";
		  value = element.getText();
		  
		}
	  }



	  if (status.equals("true")) {
		extentTest.log(LogStatus.PASS, " Found " + value + "in " + controlName);
	  } else {
		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
		ExtentTestManager.setThreadStatus("f");
	  }

	} catch (Exception ex) {
	  try {
		ex.printStackTrace();
		String err[] = ex.getMessage().split("\n");
		CTLogger.writeToLog("RetrunText", "returnText()", " Object not found exeption thrown");
		status = "Exception " + err[0].replaceAll("'", "") + " Occurred";
	  } finally {

		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
		ExtentTestManager.setThreadStatus("f");
		webDriver.quit();

	  }
	}
	return value;
	
  }
}
