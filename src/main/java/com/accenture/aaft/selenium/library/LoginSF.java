package com.accenture.aaft.selenium.library;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.runner.selenium.AES_Encryption;
import com.relevantcodes.extentreports.ExtentTest;

/**
 * Class is used to perform click action on UI
 *
 * @author Azeem mohammed
 *
 */
public class LoginSF {

	/**
	 * Method is used to perform click action on UI
	 *
	 * @param webDriver
	 *            - represents WebDriver
	 * @param objectName
	 *            - represents object name
	 * @param inputValue
	 *            - represents input value
	 * @param extentTest
	 *            - represents ExtentTest
	 * @param controlName
	 *            - represents control name
	 * @return status
	 */
	public String login(WebDriver webDriver, ExtentTest extentTest, String controlName) {

		CTLogger.writeToLog("LoginSF", "login() ", " method called");
		String status = "false";
		String cwsIdPath = "//*[@name=\"username\"]";
		String passwordPath = "//*[@name=\"pw\"]";
		String loginPath = "//*[@id=\"Login\"]";
		String selector = "xpath";

		try {
			
			PropertyFileReader propertyFileReader = new PropertyFileReader();
			String Username = propertyFileReader.getValue("SOLARTURBINES_USERNAME");

			String Passwordvalue = propertyFileReader.getValue("SOLARTURBINES_PASSWORD");
			
			final String secretKey = propertyFileReader.getValue("SOLARTURBINES_SECRETKEY");
			
			String decryptedString = AES_Encryption.decrypt(Passwordvalue, secretKey) ;

			Input input = new Input();
			input.enterText(webDriver, cwsIdPath, selector, Username, extentTest, "CWS_ID_" + controlName);

			input.enterText(webDriver, passwordPath, selector, decryptedString, extentTest, "Password_" + controlName);

			Click click = new Click();
			click.click(webDriver, loginPath, selector, extentTest, "Login_Button_" + controlName);

			Thread.sleep(7000);
			
			String url = webDriver.getCurrentUrl();			
			
			
			Thread.sleep(3000);
					
			if(url.contains("lightning.force.com/one/one.app"))
			{
				Thread.sleep(7000);
				webDriver.findElement(By.xpath("//div[contains(@class,'profileTrigger')]/span[1]")).click();
				
				Thread.sleep(7000);
				webDriver.findElement(By.xpath("//a[text()='Switch to Salesforce Classic']")).click();
				
				System.out.println("It's Lightning URL Switching to Sales-force Normal >::"+url);
			}
			else if(url.contains("lightning.force.com/lightning"))
			{
				Thread.sleep(5000);
				webDriver.findElement(By.xpath("(//span[@class='uiImage']/img[@title='User'])[1]")).click();
				try {
					Thread.sleep(7000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				webDriver.findElement(By.xpath("//a[text()='Switch to Salesforce Classic']")).click();
				
				System.out.println("Second Catch It's Lightning URL Switching to Sales-force Normal >::"+url);
			}
			else
			{
				System.out.println("It's Non Lightning URL >::"+url);
			}
			
			status = "true";

		} catch (Exception e) {
			e.printStackTrace();
			status = "false";
		} /*finally {
			status = "false";
		}*/
		return status;
	}

}
