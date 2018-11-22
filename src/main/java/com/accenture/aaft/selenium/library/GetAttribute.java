package com.accenture.aaft.selenium.library;

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
 * Get Attribute keyword to retrieve attribute of the Web Element
 * @author vijay.venkatappa
 */

public class GetAttribute {

	public String getAttribute(WebDriver webDriver, String objectName, String selector, String inputValue, ExtentTest extentTest, String controlName) {

		CTLogger.writeToLog("getAttribute", "getAttribute()", " method called");
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		String status = "false";
		String attributeValue = null;
		WebElement element = null;

		try {
			// Get the Web Element with selector and objectName
			if (objectName != null && selector != null) {
				WaitForObjectExist waitForObjectExist = new WaitForObjectExist();
				waitForObjectExist.waitForExistance(webDriver, objectName, selector);

				WebElementDetails webElementDetails = new WebElementDetails();
				element = webElementDetails.getElement(webDriver, objectName, selector);
			}

			// Assign the attribute value with attribute user-input
			if (inputValue != null && inputValue != "") {
				attributeValue = element.getAttribute(inputValue);
				status = "true";
			}

			// Pass if the attribute value retrieved successfully
			if (status.equals("true")) {
				extentTest.log(LogStatus.PASS, "Attribute value " + attributeValue + " retrived");
			} else {
				extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(
						ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH"))));
				ExtentTestManager.setThreadStatus("f");
			}

		} catch (Exception ex) {
			try {
				ex.printStackTrace();
				String err[] = ex.getMessage().split("\n");
				CTLogger.writeToLog("getAttribute", "getAttribute()", " Object not found exception thrown");
				status = "Exception " + err[0].replaceAll("'", "") + " Occurred";
			} finally {
				extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(
						ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH"))));
				ExtentTestManager.setThreadStatus("f");
				webDriver.quit();
			}
		}
		return attributeValue;
	}

}
