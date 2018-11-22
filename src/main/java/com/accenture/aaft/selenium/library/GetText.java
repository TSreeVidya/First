package com.accenture.aaft.selenium.library;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.accenture.aaft.selenium.library.utility.KeyValueRepo;
import com.accenture.aaft.selenium.library.utility.WaitForObjectExist;
import com.accenture.aaft.selenium.library.utility.WebElementDetails;
import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.report.DateUtil;
import com.accenture.aaft.report.ExtentTestManager;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Get Attribute keyword to retrieve attribute of the Web Element
 * @author vijay.venkatappa
 */

public class GetText {

	public String getText(WebDriver webDriver, String objectName, String selector, ExtentTest extentTest, String controlName, String param1) {

		CTLogger.writeToLog("GetText", "getText()", " method called");
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		String status = "";
		String textValue = null;
		WebElement element = null;

		try {
			if (null != objectName && null != selector) {
				WaitForObjectExist waitForObjectExist = new WaitForObjectExist();
				waitForObjectExist.waitForExistance(webDriver, objectName, selector);

				WebElementDetails webElementDetails = new WebElementDetails();
				element = webElementDetails.getElement(webDriver, objectName, selector);
				textValue = element.getText();
				if(null != propertyFileReader.getValue("STORE_KEY_VALUE") && propertyFileReader.getValue("STORE_KEY_VALUE").equalsIgnoreCase("Yes")){
					HashMap<String, String> map = new HashMap<>();
					map.put(param1,textValue);
					KeyValueRepo.storeKeyValues(map);
				}
				status = "true";
			}
			
			if (status.equals("true")) {
				extentTest.log(LogStatus.PASS, "Text value " + textValue + " retrived");
			} else {
				extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(
						ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH")+DateUtil.now("d MMM yyyy H.mm.ss.SSS"))));
				ExtentTestManager.setThreadStatus("f");
			}

		} catch (Exception ex) {
			try {
				CTLogger.writeToLog(ex.getMessage());
				String err[] = ex.getMessage().split("\n");
				CTLogger.writeToLog("GetText", "getText()", " Object not found exception thrown");
				status = "Exception " + err[0].replaceAll("'", "") + " Occurred";
			} finally {
				extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(
						ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH")+DateUtil.now("d MMM yyyy H.mm.ss.SSS"))));
				ExtentTestManager.setThreadStatus("f");
				webDriver.quit();
			}
		}
		return status;
	}

}
