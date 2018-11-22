package com.accenture.aaft.selenium.library;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.report.ExtentTestManager;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Finding the ShowLink button.
 *
 * @author Azeem.Mohammad
 *
 */
public class ShowLinkPresent {
	

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
  public String showLinkPresent(WebDriver webDriver,ExtentTest extentTest) {

	CTLogger.writeToLog("showLinkPresent", " showLinkPresent() ", " method called");
	PropertyFileReader propertyFileReader = new PropertyFileReader();
	String status = "true";
	//boolean present;

	try {
		
		if(webDriver.findElements(By.xpath("//h3[text()='Opportunities']/following::div[@class='pShowMore']/a[1]")).size() != 0){
			webDriver.findElement(By.xpath("//h3[text()='Opportunities']/following::div[@class='pShowMore']/a[1]")).click();
			System.out.println("Showlink Element is Present");
			}
			else if(webDriver.findElements(By.xpath("//h3[text()='Risk Screening']/following::div[@class='pShowMore']/a[1]")).size() != 0)
			{
				webDriver.findElement(By.xpath("//h3[text()='Risk Screening']/following::div[@class='pShowMore']/a[1]")).click();
				System.out.println("Showlink Element is Present");
			}
			else{
			System.out.println("Showlink Element is Absent");
			}	    
	    //status="true";

	} catch (Exception e) {
	  try {
		e.printStackTrace();
	  } finally {

		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") )));
		webDriver.quit();
		ExtentTestManager.setThreadStatus("f");
	  }
	}
	return status;
  }

}
