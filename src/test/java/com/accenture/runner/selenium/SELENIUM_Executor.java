package com.accenture.runner.selenium;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.report.ExtentManager;
import com.accenture.runner.utility.ListAllRunner;

/**
 * Class is used to execute multiple selenium based tests in parallel
 *
 * @author Vijay.
 *
 */
@SuppressWarnings({ "rawtypes" })
public class SELENIUM_Executor {

  //static List<Class> testCases = new ArrayList();

  /**
   * Method is used to distribute the tests
   *
   * @param testCaseCount - represents test case count
   * @throws Exception - represents exception
   */
  public void distributeTests(List<Class> testCases) throws Exception {
  
	ExecutorService executorService = Executors.newFixedThreadPool(testCases.size());
	for (final Class testCase : testCases) {
	  CTLogger.writeToLog("testCase - " + testCase.getName());
	  executorService.submit(new Runnable() {
		public void run() {
		  System.out.println("Running test file: " + testCase + Thread.currentThread().getId());
		  runTestCase(testCase);

		}
	  });
	}
	executorService.shutdown();
	try {
	  executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
	} catch (InterruptedException e) {
	  e.printStackTrace();
	}
	try {
	  Thread.sleep(3000);
	} catch (InterruptedException e) {
	  e.printStackTrace();
	}
  }

  /**
   * Method is used to run test cases
   *
   * @param testCase - represents test case
   */
  public void runTestCase(Class testCase) {
	Result result = JUnitCore.runClasses(testCase);
	for (Failure failure : result.getFailures()) {
	  System.out.println(failure.toString());
	}
  }

  /**
   * Main method
   *
   * @param args - represents command line arguments
   * @throws Exception - represents exception
   */
  public static void main(String[] args) throws Exception {
	  
	// new Login();

	SELENIUM_Executor aaftExecutor = new SELENIUM_Executor();
	System.out.println("Curr dir - " + System.getProperty("user.dir"));
	ListAllRunner listAllRunner = new ListAllRunner();
	PropertyFileReader propertyFileReader = new PropertyFileReader();
	String parallelExecution = propertyFileReader.getValue("PARALLEL_EXECUTION");
	
	String Imagepath = ExtentManager.Screenshot;
	propertyFileReader.setValue("IMAGE_PATH", Imagepath);
	System.out.println("Imagepath::"+Imagepath);
	
	if(null != parallelExecution && parallelExecution.equalsIgnoreCase("Yes")){
		aaftExecutor.distributeTests(listAllRunner.getAllRunnerClasses("selenium"));
	}else{
		for (Class clazz : listAllRunner.getAllRunnerClasses("selenium")) {
			Thread.sleep(10000);  
			JUnitCore junit = new JUnitCore();
			Result result = junit.run(clazz);
			for (Failure failure : result.getFailures()) {
				System.out.println(failure.toString());
			}
		}
	}
   	
	try {
		String browserName = propertyFileReader.getValue("BROWSER_NAME");
		if(browserName.equalsIgnoreCase("chrome")){
		Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");
		Thread.sleep(10000);  

		}
		else if(browserName.equalsIgnoreCase("ie") || browserName.equalsIgnoreCase("internet")){
			Runtime.getRuntime().exec("taskkill /F /IM iedriverserver.exe");
		}
		
	} catch (IOException e) {
		e.printStackTrace();
	}

	String path = ExtentManager.filePath;	
	System.out.println(path);

	//propertyFileReader.setValue("Salesforce_password", "t");
  }

}
