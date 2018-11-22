package com.accenture.aaft.selenium.library;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.report.ExtentTestManager;
import com.accenture.aaft.selenium.library.utility.WaitForObjectExist;
import com.accenture.aaft.selenium.library.utility.WebElementDetails;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Class is used to perform click action on UI
 *
 * @author Azeem.Mohammad
 *
 */
public class CheckboxSelect {

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
	public String checkboxSelect(WebDriver webDriver, String objectName, String selector, String inputValue,
			ExtentTest extentTest, String controlName) {

		CTLogger.writeToLog("checkboxSelect", " checkboxSelect() ", " method called");
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		String status = "false";
		String webDriverString;
		
		System.out.println("inputValue-------------->:"+inputValue);

		//UAT_TS_01_1_OpportunityCreation1_ES
		try {

			webDriverString = webDriver.toString();
			WaitForObjectExist waitForObjectExist = new WaitForObjectExist();
			waitForObjectExist.waitForExistance(webDriver, objectName, selector);
			WebElementDetails webElementDetails = new WebElementDetails();
			WebElement txtField = null;

			String[] inputArray = inputValue.split(",");
			String CheckboxValue = inputArray[0];
			String Priority = " ";
			
			System.out.println("CheckboxValue-------------->:"+CheckboxValue);
			System.out.println("Priority-------------->:"+Priority);

			if (Priority.equals("Yes")) {
				if (!webDriver.findElement(By.xpath("//label[contains(text(),\"" + CheckboxValue + "\")]/following::input[1]")).isSelected()) {
					webDriver.findElement(By.xpath("//label[contains(text(),\"" + CheckboxValue + "\")]/following::input[1]")).click();
				}
			} else if (Priority.equals("No")) {
				System.out.println(CheckboxValue + " Selected Check-box is False");
			} else {
				
				if (webDriverString.toLowerCase().contains("androiddriver") || webDriverString.toLowerCase().contains("iosdriver")) {
					txtField = webElementDetails.getElement(webDriver, objectName, selector);
				  } else {
					List<WebElement> listElements = webElementDetails.getElements(webDriver, objectName, selector);
					System.out.println("webDriver-------->:"+webDriver);
					System.out.println("objectName-------->:"+objectName);
					System.out.println("selector-------->:"+selector);
					txtField = listElements.get(0);
				  }
				if (txtField.isDisplayed())
				

				txtField.click();
			}

			status = "true";

		} catch (Exception e) {
			try {
				e.printStackTrace();
				CTLogger.writeToLog("checkboxSelect", "checkboxSelect()"," Object not found exception thrown  --> path : ");
				status = "false";
			} finally {

				extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH"))));
				webDriver.quit();
				ExtentTestManager.setThreadStatus("f");
			}
		}
		return status;
	}
}
