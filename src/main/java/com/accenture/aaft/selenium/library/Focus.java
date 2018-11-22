package com.accenture.aaft.selenium.library;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.report.ExtentTestManager;
import com.accenture.aaft.selenium.library.utility.WaitForObjectExist;
import com.accenture.aaft.selenium.library.utility.WebElementDetails;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Focus {

	public String focusElement(WebDriver webDriver, String objectName, String selector, ExtentTest extentTest, String controlName) {
		CTLogger.writeToLog("Focus", "focus()", "method called");
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		String status = "false";

		try {
			WaitForObjectExist waitForObjectExist = new WaitForObjectExist();
			waitForObjectExist.waitForExistance(webDriver, objectName, selector);

			WebElementDetails webElementDetails = new WebElementDetails();
			List<WebElement> listElements = webElementDetails.getElements(webDriver, objectName, selector);

			WebElement field = listElements.get(0);
			JavascriptExecutor js = (JavascriptExecutor) webDriver;
			js.executeScript("arguments[0].scrollIntoView(true);", field);

			CTLogger.writeToLog("Focus", " focus() ", " focused on: " + objectName + " --> " + controlName);
			status = "true";

			if (status.equals("true")) {
				extentTest.log(LogStatus.PASS, " focused on " + controlName);
			} else {

				CTLogger.writeToLog("Focus", "focus()",
						" Object not found exeption thrown  --> path : " + objectName + " --> " + controlName);
				extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver,
						propertyFileReader.getValue("IMAGE_PATH") + controlName)));
				webDriver.quit();
				ExtentTestManager.setThreadStatus("f");
			}

		} catch (Exception e) {
			try {
				e.printStackTrace();
				CTLogger.writeToLog("Focus", "focus()",
						" Object not found exeption thrown  --> path : " + objectName + " --> " + controlName);
				status = "false";
			} finally {
				extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver,
						propertyFileReader.getValue("IMAGE_PATH") + controlName)));
				webDriver.quit();
				ExtentTestManager.setThreadStatus("f");
			}

		}
		return status;
	}

}
