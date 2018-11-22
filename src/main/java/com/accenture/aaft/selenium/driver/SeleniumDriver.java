package com.accenture.aaft.selenium.driver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.accenture.aaft.excel.utility.ExcelTestScript;
import com.accenture.aaft.logger.CTLogger;
import com.accenture.aaft.propertyreader.ConfigParser;
import com.accenture.aaft.propertyreader.PropertyFileReader;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

/**
 * Class is used to get selenium web driver
 *
 * @author vijay.venkatappa
 *
 */
@SuppressWarnings({ "unused", "deprecation", "rawtypes" })
public class SeleniumDriver {

	
	public static WebDriver getWebDriver(String browserType, String appPath) {
		WebDriver webDriver = null;
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		DesiredCapabilities capabilities = new DesiredCapabilities();
		URL url = null;

		if (browserType.toLowerCase().contains("appium")) {

			if (appPath != null) {

				String ip = propertyFileReader.getValue("APPIUM_IP");
				String port = propertyFileReader.getValue("APPIUM_PORT");

				ip = (ip == null || ip.isEmpty()) ? "127.0.0.1" : ip;
				port = (port == null || port.isEmpty()) ? "4723" : port;

				if (browserType.toLowerCase().contains("android")) {
					capabilities.setCapability("device", "Android");
					capabilities.setCapability("deviceName", "Android");
					capabilities.setCapability("platformName", "Android");
				} else {
					capabilities.setCapability("device", "iOS");
					capabilities.setCapability("deviceName", "iOS");
					capabilities.setCapability("platformName", "iOS");
				}

				capabilities.setCapability("app", appPath);

				try {
					url = new URL("http://" + ip + ":" + port + "/wd/hub");

					if (browserType.toLowerCase().contains("android")) {
						webDriver = new AndroidDriver(url, capabilities);
					} else {
						webDriver = new IOSDriver(url, capabilities);
					}

				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}

		return webDriver;
	}

	/**
	 * Method is used to get selenium driver for browser or Native testing in cloud
	 * platform
	 *
	 * @param cloudPlatformName
	 *            - represents cloud platform name
	 * @param capabilities
	 *            - represents DesiredCapabilities of browser
	 * @return WebDriver
	 */
	public static WebDriver getWebDriver(String cloudPlatformName, DesiredCapabilities capabilities) {
		WebDriver webDriver = null;
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		URL url = null;

		String userName = propertyFileReader.getValue(cloudPlatformName.toUpperCase() + "_USERNAME");
		String accessKey = propertyFileReader.getValue(cloudPlatformName.toUpperCase() + "_ACCESSKEY");
		String host = propertyFileReader.getValue(cloudPlatformName.toUpperCase() + "_HOST");

		if (userName != null && accessKey != null && host != null) {
			try {
				url = new URL("http://" + userName + ":" + accessKey + "@" + host + "/wd/hub");
				if (capabilities.getCapability("app") != null) {
					String appPath = (String) capabilities.getCapability("app");
					if (appPath.contains(".apk")) {
						webDriver = new AndroidDriver(url, capabilities);
					} else {
						webDriver = new IOSDriver(url, capabilities);
					}
				} else {
					webDriver = new RemoteWebDriver(url, capabilities);
				}

			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		return webDriver;
	}

	/**
	 * Method is used to get selenium driver for browser or Native testing in cloud
	 * platform
	 *
	 * @param cloudPlatformName
	 *            - represents cloud platform name
	 * @param capabilities
	 *            - represents DesiredCapabilities of browser
	 * @return WebDriver
	 */
	public static WebDriver getRemoteWebDriver(String cloudPlatformName, String os, String version, String browser,
			String scriptName) {
		WebDriver webDriver = null;
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		URL url = null;

		String userName = propertyFileReader.getValue(cloudPlatformName.toUpperCase() + "_USERNAME");
		String accessKey = propertyFileReader.getValue(cloudPlatformName.toUpperCase() + "_ACCESSKEY");
		String host = propertyFileReader.getValue(cloudPlatformName.toUpperCase() + "_HOST");

		if (userName != null && accessKey != null && host != null) {
			try {
				DesiredCapabilities capabilities = getDesiredCapabilities(os, version, browser, scriptName);
				webDriver = new RemoteWebDriver(
						new URL("http://" + userName + ":" + accessKey + "@localhost:4445/wd/hub"), capabilities);

			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		return webDriver;
	}

	public static WebDriver getRemoteWebDriver(String cloudPlatformName, String os, String version, String browser,
			String device, String deviceOrientation, String appiumVersion, String app, String scriptName) {
		WebDriver webDriver = null;
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		URL url = null;

		String userName = propertyFileReader.getValue("SAUCELABS_USERNAME");
		String accessKey = propertyFileReader.getValue("SAUCELABS_ACCESSKEY");
		String host = propertyFileReader.getValue("SAUCELABS_HOST");

		if (userName != null && accessKey != null && host != null) {
			try {
				DesiredCapabilities capabilities = getDesiredCapabilities(os, version, browser, device,
						deviceOrientation, appiumVersion, app, scriptName);
				webDriver = new RemoteWebDriver(
						new URL("http://" + userName + ":" + accessKey + "@" + host + "/wd/hub"), capabilities);

			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		return webDriver;
	}

	public static DesiredCapabilities getDesiredCapabilities(String os, String version, String browser,
			String scriptName) {
		DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities.setCapability("platform", os);
		capabilities.setCapability("version", version);
		capabilities.setCapability("browserName", browser);
		capabilities.setCapability("name", scriptName + "-" + os + "-" + browser + "-" + version);
		return capabilities;

	}

	public static DesiredCapabilities getDesiredCapabilities(String os, String version, String browser, String device,
			String deviceOrientation, String appiumVersion, String app, String scriptName) {
		DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities.setCapability("platformName", os);
		capabilities.setCapability("platformVersion", version);
		capabilities.setCapability("browserName", browser);
		capabilities.setCapability("deviceName", device);
		capabilities.setCapability("deviceOrientation", deviceOrientation);
		capabilities.setCapability("appiumVersion", appiumVersion);
		if (app != null && !app.trim().isEmpty()) {
			capabilities.setCapability("app", app);
		}
		capabilities.setCapability("name", scriptName + "-" + os + "-" + browser + "-" + version);
		return capabilities;

	}

	/**
	 * Method is used to get web driver
	 *
	 * @param browserType
	 *            - represents browser type
	 * @return WebDriver
	 */
	public static WebDriver getWebDriver(String os, String version, String browserType)

	{
		WebDriver webDriver = null;
		PropertyFileReader propertyFileReader = new PropertyFileReader();

		if (browserType.toLowerCase().contains("browserstack")) {
			DesiredCapabilities caps = null;
			String platform = propertyFileReader.getValue("PLATFORM");
			if (platform.toLowerCase().contains("android")) {
				caps = DesiredCapabilities.android();

			} else if (platform.toLowerCase().contains("iphone") || platform.toLowerCase().contains("mac")
					|| platform.toLowerCase().contains("ios")) {
				caps = DesiredCapabilities.iphone();
			}
			caps.setCapability("browserName", propertyFileReader.getValue("CLOUD_BROWSER_NAME"));
			caps.setCapability("platform", propertyFileReader.getValue("PLATFORM"));
			caps.setCapability("device", propertyFileReader.getValue("DEVICE_NAME"));
			return SeleniumDriver.getWebDriver("browserstack", caps);
		}

		if (browserType.toLowerCase().contains("saucelabs")) {
			DesiredCapabilities caps = null;

			if (browserType.indexOf("_") > -1) {
				Map<String, String> capabilitites = ConfigParser.configMap.get(browserType);

				String[] metaDetails = ConfigParser.configMetaDataMap.get(browserType);

				if (metaDetails[1].equalsIgnoreCase("saucelabs") && capabilitites != null) {

					String platform = capabilitites.get("platformName");
					if (platform != null && platform.equalsIgnoreCase("Android")) {
						caps = DesiredCapabilities.android();
					} else if (platform != null && platform.equalsIgnoreCase("iOS")) {
						caps = DesiredCapabilities.iphone();
					} else {
						caps = new DesiredCapabilities();
					}

					for (Iterator<String> it = capabilitites.keySet().iterator(); it.hasNext();) {
						String key = it.next();
						caps.setCapability(key, capabilitites.get(key));
					}

					return SeleniumDriver.getWebDriver("saucelabs", caps);
				} else {
					System.out.println("[ERROR]: attribute 'type' has a different value for the particular device");
				}
			}
			return null;
		}

		if (browserType.toLowerCase().contains("fire")) {

			CTLogger.writeToLog("Before getting firefox driver");
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			String fireFoxBrowser = propertyFileReader.getValue(FirefoxDriver.BINARY);

			if (fireFoxBrowser == null || fireFoxBrowser.trim().equalsIgnoreCase("")) {
				fireFoxBrowser = "";
				propertyFileReader.setValue(FirefoxDriver.BINARY, fireFoxBrowser);
			} else {
				capabilities.setCapability(FirefoxDriver.BINARY, fireFoxBrowser);
			}

			try {
				capabilities.setCapability("platform", os);
				capabilities.setCapability("version", version);
				webDriver = new FirefoxDriver(capabilities);
			} catch (Exception e) {
				e.printStackTrace();
			}
			CTLogger.writeToLog("After getting fire driver");

		} else if ((browserType.toLowerCase().contains("safari"))) {

			DesiredCapabilities capabilities = DesiredCapabilities.safari();
			capabilities.setJavascriptEnabled(true);
			capabilities.setCapability("ignoreProtectedModeSettings", true);
			capabilities.setCapability("platform", os);
			capabilities.setCapability("version", version);
			webDriver = new SafariDriver(capabilities);

		} else if ((browserType.toLowerCase().contains("opera"))) {

			CTLogger.writeToLog("Before getting opera driver");
			String operaBrowser = propertyFileReader.getValue("opera_binary");
			DesiredCapabilities capabilities = DesiredCapabilities.opera();

			if (operaBrowser == null || operaBrowser.trim().equalsIgnoreCase("")) {
				operaBrowser = "";
				propertyFileReader.setValue("opera_binary", operaBrowser);

			} else {
				capabilities.setCapability("opera_binary", operaBrowser);
			}

			try {
				capabilities.setCapability("platform", os);
				capabilities.setCapability("version", version);
				webDriver = new OperaDriver(capabilities);
			} catch (Exception e) {
				e.printStackTrace();
			}
			CTLogger.writeToLog("After getting opera driver");

		} else if ((browserType.toLowerCase().contains("google") || ((browserType.toLowerCase().contains("chrome"))))) {

			CTLogger.writeToLog("Before getting chrome driver");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("browserName", "chrome");

			String chromeDriverLoc = propertyFileReader.getValue("CHROME_DRIVER_LOCATION");
			ChromeOptions options = new ChromeOptions();
			// options.setExperimentalOption("excludeSwitches", Arrays.asList("test-type"));
		    options.addArguments("chrome.switches", "--disable-extensions");
			System.setProperty("webdriver.chrome.driver", chromeDriverLoc);
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			capabilities.setCapability("platform", os);
			capabilities.setCapability("version", version);
			options.addArguments("chrome.switches", "--disable-extensions");
			options.addArguments("--disable-notifications");
			// Browserchrome			
			//options.addArguments("headless"); options.addArguments("window-size=1200x600");

			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			options.setExperimentalOption("prefs", prefs);

			try {
				webDriver = new ChromeDriver(capabilities);
				// webDriver.manage().deleteAllCookies();
				/*
				 * Set<Cookie> allCookies = webDriver.manage().getCookies(); for (Cookie cookie
				 * : allCookies) { webDriver.manage().deleteCookieNamed(cookie.getName()); }
				 */

				// webDriver = new ChromeDriver();

			} catch (Exception e) {
				e.printStackTrace();
			}
			CTLogger.writeToLog("After getting chrome driver");

		} else if ((browserType.toLowerCase().contains("ie")) || ((browserType.toLowerCase().contains("internet")))) {

			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability("ignoreProtectedModeSettings", true);
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			String ieDriverLoc = propertyFileReader.getValue("IE_DRIVER_LOCATION");
			File file = new File(ieDriverLoc);
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			try {
				capabilities.setCapability("platform", os);
				capabilities.setCapability("version", version);
				// [Varija] added to ignore default zoom settings and button click enabling
				capabilities.setCapability("nativeEvents", false);
				webDriver = new InternetExplorerDriver(capabilities);
				webDriver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		else if (browserType.toLowerCase().contains("androiddevice")
				|| browserType.toLowerCase().contains("iosdevice")) {

			String ip = propertyFileReader.getValue("APPIUM_IP");
			String port = propertyFileReader.getValue("APPIUM_PORT");

			ip = (ip == null) ? "127.0.0.1" : ip;
			port = (port == null) ? "4723" : port;

			DesiredCapabilities capabilities = new DesiredCapabilities();

			if (browserType.toLowerCase().contains("androiddevice")) {
				capabilities.setCapability("browserName", "chrome");
				capabilities.setCapability("deviceName", "android");
				capabilities.setCapability("platformName", "Android");
			} else {
				capabilities.setCapability("browserName", "Safari");
				capabilities.setCapability("safariAllowPopups", "true");
				capabilities.setCapability("deviceName", "iOS");
				capabilities.setCapability("platformName", "iOS");
			}

			capabilities.setCapability("orientation", "PORTRAIT");

			URL u = null;
			try {
				u = new URL("http://" + ip + ":" + port + "/wd/hub");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			webDriver = new RemoteWebDriver(u, capabilities);
		}
		return webDriver;
	}

}
