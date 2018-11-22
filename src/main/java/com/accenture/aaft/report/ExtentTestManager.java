package com.accenture.aaft.report;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

/**
 * Class is used to generate the extent report
 *
 * @author vijay.venkatappa
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ExtentTestManager {

  //Added for Live reporting
  public static int API_TC = 1;
  public static int PLT_TC = 1;
  public static Map<String, Queue<String>> threadExecutionInfoMap = new HashMap<>();
  public static ConcurrentHashMap liveReportingMap = new ConcurrentHashMap();
  public static ConcurrentHashMap threadStatusMap = new ConcurrentHashMap();
  
  public static Map extentTestMap = new HashMap();
  static ExtentReports extent = ExtentManager.getReporter();
 
  /**
   * Method is used to get test name
   *
   * @return ExtentTest
   */
  public static synchronized ExtentTest getTest() {
	return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
  }

  /**
   * Method is used to end the test
   *
   */
  public static synchronized void endTest() {
	extent.endTest((ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId())));
  }

  /**
   * Method is used to start the test
   *
   * @param testName - test name
   * @return ExtentTest
   */
  public static synchronized ExtentTest startTest(String testName) {
	return startTest(testName, "");
  }

  /**
   * Method is used to start test
   *
   * @param testName - represents test name
   * @param desc - represents test description
   * @return ExtentTest
   */
  public static synchronized ExtentTest startTest(String testName, String desc) {
	ExtentTest test = extent.startTest(testName, desc);
	extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);

	return test;
  }
  
  /**
   * Method is used to start test
   *
   * @param testName - represents test name
   * @param desc - represents test description
   * @return ExtentTest
   */
  public static synchronized ExtentTest startTest(String testName, String desc, String className) {
	ExtentTest test = extent.startTest(testName, desc);
	extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
	
	String slno = null;
	if ("api_test".equals(className))  {
		slno = "ap_" + String.valueOf(API_TC);
		API_TC++;
	} else if ("plt_test".equals(className))  {
		slno = "pl_" + String.valueOf(PLT_TC);
		PLT_TC++;
	} else {
		Queue q = threadExecutionInfoMap.get(className);
		if (q != null) {
			slno = (String) q.poll();
		}
	}
	
	PropertyFileReader propertyFileReader = new PropertyFileReader();
	String liveReport =  propertyFileReader.getValue("LIVE_REPORTING");
	if(null != liveReport && liveReport.equalsIgnoreCase("Yes")){	
		liveReportingMap.put((int) (long) (Thread.currentThread().getId()), slno);	
	}
	
	return test;
  }
  
  
  public static void setThreadStatus(String status) {
	  threadStatusMap.put((int) (long) (Thread.currentThread().getId()), status);
  }
  
  public static String getThreadStatus() {
	  return (String) threadStatusMap.get((int) (long) (Thread.currentThread().getId()));
  }
  
  public static String getTestCaseNumber() {
	  return (String) liveReportingMap.get((int) (long) (Thread.currentThread().getId()));
  }

  /**
   * Method is used to capture the screen
   *
   * @param driver - represents WebDriver instance
   * @param imagesPath - represents image path
   * @return .png image
   */
  public static String captureScreen(WebDriver driver, String imagesPath) {
	System.out.println("ImagesPath - " + imagesPath);
	CTLogger.writeToLog("ExtentTestManager", "CaptureScreen()", " method called");
	TakesScreenshot oScn = (TakesScreenshot) driver;
	File oScnShot = oScn.getScreenshotAs(OutputType.FILE);
	File oDest = new File(imagesPath + ".png"); // .jpg commented as it is not displayed in IE
	try {
	  FileUtils.copyFile(oScnShot, oDest);
	} catch (Exception e) {
	  System.out.println(e.getMessage());
	}
	
	return imagesPath + ".png";
  }
}