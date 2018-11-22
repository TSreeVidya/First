package com.accenture.aaft.propertyreader;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import com.accenture.aaft.vo.SeleniumConfigXmlReaderVO;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class SeleniumConfigXmlReader {

	/**
	 * Method is used read the OS, browser and version combination
	 * @param xml
	 * @return LinkedHashMap<String, SeleniunConfigXmlReaderVO>
	 * @throws Exception
	 */
	public LinkedHashMap<String, SeleniumConfigXmlReaderVO> read(String xml,String executionType) throws Exception {
		LinkedHashMap<String, SeleniumConfigXmlReaderVO> map = new LinkedHashMap<>();
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(xml);
		Element css = doc.getDocumentElement();
		NodeList nodeList = css.getElementsByTagName("name");

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			NamedNodeMap attribute = node.getAttributes();
			Node nodeValue = attribute.getNamedItem("value");
			Node nodeValue1 = attribute.getNamedItem("for");
			Node nodeValue2 = attribute.getNamedItem("type");
			String value = nodeValue.getNodeValue();
			
			String os = "";
			String browserVersion = "";
			String browserName = "";
			String execute = "";
			String appiumVersion = "";
			String deviceName = "";
			String deviceOrientation = "";
			String platformVersion = "";
			String platformName = "";
			String app = "";
			Element rule = (Element) nodeList.item(i);
			
			if(executionType.equalsIgnoreCase("saucelabs")){
				if (null != rule.getElementsByTagName("os").item(0)
						&& null != rule.getElementsByTagName("browserVersion").item(0)
						&& null != rule.getElementsByTagName("browserName").item(0)) {
					 os = rule.getElementsByTagName("os").item(0).getTextContent();
					 browserVersion = rule.getElementsByTagName("browserVersion").item(0).getTextContent();
					 browserName = rule.getElementsByTagName("browserName").item(0).getTextContent();
					//String execute = rule.getElementsByTagName("execute").item(0).getTextContent();
					/*SeleniunConfigXmlReaderVO seleniunConfigXmlReaderVO = new SeleniunConfigXmlReaderVO();
					seleniunConfigXmlReaderVO.setBrowserName(browserName);
					seleniunConfigXmlReaderVO.setOs(os);
					seleniunConfigXmlReaderVO.setBrowserVersion(browserVersion);*/

					/*if (nodeValue1.getNodeValue().equalsIgnoreCase("browser")
							&& nodeValue2.getNodeValue().equalsIgnoreCase("saucelabs")) {
						if (!os.equals("") && !browserVersion.equals("") && !browserName.equals("")) {
							map.put(value, seleniunConfigXmlReaderVO);
						}
					}*/
				}	
			} else if (executionType.equalsIgnoreCase("saucelabs-app")) {
				if (null != rule.getElementsByTagName("browserName").item(0)
						&& null != rule.getElementsByTagName("appiumVersion").item(0)
						&& null != rule.getElementsByTagName("deviceName").item(0)
						&& null != rule.getElementsByTagName("deviceOrientation").item(0)
						&& null != rule.getElementsByTagName("platformVersion").item(0)
						&& null != rule.getElementsByTagName("platformName").item(0)
						&& null != rule.getElementsByTagName("app").item(0)) {
					 browserName = rule.getElementsByTagName("browserName").item(0).getTextContent();
					 appiumVersion = rule.getElementsByTagName("appiumVersion").item(0).getTextContent(); 
					 deviceName = rule.getElementsByTagName("deviceName").item(0).getTextContent(); 
					 deviceOrientation = rule.getElementsByTagName("deviceOrientation").item(0).getTextContent(); 
					 platformVersion = rule.getElementsByTagName("platformVersion").item(0).getTextContent(); 
					 platformName = rule.getElementsByTagName("platformName").item(0).getTextContent(); 
					 app = rule.getElementsByTagName("app").item(0).getTextContent(); 					 
				}
			} else if (executionType.equalsIgnoreCase("saucelabs-device")) {
				if (null != rule.getElementsByTagName("browserName").item(0)
						&& null != rule.getElementsByTagName("appiumVersion").item(0)
						&& null != rule.getElementsByTagName("deviceName").item(0)
						&& null != rule.getElementsByTagName("deviceOrientation").item(0)
						&& null != rule.getElementsByTagName("platformVersion").item(0)
						&& null != rule.getElementsByTagName("platformName").item(0)) {
					 browserName = rule.getElementsByTagName("browserName").item(0).getTextContent();
					 appiumVersion = rule.getElementsByTagName("appiumVersion").item(0).getTextContent(); 
					 deviceName = rule.getElementsByTagName("deviceName").item(0).getTextContent(); 
					 deviceOrientation = rule.getElementsByTagName("deviceOrientation").item(0).getTextContent(); 
					 platformVersion = rule.getElementsByTagName("platformVersion").item(0).getTextContent(); 
					 platformName = rule.getElementsByTagName("platformName").item(0).getTextContent(); 				 
				}
			}				
			
			else if(executionType.equalsIgnoreCase("local")){
				
			if (null != rule.getElementsByTagName("os").item(0)
					&& null != rule.getElementsByTagName("browserVersion").item(0)
					&& null != rule.getElementsByTagName("browserName").item(0)
					&& null != rule.getElementsByTagName("execute").item(0)) {
				 os = rule.getElementsByTagName("os").item(0).getTextContent();
				 browserVersion = rule.getElementsByTagName("browserVersion").item(0).getTextContent();
				 browserName = rule.getElementsByTagName("browserName").item(0).getTextContent();
				 execute = rule.getElementsByTagName("execute").item(0).getTextContent();
				/*SeleniunConfigXmlReaderVO seleniunConfigXmlReaderVO = new SeleniunConfigXmlReaderVO();
				seleniunConfigXmlReaderVO.setBrowserName(browserName);
				seleniunConfigXmlReaderVO.setOs(os);
				seleniunConfigXmlReaderVO.setExecute(execute);
				seleniunConfigXmlReaderVO.setBrowserVersion(browserVersion);*/

				/*if (nodeValue1.getNodeValue().equalsIgnoreCase("browser")
						&& nodeValue2.getNodeValue().equalsIgnoreCase("local")) {
					if (!browserName.equals("") && !execute.equals("") && execute.equals("Yes")) {
						map.put(value, seleniunConfigXmlReaderVO);
					}
				}*/
			}
		}
			SeleniumConfigXmlReaderVO seleniunConfigXmlReaderVO = new SeleniumConfigXmlReaderVO();
			seleniunConfigXmlReaderVO.setBrowserName(browserName);
			seleniunConfigXmlReaderVO.setOs(os);
			seleniunConfigXmlReaderVO.setExecute(execute);
			seleniunConfigXmlReaderVO.setBrowserVersion(browserVersion);
			seleniunConfigXmlReaderVO.setAppiumVersion(appiumVersion);
			seleniunConfigXmlReaderVO.setDeviceName(deviceName);
			seleniunConfigXmlReaderVO.setDeviceOrientation(deviceOrientation);
			seleniunConfigXmlReaderVO.setPlatformName(platformName);
			seleniunConfigXmlReaderVO.setPlatformVersion(platformVersion);
			seleniunConfigXmlReaderVO.setApp(app);
			
			
			if(nodeValue2.getNodeValue().equalsIgnoreCase("local")) {
				if (!browserName.equals("") && execute.equals("Yes")) {
					map.put(value, seleniunConfigXmlReaderVO);
				}
			} else{
				if(nodeValue1.getNodeValue().equalsIgnoreCase("browser")){
					if (!os.equals("") && !browserVersion.equals("") && !browserName.equals("")) {
						map.put(value, seleniunConfigXmlReaderVO);
					} 
				} else if (nodeValue1.getNodeValue().equalsIgnoreCase("browser-device")){
					if (!platformName.equals("") && !deviceName.equals("") && !browserName.equals("")) {
						map.put(value, seleniunConfigXmlReaderVO);
					}
				}			
				else if (nodeValue1.getNodeValue().equalsIgnoreCase("app")){
					if (!deviceName.equals("") && !app.equals("")) {
						map.put(value, seleniunConfigXmlReaderVO);
					}
				}
			}
		}

		return map;
	}

	/**
	 * @return browsers
	 */
	public LinkedList<String[]> getBrowserList(String executionType) {
		LinkedList<String[]> browsers = new LinkedList<String[]>();
		LinkedHashMap<String, SeleniumConfigXmlReaderVO> map = null;
		SeleniumConfigXmlReader seleniunConfigXmlReader = new SeleniumConfigXmlReader();
		try {
			map = seleniunConfigXmlReader.read(System.getProperty("user.dir") +System.getProperty("file.separator")+"selenium-config.xml", executionType);
			Set<String> keyset = map.keySet();
			Iterator<String> it = keyset.iterator();
			while (it.hasNext()) {
				String key = it.next();
				SeleniumConfigXmlReaderVO vo = map.get(key);
				browsers.add(new String[] { vo.getOs(), vo.getBrowserVersion(), vo.getBrowserName(), vo.getPlatformName(), vo.getPlatformVersion(), vo.getDeviceOrientation(), vo.getDeviceName(), vo.getAppiumVersion(), vo.getApp() });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return browsers;
	}
	
	

	

	public static void main(String[] args) throws Exception {
		SeleniumConfigXmlReader configXmlReader = new SeleniumConfigXmlReader();
		configXmlReader.read(System.getProperty("user.dir") + "\\selenium-config.xml", "local");

	}
}
