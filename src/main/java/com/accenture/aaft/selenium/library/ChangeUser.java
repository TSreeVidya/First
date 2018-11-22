package com.accenture.aaft.selenium.library;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.report.ExtentTestManager;
import com.accenture.aaft.selenium.library.utility.WaitForObjectExist;
import com.accenture.aaft.selenium.library.utility.WebElementDetails;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Class is used to perform change user action on UI
 *
 * @author Azeem Mohammad
 *
 */
public class ChangeUser {
  String searchAlphabet = "-";
  String searchAlphabetPath = "";
  String nextPagePath = "";
  String nextPageDisabledPath = "";
  String userLinkPath = "";
  String loginButtonPath = "";

  /**
   * Method is used to perform change user action on UI
   *
   * @param webDriver - represents WebDriver
   * @param objectName - represents object name
   * @param selector - represents selector type
   * @param extentTest - represents ExtentTest
   * @param controlName - represents control name
   * @return status
   */
  public String changeUser(WebDriver webDriver, String user, ExtentTest extentTest) {

	CTLogger.writeToLog("ChangeUser", " changeUser() ", " method called");
	PropertyFileReader propertyFileReader = new PropertyFileReader();
	String status = "false";
	String selector = "xpath";
	boolean setupStatus = false;

	try {
			
		Thread.sleep(5000);
		
		String url = webDriver.getCurrentUrl();			
			
		Thread.sleep(3000);
				
		if(url.contains("lightning.force.com/one/one.app"))
		{
			Thread.sleep(5000);
			webDriver.findElement(By.xpath("//div[contains(@class,'profileTrigger')]/span[1]")).click();
			
			Thread.sleep(5000);
			webDriver.findElement(By.xpath("//a[text()='Switch to Salesforce Classic']")).click();
			
			System.out.println("It's Lightning URL Switching to Sales-force Normal >::"+url);
		}
		else
		{
			System.out.println("It's Non Lightning URL >::"+url);
		}
					
	  Thread.sleep(5000);
	  WebElement element1=webDriver.findElement(By.xpath("//select[@id='fcf']"));
	  Select se=new Select(element1);
	  se.selectByVisibleText("All Users");
	  Thread.sleep(5000);
	  String selectedValue = se.getFirstSelectedOption().getText();
	  System.out.println("Selectedvalue------------------->"+selectedValue);
	  
	  webDriver.findElement(By.xpath("//a[contains(text(),'Full Name')]")).click();
	  Thread.sleep(5000);
	  
	  setupStatus = setupObjectPaths(user);
	  System.out.println("setupStatus--------------------->:"+setupStatus);
	  
	  
	  
	  if (setupStatus) {
		  WaitForObjectExist waitForObjectExist = new WaitForObjectExist();
		  waitForObjectExist.waitForExistance(webDriver, searchAlphabetPath, selector);
	
		  WebElementDetails webElementDetails = new WebElementDetails();
		  List<WebElement> listElements = webElementDetails.getElements(webDriver, searchAlphabetPath, selector);
	
		  WebElement element = listElements.get(0);
		  if (element.isDisplayed()) {
			String jScript = "var element = arguments[0]; element.click();"; 
			JavascriptExecutor executor = (JavascriptExecutor) webDriver;
			executor.executeScript(jScript, element);
	
			Thread.sleep(3000);
			
			CTLogger.writeToLog("ChangeUser", " changeUser() ", " control clicked -- path1 : " + searchAlphabetPath);
			status = "true";
	
		  } else {
	
			CTLogger.writeToLog("ChangeUser ", " changeUser()", " Object not found -- path : " + searchAlphabetPath);
			status = "false";
		  }

		  if ("true".equals(status)) {
			while(true) {
			    try {
			      waitForObjectExist = new WaitForObjectExist();
			      waitForObjectExist.waitForExistance(webDriver, userLinkPath, selector, 5);
				  webElementDetails = new WebElementDetails();
			    }
			    catch(Exception ex) {
			    }

		        listElements = webElementDetails.getElements(webDriver, userLinkPath, selector, 1);
				if (null == listElements || listElements.size() == 0) {
				  try {
					waitForObjectExist = new WaitForObjectExist();
					waitForObjectExist.waitForExistance(webDriver, nextPagePath, selector, 5);
					webElementDetails = new WebElementDetails();
				  }
				  catch(Exception ex) {
				  }

				  listElements = webElementDetails.getElements(webDriver, nextPagePath, selector, 1);
			      if (null == listElements || listElements.size() == 0) {
					CTLogger.writeToLog("ChangeUser ", " changeUser()", " Next Page not found -- path : " + nextPagePath);
					status = "false";
					break;
			      }
			      else {
			        element = listElements.get(0);
				    if (element.isDisplayed()) {
					  String jScript = "var element = arguments[0]; element.click();"; 
					  JavascriptExecutor executor = (JavascriptExecutor) webDriver;
					  Thread.sleep(3000);
					  executor.executeScript(jScript, element);
				
					  CTLogger.writeToLog("ChangeUser", " changeUser() ", " Next Page clicked -- path : " + nextPagePath);
				    }
				    else {
					  CTLogger.writeToLog("ChangeUser ", " changeUser()", " Next Page not visible -- path : " + nextPagePath);
					  status = "false";
					  break;
				    }
			      }
				}
				else {
			      element = listElements.get(0);
				  if (element.isDisplayed()) {
					String jScript = "var element = arguments[0]; element.click();"; 
					JavascriptExecutor executor = (JavascriptExecutor) webDriver;
					executor.executeScript(jScript, element);
					
					if (user.contains("#")) {
				      try {
					    waitForObjectExist = new WaitForObjectExist();
					    waitForObjectExist.waitForExistance(webDriver, loginButtonPath, selector, 5);
						webElementDetails = new WebElementDetails();
					  }
					  catch(Exception ex) {
					  }

				      listElements = webElementDetails.getElements(webDriver, loginButtonPath, selector, 1);
					  element = listElements.get(0);
					  if (element.isDisplayed()) {
						jScript = "var element = arguments[0]; element.click();"; 
						executor = (JavascriptExecutor) webDriver;
						Thread.sleep(3000);
						executor.executeScript(jScript, element);
					  }
					}

					CTLogger.writeToLog("ChangeUser", " changeUser() ", " control clicked -- path2 : " + userLinkPath);
		
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
										
					String url3 = webDriver.getCurrentUrl();			
					Thread.sleep(5000);
	
					if(url3.contains("lightning.force.com/one/one.app"))
					{
						try {
							Thread.sleep(15000);
							System.out.println("Entered first");	
							
							/*if(webDriver.findElement(By.xpath("//button[@title='Close this window']")))
							{
								webDriver.findElement(By.xpath("//button[@title='Close this window']")).click();
							}*/
							
							Actions actions = new Actions(webDriver);
							WebElement subMenu = webDriver.findElement(By.xpath("//span[@class='uiImage']/img[@title='User']"));
							actions.moveToElement(subMenu);
							actions.click().build().perform();
							
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							
							System.out.println("Unable to find the Webelement");
							e.printStackTrace();
						}
						
						try {
							Thread.sleep(5000);
							webDriver.findElement(By.xpath("//a[text()='Switch to Salesforce Classic']")).click();
							System.out.println("First Catch It's Lightning URL Switching to Sales-force Normal >::"+url3);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			
					}
					
					
					else if(url3.contains("lightning.force.com/lightning/page"))
					{	
						Thread.sleep(12000);
						
						/*if(webDriver.findElement(By.xpath("//button[@title='Close this window']")))
						{
							webDriver.findElement(By.xpath("//button[@title='Close this window']")).click();
						}*/
												
						Actions actions = new Actions(webDriver);
						WebElement subMenu = webDriver.findElement(By.xpath("//span[@class='uiImage']/img[@title='User']"));
						actions.moveToElement(subMenu);
						actions.click().build().perform();
						
						try {								
							Thread.sleep(5000);						
							webDriver.findElement(By.xpath("//a[text()='Switch to Salesforce Classic']")).click();							
							System.out.println("Second Catch It's Lightning URL Switching to Sales-force Normal >::"+url3);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}							
					}
					else
					{
						System.out.println("It's Non Lightning URL >::"+url3);
					}
					
					status = "true";
					
				  }
				  else {
					CTLogger.writeToLog("ChangeUser ", " changeUser()", " Object not found -- path : " + userLinkPath);
					status = "false";
				  }
				  break;
				}
			}
		  }
	  }

	  if (status.equals("true")) {
		extentTest.log(LogStatus.PASS, " changed to user '" + user + "'");
	  }
	  else {
		CTLogger.writeToLog("ChangeUser", "changeUser()", " Unable to change user to '" + user + "'");
		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + user)));
		webDriver.quit();
		ExtentTestManager.setThreadStatus("f");
	  }

	}
	catch (Exception e) {
	  try {
		e.printStackTrace();
		CTLogger.writeToLog("ChangeUser", "changeUser()", " Unable to change user to '" + user + "'");
		status = "false";
	  }
	  finally {
		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + user)));
		webDriver.quit();
		ExtentTestManager.setThreadStatus("f");
	  }
	}
		
	return status;
  }

	 
  public boolean setupObjectPaths(String user) {
	if(user == null || user.length() == 0)
	  return false;

	char alphabet = user.toUpperCase().charAt(0);
	if(alphabet < 'A' || alphabet > 'Z')
		searchAlphabet = "All";
	else
		searchAlphabet = "" + alphabet;

	searchAlphabetPath = "//span[text()=\"" + searchAlphabet + "\"]/parent::a[@class=\"listItem\"]";
	//System.out.println("searchAlphabetPath---------->:"+searchAlphabetPath);
	nextPagePath = "//div[@class=\"next\"]/a[contains(text(),\"Next Page\")]";
	nextPageDisabledPath = "//div[@class=\"next\"]/span[contains(text(),\"Next Page\")]";
	loginButtonPath = "//input[@name=\"login\" and @type=\"button\"]";

	String[] inputArray = user.split(",");
	String[] name = inputArray[1].split("#");
	if (inputArray.length <= 2) {
		if (name.length == 2)
		    userLinkPath = "//a[text()=\"Login\" and contains(@title,\"" + (inputArray[0] + "," + name[0]) + "\")]/following::a[text()=\"" + (inputArray[0] + "," + name[0]) + "\"]";
		else
		    userLinkPath = "//a[text()=\"" + (inputArray[0] + "," + name[0]) + "\"]/preceding::a[text()=\"Login\" and contains(@title,\"" + (inputArray[0] + "," + name[0]) + "\")]";
	}
	else {
		if (name.length == 2)
			userLinkPath = "//a[text()=\"Login\" and contains(@title,\"" + (inputArray[0] + "," + name[0]) + "\")]/following::a[text()=\"" + inputArray[2] + "\"]/preceding::a[text()=\"" + (inputArray[0] + "," + name[0]) + "\"][1]";
		else
			userLinkPath = "//a[text()=\"" + inputArray[2] + "\"]/preceding::a[text()=\"" + (inputArray[0] + "," + name[0]) + "\"]/preceding::a[text()=\"Login\" and contains(@title,\"" + (inputArray[0] + "," + name[0]) + "\")]";
	}

    return true;
  }
  
  
  public static WebElement isElementPresnt(WebDriver driver,String xpath,int time)
	{
		WebElement ele = null;
		
			for(int i=0;i<time;i++)
			{
				try
				{
					System.out.println("Value of i------------->:"+i);
					ele=driver.findElement(By.xpath(xpath));
					System.out.println("Xpath------------->:"+ele);
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
