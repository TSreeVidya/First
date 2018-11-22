package com.accenture.aaft.selenium.library.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KeyValueRepo {
static List<HashMap<String,String>> listKeyValue;
public static void storeKeyValues(HashMap<String, String> map){
	listKeyValue =  new ArrayList<HashMap<String, String>>();
	listKeyValue.add(map);
	
}
public static List<HashMap<String,String>> getKeyValues(){

	return listKeyValue;
}
}
