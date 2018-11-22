package com.accenture.aaft.selenium.library;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.report.ExtentTestManager;
import com.accenture.aaft.selenium.library.utility.WaitForObjectExist;
import com.accenture.aaft.selenium.library.utility.WebElementDetails;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Class is used to perform mouse over and click action
 *
 * @author vijay.venkatappa
 *
 */
public class MouseOverAndClick {

  /**
   * Method is used to perform mouse over to element
   *
   * @param webDriver - represents WebDriver
   * @param objectName - represents object name
   * @param selector - represents selector type
   * @param extentTest - represents ExtentTest
   * @param controlName - represents control name
   * @return status
   */
  public String moveToElement(WebDriver webDriver, String objectName, String selector, ExtentTest extentTest, String controlName) {

	CTLogger.writeToLog("MouseOver", " moveToElement() ", " method called");
	PropertyFileReader propertyFileReader = new PropertyFileReader();
	String status = "false";

	try {

	  WaitForObjectExist waitForObjectExist = new WaitForObjectExist();
	  waitForObjectExist.waitForExistance(webDriver, objectName, selector);

	  WebElementDetails webElementDetails = new WebElementDetails();
	  List<WebElement> listElements = webElementDetails.getElements(webDriver, objectName, selector);

	  WebElement element = listElements.get(0);

	  Actions action = new Actions(webDriver);
	  action.moveToElement(element).build().perform();
	  status = "true";

	  Thread.sleep(5000);

	  if (status.equals("true")) {
		extentTest.log(LogStatus.PASS, " clicked on " + controlName);
	  } else {

		CTLogger.writeToLog("Click", "moveToElement()", " Object not found exeption thrown  --> path : " + objectName + " --> " + controlName);
		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
		ExtentTestManager.setThreadStatus("f");
		webDriver.quit();
	  }

	} catch (Exception e) {
	  try {
		e.printStackTrace();
		CTLogger.writeToLog("MouseOver", "click()", " Object not found exeption thrown  --> path : " + objectName + " --> " + controlName);
		status = "false";
	  } finally {

		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
		ExtentTestManager.setThreadStatus("f");
		webDriver.quit();

	  }
	}
	return status;
  }

