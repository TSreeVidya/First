package com.accenture.aaft.selenium.library;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.propertyreader.PropertyFileReader2;
import com.accenture.aaft.report.ExtentTestManager;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Class is used to perform click action on UI
 *
 * @author Azeem.Mohammad
 *
 */
public class RiskScreening {

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
	public String Riskscreening(WebDriver webDriver, String inputValue,String ControlName, ExtentTest extentTest) {

		CTLogger.writeToLog("Riskscreening", " Riskscreening() ", " method called");
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		PropertyFileReader2 propertyFileReader2 = new PropertyFileReader2();
		String status = "false";
		String controlName="DeliveryTerms";
		String RiskScreeningButton="//input[@value='RiskScreening' and @type='button']";
		String DeliveryTermsobjectName="//select[contains(@name,'Delivery_Terms')]";
		String selector="xpath";
		String Nextbutton="//input[@value='Next' and @type='submit']";
		String SolarQuotingEntity="//label[contains(text(),'Solar Quoting Entity')]/following::select[1]";
		String ProposedPaymentSchedulePercentage ="//label[contains(@id,'Proposed_Payment_Schedule_Percentages_Milestones')]/following::div[1]/textarea[1]";
		String ProposedNetInvoice="//select[contains(@id,'Proposed_Net_Invoice_Payment_Terms')]";
		
		String[] inputArray = inputValue.split(",");
		
		String selection  = inputArray[1];
		
		try {
			SelectWindow sw=new SelectWindow();
			SelectFromList sf=new SelectFromList();
			Input input=new Input();
			
			String Value=propertyFileReader2.getValue("DeliveryTerms");
			System.out.println("Value---->:"+Value);
						
			if(ControlName.equalsIgnoreCase("Delivery Terms"))
			{
				webDriver.findElement(By.xpath(RiskScreeningButton)).click();
				sw.selectWindow(webDriver, "Customer Services  - Risk Screening1", extentTest, controlName);
				if (selection.equalsIgnoreCase("value")|| selection.equalsIgnoreCase("text")) {					
					sf.selectFromList(webDriver,DeliveryTermsobjectName,selector,inputValue,selection,extentTest,controlName);
				}					
				Thread.sleep(2000);					
				webDriver.findElement(By.xpath(Nextbutton)).click();
			}
						
			else if(ControlName.equalsIgnoreCase("Solar Quoting Entity"))
			{
				if (selection.equalsIgnoreCase("value")|| selection.equalsIgnoreCase("text")) {
					sf.selectFromList(webDriver,SolarQuotingEntity,selector,inputValue,selection,extentTest,controlName);
				}					
			}
			
			else if(ControlName.equalsIgnoreCase("Proposed Net Invoice"))
			{
				if (selection.equalsIgnoreCase("value")|| selection.equalsIgnoreCase("text")) {
					sf.selectFromList(webDriver,ProposedNetInvoice,selector,inputValue,selection,extentTest,controlName);
					input.enterText(webDriver, ProposedPaymentSchedulePercentage, selector, inputValue, extentTest, controlName);								
				}	
				Thread.sleep(2000);					
				webDriver.findElement(By.xpath(Nextbutton)).click();
			}
			
			
			else {
				propertyFileReader2.getValue("IMAGE_PATH");
				System.out.println("-------None---------");
			}

						
			status = "true";

		} catch (Exception e) {
			try {
				e.printStackTrace();
				CTLogger.writeToLog("Riskscreening", "Riskscreening()", " Object not found exeption thrown  --> path : ");
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
