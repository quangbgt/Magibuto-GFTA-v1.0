package io.traderx.darkness.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpHelper {

	public static String getContent(String strUrl) {
		
		String content = "";
		
		try {
			URLConnection conn = new URL(strUrl).openConnection();
			conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = null;

			while ((line = br.readLine()) != null) {
				content += line;
			}
			br.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return content;
	}
}