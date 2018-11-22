package com.accenture.aaft.selenium.library.utility;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.accenture.aaft.logger.CTLogger;

/**
 * Class is used to pause the execution till page load
 *
 * @author vijay.venkatappa
 *
 */
public class WaitForPageLoad {

  private int waitTime = 15;

  /**
   * Method is used pause the execution till page load
   *
   * @param driver - represents WebDriver
   */
  public void waitForPageLoaded(WebDriver driver) {

	ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
	  public Boolean apply(WebDriver driver) {
		return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
	  }
	};

	Wait<WebDriver> wait = new WebDriverWait(driver, waitTime);
	try {
	  wait.until(expectation);
	} catch (Throwable error) {
	  CTLogger.writeToLog("WaitForPageLoad", "waitForPageLoaded() ", "Timeout waiting for Page Load Request to complete.");
	}
  }
}
