package com.accenture.aaft.selenium.library;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
 * @author Azeem.Mohammad
 *
 */
public class Click2 {
	

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
  public String click2(WebDriver webDriver,ExtentTest extentTest) {

	CTLogger.writeToLog("click2", " click2() ", " method called");
	PropertyFileReader propertyFileReader = new PropertyFileReader();
	String status = "false";

	try {
		
		JavascriptExecutor jse = (JavascriptExecutor)webDriver;
		jse.executeScript("document.body.style.zoom = '90%';"); 
		
		WebElement element1=webDriver.findElement(By.xpath("//span[text()='Generator Set Operation and Maintenance Principles at Solar']/following::input[1]"));
		
	    jse.executeScript("arguments[0].click();", element1);
	    
	    WebElement element2=webDriver.findElement(By.xpath("//label/span[text()='CS/MD Operation and Maintenance Principles at Solar']/following::input[1]"));
		
	    jse.executeScript("arguments[0].click();", element2);
	    
	    WebElement element3=webDriver.findElement(By.xpath("//label/span[text()='Turbotronic Control System at Solar']/following::input[1]"));
		
	    jse.executeScript("arguments[0].click();", element3);
	    
	    status="true";

	} catch (Exception e) {
	  try {
		e.printStackTrace();
		CTLogger.writeToLog("click2", "click2()", " Object not found exeption thrown  --> path : " );
		status = "false";
	  } finally {

		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") )));
		webDriver.quit();
		ExtentTestManager.setThreadStatus("f");
	  }

	}
	return status;
  }

}
