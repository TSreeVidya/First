package com.accenture.aaft.propertyreader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Class is used to parse the xml file
 *
 * @author vijay.venkatappa
 *
 */
public class ConfigParser extends DefaultHandler {

  public static Map<String, Map<String, String>> configMap = new HashMap<>();
  public static Map<String, String[]> configMetaDataMap = new HashMap<>();

  Map<String, String> capabilitiesMap;
  String name;
  StringBuffer buffer;

  /**
   * Method is used to parse the XML file
   *
   * @param file - name of the XML file to parse
   */
  public void parseFile(File file) {
	try {
	  SAXParserFactory factory = SAXParserFactory.newInstance();
	  SAXParser parser = factory.newSAXParser();
	  parser.parse(file, this);
	} catch (Exception e) {
	  e.printStackTrace();
	}
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String,
   * org.xml.sax.Attributes)
   */
  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) {
	if (qName.equalsIgnoreCase("name")) {
	  capabilitiesMap = new HashMap<>();
	  name = attributes.getValue("value");

	  String[] attrArray = new String[2];
	  attrArray[0] = attributes.getValue("for");
	  attrArray[1] = attributes.getValue("type");

	  if (attrArray[1] != null && attrArray[0] != null) {
		name = attrArray[1] + "_" + attrArray[0] + "_" + name;
	  }
	  configMetaDataMap.put(name, attrArray);
	} else if (!qName.equalsIgnoreCase("selenium-config")) {
	  buffer = new StringBuffer();
	}
  }

  @Override
  public void endElement(String uri, String localName, String qName) {
	if (qName.equalsIgnoreCase("name")) {
	  configMap.put(name, capabilitiesMap);
	} else if (!qName.equalsIgnoreCase("selenium-config")) {
	  capabilitiesMap.put(qName, buffer.toString());
	  buffer = null;
	}
  }

  @Override
  public void characters(char ch[], int start, int length) {
	if (buffer != null) {
	  buffer.append(ch, start, length);
	}
  }
}
