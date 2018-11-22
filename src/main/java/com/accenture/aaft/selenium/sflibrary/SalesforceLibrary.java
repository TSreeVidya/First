package com.accenture.aaft.selenium.sflibrary;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.report.ExtentTestManager;
import com.accenture.aaft.selenium.library.Click;
import com.accenture.aaft.selenium.library.Input;
import com.accenture.aaft.selenium.library.SelectChildWindow;
import com.accenture.aaft.selenium.library.SelectFrame;
import com.accenture.aaft.selenium.library.SelectFromList;
import com.accenture.aaft.selenium.library.WaitTime;
import com.accenture.aaft.selenium.library.utility.WaitForPageLoad;
import com.accenture.aaft.selenium.library.utility.WebElementDetails;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Class is used to for Sales Force application UI operations
 * 
 * @author deepak.mahapatro
 *
 */
@SuppressWarnings("unused")
public class SalesforceLibrary {
	private WebElementDetails webElementDetails = new WebElementDetails();
	private PropertyFileReader propertyFileReader = new PropertyFileReader();
	private Input input = new Input();
	private Click click = new Click();
	private SelectFromList selectFromList = new SelectFromList();
	private SelectChildWindow selectChildWindow = new SelectChildWindow();
	private SelectFrame selectFrame = new SelectFrame();
	String status = "false";

