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
 * @author Vivek SR
 *
 */
public class CloseWindows {

	/**
	 * Method is used to perform select window action
	 *
	 * @param webDriver
	 *            - represents WebDriver
	 * @param title
	 *            - represents browser title
	 * @param extentTest
	 *            - represents ExtentTest
	 * @param controlName
	 *            - represents control name
	 * @return status
	 */
	public String closeWindows(WebDriver webDriver, String title, ExtentTest extentTest, String controlName) {

		CTLogger.writeToLog("CloseWindow", "closeWindow() ", " title - " + title);
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		String status = "false";

		try {

			String parentwindow = webDriver.getWindowHandle();
			Set<String> availableWindows = webDriver.getWindowHandles();

			if (!availableWindows.isEmpty()) {

				CTLogger.writeToLog("CloseWindow", "closeWindow() ",
						"No Of windows available" + availableWindows.size());

				for (String winHandle : availableWindows) {
					CTLogger.writeToLog("Window Title  " + webDriver.getTitle());
					String currURL = webDriver.getCurrentUrl();
					CTLogger.writeToLog("Window getCurrentUrl  " + currURL);

					if (!parentwindow.equalsIgnoreCase(winHandle)) {
						webDriver.switchTo().window(winHandle);
						webDriver.close();
						webDriver.switchTo().defaultContent();
						CTLogger.writeToLog("CloseWindow", "closeWindow() ", "'" + title + "' =>closed successfully");
						status = "true";
					}
				}

				status = "true";
				if (status.equals("true")) {
					extentTest.log(LogStatus.PASS, " clicked on " + controlName);
				} else {
					extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager
							.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
					ExtentTestManager.setThreadStatus("f");
				}

			} else {
				CTLogger.writeToLog("CloseWindow", "closeWindow() ", "Available windows size is zero");
				status = "Available windows size is zero";
				extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver,
						propertyFileReader.getValue("IMAGE_PATH") + controlName)));
				ExtentTestManager.setThreadStatus("f");
				return status;
			}

		}

		catch (Exception ex) {
			try {
				ex.printStackTrace();
				CTLogger.writeToLog("CloseWindow", "closeWindow() ", "Exception occurred");

			} finally {
				extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver,
						propertyFileReader.getValue("IMAGE_PATH") + controlName)));
				ExtentTestManager.setThreadStatus("f");
				webDriver.quit();

			}
		}
		return status;
	}

}
