package com.accenture.runner.selenium;


import org.junit.Test;
import com.accenture.runner.utility.ExecuteScriptRunner;


/**
* Class is used for selenium test
* 
* @author      Prashant Gurunathan
* @version     1.0
*/


public class ExecuteScript_UAT_TS_01_1_OpportunityCreation1_Runner extends ExecuteScriptRunner{
	public ExecuteScript_UAT_TS_01_1_OpportunityCreation1_Runner() {
		super("UAT_TS_01_1_OpportunityCreation1", "com.accenture.runner.selenium.ExecuteScript_UAT_TS_01_1_OpportunityCreation1_Runner", "local");
	}
	/**
	 * Method is used to execute script
	 *
	 */
	@Test
	public void test() {
		executeTest();
	}
}
