package com.accenture.aaft.selenium.library;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import org.openqa.selenium.JavascriptExecutor;
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
 * @author vijay.venkatappa
 *
 */
@SuppressWarnings({ "unused", "resource" })
public class DragAndDropForScrollingObject {

	/**
	 * Method is used perform drag and drop action
	 * 
	 * @param webDriver
	 * @param dragElementObjectPath
	 * @param inputValue
	 * @return
	 */
	public String dragAndDropForScrollingObject(WebDriver webDriver, String dragElementObjectPath, String selector,
			String controlName, String inputValue, ExtentTest extentTest) {

		CTLogger.writeToLog("DragAndDropForScrollingObject", "dagAndDropForScrollingObject", "method called");
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		String[] input = inputValue.split(",");
		String dropElementObjectPath = input[0];
		String selectorType = input[1];
		String waitTime = input[2];
		String status = "false";
		try {
			if (null != dragElementObjectPath && null != dropElementObjectPath) {
				if (dragElementObjectPath.equals("") || dropElementObjectPath.equals("")) {
					status = "false";
				} else {

					WaitForObjectExist waitForObjectVisible = new WaitForObjectExist();
					waitForObjectVisible.waitForExistance(webDriver, dragElementObjectPath, selector);

					waitForObjectVisible = new WaitForObjectExist();
					waitForObjectVisible.waitForExistance(webDriver, dragElementObjectPath, selector);

					WaitTime time = new WaitTime();
					time.waitTime("5");

					String line = null;

					BufferedReader br = new BufferedReader(new FileReader("js\\DragAndDrop.js"));
					StringBuffer buffer = new StringBuffer();

					while ((line = br.readLine()) != null)
						buffer.append(line);

					String javaScript = buffer.toString();

					time.waitTime("5");

					String java_Script = javaScript + "$('#" + dragElementObjectPath
							+ "').simulateDragDrop({ dropTarget: '#" + dropElementObjectPath + "'});";

					((JavascriptExecutor) webDriver).executeScript(java_Script);

					status = "true";
				}
			} else {
				CTLogger.writeToLog("Elements are null");
				status = "false";
			}
			if (status.equals("true")) {
				extentTest.log(LogStatus.PASS, " clicked on " + controlName);
			} else {

				CTLogger.writeToLog("Click", "click()",
						" Object not found exeption thrown");
				extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver,
						propertyFileReader.getValue("IMAGE_PATH") + controlName)));
				webDriver.quit();
				ExtentTestManager.setThreadStatus("f");
			}
		} catch (Exception e) {
			try {
				e.printStackTrace();
				CTLogger.writeToLog("DragAndDropForScrollingObject"+" Object not found");
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
