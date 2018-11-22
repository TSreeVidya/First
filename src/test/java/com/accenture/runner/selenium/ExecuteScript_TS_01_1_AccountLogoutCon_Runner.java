package com.accenture.runner.selenium;


import org.junit.Test;
import com.accenture.runner.utility.ExecuteScriptRunner;


/**
* Class is used for selenium test
* 
* @author      Prashant Gurunathan
* @version     1.0
*/


public class ExecuteScript_TS_01_1_AccountLogoutCon_Runner extends ExecuteScriptRunner{
	public ExecuteScript_TS_01_1_AccountLogoutCon_Runner() {
		super("TS_01_1_AccountLogoutCon", "com.accenture.runner.selenium.ExecuteScript_TS_01_1_AccountLogoutCon_Runner", "local");
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
