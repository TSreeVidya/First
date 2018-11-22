package com.accenture.aaft.selenium.library;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.report.ExtentTestManager;
import com.accenture.aaft.selenium.library.utility.WaitForObjectExist;
import com.accenture.aaft.selenium.library.utility.WebElementDetails;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class SelectByIndex {
	public String selectByIndex(WebDriver webDriver, String objectName, String selector, String inputValue, ExtentTest extentTest, String controlName) {
		CTLogger.writeToLog("SelectByIndex", " selectByIndex() ", " method called");
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		try {
			WaitForObjectExist waitForObjectExist = new WaitForObjectExist();
			waitForObjectExist.waitForExistance(webDriver, objectName, selector);

			WebElementDetails webElementDetails = new WebElementDetails();
			List<WebElement> listElements = webElementDetails.getElements(webDriver, objectName, selector);

			WebElement selectElement = listElements.get(0);
			if(selectElement.isDisplayed()) {
				Select select=new Select(selectElement);
				select.selectByIndex(Integer.parseInt(inputValue));
			}
			else
			{
				extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
				CTLogger.writeToLog("SelectByIndex", " selectByIndex() ", objectName + " object not found");
				return "false";
			}
		}
		catch(Exception e) {
			extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
			e.printStackTrace();
			return "Fail";

		}
		extentTest.log(LogStatus.PASS, " clicked on " + controlName);
		return "true";
	}
}
