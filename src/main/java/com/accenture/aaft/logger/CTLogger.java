package com.accenture.aaft.logger;

import java.net.URL;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.helpers.Loader;
import org.apache.log4j.xml.DOMConfigurator;

import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.selenium.driver.ExecuteScript;

/**
 * Class is used for logging
 *
 * @author vijay.venkatappa
 *
 */
@SuppressWarnings({ "unused", "deprecation" })

public class CTLogger {

  private static CTLogger ctLogger;
  public static Logger log = Logger.getLogger(CTLogger.class);

  /** Creates a new instance of AsyncLogging */
  public CTLogger() {

	URL url = Loader.getResource(System.getProperty("user.dir") +System.getProperty("file.separator") + "log4j.xml");
	DOMConfigurator.configure(System.getProperty("user.dir") + System.getProperty("file.separator")+ "log4j.xml");

  }

  /**
   * Method is used to write the logs
   * 
   * @param message - represents customized log message
   */
  public static void writeToLog(String message)

  {
	if (null == ctLogger) {
	  ctLogger = new CTLogger();
	}

	PropertyFileReader cfg = new PropertyFileReader();
	String logType = cfg.getValue("LogType");
	Date currDt = new Date();

	if (logType != null) {

	  if (logType.equalsIgnoreCase("file")) {
		log.debug(message);
	  } else if (logType.equalsIgnoreCase("console")) {
		System.out.println(currDt.getDay() + "  " + message);
	  }
	}

  }

  /**
   * Method is used to write log information with class name, method name and message details
   *
   * @param className - represents name of the class
   * @param methodName - represents name of the method
   * @param message - represents customized message
   */
  public static void writeToLog(String className, String methodName, String message)

  {
	if (null == ctLogger) {
	  ctLogger = new CTLogger();
	}
	PropertyFileReader cfg = new PropertyFileReader();
	String logType = cfg.getValue("LogType");
	Date currDt = new Date();
	if (logType != null) {
	  if (logType.equalsIgnoreCase("file")) {
		log.debug(currDt.getTime() + " : " + className + " : " + methodName + " : " + message);
	  } else if (logType.equalsIgnoreCase("console")) {
		System.out.println(currDt.getDate() + " : " + className + " : " + methodName + " : " + message);
	  }
	}

  }

  /**
   * Method is used to
   *
   * @param args - string arguments
   */
  public static void main(String args[]) {
	CTLogger.writeToLog("vijay");

  }
}
