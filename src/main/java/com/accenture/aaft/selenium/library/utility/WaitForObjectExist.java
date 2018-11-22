package com.accenture.aaft.selenium.library.utility;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.accenture.aaft.propertyreader.PropertyFileReader;

/**
 * Class is used to pause the execution till object exist
 *
 * @author vijay.venkatappa
 *
 */
public class WaitForObjectExist {

  public WebElement waitForExistance(WebDriver webDriver, String objectName, String selector) {
	return waitForExistance(webDriver, objectName, selector, -1);
  }

  /**
   * Method is used to pause the execution till object exist
   *
   * @param webDriver - represents WebDriver
   * @param objectName - represents object name
   * @param selector - represents selector
   * @param wait - represents wait time
   * @return WebElement
   */
  public WebElement waitForExistance(WebDriver webDriver, String objectName, String selector, int wait) {

	WebElement elementList = null;
	PropertyFileReader propertyFileReader = new PropertyFileReader();

	String webDriverString = webDriver.toString();

	int waitTime = Integer.parseInt(propertyFileReader.getValue("MAX_TIME_TO_FIND_OBJECT"));
	if (wait >= 0)
		waitTime = wait;

	if (webDriverString != null && webDriverString.toLowerCase().indexOf("androiddriver") < 0 && webDriverString.toLowerCase().indexOf("iosdriver") < 0
	    && (webDriverString.toLowerCase().contains("chrome") || webDriverString.toLowerCase().contains("ie") || webDriverString.toLowerCase().contains("internet")
	        || webDriverString.toLowerCase().contains("edge") || webDriverString.toLowerCase().contains("opera") || webDriverString.toLowerCase().contains("safari")
	        || webDriverString.toLowerCase().contains("fire") || webDriverString.toLowerCase().contains("android"))) {
	  //webDriver.manage().timeouts().pageLoadTimeout(waitTime, TimeUnit.SECONDS);
	  webDriver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
	}
	if (selector.equalsIgnoreCase("xpath")) {
	  elementList = (new WebDriverWait(webDriver, waitTime)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(objectName)));
	} else if (selector.equalsIgnoreCase("id")) {
	  elementList = (new WebDriverWait(webDriver, waitTime)).until(ExpectedConditions.presenceOfElementLocated(By.id(objectName)));
	} else if (selector.equalsIgnoreCase("name")) {
	  elementList = (new WebDriverWait(webDriver, waitTime)).until(ExpectedConditions.presenceOfElementLocated(By.name(objectName)));
	}else if (selector.equalsIgnoreCase("link")) {
		  elementList = (new WebDriverWait(webDriver, waitTime)).until(ExpectedConditions.presenceOfElementLocated(By.linkText(objectName)));
		}

	return elementList;

  }

}
