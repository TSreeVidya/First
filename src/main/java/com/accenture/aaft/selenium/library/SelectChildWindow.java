package com.accenture.aaft.selenium.library;

import java.util.Set;

import org.openqa.selenium.WebDriver;

/**
 * Class is used to perform select browser child window action
 *
 * @author vijay.venkatappa
 *
 */
public class SelectChildWindow {

  /**
   * Method is used to perform select child window action
   *
   * @param driver - represents WebDriver
   * @return child window name
   */
  public String selectChildWindow(WebDriver driver) {
	Set<String> handles = driver.getWindowHandles();// To handle multiple windows
	String firstWinHandle = driver.getWindowHandle(); // To get your main window
	System.out.println("firstWinHandle user navigate - " + firstWinHandle);
	handles.remove(firstWinHandle);
	String winHandle = handles.iterator().next(); // To find popup window
	String secondWinHandle = null;
	if (winHandle != firstWinHandle) {
	  secondWinHandle = winHandle;
	}
	driver.switchTo().window(secondWinHandle); // To switch to popup window
	System.out.println("Window title - " + driver.getTitle());
	return firstWinHandle;
  }

}
