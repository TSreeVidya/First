package com.accenture.aaft.selenium.library;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.report.ExtentTestManager;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Class is used to perform click action on UI
 *
 * @author vijay.venkatappa
 *
 */
public class KeysDown {

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
  public String scrollDown(WebDriver webDriver, String inputValue ,ExtentTest extentTest) {

	CTLogger.writeToLog("KeysDown", " scrollDown() ", " method called");
	String status = "false";

	try {


	  Actions action = new Actions(webDriver);
	  if(null != inputValue && !inputValue.equals("")){
		  for(int i=0;i<=Integer.parseInt(inputValue);i++){
			  action.sendKeys(Keys.ARROW_DOWN);  
		  } 
	  }
	  else{
		  action.sendKeys(Keys.ARROW_DOWN);  
	  }
      action.sendKeys(Keys.ENTER).perform();
      status = "true";

	  if (status.equals("true")) {
		extentTest.log(LogStatus.PASS, " scrolled down ");
	  } 

	} catch (Exception e) {
	  try {
		CTLogger.writeToLog(e.getMessage());
		status = "false";
	  } finally {

		webDriver.quit();
		ExtentTestManager.setThreadStatus("f");
	  }

	}

	return status;
  }

}
