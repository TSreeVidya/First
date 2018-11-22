package com.accenture.aaft.selenium.library;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
 * @author prashant.gurunathan
 *
 */
public class FindAndClickSFAccount {

  /**
   * Method is used to find and select account on UI
   *
   * @param webDriver - represents WebDriver
   * @param objectName - represents object name
   * @param selector - represents selector type
   * @param extentTest - represents ExtentTest
   * @param controlName - represents control name
   * @param hmTempStore - represents temporary store
   * @return status
   */
  public String findAndClickSFAccount(WebDriver webDriver, String inputValue, ExtentTest extentTest, Map<String,String> hmTempStore) {

	String[] inputArray = inputValue.split(","); 
	String inputAccountVariable = inputArray[0];
	String inputAccountName = "";

	if (hmTempStore.containsKey(inputAccountVariable))
	  inputAccountName = hmTempStore.get(inputAccountVariable);
	else
	  inputAccountName = inputArray[1];

	CTLogger.writeToLog("FindAndClickSFAccount", " findAndClickSFAccount() ", " method called to click case - " + inputAccountName);
	PropertyFileReader propertyFileReader = new PropertyFileReader();
	String status = "false";

	try {
		  
	  char firstChar = Character.toUpperCase(inputAccountName.trim().charAt(0));
	  CTLogger.writeToLog("firstchar -" + firstChar);
	  
	  WebElement element = webDriver.findElement(By.xpath("//span[@class='left']/span[contains(@id, 'rpp_target')]"));
	  String jScript = "var element = arguments[0]; element.click();"; 
	  JavascriptExecutor executor = (JavascriptExecutor) webDriver;
	  executor.executeScript(jScript, element);
	  
	  element = webDriver.findElement(By.xpath("//td[@class='rppOpt' and text()='200']"));
	  executor.executeScript(jScript, element);
	  
	  WebElement pageLink = webDriver.findElement(By.xpath(".//span[text()='"+ firstChar + "']/parent::a[@class='listItem']"));
	  if (pageLink.isDisplayed()) {
		  executor.executeScript(jScript, pageLink);
		  CTLogger.writeToLog("Click", " click() ", " Link clicked -- path : " + firstChar);

		  Thread.sleep(3000);
	      WebElementDetails webElementDetails = new WebElementDetails();
	      WebElement pagination = webElementDetails.getElement(webDriver, "//span[@class='right']", "xpath");

		  String[] pageArray = pagination.getText().split(" ");
		  int pagecnt = Integer.parseInt(pageArray[pageArray.length - 1]);
		  CTLogger.writeToLog("page count - " + pagecnt);
		  int pageIndex = 1;
		  do{
			  CTLogger.writeToLog("page index - " + pageIndex);
			  if(pageIndex!=1 && pageIndex<=pagecnt){
				  WebElement lnkNext = webDriver.findElement(By.xpath("//span[@class='prevNext']/a[contains(text(),'Next')]"));
				  executor.executeScript(jScript, lnkNext);
			  }	  
			  try{
				  WebElement lnkCase = webDriver.findElement(By.xpath("//div/a/span[text()='" + inputAccountName + "']/parent::a"));
				  if(lnkCase.isDisplayed()){
					  executor.executeScript(jScript, lnkCase);
					  status = "true";
					  break;
				  	}
				  }
			  catch(Exception e){
				  CTLogger.writeToLog(inputAccountName + " object is not found in page index- " + pageIndex);
			  }
			  pageIndex++;
		  }while(pageIndex <= pagecnt);
	  }
	  else {
		CTLogger.writeToLog("FindAndClickSFAccount ", " findAndClickSFAccount()", " Link not found" + " --> " + firstChar);
		status = "false";
	  }

	  
	  if (status.equals("true")) {
		extentTest.log(LogStatus.PASS, " clicked on " + inputAccountName);
	  } else {

		CTLogger.writeToLog("FindAndClickSFAccount", "findAndClickSFAccount()", " Object not found exeption thrown  --> path : " + inputAccountName );
		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + inputAccountName)));
		webDriver.quit();
		ExtentTestManager.setThreadStatus("f");
	  }

	} catch (Exception e) {
	  try {
		e.printStackTrace();
		CTLogger.writeToLog("FindAndClickSFAccount", "findAndClickSFAccount()", " Object not found exeption thrown  --> path : " + inputAccountName );
		status = "false";
	  } finally {

		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + inputAccountName)));
		webDriver.quit();
		ExtentTestManager.setThreadStatus("f");
	  }

	}

	return status;
  }

}