	/**
	 * method is used to login to Sales Force Organization
	 * 
	 * @param webDriver
	 * @param userName
	 * @param password
	 * @param extentTest
	 * @return status
	 */
	public String logInSFOrg(WebDriver webDriver, String userName, String password, ExtentTest extentTest) {
		CTLogger.writeToLog("Logging into Application with credential : ");
		CTLogger.writeToLog("Username : " + userName);
		CTLogger.writeToLog("Password : " + password);
		try{
			input.enterText(webDriver, "username", "ID", userName, extentTest, "username");
			input.enterText(webDriver, "password", "ID", password, extentTest, "password");
			clickElement(webDriver, "Login", "ID", extentTest, "Login", true);
			(new WaitTime()).waitTime("5");
			(new WaitForPageLoad()).waitForPageLoaded(webDriver);
			status = "true";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method is used to click on link
	 * 
	 * @param webDriver
	 * @param linkText
	 * @param extentTest
	 * @return status
	 */
	public String clickLink(WebDriver webDriver, String linkText, ExtentTest extentTest) {
		CTLogger.writeToLog("Clicking on link : " + linkText);
		try{
			clickElement(webDriver, linkText, "LINK", extentTest, "Login", true);
			(new WaitForPageLoad()).waitForPageLoaded(webDriver);
			status = "true";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method is used to selecect view
	 * 
	 * @param webDriver
	 * @param viewText
	 * @param extentTest
	 * @return status
	 */
	public String selectView(WebDriver webDriver, String viewText, ExtentTest extentTest) {
		CTLogger.writeToLog("Selecting view : " + viewText);
		try{
			Select viewList = new Select(webDriver.findElement(By.xpath("//select[@title='View:']")));
			String selectedOption = viewList.getFirstSelectedOption().getText();
	
			if (selectedOption.trim().equalsIgnoreCase(viewText)) {
				click.click(webDriver, "//input[@name='go']", "XPATH", extentTest, "btnGo");
			} else {
				viewText += ",text";
				selectFromList.selectFromList(webDriver, "//select[@title='View:']", "XPATH", viewText, "", extentTest,
						"cboxView");
			}
			(new WaitForPageLoad()).waitForPageLoaded(webDriver);
			status = "true";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method is used to log out from Sales Force application
	 * 
	 * @param webDriver
	 * @param extentTest
	 * @return status
	 */
	public String logOutSFOrg(WebDriver webDriver, ExtentTest extentTest) {
		try{
		closeAllPrimaryTabs(webDriver, extentTest);
		CTLogger.writeToLog("Logging Out");
		switchToDefault(webDriver);
		clickElement(webDriver, "userNavButton", "ID", extentTest, "UserNav", true);
		clickElement(webDriver, "Logout", "LINK", extentTest, "Logout", true);
		status = "true";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method is used to select from lookup drop down
	 * 
	 * @param webDriver
	 * @param lookUpLabel
	 * @param lookUpValueToSelect
	 * @param extentTest
	 * @return status
	 */
	public String selectFromLookUp(WebDriver webDriver, String lookUpLabel, String lookUpValueToSelect,
			ExtentTest extentTest) {

		CTLogger.writeToLog("SalesforceLibrary", "selectFromLookUp()", " method called");
		CTLogger.writeToLog("SalesforceLibrary", "selectFromLookUp()","Selecting " + lookUpValueToSelect + " in " + lookUpLabel + " lookUp.");
        try{
			String lookupXPath = getElementXPath(webDriver, extentTest, lookUpLabel, "LOOKUP");
			clickElement(webDriver, lookupXPath, "XPATH", extentTest, lookUpLabel + " Lookup", true);
			String currentWndHadle = webDriver.getWindowHandle();
	
			selectChildWindow.selectChildWindow(webDriver);
			selectFrame.selectFrame(webDriver, "searchFrame", "ID", extentTest, "searchFrame");
			input.enterText(webDriver, "lksrch", "ID", lookUpValueToSelect, extentTest, "Lookup Search");
			clickElement(webDriver, "//input[@title='Go!']", "XPATH", extentTest, "Lookup Search Button", true);
			selectFrame.selectFrame(webDriver, "resultsFrame", "ID", extentTest, "resultsFrame");
			clickElement(webDriver, lookUpValueToSelect, "LINK", extentTest, "Lookup Value", false);
	
			webDriver.switchTo().window(currentWndHadle);
			status = "true";
        }catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method is used to select from pick list pop up
	 * 
	 * @param webDriver
	 * @param picklistLabel
	 * @param picklistValueToSelect
	 * @param extentTest
	 * @return status
	 */
	public String selectFromPopupPicklist(WebDriver webDriver, String picklistLabel, String picklistValueToSelect,
			ExtentTest extentTest) {

		CTLogger.writeToLog("SalesforceLibrary", "selectFromLookUp()", " method called");
		CTLogger.writeToLog("SalesforceLibrary", "selectFromLookUp()", "Selecting " + picklistValueToSelect + " in " + picklistLabel + " Popup Picklist.");
		try{
			String picklistXPath = getElementXPath(webDriver, extentTest, picklistLabel, "POPUPPICKLIST");
			clickElement(webDriver, picklistXPath, "XPATH", extentTest, picklistLabel + " Popup Picklist", true);
	
			webDriver.switchTo().defaultContent();
			selectChildWindow.selectChildWindow(webDriver);
			clickElement(webDriver, picklistValueToSelect, "LINK", extentTest, "Picklist Value", false);
	
			status = "true";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method is used to open console application
	 * 
	 * @param webDriver
	 * @param appName
	 * @param extentTest
	 * @return status
	 */
	public String openConsoleApplication(WebDriver webDriver, String appName, ExtentTest extentTest) {
		CTLogger.writeToLog("SalesforceLibrary", "openConsoleApplication()", "Opening application \"" + appName + "\"");
		try{
			switchToDefault(webDriver);
			String currentAppTitle = getElementAttribute(webDriver, "tsid", "ID", "title");
			String appLabel = getContentText(webDriver, "tsidLabel", "ID");
	
			boolean loadApplication = true;
	
			CTLogger.writeToLog("Current application title " + currentAppTitle);
			CTLogger.writeToLog("Current application label " + appLabel);
	
			currentAppTitle = currentAppTitle.replaceAll("\\.", "").trim();
			String modifiedAppName = appName.replaceAll("\\.", "").trim().substring(0, 25);
	
			if ((appLabel == null || appLabel.length() == 0) && currentAppTitle.equalsIgnoreCase(modifiedAppName)) {
				loadApplication = false;
			}
	
			if (loadApplication) {
				clickElement(webDriver, "tsid", "ID", extentTest, "App Selector", true);
				clickElement(webDriver, appName, "LINK", extentTest, appName, true);
				(new WaitForPageLoad()).waitForPageLoaded(webDriver);
				waitUntilObjectNotAvailable(webDriver, "//div[contains(@class, 'x-mask-loading')]", "XPATH");
				(new WaitForPageLoad()).waitForPageLoaded(webDriver);
			}
			status = "true";
	
			closeAllPrimaryTabs(webDriver, extentTest);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method is used to open console application object
	 * 
	 * @param webDriver
	 * @param objectName
	 * @param extentTest
	 * @return status
	 */
	public String openConsoleApplicationObject(WebDriver webDriver, String objectName, ExtentTest extentTest) {
		CTLogger.writeToLog("Opening Object : " + objectName);
		final String viewListXpath = "(//div[@id='servicedesk']//em)[1]";
		try{
			switchToDefault(webDriver);
			closeAllPrimaryTabs(webDriver, extentTest);
	
			String currentObjectName = webElementDetails.getElement(webDriver, viewListXpath + "//span", "XPATH").getText();
	
			if (!currentObjectName.equalsIgnoreCase(objectName)) {
				moveToElementAndClick(webDriver, viewListXpath, "XPATH", 237, 15);
				clickElement(webDriver, "//a/span[@class='x-menu-item-text' and text()='" + objectName + "']", "XPATH",
						extentTest, "Object : " + objectName.trim(), true);
				waitUntilObjectNotAvailable(webDriver, "//div[contains(@class, 'x-mask-loading')]", "XPATH");
			}
			status = "true";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method is used to open custom application object
	 * 
	 * @param webDriver
	 * @param tabName
	 * @param extentTest
	 * @return status
	 */
	public String openCustomApplicationObject(WebDriver webDriver, String tabName, ExtentTest extentTest) {
		CTLogger.writeToLog("Opening Tab " + tabName);
		try{
			openCustomApplication(webDriver, "Sales", extentTest);
			clickElement(webDriver, "//img[@title='All Tabs']", "XPATH", extentTest, "All Tabs", true);
			clickElement(webDriver, tabName.trim(), "LINK", extentTest, tabName, true);
			status = "true";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method is used to open custom application
	 * 
	 * @param webDriver
	 * @param appName
	 * @param extentTest
	 * @return status
	 */
	public String openCustomApplication(WebDriver webDriver, String appName, ExtentTest extentTest) {
		CTLogger.writeToLog("Opening application " + appName);
		try{
			String currentAppTitle = getElementAttribute(webDriver, "tsid", "ID", "title");
			String appLabel = getContentText(webDriver, "tsidLabel", "ID");
			boolean loadApplication = true;
	
			CTLogger.writeToLog("Current application title " + currentAppTitle);
			CTLogger.writeToLog("Current application label " + appLabel);
	
			if (currentAppTitle.equalsIgnoreCase("Force.com App Menu") && appLabel.equalsIgnoreCase(appName)) {
				loadApplication = false;
			}
	
			if (loadApplication) {
				clickElement(webDriver, "tsid", "ID", extentTest, "App Selector", true);
				clickElement(webDriver, appName, "LINK", extentTest, appName, true);
				(new WaitForPageLoad()).waitForPageLoaded(webDriver);
			}
			status = "true";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method is used to open item from pagination 
	 * 
	 * @param webDriver
	 * @param itemDetailsToOpen
	 * @param extentTest
	 * @return status
	 */
	public String openItemFromPagination(WebDriver webDriver, String itemDetailsToOpen, ExtentTest extentTest) {
		WebElement nextPageElement;
		String nextPageElementClass;
		try{
			int pageNumber = 1;
			boolean elementFound = false;
			do {
				nextPageElementClass = getElementAttribute(webDriver, "//img[@alt='Next']", "XPATH", "class");
				String accountLinkLocator = "//a/span[text()='" + itemDetailsToOpen + "']";
	
				if (isElementExists(webDriver, accountLinkLocator, "XPATH")) {
					CTLogger.writeToLog(itemDetailsToOpen + " available in page " + pageNumber);
					clickElement(webDriver, accountLinkLocator, "XPATH", extentTest, accountLinkLocator, true);
					elementFound = true;
					break;
				} else if (isElementExists(webDriver, itemDetailsToOpen, "LINK")) {
					CTLogger.writeToLog(itemDetailsToOpen + " available in page " + pageNumber);
					clickElement(webDriver, itemDetailsToOpen, "LINK", extentTest, itemDetailsToOpen, true);
					elementFound = true;
					break;
				}
	
				CTLogger.writeToLog("Loading Page " + (++pageNumber));
				input.enterText(webDriver, "//div[contains(@id,'bottomNav')]//input[@class='pageInput']", "XPATH",
						String.valueOf(pageNumber) + Keys.ENTER, extentTest, "Page Number");
				waitUntilObjectAvailable(webDriver, "//div[contains(@id, 'loading') and contains(@style,'display: none')]",
						accountLinkLocator);
			} while (!(nextPageElementClass.toLowerCase().endsWith("off")));
			status = "true";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method is used to close all primary tabs
	 * 
	 * @param webDriver
	 * @param extentTest
	 * @return status
	 */
	public String closeAllPrimaryTabs(WebDriver webDriver, ExtentTest extentTest) {
		String mainTabMenuXPath = "//div[contains(@class,'sd_primary_tabstrip')]//div[contains(@class,'x-tab-tabmenu')]";
		try{
			switchToDefault(webDriver);
			if (isElementExists(webDriver, mainTabMenuXPath, "XPATH")) {
				clickElement(webDriver, mainTabMenuXPath, "XPATH", extentTest, "", false);
				if (isElementExists(webDriver,
						"//li[not(contains(@class,'disabled'))]/a/span[text()='Close all primary tabs']", "XPATH")) {
					clickElement(webDriver, "//li//span[text()='Close all primary tabs']", "XPATH", extentTest, "", true);
				} else {
					clickElement(webDriver, mainTabMenuXPath, "XPATH", extentTest, "", false);
				}
	
				if (isElementExists(webDriver, "//button[text()=\"Don't Save\"]", "XPATH")) {
					clickElement(webDriver, "//button[text()=\"Don't Save\"]", "XPATH", extentTest, "", true);
				}
				(new WaitForPageLoad()).waitForPageLoaded(webDriver);
			}
			status = "true";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method is used to close all sub tabs
	 * 
	 * @param webDriver
	 * @param extentTest
	 * @return status
	 */
	public String closeAllSubTabs(WebDriver webDriver, ExtentTest extentTest) {
		String subTabMenuXPath = "//div[contains(@class,'sd_primary_container') and not(contains(@class,'x-hide-offsets'))]/div[contains(@class,'sd-tab-strip-visible')]//div[contains(@class,'x-tab-tabmenu')]";
		try{
			switchToDefault(webDriver);
			if (isElementExists(webDriver, subTabMenuXPath, "XPATH")) {
				clickElement(webDriver, subTabMenuXPath, "XPATH", extentTest, "", false);
				clickElement(webDriver, "//li//span[text()='Close all subtabs']", "XPATH", extentTest, "", true);
	
				if (isElementExists(webDriver, "//button[text()=\"Don't Save\"]", "XPATH")) {
					clickElement(webDriver, "//button[text()=\"Don't Save\"]", "XPATH", extentTest, "", true);
				} else {
					(new WaitForPageLoad()).waitForPageLoaded(webDriver);
				}
			}
			status = "true";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method is used to refresh all primary tabs
	 * 
	 * @param webDriver
	 * @param extentTest
	 * @return status
	 */
	public String refreshAllPrimaryTabs(WebDriver webDriver, ExtentTest extentTest) {
		String mainTabMenuXPath = "//div[contains(@class,'sd_primary_tabstrip')]//div[contains(@class,'x-tab-tabmenu')]";
		try{
			switchToDefault(webDriver);
			if (isElementExists(webDriver, mainTabMenuXPath, "XPATH")) {
				clickElement(webDriver, mainTabMenuXPath, "XPATH", extentTest, "", false);
				if (isElementExists(webDriver,
						"//li[not(contains(@class,'disabled'))]/a/span[text()='Refresh all primary tabs']", "XPATH")) {
					clickElement(webDriver, "//li//span[text()='Refresh all primary tabs']", "XPATH", extentTest, "", true);
				} else {
					clickElement(webDriver, mainTabMenuXPath, "XPATH", extentTest, "", false);
				}
	
				if (isElementExists(webDriver, "//button[text()=\"Don't Save\"]", "XPATH")) {
					clickElement(webDriver, "//button[text()=\"Don't Save\"]", "XPATH", extentTest, "", true);
				} else {
					(new WaitForPageLoad()).waitForPageLoaded(webDriver);
				}
			}
		status = "true";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 *  method is used refresh all tabs
	 *   
	 * @param webDriver
	 * @param extentTest
	 * @return status
	 */
	public String refreshAllSubTabs(WebDriver webDriver, ExtentTest extentTest) {
		String subTabMenuXPath = "//div[contains(@class,'sd_primary_container') and not(contains(@class,'x-hide-offsets'))]/div[contains(@class,'sd-tab-strip-visible')]//div[contains(@class,'x-tab-tabmenu')]";
		try{
			switchToDefault(webDriver);
			if (isElementExists(webDriver, subTabMenuXPath, "XPATH")) {
				clickElement(webDriver, subTabMenuXPath, "XPATH", extentTest, "", false);
				clickElement(webDriver, "//li//span[text()='Refresh all subtabs']", "XPATH", extentTest, "", true);
	
				if (isElementExists(webDriver, "//button[text()=\"Don't Save\"]", "XPATH")) {
					clickElement(webDriver, "//button[text()=\"Don't Save\"]", "XPATH", extentTest, "", true);
				} else {
					(new WaitForPageLoad()).waitForPageLoaded(webDriver);
				}
			}
			status = "true";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * 
	 * 
	 * @param webDriver
	 * @param itemCategory
	 * @param itemToSearch
	 * @param extentTest
	 * @return status
	 */
	public String openItemFromGlobalSearch(WebDriver webDriver, String itemCategory, String itemToSearch, ExtentTest extentTest) {
		try{
			closeAllPrimaryTabs(webDriver, extentTest);
			refreshDriver(webDriver);
			input.enterText(webDriver, "phSearchInput", "ID", itemToSearch + "*" + Keys.ENTER, extentTest, "");
	
			selectFrame.selectFrame(webDriver, "//iframe[contains(@src, 'UnifiedSearchResults')]", "XPATH", extentTest, "");
			String xPathSearchItem = "//span[starts-with(text(), '" + itemCategory + "')]/ancestor::div[2]//a[text()='"
					+ itemToSearch + "']";
	
			clickElement(webDriver, xPathSearchItem, "XPATH", extentTest, "", true);
			switchToDefault(webDriver);
			waitForPageTitle(webDriver, "Search");
			(new WaitForPageLoad()).waitForPageLoaded(webDriver);
			waitUntilObjectNotAvailable(webDriver, "//span[text()='Loading...']", "XPATH");
			status = "true";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method is used to open set up option
	 * 
	 * @param webDriver
	 * @param setUpOptionItem
	 * @param extentTest
	 * @return status
	 */
	public String openSetUpOption(WebDriver webDriver, String setUpOptionItem, ExtentTest extentTest) {
		try{
			clickElement(webDriver, "setupLink", "ID", extentTest, "", true);
			input.enterText(webDriver, "setupSearch", "ID", setUpOptionItem, extentTest, "");
	
			String xPathSetUpItem = "//table[@class='list']//th/a[translate(text(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
					+ setUpOptionItem.trim().toUpperCase() + "']";
			clickElement(webDriver, xPathSetUpItem, "XPATH", extentTest, "", true);
			status = "true";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method is used to verify record available in page
	 * 
	 * @param webDriver
	 * @param elementLabel
	 * @param extentTest
	 * @return status
	 */
	public String verifyIfRecordAvailableInPage(WebDriver webDriver, String elementLabel, ExtentTest extentTest) {
		int pageNumber = 1;
		try{
			String itemXPath = "//form[contains(@id,'actionForm')]//span[text()='" + elementLabel + "']";
			if (!isElementExists(webDriver, itemXPath, "XPATH")) {
				itemXPath = "//form[contains(@id,'actionForm')]//a[text()='" + elementLabel + "']";
			}
	
			String elementClass;
			(new WaitForPageLoad()).waitForPageLoaded(webDriver);
			do {
				elementClass = getElementAttribute(webDriver, "//img[@alt='Next']", "XPATH", "class");
	
				if (isElementExists(webDriver, itemXPath, "XPATH")) {
					CTLogger.writeToLog(elementLabel + " available in page " + pageNumber);
					status = "true";
					break;
				}
				CTLogger.writeToLog("Loading Page " + (++pageNumber));
				input.enterText(webDriver, "//div[contains(@id,'bottomNav')]//input[@class='pageInput']", "XPATH",String.valueOf(pageNumber) + Keys.ENTER, extentTest, "Next");
				waitUntilObjectAvailable(webDriver, "//div[contains(@id, 'loading') and contains(@style,'display: none')]","XPATH");
			} while (!(elementClass.toLowerCase().endsWith("off")));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method is used to verify record available in console page
	 * 
	 * @param webDriver
	 * @param itemToFind
	 * @param extentTest
	 * @return status
	 */
	public String verifyIfRecordAvailableInConsolePage(WebDriver webDriver, String itemToFind, ExtentTest extentTest) {
		int pageNumber = 1;
		try{
			String elementClass;
			(new WaitForPageLoad()).waitForPageLoaded(webDriver);
			do {
				elementClass = getElementAttribute(webDriver, "//img[@alt='Next']", "XPATH", "class");
	
				if (isElementExists(webDriver, itemToFind, "LINK")) {
					CTLogger.writeToLog(itemToFind + " available in page " + pageNumber);
					status = "true";
					break;
				}
				CTLogger.writeToLog("Loading Page " + (++pageNumber));
				input.enterText(webDriver, "//div[contains(@id,'bottomNav')]//input[@class='pageInput']", "XPATH",String.valueOf(pageNumber) + Keys.ENTER, extentTest, "Next");
				waitUntilObjectAvailable(webDriver, "//div[contains(@id, 'loading') and contains(@style,'display: none')]",	"XPATH");
			} while (!(elementClass.toLowerCase().endsWith("off")));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method is used to check the related list column header
	 * 
	 * @param webDriver
	 * @param relatedListTitle
	 * @param columnHeader
	 * @param extentTest
	 * @return status
	 */
	public String verifyRelatedListColumnHeader(WebDriver webDriver, String relatedListTitle, String columnHeader,	ExtentTest extentTest) {

		String xPathRelListColumn = "//h3[text()='" + relatedListTitle + "']/ancestor::div[2]//th[text()='"	+ columnHeader + "']";
		status = "true";
		
		return status;
	}

	/**
	 * method is used to check the related list column data
	 * 
	 * @param webDriver
	 * @param relatedListTitle
	 * @param recordPrimaryKey
	 * @param columnHeader
	 * @param expectedData
	 * @param extentTest
	 * @return status
	 */
	public String verifyRelatedListColumnData(WebDriver webDriver, String relatedListTitle, String recordPrimaryKey, String columnHeader, String expectedData, ExtentTest extentTest) {
		String xPathRelListColumn = "//h3[text()='" + relatedListTitle + "']/ancestor::div[2]//a[text()='"+ recordPrimaryKey + "']/ancestor::tr[1]//*[text()='" + expectedData + "']";
		status = "true";
		
		return status;
	}

	/**
	 * method is used to verify the related list record
	 * 
	 * @param webDriver
	 * @param relatedListTitle
	 * @param recordPrimaryKey
	 * @param extentTest
	 * @return status
	 */
	public String verifyIfRelatedListRecordExists(WebDriver webDriver, String relatedListTitle, String recordPrimaryKey, ExtentTest extentTest) {
		
		String xPathRelListRecord = "//h3[text()='" + relatedListTitle + "']/ancestor::div[2]//a[text()='"+ recordPrimaryKey + "']";
		status = "true";
		
		return status;
	}

	/**
	 * method is used to open related list record
	 * 
	 * @param webDriver
	 * @param relatedListTitle
	 * @param recordPrimaryKey
	 * @param extentTest
	 * @return status
	 */
	public String openRelatedListRecord(WebDriver webDriver, String relatedListTitle, String recordPrimaryKey,	ExtentTest extentTest) {
		String xPathRelListRecord = "//h3[text()='" + relatedListTitle + "']/ancestor::div[2]//a[text()='"+ recordPrimaryKey + "']";

		clickElement(webDriver, xPathRelListRecord, "XPATH", extentTest, relatedListTitle, true);
		status = "true";
		return status;
	}

	/**
	 * method is used to edit related list record
	 * 
	 * @param webDriver
	 * @param relatedListTitle
	 * @param recordPrimaryKey
	 * @param extentTest
	 * @return status
	 */
	public String editRelatedListRecord(WebDriver webDriver, String relatedListTitle, String recordPrimaryKey, ExtentTest extentTest) {
		String xPathRelListRecordToEdit = "//h3[text()='" + relatedListTitle + "']/ancestor::div[2]//a[text()='"+ recordPrimaryKey + "']/ancestor::tr[1]//a[text()='Edit']";

		clickElement(webDriver, xPathRelListRecordToEdit, "XPATH", extentTest, relatedListTitle, true);
		status = "true";
		return status;
	}

	/**
	 * method is used to delete related list record
	 * 
	 * @param webDriver
	 * @param relatedListTitle
	 * @param recordPrimaryKey
	 * @param extentTest
	 * @return status
	 */
	public String deleteRelatedListRecord(WebDriver webDriver, String relatedListTitle, String recordPrimaryKey, ExtentTest extentTest) {
		(new WaitForPageLoad()).waitForPageLoaded(webDriver);
		try{
			String xPathRelListRecordToDelete = "//h3[text()='" + relatedListTitle + "']/ancestor::div[2]//a[text()='"+ recordPrimaryKey + "']/ancestor::tr[1]//a[text()='Del']";
	
			clickElement(webDriver, xPathRelListRecordToDelete, "XPATH", extentTest, relatedListTitle, false);
			waitForAlertAndAccept(webDriver, extentTest);
			status = "true";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public String goToRelatedList(WebDriver webDriver, String relatedListTitle, ExtentTest extentTest) {
		try{
			String xPathRelList = "//a/span[text()='" + relatedListTitle + "']";
			clickElement(webDriver, xPathRelList, "XPATH", extentTest, relatedListTitle, true);
			status = "true";
			}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method is used to open related list new record form
	 * 
	 * @param webDriver
	 * @param relatedListTitle
	 * @param extentTest
	 * @return status
	 */
	public String openRelatedListNewRecordForm(WebDriver webDriver, String relatedListTitle, ExtentTest extentTest) {
		try{
			String xPathNewRecord = "//h3[text()='" + relatedListTitle + "']/ancestor::div[2]//input[@type='button'][starts-with(@title,'New ')]";
			if (!isElementExists(webDriver, xPathNewRecord, "XPATH")) {
				xPathNewRecord = "//h3[text()='" + relatedListTitle	+ "']/ancestor::div[2]//span[starts-with(text(),'New ')]";
			}
			clickElement(webDriver, xPathNewRecord, "XPATH", extentTest, relatedListTitle, true);
			closePopUp(webDriver);
			status = "true";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method is used to validate error message
	 * 
	 * @param webDriver
	 * @param errorMessage
	 * @param extentTest
	 * @return status
	 */
	public String validateErrorMessage(WebDriver webDriver, String errorMessage, ExtentTest extentTest) {
		String errorMsgObject = "errorMsg";
		String errorMsgLocator = "CLASS";
		try{
		if (!isElementExists(webDriver, errorMsgObject, errorMsgLocator)) {
			errorMsgObject = "errorDiv_ep";
			errorMsgLocator = "ID";
			if (!isElementExists(webDriver, errorMsgObject, errorMsgLocator)) {
				errorMsgObject = "//div[contains(@class,'errorMessage')]";
				errorMsgLocator = "XPATH";
			}
		}

		String errorMessageFromPage = getContentText(webDriver, errorMsgObject, errorMsgLocator);
		CTLogger.writeToLog("Error Message on page : " + errorMessageFromPage);
		CTLogger.writeToLog("Error Message to look for : " + errorMessage);
		if (errorMessageFromPage.trim().toLowerCase().contains(errorMessage.trim().toLowerCase())) {
			status = "true";
		} else {
			extentTest.log(LogStatus.FAIL, "Error message validation failed. Expected : " + errorMessage + ". Actual : "+ errorMessageFromPage);
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method is used to click tab
	 * 
	 * @param webDriver
	 * @param tabHeader
	 * @param extentTest
	 * @return status
	 */
	public String clickTab(WebDriver webDriver, String tabHeader, ExtentTest extentTest) {
		try{
			String mainTabMenuXPath = "//div[contains(@class,'sd_primary_tabstrip')]//div[contains(@class,'x-tab-tabmenu')]";
			switchToDefault(webDriver);
			CTLogger.writeToLog("Refreshing Tab " + tabHeader);
			if (tabHeader.length() > 20) {
				tabHeader = tabHeader.substring(0, 17) + "...";
			}
	
			clickElement(webDriver, mainTabMenuXPath, "XPATH", extentTest, tabHeader, false);
			clickElement(webDriver, "//div[@class='tabTitle']/span[text()='" + tabHeader + "']", "XPATH", extentTest,
					tabHeader, false);
			status = "true";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method is used to click sub tab
	 * 
	 * @param webDriver
	 * @param tabHeader
	 * @param extentTest
	 * @return status
	 */
	public String clickSubTab(WebDriver webDriver, String tabHeader, ExtentTest extentTest) {
		try{
			String subTabMenuXPath = "//div[contains(@class,'sd_primary_container') and not(contains(@class,'x-hide-offsets'))]/div[contains(@class,'sd-tab-strip-visible')]//div[contains(@class,'x-tab-tabmenu')]";
			switchToDefault(webDriver);
			CTLogger.writeToLog("Refreshing Tab " + tabHeader);
			if (tabHeader.length() > 20) {
				tabHeader = tabHeader.substring(0, 17) + "...";
			}
	
			if (isElementExists(webDriver, subTabMenuXPath, "XPATH")) {
				clickElement(webDriver, subTabMenuXPath, "XPATH", extentTest, tabHeader, false);
				clickElement(webDriver, "//div[@class='tabTitle']/span[text()='" + tabHeader + "']", "XPATH", extentTest, tabHeader, false);
				status = "true";
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method is used to close primary
	 * 
	 * @param webDriver
	 * @param tabHeader
	 * @param extentTest
	 * @return status
	 */
	public String closePrimaryTab(WebDriver webDriver, String tabHeader, ExtentTest extentTest) {
		try{
			switchToDefault(webDriver);
			CTLogger.writeToLog("Closing Tab " + tabHeader);
			if (tabHeader.length() > 20) {
				tabHeader = tabHeader.substring(0, 17) + "...";
			}
			String xPathHeader = "//span[text()='" + tabHeader + "']/ancestor::li[1]";
	
			int tabWidth = webElementDetails.getElement(webDriver, xPathHeader, "XPATH").getSize().width;
			moveToElementAndClick(webDriver, xPathHeader, "XPATH", tabWidth - 10, 10);
			status = "true";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method is used to refresh tab
	 * 
	 * @param webDriver
	 * @param tabHeader
	 * @param extentTest
	 * @return status
	 */
	public String refreshTab(WebDriver webDriver, String tabHeader, ExtentTest extentTest) {
		
		try{
			String mainTabMenuXPath = "//div[contains(@class,'sd_primary_tabstrip')]//div[contains(@class,'x-tab-tabmenu')]";
			switchToDefault(webDriver);
			CTLogger.writeToLog("Refreshing Tab " + tabHeader);
			if (tabHeader.length() > 20) {
				tabHeader = tabHeader.substring(0, 17) + "...";
			}
	
			clickElement(webDriver, mainTabMenuXPath, "XPATH", extentTest, tabHeader, false);
			clickElement(webDriver, "//div[@class='tabTitle']/span[text()='" + tabHeader + "']/ancestor::span[1]/img[@class='refresh-icon']", "XPATH", extentTest, tabHeader, true);
			status = "true";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method is used to refresh sub tab
	 * 
	 * @param webDriver
	 * @param tabHeader
	 * @param extentTest
	 * @return status
	 */
	public String refreshSubTab(WebDriver webDriver, String tabHeader, ExtentTest extentTest) {
		try{
			String subTabMenuXPath = "//div[contains(@class,'sd_primary_container') and not(contains(@class,'x-hide-offsets'))]/div[contains(@class,'sd-tab-strip-visible')]//div[contains(@class,'x-tab-tabmenu')]";
			switchToDefault(webDriver);
			CTLogger.writeToLog("Refreshing Tab " + tabHeader);
			if (tabHeader.length() > 20) {
				tabHeader = tabHeader.substring(0, 17) + "...";
			}
	
			clickElement(webDriver, subTabMenuXPath, "XPATH", extentTest, tabHeader, false);
			clickElement(webDriver, "//div[@class='tabTitle']/span[text()='" + tabHeader + "']/ancestor::span[1]/img[@class='refresh-icon']", "XPATH", extentTest, tabHeader, true);
			status = "true";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method is used to verify alert text
	 * 
	 * @param webDriver
	 * @param messageToVerify
	 * @param extentTest
	 * @return status
	 */
	public String verifyAlertText(WebDriver webDriver, String messageToVerify, ExtentTest extentTest) {
		try{
			waitForAlert(webDriver, extentTest);
			String alertMessage = getAlertText(webDriver);
			CTLogger.writeToLog("Message on alert : " + alertMessage);
			closeAlert(webDriver);
			status = "true";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method is used to verify record exist
	 * 
	 * @param webDriver
	 * @param relatedListTitle
	 * @param extentTest
	 * @return status
	 */
	public String verifyRecordsExistInRelatedList(WebDriver webDriver, String relatedListTitle, ExtentTest extentTest) {
		try{
			WebElement Record = webElementDetails.getElement(webDriver,	"//h3[text()='" + relatedListTitle + "']/ancestor::form[1]//th", "XPATH");
	
			if (Record.getText().equalsIgnoreCase("No records to display")) {
				extentTest.log(LogStatus.FAIL, "Records exists in related list");
				extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH"))));
				status = "true";
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method is used to verify record not exist
	 * 
	 * @param webDriver
	 * @param relatedListTitle
	 * @param extentTest
	 * @return status 
	 */
	public String verifyRecordsNotExistInRelatedList(WebDriver webDriver, String relatedListTitle,	ExtentTest extentTest) {
		try{
			WebElement Record = webElementDetails.getElement(webDriver,	"//h3[text()='" + relatedListTitle + "']/ancestor::form[1]//th", "XPATH");
	
			if (!Record.getText().equalsIgnoreCase("No records to display")) {
				extentTest.log(LogStatus.FAIL, "Records exists in related list");
				extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(
						ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH"))));
				status = "true";
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method is used to verify object exist
	 * 
	 * @param webDriver
	 * @param objectName
	 * @param selector
	 * @param controlName
	 * @param extentTest
	 * @return status
	 */
	public String verifyObjectExists(WebDriver webDriver, String objectName, String selector, String controlName, ExtentTest extentTest) {
		try{
			if (webElementDetails.getElements(webDriver, objectName, selector).isEmpty()) {
				CTLogger.writeToLog("SalesforceLibrary", "verifyObjectExists()",
						" Object not found exeption thrown  --> path : " + objectName + " --> " + controlName);
				extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
				webDriver.quit();
			}
			status = "true";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method is used to verify object not exist
	 * 
	 * @param webDriver
	 * @param objectName
	 * @param selector
	 * @param controlName
	 * @param extentTest
	 * @return status
	 */
	public String verifyObjectNotExist(WebDriver webDriver, String objectName, String selector, String controlName,	ExtentTest extentTest) {
		try{
			if (!webElementDetails.getElements(webDriver, objectName, selector).isEmpty()) {
				CTLogger.writeToLog("SalesforceLibrary", "verifyObjectExists()", " Object not found exeption thrown  --> path : " + objectName + " --> " + controlName);
				extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver, propertyFileReader.getValue("IMAGE_PATH") + controlName)));
				webDriver.quit();
			}
			status = "true";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * method is used for wait for alert
	 * 
	 * @param webDriver
	 * @param extentTest
	 */
	private void waitForAlert(WebDriver webDriver, ExtentTest extentTest) {
		WebDriverWait webDriverWait = new WebDriverWait(webDriver, 20);
		try {
			webDriverWait.until(ExpectedConditions.alertIsPresent());
		} catch (TimeoutException e) {
			CTLogger.writeToLog("", "waitForAlert()", " Alert not found exception");
			extentTest.log(LogStatus.FAIL, "Alert not found");
		}
	}

	private String getAlertText(WebDriver webDriver) {
		return webDriver.switchTo().alert().getText();
	}

	private void closeAlert(WebDriver webDriver) {
		webDriver.switchTo().alert().accept();
	}

	private void waitForAlertAndAccept(WebDriver webDriver, ExtentTest extentTest) {
		waitForAlert(webDriver, extentTest);
		closeAlert(webDriver);
	}

	private void waitUntilObjectNotAvailable(WebDriver webDriver, String objectName, String selector) {
		while (!webElementDetails.getElements(webDriver, objectName, selector).isEmpty());
	}

	private void waitUntilObjectAvailable(WebDriver webDriver, String objectName, String selector) {
		while (webElementDetails.getElements(webDriver, objectName, selector).isEmpty());
	}

	private void waitForPageTitle(WebDriver webDriver, String pageTitle) {
		while (webDriver.getTitle().startsWith(pageTitle));
		(new WaitForPageLoad()).waitForPageLoaded(webDriver);
	}

	private void refreshDriver(WebDriver webDriver) {
		webDriver.navigate().refresh();
	}

	private void moveToElementAndClick(WebDriver webDriver, String viewListXpath, String selector, int xOffset,	int yOffset) {
		Actions actions = new Actions(webDriver);
		actions.moveToElement(webElementDetails.getElement(webDriver, viewListXpath, selector), xOffset, yOffset)
				.click().build().perform();
	}

	private void switchToDefault(WebDriver webDriver) {
		webDriver.switchTo().defaultContent();
	}

	private void clickElement(WebDriver webDriver, String objectName, String selector, ExtentTest extentTest,
		String controlName, boolean waitForPageLoad) {
		click.click(webDriver, objectName, selector, extentTest, controlName);
		if (waitForPageLoad) {
			(new WaitForPageLoad()).waitForPageLoaded(webDriver);
		}
	}

	private String getElementAttribute(WebDriver webDriver, String objectName, String selector, String attributeName) {
		WebElement element = webElementDetails.getElement(webDriver, objectName, selector);
		return element.getAttribute(attributeName);
	}

	private String getContentText(WebDriver webDriver, String objectName, String selector) {
		WebElement element = webElementDetails.getElement(webDriver, objectName, selector);
		return element.getText().trim();
	}

	private void closePopUp(WebDriver webDriver) {
		String currentWndHadle = webDriver.getWindowHandle();
		Set<String> wndHandles = webDriver.getWindowHandles();
		wndHandles.remove(currentWndHadle);
		for (String popUpWndHandle : wndHandles) {
			if (!popUpWndHandle.equals(currentWndHadle)) {
				webDriver.switchTo().window(popUpWndHandle);
				webDriver.close();
			}
		}
		webDriver.switchTo().window(currentWndHadle);
	}

	private String getElementXPath(WebDriver webDriver, ExtentTest extentTest, String elementLabel,	String elementType) {
		String elementXPath = "";
		boolean elementExists = true;

		elementType = elementType.trim().toUpperCase();

		if (isElementExists(webDriver, "//label[contains(normalize-space(text()),\"" + elementLabel + "\")]", "XPATH")) {
			elementXPath = "//label[contains(normalize-space(text()),\"" + elementLabel + "\")]";
		} else if (isElementExists(webDriver, "//span[contains(normalize-space(text()),\"" + elementLabel + "\")]",	"XPATH")) {
			elementXPath = "//span[contains(normalize-space(text()),\"" + elementLabel + "\")]";
		} else if (isElementExists(webDriver, "//a[contains(normalize-space(text()),\"" + elementLabel + "\")]", "XPATH")) {
			elementXPath = "//a[contains(normalize-space(text()),\"" + elementLabel + "\")]";
		} else if (isElementExists(webDriver, "//td[contains(normalize-space(text()),\"" + elementLabel + "\")]", "XPATH")) {
			elementXPath = "//td[contains(normalize-space(text()),\"" + elementLabel + "\")]";
		} else if (isElementExists(webDriver, "//th[contains(normalize-space(text()),\"" + elementLabel + "\")]", "XPATH")) {
			elementXPath = "//th[contains(normalize-space(text()),\"" + elementLabel + "\")]";
		} else {
			elementExists = false;
		}

		if (!elementExists) {
			CTLogger.writeToLog("SalesforceLibrary", "getElementXPath() ",
					"Xpath of " + elementLabel + " : " + elementXPath);
			extentTest.log(LogStatus.FAIL, "Unable to locate field : " + elementLabel,	extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver,
							propertyFileReader.getValue("IMAGE_PATH") + elementLabel)));
		}

		switch (elementType) {
		case "LABEL":
			elementXPath = elementXPath.trim();
			break;
		default:
			elementXPath += getElementXPath(elementType);
		}
		CTLogger.writeToLog("SalesforceLibrary", "getElementXPath() ", "Xpath of " + elementLabel + " : " + elementXPath);
		return elementXPath;
	}

	private boolean isElementExists(WebDriver webDriver, String objectDetails, String objectSelector) {
		return (!webElementDetails.getElements(webDriver, objectDetails, objectSelector).isEmpty());
	}

	private String getElementXPath(String elementType) {
		switch (elementType) {
		case "PICKLIST":
			return "/following::select[1]";
		case "MULISELECTPICKLIST":
			return "/following::select[@multiple='multiple' and not(contains(@style, 'display:none'))][1]";
		case "POPUPPICKLIST":
			return "/following::a[contains(@href, 'openPopupFocus') and contains(@title, 'Combo')][1]";
		case "TEXTBOX":
			return "/following::input[@type='text'][1]";
		case "TEXTAREA":
			return "/following::textarea[1]";
		case "CHECKBOX":
			return "/following::input[@type='checkbox'][1]";
		case "RADIOBUTTON":
			return "/following::input[@type='radio'][1]";
		case "IMAGE":
			return "/following::img[1]";
		case "LINK":
			return "/following::a[1]";
		case "LOOKUP":
			return "/following::a[contains(@href, 'openLookup') and contains(@title, 'Lookup')][1]";
		case "DATALABEL":
			return "/following::td[1]";
		default:
			return "";
		}
	}
}