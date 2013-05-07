package net.ferrerabertran.oracle;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * A simple Java URL fetcher.
 *  
 * @author pere
 */
public class Fetcher {

	/**
	 * Returns the string of the URL, or null. Uses HTTPClient 3.
	 *  
	 * @param url
	 * @return
	 */
	public static String fetch(String url) {
		try {
			HttpClient client = new HttpClient();
			GetMethod get = new GetMethod(url);
			client.executeMethod(get);
			return get.getResponseBodyAsString();
		} catch(HttpException e) {
			e.printStackTrace();
			return null;
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
