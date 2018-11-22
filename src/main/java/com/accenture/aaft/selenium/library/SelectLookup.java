package com.accenture.aaft.selenium.library;

import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.report.ExtentTestManager;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Class is used to find and select account on UI
 *
 * @author Azeem.Mohammad.
 *
 */
public class SelectLookup {

	/**
	 * Method is used to find and select account on UI
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
	public String selectLookup(WebDriver webDriver, String inputValue, ExtentTest extentTest) {

		String[] inputArray = inputValue.split(",");
		String Lookupvalue = inputArray[0];
		System.out.println("lookupvalue----------->:" + Lookupvalue);
		String LookupIconxpath = inputArray[1] + "," + inputArray[2];

		System.out.println("Lookupxpath------->:" + LookupIconxpath);

		CTLogger.writeToLog("selectLookup", "selectLookup() ", "method called to click case - " + Lookupvalue);
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		String status = "false";

		try {

			WebElement element = webDriver.findElement(By.xpath(LookupIconxpath));
			((JavascriptExecutor) webDriver).executeScript("arguments[0].style.border='2px groove green'", element);
			element.click();

			System.out.println("Calling SelectLookup");

			Set<String> handles = webDriver.getWindowHandles();// To handle multiple windows
			String firstWinHandle = webDriver.getWindowHandle(); // To get your main window
			System.out.println("firstWinHandle user navigate - " + firstWinHandle);
			handles.remove(firstWinHandle);
			String winHandle = handles.iterator().next(); // To find popup window
			String secondWinHandle = null;
			if (winHandle != firstWinHandle) {
				secondWinHandle = winHandle;
			}
			webDriver.switchTo().window(secondWinHandle); // To switch to popup window
			System.out.println("Window title - " + webDriver.getTitle());

			webDriver.switchTo().frame("searchFrame");
			Thread.sleep(3000);
			webDriver.findElement(By.xpath("//input[@id='lksrch']")).sendKeys(Lookupvalue);
			webDriver.findElement(By.xpath("//input[@id='lksrch']")).sendKeys(Keys.ENTER);

			webDriver.switchTo().defaultContent();
			webDriver.switchTo().frame("resultsFrame");
			Thread.sleep(3000);
			webDriver.findElement(By.xpath("//table[@class='list']/tbody/tr[2]/th/a")).click();

			webDriver.switchTo().window(firstWinHandle);

		} catch (Exception e) {
			try {
				e.printStackTrace();
				CTLogger.writeToLog("selectLookup", "selectLookup()",
						"Object not found exeption thrown  --> path : " + Lookupvalue);
				status = "false";
			} finally {

				extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver,
						propertyFileReader.getValue("IMAGE_PATH") + Lookupvalue)));
				webDriver.quit();
				ExtentTestManager.setThreadStatus("f");
			}

		}

		return status;
	}

}
