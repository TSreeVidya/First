package com.accenture.aaft.selenium.library;

import java.util.Map;

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
 * Class is used to find and select case on UI
 *
 * @author prashant.gurunathan
 *
 */
public class FindAndClickSFCase {

  /**
   * Method is used to find and select case on UI
   *
   * @param webDriver - represents WebDriver
   * @param objectName - represents object name
   * @param selector - represents selector type
   * @param extentTest - represents ExtentTest
   * @param controlName - represents control name
   * @param hmTempStore - represents temporary store
   * @return status
   */
  public String findAndClickSFCase(WebDriver webDriver, String inputValue, ExtentTest extentTest, Map<String,String> hmTempStore) {

	String[] inputArray = inputValue.split(","); 
	String inputCaseVariable = inputArray[0];
	String inputCaseNumber = "";

	System.out.println("inputArray[0]------------->:"+inputArray[0]);
	
	if (hmTempStore.containsKey(inputCaseVariable))
	{
	  inputCaseNumber = hmTempStore.get(inputCaseVariable);
	  System.out.println("inputCaseNumber------------->:"+inputCaseNumber);
	}
	  else
	  {
	  inputCaseNumber = inputArray[1];
	  System.out.println("inputArray[1]------------->:"+inputArray[1]);
	  }
	CTLogger.writeToLog("FindAndClickSFCase", " findAndClickSFCase() ", " method called to click case - " + inputCaseNumber);
	PropertyFileReader propertyFileReader = new PropertyFileReader();
	String status = "false";
	
	
	try {
		
	//wait = new WebDriverWait(webDriver,10);	
	
	Thread.sleep(2000);		
	webDriver.findElement(By.xpath("//li[@id='home_Tab']/a[1]")).click();
	
	Thread.sleep(2000);	
	webDriver.findElement(By.id("phSearchInput")).clear();
	
	webDriver.findElement(By.id("phSearchInput")).sendKeys(inputCaseNumber+Keys.ENTER);
	
	Thread.sleep(2000);
		
	CTLogger.writeToLog("SearchItem", " SearchItem() ", " control clicked -- path : " + inputCaseNumber);

	////a[contains(text(),\""+AccountName+"\")]"

	webDriver.findElement(By.xpath("//a[contains(text(),\""+inputCaseNumber+"\")]")).click();
	
/*	if(webDriver.findElements(By.xpath("//span[contains(text(),'Opportunities')]/following::span[1]/a[1]")).size()!=0)
	{
		System.out.println("Entered into the if conditon");
		webDriver.findElement(By.xpath("//div[@class='pSearchShowMore']/span[1]/a[1]")).click();
		Thread.sleep(2000);
		webDriver.findElement(By.xpath("//div[@id='Opportunity_body']/table/tbody/tr/td[text()='"+inputInquiryNumber+"']/preceding::th[1]/a[text()='"+inputOpportunityName+"']")).click();		
	}
	else
	{
		System.out.println("Entered into the else conditon");
		webDriver.findElement(By.xpath("//div[@id='Opportunity_body']/table/tbody/tr/td[text()='"+inputInquiryNumber+"']/preceding::th[1]/a[text()='"+inputOpportunityName+"']")).click();
	}
		
	String DetailPageInquiryNumber=webDriver.findElement(By.xpath("//td[text()='Inquiry Number']/following::td[1]/div")).getText();
	
	System.out.println("DetailPage Inquiry-Number--------------------->:"+DetailPageInquiryNumber);*/
	
	status = "true";
	
	}
	
	 catch (Exception ex) {
	  try {

		ex.printStackTrace();
		if (ex.getMessage().toString().contains("Modal dialog present")) {

		  CTLogger.writeToLog("SearchItem", "searchItem() ", " not able to search.");
		  extentTest.log(LogStatus.INFO, "Not able to find out: " + inputValue);

		}
	  } finally 
	  {
		ex.printStackTrace();
		CTLogger.writeToLog("SearchItem", "searchItem() ", " not able to search.");
		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + "open url")));
		ExtentTestManager.setThreadStatus("f");
		webDriver.quit();

	  }
	 }
  	
	return status;
  }


/*	try {
		  
		  WebElement element = webDriver.findElement(By.xpath(".//span[@class='left']/span[contains(@id, 'rpp_target')]"));
		  String jScript = "var element = arguments[0]; element.click();"; 
		  JavascriptExecutor executor = (JavascriptExecutor) webDriver;
		  executor.executeScript(jScript, element);
		  
		  element = webDriver.findElement(By.xpath("//td[@class='rppOpt' and text()='200']"));
		  executor.executeScript(jScript, element);
		  
		  WebElement pageLink = webDriver.findElement(By.xpath("//div[@title='Case Number']"));
		  if (pageLink.isDisplayed()) {
	
			  Thread.sleep(3000);
			  WebElementDetails webElementDetails = new WebElementDetails();
			  WebElement pagination = webElementDetails.getElement(webDriver, "//span[@class='right']", "xpath");

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
					  WebElement lnkCase = webDriver.findElement(By.xpath("//div/a[text()='" + inputCaseNumber + "']"));
					  if(lnkCase.isDisplayed()){
						  executor.executeScript(jScript, lnkCase);
						  status = "true";
						  break;
					  	}
					  }
				  catch(Exception e){
					  CTLogger.writeToLog(inputCaseNumber + " object is not found in page index- " + pageIndex);
				  }
				  pageIndex++;
			  }while(pageIndex <= pagecnt || !maxPagesKnown);
			} 
			else {
			CTLogger.writeToLog("Click ", " click()", " Link not found" + " --> " + "Case Number Xpath");
			status = "false";
		  }
	 
		  
		  if (status.equals("true")) {
			extentTest.log(LogStatus.PASS, " clicked on " + inputCaseNumber);
		  } else {

			CTLogger.writeToLog("Click", "click()", " Object not found exeption thrown  --> path : " + inputCaseNumber);
			extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + inputCaseNumber)));
			webDriver.quit();
			ExtentTestManager.setThreadStatus("f");
		  }

		} catch (Exception e) {
		  try {
			e.printStackTrace();
			CTLogger.writeToLog("Click", "click()", " Object not found exeption thrown  --> path : " + inputCaseNumber);
			status = "false";
		  } finally {

			extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + inputCaseNumber)));
			webDriver.quit();
			ExtentTestManager.setThreadStatus("f");
		  }

		}

		return status;
	  } */

	}

