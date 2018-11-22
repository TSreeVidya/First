package com.accenture.runner.selenium;


import org.junit.Test;
import com.accenture.runner.utility.ExecuteScriptRunner;


/**
* Class is used for selenium test
* 
* @author      Prashant Gurunathan
* @version     1.0
*/


public class ExecuteScript_TS_01_1_AccountLogoutLight_Runner extends ExecuteScriptRunner{
	public ExecuteScript_TS_01_1_AccountLogoutLight_Runner() {
		super("TS_01_1_AccountLogoutLight", "com.accenture.runner.selenium.ExecuteScript_TS_01_1_AccountLogoutLight_Runner", "local");
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
