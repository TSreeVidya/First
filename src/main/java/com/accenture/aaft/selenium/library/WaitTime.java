package com.accenture.aaft.selenium.library;

import com.accenture.aaft.logger.CTLogger;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Class is used to pause execution till given time
 *
 * @author vijay.venkatappa
 *
 */
public class WaitTime {

  /**
   * Method is used to pause execution till given time
   *
   * @param time - represents time in seconds
   * @param extentTest - represents ExtentTest
   * @param controlName - represents control name
   * @return status
   */
  public String waitTime(String time, ExtentTest extentTest, String controlName) {

	String status = "false";

	int sleepTime = Integer.parseInt(time);
	sleepTime = sleepTime * 1000;

	try {
	  Thread.sleep(Long.parseLong(new Integer(sleepTime).toString()));
	  CTLogger.writeToLog("WaitTime", "waitTime() ", "called to page or object load ::" + sleepTime);
	  status = "true";
	  extentTest.log(LogStatus.PASS, " wait successful ");

	} catch (Exception e) {
	  e.printStackTrace();
	  CTLogger.writeToLog("WaitTime", "waitTime() ", e.getMessage());
	  return "false";
	}

	return status;
  }

  /**
   * Method is used to pause execution till given time
   *
   * @param time - represents time in seconds
   * @return status
   */
  public String waitTime(String time) {

	String status = "false";

	int sleepTime = Integer.parseInt(time);
	sleepTime = sleepTime * 1000;

	try {
	  Thread.sleep(Long.parseLong(new Integer(sleepTime).toString()));
	  CTLogger.writeToLog("WaitTime", "waitTime() ", "called to page or object load ::" + sleepTime);
	  status = "true";
	} catch (Exception e) {
	  e.printStackTrace();
	  CTLogger.writeToLog("WaitTime", "waitTime() ", e.getMessage());
	  return "false";
	}

	return status;
  }
}