  /**
   * Method is used to perform mouse over and select the element
   *
   * @param webDriver - represents WebDriver
   * @param objectName - represents object name
   * @param selector - represents selector type
   * @param inputValue - represents input value
   * @param extentTest - represents ExtentTest
   * @param controlName - represents control name
   * @return status
   */
  public String mouseOverAndSelect(WebDriver webDriver, String objectName, String selector, String inputValue, ExtentTest extentTest, String controlName) {
	CTLogger.writeToLog("MouseOverAndClick", "mouseOverAndSelect()", " object name" + objectName);
	PropertyFileReader propertyFileReader = new PropertyFileReader();
	String status = "false";
	try {
	  WaitForObjectExist waitForObjectExist = new WaitForObjectExist();
	  waitForObjectExist.waitForExistance(webDriver, objectName, selector);

	  WebElementDetails webElementDetails = new WebElementDetails();
	  List<WebElement> listElements = webElementDetails.getElements(webDriver, objectName, selector);

	  WebElement element = listElements.get(0);
	  
	  String elementpath=element.getText();
	  
	  Thread.sleep(2000);
	  System.out.println("Elementpath----------->:"+elementpath);

	  String path = inputValue;
	   
	  if (listElements.size() > 1) {
		CTLogger.writeToLog("MouseOverAndClick", "mouseOverAndSelect() ", "found more than one element.");
		Actions act = new Actions(webDriver);

		if (element.isDisplayed()) {

		  act.moveToElement(element).click(element).build().perform();
		  if (!inputValue.equals("")) {

			act.moveToElement(webDriver.findElement(By.xpath(path))).click(webDriver.findElement(By.xpath(path))).build().perform();
			
			System.out.println("inputValue------------->:"+inputValue);

		  }
		  status = "true";
		  extentTest.log(LogStatus.PASS, "select " + inputValue + " from " + objectName + " Menu List");
		  CTLogger.writeToLog("MouseOverAndClick", "mouseOverAndSelect() ", "'" + objectName + "' =>Hover is performed sucessfully ");
		  return status;
		} else {
		  act.moveToElement(element).click(element).build().perform();
		  act.moveToElement(webDriver.findElement(By.xpath(path))).click(webDriver.findElement(By.xpath(path))).build().perform();

		  CTLogger.writeToLog("MouseOverAndClick", "mouseOverAndSelect() ", "'" + objectName + "' object visible");
		  status = "true";
		  extentTest.log(LogStatus.PASS, "select " + inputValue + " from " + objectName + " Menu List");
		  return status;
		}
	  } else if (listElements.size() == 1) {

		Actions act = new Actions(webDriver);
		CTLogger.writeToLog("MouseOverAndClick", "mouseOverAndSelect() ", "found one element.");
		if (element.isDisplayed()) {

		  // act.moveToElement(element).click(element).build().perform();
		  // act.moveToElement(element).click(element).build().perform();
		  Thread.sleep(3000);
		  if (!inputValue.equals("")) {
			CTLogger.writeToLog("found one element with input:" + inputValue);

			CTLogger.writeToLog("found one element with xpath");
			System.out.println("Path------------>:"+path);

			//[varija] -Added below code, as Actions - mouseover & click operation is not performed on IE. Triggering using JavaScript
			
			//The below JavaScript code creates, initializes and dispatches mouse event to an object on fly.
			String strJavaScript = "var element = arguments[0];"
			            + "var mouseEventObj = document.createEvent('MouseEvents');"
			            + "mouseEventObj.initEvent( 'mouseover', true, true );"
			            + "element.dispatchEvent(mouseEventObj);";
				
			//Then JavascriptExecutor class is used to execute the script to trigger the dispatched event.
			((JavascriptExecutor) webDriver).executeScript(strJavaScript, element);
			
			
			String jScript = "var element = arguments[0]; element.click();"; 
			JavascriptExecutor executor = (JavascriptExecutor) webDriver;
			executor.executeScript(jScript, webDriver.findElement(By.xpath(path)));
			
			
			//[varija] -commented this code, as mouseover & click operation is not performed on IE
			//act.moveToElement(element).click(webDriver.findElement(By.xpath(path))).build().perform();

			// act.moveToElement(webDriver.findElement(By.xpath(path))).click(webDriver.findElement(By.xpath(path))).build().perform();
			status = "true";
			extentTest.log(LogStatus.PASS, "select " + inputValue + " from " + objectName + " Menu List");
			CTLogger.writeToLog("MouseOverAndClick", "mouseOverAndSelect() ", "'" + objectName + "' =>Hover is performed sucessfully ");
			return status;
		  } else {

			act.moveToElement(element).click(element).build().perform();
			act.moveToElement(webDriver.findElement(By.xpath(path))).click(webDriver.findElement(By.xpath(path))).build().perform();
			status = "true";
			extentTest.log(LogStatus.PASS, "select " + inputValue + " from " + objectName + " Menu List");
			CTLogger.writeToLog("MouseOverAndClick", "mouseOverAndSelect() ", "'" + objectName + "' =>Hover is performed sucessfully ");
			return status;
		  }

		} else {
		  CTLogger.writeToLog("MouseOverAndClick", "mouseOverAndSelect() ", "'" + objectName + "' object was not found");
		  status = "false";
		  extentTest.log(LogStatus.FAIL, "select " + inputValue + " from " + objectName + " Menu List");
		  CTLogger.writeToLog("MouseOverAndClick", "mouseOverAndSelect() ", "objeswitchto" + "ct not found");
		  return status;
		}

	  }
	} catch (Exception ex) {
	  try {
		ex.printStackTrace();
		CTLogger.writeToLog("MouseOverAndClick", "mouseOverAndSelect() ", "Exception occurred");
		CTLogger.writeToLog("MouseOverAndClick", "mouseOverAndSelect() ", "Exception '" + ex.getMessage() + "' Occurred");
		status = "false";
		extentTest.log(LogStatus.FAIL, "select " + inputValue + " from " + objectName + "Menu List");
	  } finally {
		extentTest.log(LogStatus.FAIL, "Unable to select " + inputValue + " from " + objectName + "Menu List");
		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH"))));
		webDriver.quit();

	  }

	}
	return status;
  }

}
