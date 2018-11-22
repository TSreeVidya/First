package com.accenture.aaft.selenium.driver;

import java.util.Map;
import org.openqa.selenium.WebDriver;
import com.accenture.aaft.propertyreader.PropertyFileReader;
import com.accenture.aaft.report.DateUtil;
import com.accenture.aaft.report.ExtentTestManager;
import com.accenture.aaft.selenium.library.ChangeUser;
import com.accenture.aaft.selenium.library.CheckboxSelect;
import com.accenture.aaft.selenium.library.Click;
import com.accenture.aaft.selenium.library.Click2;
import com.accenture.aaft.selenium.library.ClickCP;
import com.accenture.aaft.selenium.library.ClickLink;
import com.accenture.aaft.selenium.library.CloseChildWindow;
import com.accenture.aaft.selenium.library.CompareToStore;
import com.accenture.aaft.selenium.library.FindAndClickSFAccount;
import com.accenture.aaft.selenium.library.FindAndClickSFBRE;
import com.accenture.aaft.selenium.library.FindAndClickSFCase;
import com.accenture.aaft.selenium.library.FindAndClickSFCompetitiveMarketName;
import com.accenture.aaft.selenium.library.Input;
import com.accenture.aaft.selenium.library.InputCP;
import com.accenture.aaft.selenium.library.InputWN;
import com.accenture.aaft.selenium.library.IsAlertPresent;
import com.accenture.aaft.selenium.library.LaunchUrl;
import com.accenture.aaft.selenium.library.LoginSF;
import com.accenture.aaft.selenium.library.Logout;
import com.accenture.aaft.selenium.library.MatchElementCount;
import com.accenture.aaft.selenium.library.MatchElementCountToValue;
import com.accenture.aaft.selenium.library.MouseOverAndClick;
import com.accenture.aaft.selenium.library.Multipurpose;
import com.accenture.aaft.selenium.library.QuoteCreation;
import com.accenture.aaft.selenium.library.RiskScreening;
import com.accenture.aaft.selenium.library.SearchItem;
import com.accenture.aaft.selenium.library.SelectByIndex;
import com.accenture.aaft.selenium.library.SelectFrame;
import com.accenture.aaft.selenium.library.SelectFromList;
import com.accenture.aaft.selenium.library.SelectFromList2;
import com.accenture.aaft.selenium.library.SelectLookup;
import com.accenture.aaft.selenium.library.SelectWindow;
import com.accenture.aaft.selenium.library.ShowLinkPresent;
import com.accenture.aaft.selenium.library.Store;
import com.accenture.aaft.selenium.library.SubOpportunityCreation;
import com.accenture.aaft.selenium.library.UseFromStore;
import com.accenture.aaft.selenium.library.VerifyExists;
import com.accenture.aaft.selenium.library.VerifyNotExists;
import com.accenture.aaft.selenium.library.VerifySelectedValueinList;
import com.accenture.aaft.selenium.library.VerifyState;
import com.accenture.aaft.selenium.library.VerifyText;
import com.accenture.aaft.selenium.library.WaitTime;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Class is used to execute keyword based action
 *
 * @author vijay.venkatappa
 *
 */
public class KeywordExecutor {

	WebDriver webDriver;

	/**
	 * Method is used to execute keyword based action
	 *
	 * @param webDriver
	 *            - represents WebDriver
	 * @param keyword
	 *            - represents keyword name
	 * @param controlName
	 *            - represents control name
	 * @param param1
	 *            - represents placeholder name
	 * @param param2
	 *            - represents placeholder name
	 * @param param3
	 *            - represents placeholder name
	 * @param condition
	 *            - represents condition value
	 * @param testStepNo
	 *            - represents test step number
	 * @param testStepDetails
	 *            - represents test step details
	 * @param screen
	 *            - represents screen name
	 * @param mainWindow
	 *            - represents main window name
	 * @param inputValue
	 *            - represents input value
	 * @param extentTest
	 *            - represents ExtentTest
	 * @param objectName
	 *            - represents object name
	 * @param selector
	 *            - represents selector type
	 * @return status
	 */
	public String executeKeyword(WebDriver webDriver, String keyword, String controlName, String param1, String param2,
			String param3, String condition, String testStepNo, String testStepDetails, String screen,
			String mainWindow, String inputValue, ExtentTest extentTest, String objectName, String selector) {
		return executeKeyword(webDriver, keyword, controlName, param1, param2, param3, condition, testStepNo,
				testStepDetails, screen, mainWindow, inputValue, extentTest, objectName, selector, null);
	}

