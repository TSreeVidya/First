package com.accenture.aaft.selenium.library;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;

public class Logout {
	public String Logout(WebDriver webDriver, ExtentTest extentTest, String controlName) 
	{

		String status = "false";
		  try {
	            
	            String url = webDriver.getCurrentUrl();            
	            Thread.sleep(3000);
	            if(url.contains("salesforce.com/setup")) //logout from the normal app
	            {
	               /* Thread.sleep(5000);
	                webDriver.findElement(By.xpath("//div[@id='userNavButton']")).click();
	                */
	                Thread.sleep(5000);
	                webDriver.findElement(By.xpath("//div[@id='userNav-menuItems']/a[contains(text(),'Logout')] ")).click();
	                System.out.println("It is Normal App: URL >::"+url);
	            }
	            
	           /* else if(url.contains("ap4.lightning.force.com/lightning/page/home?0.source=alohaHeader"))
	            { 
	            	Thread.sleep(4000);
	                webDriver.findElement(By.xpath("//div[@class='profileTrigger branding-user-profile bgimg branding-profile_default-small circular forceEntityIcon']/span[@class='uiImage']]")).click();
	                Thread.sleep(4000);
	                webDriver.findElement(By.xpath("//a[@class='profile-link-label logout uiOutputURL']]")).click();
	                webDriver.quit();
	            }
	            
	        */
		  else if(url.contains("https://ap4.salesforce.com/a00/o?tsid=02u6F000002amD2")) //logout from the console app
          {
              Thread.sleep(7000);
              webDriver.findElement(By.xpath("//div[@id='userNavButton']/span[@id='userNavLabel']")).click();
              Thread.sleep(7000);
              webDriver.findElement(By.xpath("//div[@id='userNav-menuItems']/a[contains(text(),'Logout')]")).click();
          
              System.out.println("Automation World Console App"+url);
          }
		  }
		  
          
	        catch (Exception e) {
	            e.printStackTrace();
	            status = "false";
	        } 
	        return status;
	}
}

		