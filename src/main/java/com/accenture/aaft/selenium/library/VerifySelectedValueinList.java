package com.accenture.aaft.selenium.library;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.report.ExtentTestManager;
import com.accenture.aaft.selenium.library.utility.WaitForObjectExist;
import com.accenture.aaft.selenium.library.utility.WebElementDetails;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Class is used to verify the user given text with list selected value
 *
 * @author varija.karampudi
 *
 */
public class VerifySelectedValueinList {

  /**
   * Method is used to verify the user given text with browser element value
   *
   * @param webDriver - represents WebDriver
   * @param objectName - represents object name
   * @param selector - represents selector type
   * @param inputValue - represents input value
   * @param extentTest - represents ExtentTest
   * @param controlName - represents control name
   * @return status
   */
  public String verifySelectedValueinList(WebDriver webDriver, String objectName, String selector, String inputValue, ExtentTest extentTest, String controlName) {

	CTLogger.writeToLog("verifySelectedValueinList", "verifySelectedValueinList()", " method called");
	PropertyFileReader propertyFileReader = new PropertyFileReader();
	String status = "false";
	String value = "";

	try {
	  WaitForObjectExist waitForObjectExist = new WaitForObjectExist();
	  waitForObjectExist.waitForExistance(webDriver, objectName, selector);
	  
	  WebElementDetails webElementDetails = new WebElementDetails();
	  List<WebElement> listElements = webElementDetails.getElements(webDriver, objectName, selector);

	  WebElement selectElement = listElements.get(0);
	  if (selectElement.getTagName().equalsIgnoreCase("select")) {
		  Select select = new Select(selectElement);
		  value = select.getFirstSelectedOption().getText().trim();
		  String actualValue="";
		  String[] inputValueList = inputValue.split(",");
		  if(inputValueList.length>1){
			  actualValue = inputValueList[0].trim();
			  String isExactMatch = inputValueList[1];
			  if(isExactMatch.equalsIgnoreCase("Yes")){
				  if (actualValue.equals(value)){
					status = "true";  
				  }
				  else {
					  status="false";
				  }
				  
			  }else{
				  if (value.contains(actualValue)){
					status = "true";  
				  }
				  else {
					status="false";
				  }
			  }
		  }else{
			  if (inputValue.equals(value)){
				status = "true";  
			  }
			  else {
				status="false";
			  }		  
		  }
	  }
	  else
	  {
		  status="false";
		  CTLogger.writeToLog("verifySelectedValueinList", "verifySelectedValueinList()", " Object type is not select");
	  }
	 	  
	  if (status.equals("true")) {
		extentTest.log(LogStatus.PASS, " verified selected value - " + inputValue + " in " + controlName);
	  } else {
		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
		ExtentTestManager.setThreadStatus("f");
	  }
	} catch (Exception ex) {
	  try {
		ex.printStackTrace();
		String err[] = ex.getMessage().split("\n");
		CTLogger.writeToLog("verifySelectedValueinList", "verifySelectedValueinList()", " Object not found exeption thrown");
		status = "Exception " + err[0].replaceAll("'", "") + " Occurred";
	  } finally {

		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
		ExtentTestManager.setThreadStatus("f");
		webDriver.quit();
	  }
	}
	return status;
  }
}
