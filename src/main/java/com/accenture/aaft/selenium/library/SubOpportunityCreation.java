package com.accenture.aaft.selenium.library;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
public class SubOpportunityCreation {

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
	public String subOpportunityCreation(WebDriver webDriver, String inputValue, ExtentTest extentTest) {

		CTLogger.writeToLog("subOpportunityCreation", " subOpportunityCreation() ", " method called");
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		String status = "false";

		try {
			String[] inputValueList =inputValue.split(",");	
	
			String ExactValuesList[] = {"Turbomachinery","Field Service","Compressor OH","Engine OH","Construction Services","Ext/Exp Warranty","Tech Training","Service Parts/Tooling","Service Contracts","PRU","Engine OH/Spare","Compressor OH/Spare","Energy Storage"};

			for (String Slist : inputValueList) {
				for (String Clist : ExactValuesList) {
					if(Slist.contains(Clist))
					{
						if(!webDriver.findElement(By.xpath("//label[contains(text(),\""+Clist+"\")]/following::input[@type=\"checkbox\"][1]")).isSelected())
						{
							webDriver.findElement(By.xpath("//label[contains(text(),\""+Clist+"\")]/following::input[@type=\"checkbox\"][1]")).click();
						}	
					}
				}
			}
			status = "true";
			
		} catch (Exception e) {
			try {
				e.printStackTrace();
				CTLogger.writeToLog("subOpportunityCreation", "subOpportunityCreation()", " Object not found exeption thrown  --> path : ");
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
