package com.accenture.runner.selenium;


import org.junit.Test;
import com.accenture.runner.utility.ExecuteScriptRunner;


/**
* Class is used for selenium test
* 
* @author      Varija Karampudi
* @version     1.0
*/


public class ExecuteScript_UAT_TS_01_2_OpportunityCreation2_Runner extends ExecuteScriptRunner{
	public ExecuteScript_UAT_TS_01_2_OpportunityCreation2_Runner() {
		super("UAT_TS_01_2_OpportunityCreation2", "com.accenture.runner.selenium.ExecuteScript_UAT_TS_01_2_OpportunityCreation2_Runner", "local");
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
