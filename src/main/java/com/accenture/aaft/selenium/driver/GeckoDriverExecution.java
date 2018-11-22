package com.accenture.aaft.selenium.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.accenture.aaft.logger.CTLogger;

public class GeckoDriverExecution {

	public static void main(String[] args) {
		CTLogger.writeToLog("Before getting gecko driver");
		System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+System.getProperty("file.separator")+"driver"
				+System.getProperty("file.separator")+"geckodriver.exe");
		WebDriver webDriver = new FirefoxDriver();
		CTLogger.writeToLog("After getting gecko driver");
		webDriver.get("http://localhost:8780/konakart/LogIn.action");
		CTLogger.writeToLog("After url");
		
	}
}
