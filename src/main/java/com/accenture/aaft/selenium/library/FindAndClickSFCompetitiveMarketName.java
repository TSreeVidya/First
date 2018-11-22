package com.accenture.aaft.selenium.library;

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
 * @author Azeem.Mohammad.
 *
 */
public class  FindAndClickSFCompetitiveMarketName {

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
  public String  FindAndClickSFCompetitiveMarketName(WebDriver webDriver, String inputValue, ExtentTest extentTest) {

	String  CMMName = inputValue;

	CTLogger.writeToLog(" FindAndClickSFCompetitiveMarketName", "  FindAndClickSFCompetitiveMarketName() ", " method called to click case - " +  CMMName);
	PropertyFileReader propertyFileReader = new PropertyFileReader();
	String status = "false";

	try {
		  
	  char firstChar = Character.toUpperCase( CMMName.trim().charAt(0));
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
				  WebElement lnkCase = webDriver.findElement(By.xpath("//div/a/span[text()='" +  CMMName + "']/parent::a"));
				  if(lnkCase.isDisplayed()){
					  executor.executeScript(jScript, lnkCase);
					  status = "true";
					  break;
				  	}
				  }
			  catch(Exception e){
				  CTLogger.writeToLog( CMMName + " object is not found in page index- " + pageIndex);
			  }
			  pageIndex++;
		  }while(pageIndex <= pagecnt);
	  }
	  else {
		CTLogger.writeToLog(" FindAndClickSFCompetitiveMarketName ", "  FindAndClickSFCompetitiveMarketName()", " Link not found" + " --> " + firstChar);
		status = "false";
	  }

	  
	  if (status.equals("true")) {
		extentTest.log(LogStatus.PASS, " clicked on " +  CMMName);
	  } else {

		CTLogger.writeToLog(" FindAndClickSFCompetitiveMarketName", " FindAndClickSFCompetitiveMarketName()", " Object not found exeption thrown  --> path : " +  CMMName );
		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") +  CMMName)));
		webDriver.quit();
		ExtentTestManager.setThreadStatus("f");
	  }

	} catch (Exception e) {
	  try {
		e.printStackTrace();
		CTLogger.writeToLog(" FindAndClickSFCompetitiveMarketName", " FindAndClickSFCompetitiveMarketName()", " Object not found exeption thrown  --> path : " +  CMMName );
		status = "false";
	  } finally {

		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") +  CMMName)));
		webDriver.quit();
		ExtentTestManager.setThreadStatus("f");
	  }

	}

	return status;
  }

}
