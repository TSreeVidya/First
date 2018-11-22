package com.accenture.aaft.selenium.library;

import java.util.List;
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
 * Class is used to verify the user given text with browser element value
 *
 * @author vijay.venkatappa
 *
 */
public class VerifyText {

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
  public String verifyText(WebDriver webDriver, String objectName, String selector, String inputValue, ExtentTest extentTest, String controlName) {

	CTLogger.writeToLog("verifyText", "verifyText()", " method called");
	PropertyFileReader propertyFileReader = new PropertyFileReader();
	String status = "false";

	try {
	  WaitForObjectExist waitForObjectExist = new WaitForObjectExist();
	  waitForObjectExist.waitForExistance(webDriver, objectName, selector);
	  ;

	  WebElementDetails webElementDetails = new WebElementDetails();
	  List<WebElement> listElements = webElementDetails.getElements(webDriver, objectName, selector);

	  // WebElement txtField = listElements.get(0);
	  WebElement element = null;
	  String value = "";

	  if (listElements != null && listElements.size() > 0) {
		element = listElements.get(0);
		if ("input".equalsIgnoreCase(element.getTagName())) {
		  value = element.getAttribute("value");
		} else {
		  value = element.getText();
		}
	  }
	  
	  String actualValue="";
	  String[] inputValueList = inputValue.split(",");
	  if(inputValueList.length>1){
		  actualValue = inputValueList[0];
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
				  System.out.println("Value Given------------>:"+actualValue);
				  System.out.println("Value Exists----------->:"+value);
				  status = "true";  
				  }
				  else {
					  System.out.println("Value Given------------>:"+actualValue);
					  System.out.println("Value Exists----------->:"+value);
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
	  
/*	  if (value != null && inputValue != null) {
		String[] actual = value.split(" ");
		String[] expected = inputValue.split(" ");
		List<String> actualString = new ArrayList<String>();
		List<String> expectedString = new ArrayList<String>();

		for (String str : actual) {
		  if (!"".equals(str.trim())) {
			actualString.add(str);
		  }
		}

		for (String str : expected) {
		  if (!"".equals(str.trim())) {
			expectedString.add(str);
		  }
		}

		if (actualString.toString().equals(expectedString.toString())) {
		  status = "true";
		}
	  }*/

	  if (status.equals("true")) {
		extentTest.log(LogStatus.PASS, " verified text '" + inputValueList[0] + "' successfully in " + controlName);
	  } else {
		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
		ExtentTestManager.setThreadStatus("f");
	  }
 
	} catch (Exception ex) {
	  try {
		ex.printStackTrace();
		String err[] = ex.getMessage().split("\n");
		CTLogger.writeToLog("verifyText", "verifyText()", " Object not found exeption thrown");
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
