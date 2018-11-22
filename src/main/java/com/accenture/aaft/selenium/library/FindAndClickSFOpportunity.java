package com.accenture.aaft.selenium.library;

import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.report.ExtentTestManager;
import com.accenture.aaft.selenium.library.utility.WebElementDetails;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Class is used to perform click action on UI
 *
 * @author varija.karampudi
 *
 */
public class FindAndClickSFOpportunity {

  /**
   * Method is used to perform click action on UI
   *
   * @param webDriver - represents WebDriver
   * @param objectName - represents object name
   * @param selector - represents selector type
   * @param extentTest - represents ExtentTest
   * @param controlName - represents control name
   * @param hmTempStore - represents temporary store
   * @return status
   */
  public String findandclicksfopportunity(WebDriver webDriver, String inputValue, ExtentTest extentTest, Map<String,String> hmTempStore) {

	String[] inputArray = inputValue.split(","); 
	String inputOpportunityName = inputArray[0];
	String inputInquiryNumber = "";
	String inputInquiryVariable = "";
	
	System.out.println("InputLength------------------------->:"+inputArray.length);

	if (inputArray.length == 3)
	{
		inputInquiryVariable = inputArray[2];
		System.out.println("inputArray[2]------------------------->:"+inputArray[2]);
	}
		
	else
	{
		inputInquiryVariable = "inquiryNumber";
		System.out.println("inputInquiryVariable in else1 conditon--------------------------->:"+inputInquiryVariable);
	}

	if (hmTempStore.containsKey(inputInquiryVariable))
	{
		inputInquiryNumber = hmTempStore.get(inputInquiryVariable);
		System.out.println("InputInquiryVariable hmTempStore------------------------->:"+hmTempStore.get(inputInquiryVariable));
	}
	  
	else
	{
		inputInquiryNumber = inputArray[1];
		System.out.println("inputInquiryVariable in else2 conditon--------------------------->:"+inputInquiryVariable);

	}
	  
	CTLogger.writeToLog("FindAndClickSFOpportunity", " findandclicksfopportunity() ", " method called to click opportunity - " + inputOpportunityName + "; inquiry number - " + inputInquiryNumber);
	PropertyFileReader propertyFileReader = new PropertyFileReader();
	String status = "false";

	try {
	  
	  char firstChar = Character.toUpperCase(inputOpportunityName.trim().charAt(0));
	  CTLogger.writeToLog("firstchar -" + firstChar);
	  
	  WebElement element = webDriver.findElement(By.xpath("(//img[@class='selectArrow'])[1]"));
	  String jScript = "var element = arguments[0]; element.click();"; 
	  JavascriptExecutor executor = (JavascriptExecutor) webDriver;
	  executor.executeScript(jScript, element);
	  element.click();
	  Thread.sleep(3000);
	  element = webDriver.findElement(By.xpath("//td[@class='rppOpt' and text()='200']"));
	  executor.executeScript(jScript, element);
	  Thread.sleep(4000);
	  
	 /* Actions builder = new Actions(webDriver);
	  Action mouseOverHome = builder.moveToElement(element).build();*/
	  
	  element.click();
	  System.out.println("Displayed dynamic list value------------------------>:"+element.getText());
	  
	  
	  WebElement pageLink = webDriver.findElement(By.xpath(".//span[text()='"+ firstChar + "']/parent::a[@class='listItem']"));
	  if (pageLink.isDisplayed()) {
		  executor.executeScript(jScript, pageLink);
		  CTLogger.writeToLog("Click", " click() ", " Link clicked -- path : " + firstChar);

		  Thread.sleep(3000);
		  //WebElement pagination = webDriver.findElement(By.xpath("//span[@class='right']/input"));
		  WebElementDetails webElementDetails = new WebElementDetails();
		  WebElement pagination = webElementDetails.getElement(webDriver, "//span[@class='right']", "xpath");

		  //int pagecnt = Integer.parseInt(pagination.getAttribute("maxlength"));
		  String[] pageArray = pagination.getText().split(" ");

		  int pagecnt = Integer.parseInt(pageArray[pageArray.length - 1]);
		  System.out.println("pagecnt----------------------->:"+pagecnt);
		  boolean maxPagesKnown = true;

		  try {
			pagination = webElementDetails.getElement(webDriver, "//span[@class='right']/input", "xpath", 3);
		  }
		  catch (Exception e) {
			maxPagesKnown = false;
		  }

		  CTLogger.writeToLog((maxPagesKnown)? ("page count - " + pagecnt): ("max pages not known."));
		  int pageIndex = 1;
		  do{
			  CTLogger.writeToLog("page index - " + pageIndex);
			  if((pageIndex!=1 && pageIndex<=pagecnt) || !maxPagesKnown){
				  //WebElement lnkNext = webDriver.findElement(By.xpath("//span[@class='prevNext']/a[text()='Next']"));
				  try {
				  WebElement lnkNext = webElementDetails.getElement(webDriver, "//span[@class='prevNext']/a[text()='Next']", "xpath", 5);
				  if (lnkNext != null)
				    executor.executeScript(jScript, lnkNext);
				  else
					maxPagesKnown = true;
				  }
				  catch(Exception e) {
					maxPagesKnown = true;
				  }
			  }	  
			  try{
				  //WebElement lnkOpportunity = webDriver.findElement(By.xpath(".//div[text()='" + inputInquiryNumber + "']/preceding::span[text()='"+ inputOpportunityName + "'][1]"));
				  //WebElement lnkOpportunity = webElementDetails.getElement(webDriver, ".//div[text()='" + inputInquiryNumber + "']/preceding::span[text()='"+ inputOpportunityName + "'][1]", "xpath", 5);
				  
				  WebElement lnkOpportunity = webElementDetails.getElement(webDriver, ".//div[@title='Inquiry Number']/following::div[contains(text(),'"+inputInquiryNumber+"')]/preceding::span[text()='"+inputOpportunityName+"'][1]", "xpath", 5);

				  
				  if(lnkOpportunity.isDisplayed()){
					  executor.executeScript(jScript, lnkOpportunity);
					  status = "true";
					  break;
				  	}
				  }
			  catch(Exception e){
				  CTLogger.writeToLog(inputOpportunityName + " object is not found in page index- " + pageIndex);
			  }
			  pageIndex++;
		  }while(pageIndex <= pagecnt || !maxPagesKnown);
		} 
		else {
		CTLogger.writeToLog("Click ", " click()", " Link not found" + " --> " + firstChar);
		status = "false";
	  }
 
	  
	  if (status.equals("true")) {
		extentTest.log(LogStatus.PASS, " clicked on " + inputOpportunityName);
	  } else {

		CTLogger.writeToLog("Click", "click()", " Object not found exeption thrown  --> path : " + inputOpportunityName + " --> " + inputOpportunityName);
		Thread.sleep(3000);
		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + inputOpportunityName)));
		webDriver.quit();
		ExtentTestManager.setThreadStatus("f");
	  }

	} catch (Exception e) {
	  try {
		e.printStackTrace();
		CTLogger.writeToLog("Click", "click()", " Object not found exeption thrown  --> path : " + inputOpportunityName + " --> " + inputOpportunityName);
		status = "false";
	  } finally {

		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + inputOpportunityName)));
		webDriver.quit();
		ExtentTestManager.setThreadStatus("f");
	  }

	}
	return status;
  }

}

