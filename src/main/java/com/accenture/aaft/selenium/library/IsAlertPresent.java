package com.accenture.aaft.selenium.library;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.report.ExtentTestManager;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Class is used to perform select frame action in browser
 *
 * @author Vivek SR
 *
 */
public class IsAlertPresent {

	/**
	 * Method is used to check alert and enter OTP
	 * 
	 * @param webDriver
	 *            - represents WebDriver
	 * @param objectName
	 *            - represents object name
	 * @param selector
	 *            - represents selector type
	 * @param extentTest
	 *            - represents ExtentTest
	 * @param controlName
	 *            - represents control name
	 * @return status
	 */

	PropertyFileReader propertyFileReader = new PropertyFileReader();
	Alert alert;

	private boolean switchToAlert(WebDriver webDriver) {
		try {
			CTLogger.writeToLog("IsAlertPresent", "isAlertPresent() ", "method called");
			
			WebDriverWait wait = new WebDriverWait(webDriver, 2);
			wait.until(ExpectedConditions.alertIsPresent());
			
			alert = webDriver.switchTo().alert();
			return true;
			
		} catch (NoAlertPresentException e) {
			return false;
		} catch (UnhandledAlertException e) {
			alert = webDriver.switchTo().alert();
			return true;
		}
	}

	public String isAlertPresent(WebDriver webDriver, ExtentTest extentTest) {

		CTLogger.writeToLog("isAlertPresent", "isAlertPresent() ", " method called");
		String status = "false";

		try {

			if (switchToAlert(webDriver)) {
				status = "true";
				alert.accept();
				extentTest.log(LogStatus.PASS, " Alert Exist ");
			} else {
				status = "false";
				extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(
						ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH"))));
				ExtentTestManager.setThreadStatus("f");
			}

			if (status.equals("true")) {
				extentTest.log(LogStatus.PASS, " Accepted the alert ");
			} else {
				extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(
						ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH"))));
				ExtentTestManager.setThreadStatus("f");
			}

		} catch (Exception ex) {
			try {

				ex.printStackTrace();
				CTLogger.writeToLog("isAlertPresent", "isAlertPresent() ", "alert is not present");

			} finally {

				extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(
						ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH"))));
				ExtentTestManager.setThreadStatus("f");
				webDriver.quit();

			}
		}

		return status;
	}

}
