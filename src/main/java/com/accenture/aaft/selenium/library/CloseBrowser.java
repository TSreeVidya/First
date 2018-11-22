package com.accenture.aaft.selenium.library;

import org.openqa.selenium.WebDriver;

import com.accenture.aaft.logger.CTLogger;

/**
 * Class is used to perform close browser action
 *
 * @author vijay.venkatappa
 *
 */
public class CloseBrowser {

  /**
   * Method is used to close the web browser
   *
   * @param webDriver - represents WebDriver
   * @return status
   */
  public String closeBrowser(WebDriver webDriver) {

	try {
	  if (webDriver != null) {
		  webDriver.quit();
		
	  }

	} catch (Exception e) {
	  e.printStackTrace();
	  CTLogger.writeToLog("CloseBrowser", "CloseBrowser() ", "Exception thrown while closing browser");
	  String err[] = e.getMessage().split("\n");
	  return "Exception " + err[0].replaceAll("'", "") + " Occurred";

	}
	return "true";
  }
}
