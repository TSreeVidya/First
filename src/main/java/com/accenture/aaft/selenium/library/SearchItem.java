package com.accenture.aaft.selenium.library;

import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
 * @author Mohammed.Azeem
 *
 */
public class SearchItem {
	
	//public static WebDriverWait wait;

  /**
   * Method is used to perform click action on UI
   *
   * @param webDriver - represents WebDriver
   * @param extentTest - represents ExtentTest
   * @param inputValue - represents inputValue 
   */
  public String searchItem(WebDriver webDriver, String inputValue, ExtentTest extentTest, Map<String,String> hmTempStore) {

	  	String[] inputArray = inputValue.split(","); 
		String inputOpportunityName = inputArray[0];
		String inputInquiryNumber = "";
		String inputInquiryVariable = "";
		

		if (inputArray.length == 3)
		{
			inputInquiryVariable = inputArray[2];
		}
			
		else
		{
			inputInquiryVariable = "inquiryNumber";
		}

		if (hmTempStore.containsKey(inputInquiryVariable))
		{
			inputInquiryNumber = hmTempStore.get(inputInquiryVariable);
		}
		  
		else
		{
			inputInquiryNumber = inputArray[1];

		}
		  
		CTLogger.writeToLog("FindAndClickSFOpportunity", " findandclicksfopportunity() ", " method called to click opportunity - " + inputOpportunityName + "; inquiry number - " + inputInquiryNumber);
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		String status = "false";
	
	try {
		
	//wait = new WebDriverWait(webDriver,10);	
		
	webDriver.findElement(By.xpath("//li[@id='home_Tab']/a[1]")).click();
	
	Thread.sleep(2000);	
	webDriver.findElement(By.id("phSearchInput")).clear();
	
	webDriver.findElement(By.id("phSearchInput")).sendKeys(inputOpportunityName+Keys.ENTER);
	Thread.sleep(2000);	
	CTLogger.writeToLog("SearchItem", " SearchItem() ", " control clicked -- path : " + inputOpportunityName);


	if(webDriver.findElements(By.xpath("//span[contains(text(),'Opportunities')]/following::span[1]/a[text()='Show More']")).size()!=0)
	{
		Thread.sleep(2000);	
		webDriver.findElement(By.xpath("//div[@class='pSearchShowMore']/span[1]/a[1]")).click();
		System.out.println("IF Condition");
		Thread.sleep(2000);	
		webDriver.findElement(By.xpath("//div[@id='Opportunity_body']/table/tbody/tr/td[text()='"+inputInquiryNumber+"']/preceding::th[1]/a[text()='"+inputOpportunityName+"']")).click();		
	}
	else
	{
		System.out.println("Else Conditon");
		Thread.sleep(1000);	
		webDriver.findElement(By.xpath("//div[@id='Opportunity_body']/table/tbody/tr/td[text()='"+inputInquiryNumber+"']/preceding::th[1]/a[text()='"+inputOpportunityName+"']")).click();
	}
		
	String DetailPageInquiryNumber=webDriver.findElement(By.xpath("//td[text()='Inquiry Number']/following::td[1]/div")).getText();
	
	System.out.println("InquiryNumber--------------------->:"+DetailPageInquiryNumber);
	
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
  
  
  //dynamic method for handling synchronization issues.
  public static WebElement isElementPresnt(WebDriver driver,String xpath,int time)
	{
		WebElement ele = null;
		
			for(int i=0;i<time;i++)
			{
				try
				{
					System.out.println("Value of i------------->:"+i);
					ele=driver.findElement(By.xpath(xpath));
					break;
				}
				catch(Exception e)
				{
					try 
					{
						Thread.sleep(1000);
					} catch (InterruptedException e1) 
					{
						System.out.println("Waiting for element to appear on DOM");
					}
				}
			}
			return ele;
	}
}
