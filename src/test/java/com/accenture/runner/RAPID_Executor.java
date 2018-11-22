package com.accenture.runner;


import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.accenture.aaft.logger.CTLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Class is used to execute multiple rapid tests in parallel
 *
 * @author vijay.venkatappa
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class RAPID_Executor {

    static List<Class> testCases = new ArrayList();

    /**
     * Method is used to distribute the tests
     *
     * @param testCaseCount - represents test case count
     * @throws Exception - represents exception
     */
    public void distributeTests(int testCaseCount) throws Exception {
    	
        ExecutorService executorService = Executors.newFixedThreadPool(testCaseCount);
        for (final Class testCase : testCases) {
        CTLogger.writeToLog("testCase - "+testCase.getName());
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
    	RAPID_Executor aaftExecutor = new RAPID_Executor();
    	System.out.println("Curr dir - "+System.getProperty("user.dir"));
    	
/*    	testCases.add(ExecuteScriptIMacRunner.class);
    	testCases.add(ExecuteScriptIPadRunner.class);
    	testCases.add(ExecuteScriptIPhoneRunner.class);
     	testCases.add(PurchaseIMacRunner.class);
    	testCases.add(PurchaseIPadRunner.class);
    	testCases.add(PurchaseIPhoneRunner.class);
    	testCases.add(PurchaseIMacProRunner.class);
    	testCases.add(APITestRunner.class);
    	testCases.add(AccountsRunner.class);
    	testCases.add(DriveCarRunner.class);
    	testCases.add(PurchaseIMacProRunner.class);
    	testCases.add(APITestRunner.class);
    	testCases.add(AccountsRunner.class);
    	testCases.add(DriveCarRunner.class);
    	testCases.add(PurchaseIMacProRunner.class);
    	testCases.add(APITestRunner.class);
    	testCases.add(AccountsRunner.class);
    	testCases.add(DriveCarRunner.class);
    	testCases.add(PurchaseIMacProRunner.class);
    	testCases.add(APITestRunner.class);
    	testCases.add(AccountsRunner.class);
    	testCases.add(DriveCarRunner.class);
    	testCases.add(PurchaseIMacProRunner.class);
    	testCases.add(APITestRunner.class);
    	testCases.add(AccountsRunner.class);
    	testCases.add(DriveCarRunner.class);
    	testCases.add(PurchaseIMacProRunner.class);
    	testCases.add(APITestRunner.class);
    	testCases.add(AccountsRunner.class);
    	testCases.add(DriveCarRunner.class);
    	testCases.add(PurchaseIMacProRunner.class);
    	testCases.add(APITestRunner.class);
    	testCases.add(AccountsRunner.class);
    	testCases.add(DriveCarRunner.class);
    	testCases.add(PurchaseIMacProRunner.class);
    	testCases.add(APITestRunner.class);
    	testCases.add(AccountsRunner.class);
    	testCases.add(DriveCarRunner.class);
    	testCases.add(PurchaseIMacProRunner.class);
    	testCases.add(APITestRunner.class);
    	testCases.add(AccountsRunner.class);
    	testCases.add(DriveCarRunner.class);*/
    	/*testCases.add(ExecuteScriptKkartPurchaseDVDMoviesRunner.class);
    	testCases.add(ExecuteScriptKkartPurchaseElectronicsRunner.class);
    	testCases.add(ExecuteScriptKkartPurchaseGamesRunner.class);
    	testCases.add(ExecuteScriptKkartPurchaseGiftRunner.class);
    	testCases.add(ExecuteScriptKkartPurchaseHomeAndGardenRunner.class);
    	testCases.add(ExecuteScriptKkartPurchaseKeyboardRunner.class);
    	testCases.add(ExecuteScriptKkartPurchaseSoftwareRunner.class);*/
    	
    	CTLogger.writeToLog("Running no. of tests - "+testCases.size());
	aaftExecutor.distributeTests(testCases.size());
  }

}