	/**
	 * Method is used to execute keyword based action
	 *
	 * @param webDriver
	 *            - represents WebDriver
	 * @param keyword
	 *            - represents keyword name
	 * @param controlName
	 *            - represents control name
	 * @param param1
	 *            - represents placeholder name
	 * @param param2
	 *            - represents placeholder name
	 * @param param3
	 *            - represents placeholder name
	 * @param condition
	 *            - represents condition value
	 * @param testStepNo
	 *            - represents test step number
	 * @param testStepDetails
	 *            - represents test step details
	 * @param screen
	 *            - represents screen name
	 * @param mainWindow
	 *            - represents main window name
	 * @param inputValue
	 *            - represents input value
	 * @param extentTest
	 *            - represents ExtentTest
	 * @param objectName
	 *            - represents object name
	 * @param selector
	 *            - represents selector type
	 * @param hmTempStore
	 *            - represents temporary store
	 * @return status
	 */
	public String executeKeyword(WebDriver webDriver, String keyword, String controlName, String param1, String param2,
			String param3, String condition, String testStepNo, String testStepDetails, String screen,
			String mainWindow, String inputValue, ExtentTest extentTest, String objectName, String selector,
			Map<String, String> hmTempStore) {

		String result = "false";
		PropertyFileReader propertyFileReader = new PropertyFileReader();

		if (keyword.equalsIgnoreCase("LAUNCH")) {

			LaunchUrl launchUrl = new LaunchUrl();
			result = launchUrl.launchUrl(webDriver, inputValue, extentTest);

			System.out.println(result + " LAUNCH");
		}

		else if (keyword.equalsIgnoreCase("LAUNCHAPP")) {
			result = "true";
			System.out.println(result + " LAUNCHAPP");
		}

		else if (keyword.equalsIgnoreCase("INPUT")) {

			Input input = new Input();
			result = input.enterText(webDriver, objectName, selector, inputValue, extentTest, controlName);

			System.out.println(result + " INPUT");
		} else if (keyword.equalsIgnoreCase("INPUTCP")) {

			InputCP input = new InputCP();
			result = input.enterText(webDriver, objectName, selector, inputValue, extentTest, controlName);

			System.out.println(result + " INPUTCP");
		}

		else if (keyword.equalsIgnoreCase("MATCHELEMENTCOUNT")) {

			MatchElementCount input = new MatchElementCount();
			result = input.matchElementCount(webDriver, objectName, selector, inputValue, extentTest, controlName);

			System.out.println(result + " MATCHELEMENTCOUNT");
		}

		else if (keyword.equalsIgnoreCase("MATCHELEMENTCOUNTTOVALUE")) {

			MatchElementCountToValue input = new MatchElementCountToValue();
			result = input.matchElementCountToValue(webDriver, objectName, selector, inputValue, extentTest,
					controlName);

			System.out.println(result + " MATCHELEMENTCOUNTTOVALUE");
		}

		else if (keyword.equalsIgnoreCase("LOGINSF")) {

			LoginSF login = new LoginSF();
			result = login.login(webDriver, extentTest, controlName);

			System.out.println(result + " LOGINSF");
		}
		//else if written me for logout functionality
		else if (keyword.equalsIgnoreCase("LOGOUT")) {

			Logout logout = new Logout();
			result = logout.Logout(webDriver, extentTest, controlName);

			System.out.println(result + " LOGOUT");
		}
		else if (keyword.equalsIgnoreCase("RISKSCREENING")) {

			RiskScreening Riskscreening = new RiskScreening();
			result = Riskscreening.Riskscreening(webDriver, inputValue,controlName,extentTest);

			System.out.println(result + " RISKSCREENING");
		}
		
		else if (keyword.equalsIgnoreCase("INPUTWN")) {

			InputWN inputWN = new InputWN();
			result = inputWN.inputWN(webDriver, extentTest);
			System.out.println(result + " INPUTWN");
		} else if (keyword.equalsIgnoreCase("STORE")) {

			Store input = new Store();
			result = input.store(webDriver, objectName, selector, inputValue, extentTest, controlName, hmTempStore);
			System.out.println(result + " STORE");
		}

		else if (keyword.equalsIgnoreCase("CHECKBOXSELECT")) {

			CheckboxSelect checkboxSelect = new CheckboxSelect();
			result = checkboxSelect.checkboxSelect(webDriver, objectName, selector, inputValue, extentTest, controlName);
			System.out.println(result + " CHECKBOXSELECT");
		}
		
		else if (keyword.equalsIgnoreCase("USEFROMSTORE")) {

			UseFromStore input = new UseFromStore();
			result = input.useFromStore(webDriver, objectName, selector, inputValue, extentTest, controlName,
					hmTempStore);

			System.out.println(result + " USEFROMSTORE");
		}

		else if (keyword.equalsIgnoreCase("COMPARETOSTORE")) {

			CompareToStore input = new CompareToStore();
			result = input.compareToStore(webDriver, objectName, selector, inputValue, extentTest, controlName,
					hmTempStore);

			System.out.println(result + " COMPARETOSTORE");
		} else if (keyword.equalsIgnoreCase("CLICK")) {

			Click click = new Click();
			result = click.click(webDriver, objectName, selector, extentTest, controlName);
			System.out.println(result + " CLICK");
		} else if (keyword.equalsIgnoreCase("SHOWLINK")) {

			ShowLinkPresent slp = new ShowLinkPresent();
			result = slp.showLinkPresent(webDriver, extentTest);
			System.out.println(result + " SHOWLINK");
		} else if (keyword.equalsIgnoreCase("CLICKCP")) {

			ClickCP click = new ClickCP();
			result = click.click(webDriver, objectName, selector, extentTest, controlName);
			System.out.println(result + " CLICKCP");
		} else if (keyword.equalsIgnoreCase("WAIT")) {

			WaitTime waitTime = new WaitTime();
			result = waitTime.waitTime(inputValue, extentTest, controlName);
			System.out.println(result + " WAIT");
		}

		else if (keyword.equalsIgnoreCase("CLICKLINK")) {

			ClickLink clickLink = new ClickLink();
			result = clickLink.clickLink(webDriver, objectName, selector, extentTest, controlName);
			System.out.println(result + " CLICKLINK");
		}

		else if (keyword.equalsIgnoreCase("SELECTFRAME")) {

			SelectFrame selectFrame = new SelectFrame();
			result = selectFrame.selectFrame(webDriver, objectName, selector, extentTest, controlName);
			System.out.println(result + " SELECTFRAME");
		} else if (keyword.equalsIgnoreCase("SELECTWINDOW")) {

			SelectWindow selectWindow = new SelectWindow();
			result = selectWindow.selectWindow(webDriver, inputValue, extentTest, controlName);
			System.out.println(result + " SELECTWINDOW");
		} else if (keyword.equalsIgnoreCase("SELECTLOOKUP")) {
			SelectLookup selectLookup = new SelectLookup();
			result = selectLookup.selectLookup(webDriver, inputValue, extentTest);
			webDriver.switchTo().defaultContent();
			System.out.println(result + " SELECTLOOKUP");
		} else if (keyword.equalsIgnoreCase("MOUSEOVER")) {

			MouseOverAndClick mouseOver = new MouseOverAndClick();
			result = mouseOver.mouseOverAndSelect(webDriver, objectName, selector, inputValue, extentTest, controlName);
			System.out.println(result + " MOUSEOVER");
		} else if (keyword.equalsIgnoreCase("VERIFYTEXT")) {

			VerifyText verifyText = new VerifyText();
			result = verifyText.verifyText(webDriver, objectName, selector, inputValue, extentTest, controlName);
			System.out.println(result + " VERIFYTEXT");
		} else if (keyword.equalsIgnoreCase("VERIFYEXISTS")) {

			VerifyExists verifyElement = new VerifyExists();
			result = verifyElement.verifyExists(webDriver, objectName, selector, inputValue, extentTest, controlName);
			System.out.println(result + " VERIFYEXISTS");
		} else if (keyword.equalsIgnoreCase("CLICK2")) {

			Click2 click2 = new Click2();
			result = click2.click2(webDriver, extentTest);
			System.out.println(result + " CLICK2");
		}

		else if (keyword.equalsIgnoreCase("MULTIPURPOSE")) {

			Multipurpose multi = new Multipurpose();
			result = multi.multipurpose(webDriver, inputValue, extentTest);
			System.out.println(result + " MULTIPURPOSE");
		} 
		else if (keyword.equalsIgnoreCase("SUBOPPORTUNITYCREATION")) {

			SubOpportunityCreation Subopp = new SubOpportunityCreation();
			result = Subopp.subOpportunityCreation(webDriver, inputValue, extentTest);
			System.out.println(result + " SUBOPPORTUNITYCREATION");
		} 
		
		else if (keyword.equalsIgnoreCase("QUOTECREATION")) {

			QuoteCreation Quote = new QuoteCreation();
			result = Quote.quoteCreation(webDriver, inputValue, extentTest);
			System.out.println(result + " QUOTECREATION");
		} 
		
		else if (keyword.equalsIgnoreCase("VERIFYNOTEXISTS")) {

			VerifyNotExists verifyElement = new VerifyNotExists();
			result = verifyElement.verifyNotExists(webDriver, objectName, selector, inputValue, extentTest,
					controlName);
			System.out.println(result + " VERIFYNOTEXISTS");
		} else if (keyword.equalsIgnoreCase("VERIFYSTATE")) {

			VerifyState verifyElement = new VerifyState();
			result = verifyElement.verifyState(webDriver, objectName, selector, inputValue, extentTest, controlName);
			System.out.println(result + " VERIFYSTATE");
		}

		else if (keyword.equalsIgnoreCase("SELECTFROMLIST")) {

			SelectFromList selectFromList = new SelectFromList();
			result = selectFromList.selectFromList(webDriver, objectName, selector, inputValue, "selection", extentTest,
					controlName);
			System.out.println(result + " SELECTFROMLIST");
		} else if (keyword.equalsIgnoreCase("SELECTFROMLIST2")) {

			SelectFromList2 selectFromList2 = new SelectFromList2();
			result = selectFromList2.selectFromList2(webDriver, objectName, selector, inputValue, "selection",
					extentTest, controlName);
			System.out.println(result + " SELECTFROMLIST2");
		}

		else if (keyword.equalsIgnoreCase("SELECTBYINDEX")) {

			SelectByIndex selectByIndex = new SelectByIndex();
			result = selectByIndex.selectByIndex(webDriver, objectName, selector, inputValue, extentTest, controlName);
			System.out.println(result + " SELECTBYINDEX");
		} else if (keyword.equalsIgnoreCase("CHANGEUSER")) {

			ChangeUser changeUser = new ChangeUser();
			System.out.println("calling CHANGEUSER");
			result = changeUser.changeUser(webDriver, inputValue, extentTest);
			System.out.println(result + " CHANGEUSER");
			
			
		} else if (keyword.equalsIgnoreCase("FINDANDCLICKSFOPPORTUNITY")) {

			/*FindAndClickSFOpportunity findAndClickSFOpportunity = new FindAndClickSFOpportunity();
			System.out.println("calling FINDANDCLICKSFOPPORTUNITY");
			result = findAndClickSFOpportunity.findandclicksfopportunity(webDriver, inputValue, extentTest,
					hmTempStore);
			System.out.println(result + " FINDANDCLICKSFOPPORTUNITY");*/
			
			//Quick Search
			
			SearchItem searchItem = new SearchItem();
			System.out.println("calling FINDANDCLICKSFOPPORTUNITY");
			result = searchItem.searchItem(webDriver, inputValue, extentTest,
					hmTempStore);
			System.out.println(result + " FINDANDCLICKSFOPPORTUNITY");
			
			
			
		} else if (keyword.equalsIgnoreCase("FINDANDCLICKSFCASE")) {

			FindAndClickSFCase findAndClickSFCase = new FindAndClickSFCase();
			System.out.println("calling FINDANDCLICKSFCASE");
			result = findAndClickSFCase.findAndClickSFCase(webDriver, inputValue, extentTest, hmTempStore);
			System.out.println(result + " FINDANDCLICKSFCASE");
		} else if (keyword.equalsIgnoreCase("FINDANDCLICKSFACCOUNT")) {

			FindAndClickSFAccount findAndClickSFAccount = new FindAndClickSFAccount();
			System.out.println("calling FINDANDCLICKSFACCOUNT");
			result = findAndClickSFAccount.findAndClickSFAccount(webDriver, inputValue, extentTest, hmTempStore);
			System.out.println(result + " FINDANDCLICKSFACCOUNT");
		} else if (keyword.equalsIgnoreCase("FINDANDCLICKSFBRE")) {
			FindAndClickSFBRE findAndClickSFBRE = new FindAndClickSFBRE();
			System.out.println("calling FINDANDCLICKSFBRE");
			result = findAndClickSFBRE.findAndClickSFBRE(webDriver, inputValue, extentTest);
			System.out.println(result + " FINDANDCLICKSFBRE");
		} else if (keyword.equalsIgnoreCase("FINDANDCLICKSFCMMNAME")) {
			FindAndClickSFCompetitiveMarketName FindAndClickSFCompetitiveMarketName = new FindAndClickSFCompetitiveMarketName();
			System.out.println("calling FINDANDCLICKSFCMMNAME");
			result = FindAndClickSFCompetitiveMarketName.FindAndClickSFCompetitiveMarketName(webDriver, inputValue,
					extentTest);
			System.out.println(result + " FINDANDCLICKSFCMMNAME");
		} else if (keyword.equalsIgnoreCase("SWITCHTOPARENTWINDOW")) {

			CloseChildWindow closeChildWindow = new CloseChildWindow();
			System.out.println("calling CloseChildWindow");
			result = closeChildWindow.closeChildWindow(webDriver, inputValue, extentTest);
			System.out.println(result + " CloseChildWindow");
		} else if (keyword.equalsIgnoreCase("VERIFYSELECTEDVALUEINLIST")) {

			VerifySelectedValueinList verifySelectedValueinList = new VerifySelectedValueinList();
			result = verifySelectedValueinList.verifySelectedValueinList(webDriver, objectName, selector, inputValue,
					extentTest, controlName);
			System.out.println(result + " VERIFYSELECTEDVALUEINLIST");
		} else if (keyword.equalsIgnoreCase("ISALERTPRESENTACCEPT")) {

			IsAlertPresent isAlertPresent = new IsAlertPresent();
			result = isAlertPresent.isAlertPresent(webDriver, extentTest);
			System.out.println(result + " ISALERTPRESENTACCEPT");
		}

		if (screen == null)
			screen = "";
		if (result.equalsIgnoreCase("true")
				&& (ExecuteScript.captureScreenPass || screen.toLowerCase().startsWith("y"))) {
			try {
				extentTest.log(LogStatus.INFO,
						extentTest.addScreenCapture(ExtentTestManager.captureScreen(webDriver,
								propertyFileReader.getValue("IMAGE_PATH") + ExecuteScript.strScriptName + "_"
										+ controlName + DateUtil.now("d_MMM_yyyy_H.mm.ss.SSS"))));
			} catch (Exception e) {
			}
		}

		return result;
	}

}
