package com.accenture.runner.utility;


import java.util.LinkedList;
import java.util.Random;

import org.junit.After;
import org.openqa.selenium.WebDriver;

import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.SeleniumConfigXmlReader;
import com.accenture.aaft.report.ExtentManager;
import com.accenture.aaft.report.ExtentTestManager;
import com.accenture.aaft.selenium.driver.ExecuteScript;
import com.accenture.aaft.selenium.library.utility.RestCall;

/**
 * Class is used for selenium test
 *
 * @author vijay.venkatappa
 *
 */

public class ExecuteScriptRunner {
	/**
     * Represents the browser to be used as part of the test run.
     */
    private String browser;
    /**
     * Represents the operating system to be used as part of the test run.
     */
    private  String os;
    /**
     * Represents the version of the browser to be used as part of the test run.
     */
    private  String version;
 
    /**
     * Represents the scriptName
     */
    private String scriptName;
    
    /**
     * Represents the executionType
     */
    private String executionType;
    /**
     * Represents the className
     */
    private String className;
    
	  
	 public ExecuteScriptRunner(String scriptName,String className,String executionType){
	try {
		 
		this.scriptName = scriptName;
		this.className = className;
		this.executionType = executionType;
		setUp();
			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	  }
	   
	   public void setUp() throws Exception {

	    	SeleniumConfigXmlReader seleniunConfigXmlReader = new SeleniumConfigXmlReader();	
	    	LinkedList<String[]> listArray = seleniunConfigXmlReader.getBrowserList(executionType);
	    	
	    	Random random = new Random();
	    	String browserConfig[] = listArray.get(random.nextInt(listArray.size()));
			os = browserConfig[0];
			version = browserConfig[1];
			browser = browserConfig[2];
			
			System.out.println("OS - "+browserConfig[0] + "--version - "+browserConfig[1] + "..browser - "+browserConfig[2]); 
			
	    	ExtentManager.getExtentManager();
	    	
	    	ExtentTestManager.startTest("Selenium Test - Solar Turbines <b>"
	    			+os+"-"+browser+"-"+version+"</b> : " + scriptName, "Online Store - "+scriptName, className);
	    	
	    }

	  /**
	   * Method is used to execute script
	   *
	   */
	  
	  public void executeTest() {
		ExecuteScript executeScript = new ExecuteScript();
		executeScript.executeScript(scriptName, os, version, browser,executionType);
				
	  }
	  /**
	   * Method is used to execute script
	   *
	   */
	  
	  public void executeTestWithoutBrowser() {
		CTLogger.writeToLog(scriptName+" : execution started");
		ExecuteScript executeScript = new ExecuteScript();
		executeScript.executeTestWithoutBrowser(scriptName);
				
	  }

	  /**
	     * Closes the {@link WebDriver} session.
	     *
	     * @throws Exception
	     */
	    @After
	    public void tearDown() throws Exception {

	        ExtentManager.getReporter().endTest(ExtentTestManager.getTest());
	  		ExtentManager.getReporter().flush();
	  		CTLogger.writeToLog(scriptName+" : execution completed");
	  		
	  		String status = ExtentTestManager.getThreadStatus();
			if (status == null || status.trim().equals("")) {
				status = "p";
			}
			RestCall rc = new RestCall();
			rc.simpleGet(ExtentTestManager.getTestCaseNumber(), status);
		  	  		
	    }
  
		
}
