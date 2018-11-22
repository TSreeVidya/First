package com.accenture.aaft.selenium.library;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.report.ExtentTestManager;
import com.accenture.aaft.selenium.library.utility.WebElementDetails;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Class is used to find and select account on UI
 *
 * @author Azeem.Mohammad.
 *
 */
public class  FindAndClickSFBRE {

  /**
   * Method is used to find and select account on UI
   *
   * @param webDriver - represents WebDriver
   * @param objectName - represents object name
   * @param selector - represents selector type
   * @param extentTest - represents ExtentTest
   * @param controlName - represents control name
   * @return status
   */
  public String  findAndClickSFBRE(WebDriver webDriver, String inputValue, ExtentTest extentTest) {

	String  BRENumber = inputValue;

	CTLogger.writeToLog(" findAndClickSFBRE", "  findAndClickSFBRE() ", " method called to click case - " +  BRENumber);
	PropertyFileReader propertyFileReader = new PropertyFileReader();
	String status = "false";

	try {

			//wait = new WebDriverWait(webDriver,10);	
				
			webDriver.findElement(By.xpath("//li[@id='home_Tab']/a[1]")).click();
			
			Thread.sleep(2000);	
			webDriver.findElement(By.id("phSearchInput")).clear();
			
			webDriver.findElement(By.id("phSearchInput")).sendKeys(BRENumber+Keys.ENTER);
			Thread.sleep(2000);	
			CTLogger.writeToLog("SearchItem", " SearchItem() ", " control clicked -- path : " + BRENumber);
			Thread.sleep(2000);	
			webDriver.findElement(By.xpath("//a[contains(text(),\""+BRENumber+"\")]")).click();
			
			status = "true";
	} catch (Exception e) {
	  try {
		e.printStackTrace();
		CTLogger.writeToLog(" findAndClickSFBRE", " findAndClickSFBRE()", " Object not found exeption thrown  --> path : " +  BRENumber );
		status = "false";
	  } finally {

		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") +  BRENumber)));
		webDriver.quit();
		ExtentTestManager.setThreadStatus("f");
	  }

	}

	return status;
	}
}
  


