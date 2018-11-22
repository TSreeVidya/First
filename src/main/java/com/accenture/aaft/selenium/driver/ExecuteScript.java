package com.accenture.aaft.selenium.driver;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.accenture.aaft.excel.utility.ExcelTestDataReader;
import com.accenture.aaft.excel.utility.ExcelTestScriptReader;
import com.accenture.aaft.excel.utility.ObjectMapReader;
import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.report.ExtentManager;
import com.accenture.aaft.report.ExtentTestManager;
import com.accenture.aaft.selenium.library.CloseBrowser;
import com.accenture.aaft.selenium.library.utility.ComponentExecutor;
import com.accenture.aaft.vo.ExcelTestDataVO;
import com.accenture.aaft.vo.ExcelTestScriptVO;
import com.accenture.aaft.vo.ObjectMapVO;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Class is used to execute the excel based script
 *
 * @author vijay.venkatappa
 *
 */
@SuppressWarnings({ "unused" })
public class ExecuteScript {
	
  ExtentTest extentTest;
  PropertyFileReader propertyFileReader = new PropertyFileReader();
  static Map<String,String> hmTempStore = new HashMap<String,String>();
  public static boolean captureScreenPass = false;
  public static String strScriptName = null;
  String fileNameTempStore = "SeleniumTempStore.tmp";
  String delimiter = "!--SPLIT--!";
  String storeToFile = "false";
  
  public WebDriverWait wait;
 
  int countComp = 0;
  int countKeyComp = 0;


	/**
	 * Method is used to read temporary store values from file into memory
	 *
	 */
	public void readTempFileStore() {
	  try {
		File fileTempStore = new File(fileNameTempStore);
		if (!fileTempStore.exists()) return;
		FileReader frTempStore = new FileReader(fileTempStore);
		BufferedReader br = new BufferedReader(frTempStore);
		String line;
		String[] arrayLine;
		while((line = br.readLine()) != null) {
		  arrayLine = line.split(delimiter);
		  if (arrayLine.length == 2)
		    hmTempStore.put(arrayLine[0], arrayLine[1]);
		}
		br.close();
		frTempStore.close();
	  }
	  catch (Exception e) {
	  }
	}

	/**
	 * Method is used to write temporary store values from memory to file
	 *
	 */
	public void writeTempFileStore() {
	  try {
		File fileTempStore = new File(fileNameTempStore);
		FileWriter fwTempStore = new FileWriter(fileTempStore);
		BufferedWriter bw = new BufferedWriter(fwTempStore);
		for (String key: hmTempStore.keySet()) {
		  bw.write (key + delimiter + hmTempStore.get(key));
		  bw.newLine();
		}
		bw.close();
		fwTempStore.close();
	  }
	  catch (Exception e) {
	  }
	}

	/**
	 * Method is used to execute the excel based script
	 *
	 * @param scriptName
	 *            - represents script name
	 * @param browser
	 *            - represents browser name
	 */
	public String executeScript(String scriptName, String os, String version, String browser, String executionType) {
		WebDriver webDriver = null;
		String ssesionId = "";
		if (executionType.equalsIgnoreCase("local")) {
			webDriver = SeleniumDriver.getWebDriver(os, version, browser);
			
		} else {
			webDriver = SeleniumDriver.getRemoteWebDriver(executionType, os, version, browser, scriptName);
			ssesionId = (((RemoteWebDriver) webDriver).getSessionId()).toString();
			
		}
		execute(webDriver, scriptName);
		
		return ssesionId;		
	}
	
