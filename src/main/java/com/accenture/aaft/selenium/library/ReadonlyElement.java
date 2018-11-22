package com.accenture.aaft.selenium.library;

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
 * Class is used to perform click action on UI
 *
 * @author Vivek S R
 *
 */
public class ReadonlyElement {

	/**
	 * Method is used to perform click action on UI
	 *
	 * @param webDriver - represents WebDriver
	 * @param objectName - represents object name
	 * @param selector   - represents selector type
	 * @param extentTest - represents ExtentTest
	 * @param controlName - represents control name
	 * @return status
	 */
	public String readonlyElement(WebDriver webDriver, String objectName, String selector, String inputValue, ExtentTest extentTest,
			String controlName) {

		CTLogger.writeToLog("ReadonlyElement", " readonlyElement() ", " method called");
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		String status = "false";

		try {

			WaitForObjectExist waitForObjectExist = new WaitForObjectExist();
			waitForObjectExist.waitForExistance(webDriver, objectName, selector);

			WebElementDetails webElementDetails = new WebElementDetails();
			List<WebElement> listElements = webElementDetails.getElements(webDriver, objectName, selector);

			WebElement element = listElements.get(0);
			WebElement txtField = listElements.get(0);

			if (txtField.isDisplayed()) {

				JavascriptExecutor executor = (JavascriptExecutor) webDriver;
				Object object = executor.executeScript(
						"var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;",
						element);

				String string = object.toString();
				String[] split = string.split("\\s");

				for (String attribute : split) {
					
					boolean contains = attribute.contains(inputValue);
					
					if (contains == true) {
						CTLogger.writeToLog("Readonly", " readonlyElement() ", " Is readonly filed" + objectName + " --> " + controlName);
						status = "true";
					} else {
						CTLogger.writeToLog("Readonly ", " readonlyElement()", " Object not found" + " --> " + controlName);
						status = "false";
					}
				}

				if (status.equals("true")) {
					extentTest.log(LogStatus.PASS, " is readonly filed " + controlName);
				} else {

					CTLogger.writeToLog("Readonly", "readonlyElement()",
							" Object not found exeption thrown  --> path : " + objectName + " --> " + controlName);
					extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager
							.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
					webDriver.quit();
					ExtentTestManager.setThreadStatus("f");
				}

			}
		} catch (Exception e) {
			try {
				e.printStackTrace();
				CTLogger.writeToLog("Readonly", "readonlyElement()",
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
