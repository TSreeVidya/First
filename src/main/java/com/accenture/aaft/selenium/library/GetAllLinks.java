package com.accenture.aaft.selenium.library;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
public class GetAllLinks {

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
  public String getAllLinks(WebDriver webDriver, ExtentTest extentTest) {

	CTLogger.writeToLog("GetAllLinks", " getAllLinks() ", " method called");
	PropertyFileReader propertyFileReader = new PropertyFileReader();
	String status = "false";

	try {

		List<WebElement> linksList = webDriver.findElements(By.tagName("a"));
		CTLogger.writeToLog("No of links in a page : "+linksList.size());
		
		if(linksList.size()>0){
			status = "true";
			extentTest.log(LogStatus.PASS, " getting links in the page ");
			for(WebElement webElement:linksList){
				System.out.println(webElement.getText());
			}
		}else{
			status = "false";
			CTLogger.writeToLog("GetAllLinks", "getAllLinks()", " links not found in the page");
			extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + "getAllLinks")));
			
		}
		
	} catch (Exception e) {
	  try {
		e.printStackTrace();
		CTLogger.writeToLog("GetAllLinks", "getAllLinks()", " exception caught");
		status = "false";
	  } finally {

		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + "getAllLinks")));
		webDriver.quit();
		ExtentTestManager.setThreadStatus("f");
	  }

	}

	return status;
  }

}
