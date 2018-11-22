package com.accenture.aaft.selenium.library.utility;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.PropertyFileReader;

/**
 * Class is used to fetch web element details
 *
 * @author vijay.venkatappa
 *
 */
public class WebElementDetails {

  public List<WebElement> getElements(WebDriver webDriver, String objectName, String selector) {
	return getElements(webDriver, objectName, selector, -1);
  }

  /**
   * Method is used to fetch web elements list
   *
   * @param webDriver - represents WebDriver
   * @param objectName - represents object name
   * @param selector - represents selector type
   * @param wait - represents wait time
   * @return List<WebElement>
   */
  public List<WebElement> getElements(WebDriver webDriver, String objectName, String selector, int wait) {

	CTLogger.writeToLog("WebElementDetails", "getElements() ", " method called");
	List<WebElement> elementList = null;
	PropertyFileReader propertyFileReader = new PropertyFileReader();

	String webDriverString = webDriver.toString();
	int waitTime = Integer.parseInt(propertyFileReader.getValue("MAX_TIME_TO_FIND_OBJECT"));

	if (webDriverString != null && webDriverString.toLowerCase().indexOf("androiddriver") < 0 && webDriverString.toLowerCase().indexOf("iosdriver") < 0
	    && (webDriverString.toLowerCase().contains("chrome") || webDriverString.toLowerCase().contains("ie") || webDriverString.toLowerCase().contains("internet")
	        || webDriverString.toLowerCase().contains("edge") || webDriverString.toLowerCase().contains("opera") || webDriverString.toLowerCase().contains("safari")
	        || webDriverString.toLowerCase().contains("fire"))) {

	  if (wait >= 0)
	    waitTime = wait;
      //webDriver.manage().timeouts().pageLoadTimeout(waitTime, TimeUnit.SECONDS);
      webDriver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
	}

	if (selector.equalsIgnoreCase("xpath")) {
	  elementList = webDriver.findElements(By.xpath(objectName));
	} else if (selector.equalsIgnoreCase("id")) {
	  elementList = webDriver.findElements(By.id(objectName));
	} else if (selector.equalsIgnoreCase("name")) {
	  elementList = webDriver.findElements(By.name(objectName));
	}else if (selector.equalsIgnoreCase("link")) {
		  elementList = webDriver.findElements(By.linkText(objectName));
		}
	if (null == elementList || elementList.size() == 0) {
	  CTLogger.writeToLog("WebElementDetails", "getElements ", " Object does not exist");
	}

	return elementList;

  }

  public WebElement getElement(WebDriver webDriver, String objectName, String selector) {
	return getElement(webDriver, objectName, selector, -1);
  }

  /**
   * Method is used to fetch web element
   *
   * @param webDriver - represents WebDriver
   * @param objectName - represents object name
   * @param selector - represents selector type
   * @param wait - represents wait time
   * @return WebElement
   */
  public WebElement getElement(WebDriver webDriver, String objectName, String selector, int wait) {

	CTLogger.writeToLog("WebElementDetails", "getElements() ", " method called");
	WebElement element = null;
	PropertyFileReader propertyFileReader = new PropertyFileReader();

	String webDriverString = webDriver.toString();
	int waitTime = Integer.parseInt(propertyFileReader.getValue("MAX_TIME_TO_FIND_OBJECT"));

	if (webDriverString != null && webDriverString.toLowerCase().indexOf("androiddriver") < 0 && webDriverString.toLowerCase().indexOf("iosdriver") < 0
	    && (webDriverString.toLowerCase().contains("chrome") || webDriverString.toLowerCase().contains("ie") || webDriverString.toLowerCase().contains("internet")
	        || webDriverString.toLowerCase().contains("edge") || webDriverString.toLowerCase().contains("opera") || webDriverString.toLowerCase().contains("safari")
	        || webDriverString.toLowerCase().contains("fire"))) {

	  if (wait >= 0)
	    waitTime = wait;
	  //webDriver.manage().timeouts().pageLoadTimeout(waitTime, TimeUnit.SECONDS);
      webDriver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
	}

	if (selector.equalsIgnoreCase("xpath")) {
	  element = webDriver.findElement(By.xpath(objectName));
	} else if (selector.equalsIgnoreCase("id")) {
	  element = webDriver.findElement(By.id(objectName));
	} else if (selector.equalsIgnoreCase("name")) {
	  element = webDriver.findElement(By.name(objectName));
	}
	if (null == element) {
	  CTLogger.writeToLog("WebElementDetails", "getElements ", " Object does not exist");
	}

	return element;

  }

}
