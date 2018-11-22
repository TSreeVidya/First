package com.accenture.aaft.selenium.library;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.selenium.library.utility.WebElementDetails;
@SuppressWarnings("unused")
public class ScrollPage {
	public String scrollPage(WebDriver webDriver, String objectName, String selector) {
		CTLogger.writeToLog("in ScrollPage");
		String status = "false";
		try {
			 WebElementDetails webElementDetails = new WebElementDetails();
			JavascriptExecutor js = (JavascriptExecutor) webDriver;
			List<WebElement> list = webElementDetails.getElements(webDriver, objectName, selector);
			WebElement we = list.get(0);

			Point scrollpoint = we.getLocation();
			CTLogger.writeToLog("Scrollpoint " + scrollpoint);
			webDriver.manage().timeouts().implicitlyWait(60, TimeUnit.MINUTES);
			((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0,"
					+ scrollpoint + ")");
			
			status = "true";
			return status;

		} catch (NoSuchElementException ns) {
			ns.printStackTrace();
			CTLogger.writeToLog("ScrollPage", "scrollPage()",
					"Scrolling Failed");
		
			return status;
		} catch (Exception ex) {
			ex.printStackTrace();
			CTLogger.writeToLog("ScrollPage", "scrollPage()",
					"Scrolling Failed");
			
			return status;
		}
	}
}
