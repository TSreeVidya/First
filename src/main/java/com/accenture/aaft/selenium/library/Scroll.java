package com.accenture.aaft.selenium.library;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.report.ExtentTestManager;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Scroll {

	public String scrollBy(WebDriver webDriver, ExtentTest extentTest, String controlName, String param1, String param2) {
		String xpixels = "";
		String ypixels = "";
		CTLogger.writeToLog("Focus", "focus()", "method called");
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		String status = "false";

		try {
			xpixels = param1;
			ypixels = param2;
			
			JavascriptExecutor js = (JavascriptExecutor) webDriver;
			js.executeScript("window.scrollBy("+xpixels+","+ypixels+")", "");

			CTLogger.writeToLog("Scroll", " scroll() ", " scolled by: " + xpixels + ":" + ypixels );
			status = "true";

			if (status.equals("true")) {
				extentTest.log(LogStatus.PASS, " scrolled by" + xpixels + ":" + ypixels);
			} else {

				CTLogger.writeToLog("Scroll", "scroll()",
						" scrolling failed by "  + xpixels + ":" + ypixels );
				extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver,
						propertyFileReader.getValue("IMAGE_PATH") + controlName)));
				webDriver.quit();
				ExtentTestManager.setThreadStatus("f");
			}

		} catch (Exception e) {
			try {
				e.printStackTrace();
				CTLogger.writeToLog("Scroll", "scroll()",
						" scrolling failed by "  + xpixels + ":" + ypixels );
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