	/**
	 * Method is used to execute the excel based script
	 *
	 * @param scriptName
	 *            - represents script name
	 * @param browser
	 *            - represents browser name
	 */
	public String executeScript(String scriptName, String os, String version, String browser, String executionType, String device, String deviceOrientation, String appiumVersion, String app) {
		WebDriver webDriver = null;
		String ssesionId = "";

		webDriver = SeleniumDriver.getRemoteWebDriver(executionType, os, version, browser, device, deviceOrientation, appiumVersion, app, scriptName);
		ssesionId = (((RemoteWebDriver) webDriver).getSessionId()).toString();			
		
		execute(webDriver, scriptName);
		return ssesionId;
	}
 

 
  /**
   * Method is used to call ExecuteScriptForIncreaseReportCount class to increase the report count
   *
   * @param scriptName - represents script name
   */
  public void executeTestWithoutBrowser(String scriptName) {
	  ExecuteScriptForIncreaseReportCount executeScriptForIncreaseReportCount = new ExecuteScriptForIncreaseReportCount();
	  executeScriptForIncreaseReportCount.execute(scriptName);
  }
  /**
   * Method is used to execute steps/actions
   *
   * @param webDriver - represents WebDriver
   * @param scriptName - represents script name
   */
  private void execute(WebDriver webDriver, String scriptName) {
	ExtentTest testReporter;
	ExtentReports reporter;
	ExcelTestDataReader dataReader = new ExcelTestDataReader();
	LinkedHashMap<String, List<ExcelTestDataVO>> map = dataReader.readTestData(scriptName);
	storeToFile = propertyFileReader.getValue("STORE_TO_FILE");
	captureScreenPass = new Boolean(propertyFileReader.getValue("CAPTURE_SCREEN_PASS"));
	strScriptName = scriptName;

	if(null != storeToFile && storeToFile.equalsIgnoreCase("true")) {
	  readTempFileStore();
	}

	Set<String> set = map.keySet();
	Iterator<String> it = set.iterator();
	int count = 0;
	String objectMapFile = "";

	while (it.hasNext()) {

	  LinkedHashMap<String, ObjectMapVO> objecthashmap = null;
	  String key = it.next();

	  extentTest = ExtentTestManager.getTest();

	  List<ExcelTestDataVO> voList = (List<ExcelTestDataVO>) map.get(key);

	  KeywordExecutor keywordExecutor = new KeywordExecutor();

	  ExcelTestScriptReader testScriptReader = new ExcelTestScriptReader();

	  List<ExcelTestScriptVO> listScriptVOs = testScriptReader.readWorkbook(scriptName);

	  for (ExcelTestScriptVO testVO : listScriptVOs) {
		try {
		 
		  if (null == webDriver) {
			break;
		  }

		} catch (TimeoutException timeoutException) {
		  System.out.println("WEB DRIVER TimeoutException -- " + webDriver);
		  if (webDriver != null) {
			webDriver.close();
		  }
		}

		String keyword = testVO.getKeyword().trim();
		String controlName = testVO.getControlName().trim();

		if (controlName.contains(".xls")) {
		  if (objectMapFile.equals("")) {
			ObjectMapReader objectMapReader = new ObjectMapReader();
			objecthashmap = objectMapReader.readObjectMap(controlName);
		  }
		}

		String param1 = testVO.getParam1().trim();
		String param2 = testVO.getParam2().trim();
		String param3 = testVO.getParam3().trim();
		String condition = testVO.getCondition().trim();

		String testStepNo = testVO.getTestStepNo();
		String testTtepDetails = testVO.getTestStepDetails();
		String screen = testVO.getScreen();
		String mainWindow = testVO.getMainWindow();
		String inputValue = "";
		for (ExcelTestDataVO vo : voList) {

		  if (vo.getName().equals(param1)) {
			inputValue = vo.getValue();
		  }
		  if (vo.getName().equals(param2)) {
			inputValue = inputValue + "," + vo.getValue();
		  }
		  if (vo.getName().equals(param3)) {
			inputValue = inputValue + "," + vo.getValue();
		  }
		}
		String objectName = "";
		String selector = "";
		if (keyword.equalsIgnoreCase("LAUNCH")) {

		} else if (keyword.equalsIgnoreCase("WAIT")) {

		} else if (keyword.equalsIgnoreCase("SELECTWINDOW")) {

		} 
		else if (keyword.equalsIgnoreCase("SUBOPPORTUNITYCREATION")) {

		}
		else if (keyword.equalsIgnoreCase("QUOTECREATION")) {

		}
		else if (keyword.equalsIgnoreCase("CHECKBOXSELECT")) {

		}
		else if (keyword.equalsIgnoreCase("SWITCHTOPARENTWINDOW")) {

		} else if (keyword.equalsIgnoreCase("CHANGEUSER")) {

		} else if (keyword.equalsIgnoreCase("FINDANDCLICKSFOPPORTUNITY")) {

		} else if (keyword.equalsIgnoreCase("FINDANDCLICKSFCASE")) {

		} else if (keyword.equalsIgnoreCase("FINDANDCLICKSFACCOUNT")) {

		} else if (keyword.equalsIgnoreCase("FINDANDCLICKSFBRE")) {

		} else if (keyword.equalsIgnoreCase("FINDANDCLICKSFCMMNAME")) {

		} else if (keyword.equalsIgnoreCase("CLICK2")) {

		} 
		else if (keyword.equalsIgnoreCase("MULTIPURPOSE")) {

		} 
		else if (keyword.equalsIgnoreCase("INPUTWN")) {

		} 
		else if (keyword.equalsIgnoreCase("SHOWLINK")) {

		} 
		else if (keyword.equalsIgnoreCase("RISKSCREENING")) {

		} 
		
		else if (keyword.equalsIgnoreCase("ISALERTPRESENTACCEPT")) {

		} 
		else if (keyword.equalsIgnoreCase("SELECTLOOKUP")) {

		}
		
		else if (keyword.equalsIgnoreCase("LOGINSF")) {

		}
		else if (keyword.equalsIgnoreCase("LOGOUT")) {
			

				 keywordExecutor.executeKeyword(webDriver, keyword, controlName, param1, param2, param3, condition, testStepNo, testTtepDetails, screen, mainWindow, inputValue, extentTest, objectName,
					      selector, hmTempStore);
				
			

		}
		else if (keyword.equalsIgnoreCase("LAUNCHAPP")) {
		  continue;
		} else if (keyword.equalsIgnoreCase("MATCHELEMENTCOUNT")) {
			String[] controlNames = controlName.split(",");
			ObjectMapVO objectMapVO = objecthashmap.get(controlNames[0]);
			objectName = objectMapVO.getObjectPath();
			selector = objectMapVO.getSelector();
			objectMapVO = objecthashmap.get(controlNames[1]);
			objectName += delimiter + objectMapVO.getObjectPath();
		} else if (keyword.equalsIgnoreCase("STORE")) {
			ObjectMapVO objectMapVO = objecthashmap.get(controlName);
			objectName = objectMapVO.getObjectPath();
			selector = objectMapVO.getSelector();
		} else {
		  if (!keyword.equalsIgnoreCase("COMPONENT")) {

			ObjectMapVO objectMapVO = objecthashmap.get(controlName);
			objectName = objectMapVO.getObjectPath();
			selector = objectMapVO.getSelector();
		  }

		}

		if (keyword.equalsIgnoreCase("COMPONENT")) {

		  ComponentExecutor componentExecutor = new ComponentExecutor();
		  componentExecutor.componentExecute(webDriver, inputValue, extentTest, objecthashmap, voList);
		} else if (keyword.equalsIgnoreCase("STORE")) {
			  keywordExecutor.executeKeyword(webDriver, keyword, controlName, param1, param2, param3, condition, testStepNo, testTtepDetails, screen, mainWindow, inputValue, extentTest, objectName,
			      selector, hmTempStore);
		} else if (keyword.equalsIgnoreCase("USEFROMSTORE")) {
			  keywordExecutor.executeKeyword(webDriver, keyword, controlName, param1, param2, param3, condition, testStepNo, testTtepDetails, screen, mainWindow, inputValue, extentTest, objectName,
			      selector, hmTempStore);
		} else if (keyword.equalsIgnoreCase("COMPARETOSTORE")) {
			  keywordExecutor.executeKeyword(webDriver, keyword, controlName, param1, param2, param3, condition, testStepNo, testTtepDetails, screen, mainWindow, inputValue, extentTest, objectName,
			      selector, hmTempStore);
		} else if (keyword.equalsIgnoreCase("FINDANDCLICKSFOPPORTUNITY")) {
			  keywordExecutor.executeKeyword(webDriver, keyword, controlName, param1, param2, param3, condition, testStepNo, testTtepDetails, screen, mainWindow, inputValue, extentTest, objectName,
			      selector, hmTempStore);
		}
	  else if (keyword.equalsIgnoreCase("SELECTLOOKUP")) {
		  keywordExecutor.executeKeyword(webDriver, keyword, controlName, param1, param2, param3, condition, testStepNo, testTtepDetails, screen, mainWindow, inputValue, extentTest, objectName,
		      selector, hmTempStore);
		} else if (keyword.equalsIgnoreCase("FINDANDCLICKSFCASE")) {
			  keywordExecutor.executeKeyword(webDriver, keyword, controlName, param1, param2, param3, condition, testStepNo, testTtepDetails, screen, mainWindow, inputValue, extentTest, objectName,
			      selector, hmTempStore);
		} else if (keyword.equalsIgnoreCase("FINDANDCLICKSFACCOUNT")) {
			  keywordExecutor.executeKeyword(webDriver, keyword, controlName, param1, param2, param3, condition, testStepNo, testTtepDetails, screen, mainWindow, inputValue, extentTest, objectName,
			      selector, hmTempStore);
		} else if (keyword.equalsIgnoreCase("FINDANDCLICKSFBRE")) {
				  keywordExecutor.executeKeyword(webDriver, keyword, controlName, param1, param2, param3, condition, testStepNo, testTtepDetails, screen, mainWindow, inputValue, extentTest, objectName,
				      selector, hmTempStore);
		} else if (keyword.equalsIgnoreCase("FINDANDCLICKSFCMMNAME")) {
				  keywordExecutor.executeKeyword(webDriver, keyword, controlName, param1, param2, param3, condition, testStepNo, testTtepDetails, screen, mainWindow, inputValue, extentTest, objectName,
				      selector, hmTempStore);
		} else {
		      keywordExecutor.executeKeyword(webDriver, keyword, controlName, param1, param2, param3, condition, testStepNo, testTtepDetails, screen, mainWindow, inputValue, extentTest, objectName,
		          selector);
		}
	  }
	  count++;

	  String webDriverString = webDriver.toString();

	  if (webDriverString != null && webDriverString.toLowerCase().indexOf("androiddriver") < 0 && webDriverString.toLowerCase().indexOf("iosdriver") < 0
	      && webDriverString.toLowerCase().indexOf("remotewebdriver") < 0 && webDriverString.toLowerCase().indexOf("ios") < 0 && webDriverString.toLowerCase().indexOf("iphone") < 0
	      && webDriverString.toLowerCase().indexOf("ipad") < 0 && webDriverString.toLowerCase().indexOf("android") < 0) {
		CloseBrowser closeBrowser = new CloseBrowser();
		String result = closeBrowser.closeBrowser(webDriver);
		if (result.equals("true")) {
		  extentTest.log(LogStatus.INFO, "Browser closed");
		} else {
		  extentTest.log(LogStatus.FAIL, "Browser not closed");
		}
	  }
	}
	try {
		Thread.sleep(5000);
	    try {
			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} catch (InterruptedException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	webDriver.quit();
	CTLogger.writeToLog("DriverQuit", "driverQuit() ", " driver quitted");
	extentTest.log(LogStatus.INFO, "Driver Quitted");
	
/*	try {
	    Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
	//System.out.println("It's successfully killed the chromedriver");
	try {
		Thread.sleep(5000);
	} catch (InterruptedException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}*/
	

	if(null != storeToFile && storeToFile.equalsIgnoreCase("true")) {
	  writeTempFileStore();
	}

	String parallelExecution = propertyFileReader.getValue("PARALLEL_EXECUTION");
	if(null != parallelExecution && !parallelExecution.equalsIgnoreCase("Yes")) {
		try {
			String browserName = propertyFileReader.getValue("BROWSER_NAME");
			if(browserName.equalsIgnoreCase("chrome")){
				Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");
			}
			else if(browserName.equalsIgnoreCase("ie") || browserName.equalsIgnoreCase("internet")){
				Runtime.getRuntime().exec("taskkill /F /IM iedriverserver.exe");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
  }


  /**
   * Main method
   *
   * @param args - string arguments
   */
  public static void main(String[] args) {


  }

}
