package com.accenture.aaft.selenium.library;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

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
public class Multipurpose {

	/**
	 * Method is used to perform click action on UI
	 *
	 * @param webDriver
	 *            - represents WebDriver
	 * @param objectName
	 *            - represents object name
	 * @param selector
	 *            - represents selector type
	 * @param extentTest
	 *            - represents ExtentTest
	 * @param controlName
	 *            - represents control name
	 * @return status
	 */
	public String multipurpose(WebDriver webDriver, String inputValue, ExtentTest extentTest) {

		CTLogger.writeToLog("multipurpose", " multipurpose() ", " method called");
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		String status = "false";

		try {

			if (inputValue.equalsIgnoreCase("INPUTCP")) 
			{

				JavascriptExecutor jse = (JavascriptExecutor) webDriver;
				jse.executeScript("document.body.style.zoom = '90%';");

				WebElement element1 = webDriver.findElement(By.xpath(
						"//span[text()='Generator Set Operation and Maintenance Principles at Solar']/following::input[1]"));

				jse.executeScript("arguments[0].click();", element1);

				WebElement element2 = webDriver.findElement(By.xpath(
						"//label/span[text()='CS/MD Operation and Maintenance Principles at Solar']/following::input[1]"));

				jse.executeScript("arguments[0].click();", element2);

				WebElement element3 = webDriver.findElement(
						By.xpath("//label/span[text()='Turbotronic Control System at Solar']/following::input[1]"));

				jse.executeScript("arguments[0].click();", element3);
			} 
			else if (inputValue.equalsIgnoreCase("Defaultpage")) 
			{
				webDriver.switchTo().defaultContent();
			}
			
			else if (inputValue.equalsIgnoreCase("AccountName")) 
			{
				String AccountName = propertyFileReader.getValue("ACCOUNTNAME");
				webDriver.findElement(By.xpath("//label[contains(text(),'Type in an account name')]/following::input[1]")).sendKeys(AccountName);
				Thread.sleep(8000);
				
				webDriver.findElement(By.xpath("//a[contains(text(),\""+AccountName+"\")]")).click();

				Thread.sleep(3000);
				String Package = propertyFileReader.getValue("ACCOUNTNAMEASPACKAGE");
				
				webDriver.findElement(By.xpath("//td[text()=\""+Package+"\" ]/preceding::td[1]/input[1]")).click();
				Thread.sleep(3000);
			}
			
			else if (inputValue.equalsIgnoreCase("Group3X")) 
			{
				
				WebDriverWait wait=new WebDriverWait(webDriver, 20);
				
				webDriver.switchTo().defaultContent();
				wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//*[@id='edit_quote']")));
				Thread.sleep(5000);
				
				WebElement element =webDriver.findElement(By.xpath("//span[text()='Group 3X - Balance of Plant']"));
				((JavascriptExecutor)webDriver).executeScript("arguments[0].scrollIntoView();", element);
				
				Thread.sleep(3000);

				webDriver.findElement(By.xpath("//span[text()='Group 3X - Balance of Plant']")).click();
				Thread.sleep(3000);
				WebElement mySelectElement = webDriver.findElement(By.xpath("//span[text()='Group 3X - Balance of Plant']/following::label[contains(text(),'Selection')][1]/following::select[1]"));
				Select dropdown= new Select(mySelectElement);
				
				dropdown.selectByVisibleText("Base Scope");
				
			}
			
			
			else if(inputValue.equalsIgnoreCase("Logout"))
			{
				webDriver.findElement(By.xpath("//div[@id='userNavButton']")).click();
				webDriver.findElement(By.xpath("//div[@id='userNav-menuItems']/a[4]")).click();
				
				Thread.sleep(5000);
				String url = webDriver.getCurrentUrl();					
				
				if(url.contains("lightning.force.com/one/one.app"))
				{
					Thread.sleep(2000);
					webDriver.findElement(By.xpath("//a[contains(@href,'/secur/logout')]")).click();					
					System.out.println("Lightning url---------------------->:"+url);
				}
				else if(url.contains("lightning.force.com/lightning/page"))
				{
					Thread.sleep(2000);
					webDriver.findElement(By.xpath("//a[contains(@href,'/secur/logout')]")).click();					
					System.out.println("Lightning url---------------------->:"+url);
				}
				else
				{
					System.out.println("Normal Salesforce url---------------------->:"+url);
				}
			}

			status = "true";

		} catch (Exception e) {
			try {
				e.printStackTrace();
				CTLogger.writeToLog("multipurpose", "multipurpose()", " Object not found exeption thrown  --> path : ");
				status = "false";
			} finally {

				extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(
						ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH"))));
				webDriver.quit();
				ExtentTestManager.setThreadStatus("f");
			}

		}
		return status;
	}

}
