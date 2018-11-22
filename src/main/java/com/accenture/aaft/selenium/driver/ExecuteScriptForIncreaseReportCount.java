package com.accenture.aaft.selenium.driver;

import java.util.List;

import com.accenture.aaft.excel.utility.ExcelTestScriptReader;
import com.accenture.aaft.report.ExtentTestManager;
import com.accenture.aaft.vo.ExcelTestScriptVO;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Class is used to execute script to increase the report count
 * 
 * @author vijay.venkatappa
 *
 */
public class ExecuteScriptForIncreaseReportCount {
	ExtentTest extentTest;
	
	String vijay;
	
	/**
	 * This method is used to call script execution
	 * 
	 * @param scriptName - represents script name
	 */
	public void execute(String scriptName){
		extentTest = ExtentTestManager.getTest();
		ExcelTestScriptReader testScriptReader = new ExcelTestScriptReader();

		  List<ExcelTestScriptVO> listScriptVOs = testScriptReader.readWorkbook(scriptName);

		  for (ExcelTestScriptVO testVO : listScriptVOs) {
			  String keyword = testVO.getKeyword().trim();
			  String controlName = testVO.getControlName().trim();
			  
			  if(keyword.equals("LAUNCH")){
				  extentTest.log(LogStatus.PASS, "URL: " + "URL: http://localhost:8780/konakart/LogIn.action" + " launched");
				  
			  }	  		  
			 
				else if (keyword.equalsIgnoreCase("INPUT")) {
					extentTest.log(LogStatus.PASS, " entered" + " text into "+controlName);
					  
				}

				else if (keyword.equalsIgnoreCase("CLICK")) {
					extentTest.log(LogStatus.PASS, " clicked on "+controlName);
				}

				else if (keyword.equalsIgnoreCase("WAIT")) {
					extentTest.log(LogStatus.PASS, "wait successful");
				}

				else if (keyword.equalsIgnoreCase("CLICKLINK")) {
					extentTest.log(LogStatus.PASS, " clicked on the link " + controlName);
				}

				else if (keyword.equalsIgnoreCase("SELECTFRAME")) {
					extentTest.log(LogStatus.PASS, " selected frame  " + controlName);
				}
			  
				else if (keyword.equalsIgnoreCase("SELECTWINDOW")) {
					extentTest.log(LogStatus.PASS, " selected window " + controlName);
				} 
			  
				else if (keyword.equalsIgnoreCase("MOUSEOVER")) {
					extentTest.log(LogStatus.PASS, " mouse over on " + controlName);
				 
				} else if (keyword.equalsIgnoreCase("VERIFYTEXT")) {
					extentTest.log(LogStatus.PASS, " verified the text & entered into " + controlName);
				}
			  
				else if (keyword.equalsIgnoreCase("SELECTFROMLIST")) {
					 extentTest.log(LogStatus.PASS, "selected  option from  dropdown ");
				}
				else if (keyword.equalsIgnoreCase("WINDOWACTIVATE")) {
					extentTest.log(LogStatus.PASS, "top window activate");
				}
				else if (keyword.equalsIgnoreCase("COMPONENT")) {
					extentTest.log(LogStatus.PASS, "component executed successfully");
				}
				else if (keyword.equalsIgnoreCase("OBJECTEXISTS") ) {
					extentTest.log(LogStatus.PASS, "object  exist in UI "+controlName);
				}
				else if (keyword.equalsIgnoreCase("OBJECTEXIST") ) {
					extentTest.log(LogStatus.FAIL, "Test Failed");
					ExtentTestManager.setThreadStatus("f");
				}
		
		  }
	}

}
