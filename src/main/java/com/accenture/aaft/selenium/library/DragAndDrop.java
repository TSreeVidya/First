package com.accenture.aaft.selenium.library;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import com.accenture.aaft.selenium.library.utility.WaitForObjectExist;
import com.accenture.aaft.selenium.library.utility.WebElementDetails;
import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.report.ExtentTestManager;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Class is used to perform click action on UI
 *
 * @author vijay.venkatappa
 *
 */
public class DragAndDrop {


	  /**
	   * Method is used to perform click action on UI
	   *
	   * @param webDriver - represents WebDriver
	   * @param objectName - represents object name
	   * @param selector - represents selector type
	   * @param extentTest - represents ExtentTest
	   * @param controlName - represents control name
	   * @return status
	   */
	  public String dragAndDrop(WebDriver webDriver, String objectName, String selector, ExtentTest extentTest, String controlNameForFromLocation, String droplocation,String selectorType) {

		CTLogger.writeToLog("DragAndDrop", "dragAndDrop() ", " method called");
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		String status = "false";

		try {
          // check from location exists or not
		  WaitForObjectExist waitForObjectExist = new WaitForObjectExist();
		  waitForObjectExist.waitForExistance(webDriver, objectName, selector);
		  // check drop location exists or not
		  WaitForObjectExist waitForObjectExist2 = new WaitForObjectExist();
		  waitForObjectExist2.waitForExistance(webDriver, droplocation, selectorType);
		  
		  WebElementDetails webElementDetails = new WebElementDetails();
		  List<WebElement> listElements = webElementDetails.getElements(webDriver, objectName, selector);

		  WebElementDetails webElementDetails2 = new WebElementDetails();
		  List<WebElement> listElements2 = webElementDetails2.getElements(webDriver, objectName, selector);
		  
		  WebElement txtField = listElements.get(0);
		  WebElement txtField2 = listElements2.get(0);
		  if (txtField.isDisplayed()) {
			  if (txtField2.isDisplayed()) {
				  Actions act = new Actions(webDriver);
				  act.dragAndDrop(txtField, txtField2).build().perform();
			  }
			  else{
				  CTLogger.writeToLog("DragAndDrop", "dragAndDrop() ", " not able find to location");
			  }
		  }else {
			  CTLogger.writeToLog("DragAndDrop", "dragAndDrop() ", " not able find from location");
				status = "false"; 
		  }
		  
		 
		 

		  if (status.equals("true")) {
			extentTest.log(LogStatus.PASS, " dropped content ");
		  } else {

			CTLogger.writeToLog("DragAndDrop", "dragAndDrop()", " Object not found exeption ");
			extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH")+droplocation)));
			webDriver.quit();
			ExtentTestManager.setThreadStatus("f");
		  }

		} catch (Exception e) {
		  try {
			e.printStackTrace();
			CTLogger.writeToLog("Click", "click()", " Object not found exeption thrown ");
			status = "false";
		  } finally {

			extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") +droplocation)));
			webDriver.quit();
			ExtentTestManager.setThreadStatus("f");
		  }

		}

		return status;
	  }


}
