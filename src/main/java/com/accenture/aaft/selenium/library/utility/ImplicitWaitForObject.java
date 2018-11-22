package com.accenture.aaft.selenium.library.utility;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;

/**
 * Class is used to pause the execution till implicit wait
 *
 * @author vijay.venkatappa
 *
 */
public class ImplicitWaitForObject {

  /**
   * Method is used to run implicit wait
   *
   * @param webDriver - represents WebDriver
   */
  public void implicitWait(WebDriver webDriver) {

	webDriver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);

  }
}
