package com.accenture.aaft.selenium.library.utility;

import java.io.IOException;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Class is used to fetch web element details
 *
 * @author vijay.venkatappa
 *
 */
public class RestCall {

	public String simpleGet(String tc, String rslt) {
		String returnString = null;
		
		if (tc != null && rslt != null) {		
			try {
				String baseUrl = "https://touchlesstesting.accenture.com/platform/rest/pipelineresult?ty=update";
				baseUrl += "&tc=" + tc + "&r=" +  rslt;
				CloseableHttpClient client = HttpClients.custom().build();
				try {
					HttpGet httpget = new HttpGet(baseUrl);
					CloseableHttpResponse response = client.execute(httpget);
					try {
						returnString = EntityUtils.toString(response.getEntity());
					} finally {
						response.close();
					}
				} finally {
					client.close();
				}
			} catch (ParseException | IOException e) {
				e.printStackTrace();
			}
		}
		
		return returnString;
	}

}
