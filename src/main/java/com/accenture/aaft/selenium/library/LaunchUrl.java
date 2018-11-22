package com.accenture.aaft.selenium.library;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.report.DateUtil;
import com.accenture.aaft.report.ExtentTestManager;
import com.accenture.aaft.selenium.library.utility.WaitForPageLoad;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Class is used to launch the URL on browser
 *
 * @author vijay.venkatappa
 *
 */
public class LaunchUrl {

  /**
   * Method is used to launch the URL
   *
   * @param webDriver - represents WebDriver
   * @param inputValue - represents input value
   * @param extentTest - represents ExtentTest
   * @return status
   */
  public String launchUrl(WebDriver webDriver, String inputValue, ExtentTest extentTest) {

	String status = "false";
	PropertyFileReader propertyFileReader = new PropertyFileReader();

	try {

	  String webdriverString = webDriver.toString();
	  int waitTime = Integer.parseInt(propertyFileReader.getValue("MAX_TIME_TO_FIND_OBJECT"));
	  String SalesforceURL=propertyFileReader.getValue("SALESFORCEDEVORGURL");
	  if(webdriverString.toLowerCase().contains("remotewebdriver")){
		  webDriver.get(SalesforceURL);
	  }

	  else if (webdriverString.toLowerCase().contains("android") || webdriverString.toLowerCase().contains("ios") || webdriverString.toLowerCase().contains("iphone")) {
		webDriver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
		webDriver.get(SalesforceURL);
		WaitForPageLoad waitForPageLoad = new WaitForPageLoad();
		waitForPageLoad.waitForPageLoaded(webDriver);
	  } else {
		webDriver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
		Long w = (Long) ((JavascriptExecutor) webDriver).executeScript("return window.screen.availWidth;");
		Long h = (Long) ((JavascriptExecutor) webDriver).executeScript("return window.screen.availHeight;");
		webDriver.manage().window().setSize(new Dimension(w.intValue(), h.intValue()));
		webDriver.manage().window().maximize();
		webDriver.get(SalesforceURL);
		WaitForPageLoad waitForPageLoad = new WaitForPageLoad();
		waitForPageLoad.waitForPageLoaded(webDriver);
	  }

	  extentTest.log(LogStatus.PASS, "URL: " + SalesforceURL + " launched");
	  
	  //webDriver.manage().deleteAllCookies();

	  status = "true";

	} catch (Exception ex) {
	  try {

		  CTLogger.writeToLog(ex.getMessage());
		if (ex.getMessage().toString().contains("Modal dialog present")) {

		  CTLogger.writeToLog("LaunchUrl", "launchUrl() ", " not able to launch.");
		  extentTest.log(LogStatus.INFO, "URL not able to launch the URL: " + inputValue);
		  status = "URL opened but popup exists";

		}
	  } finally {

		  CTLogger.writeToLog(ex.getMessage());
		String err[] = ex.getMessage().split("\n");
		CTLogger.writeToLog("LaunchUrl", "launchUrl()", "URL not able to launch the URL: " + inputValue);
		status = "Exception " + err[0].replaceAll("'", "") + " Occurred";
		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + "open url"+DateUtil.now("d MMM yyyy H.mm.ss.SSS"))));
		ExtentTestManager.setThreadStatus("f");
		webDriver.quit();

	  }

	}
	return status;

  }

}
