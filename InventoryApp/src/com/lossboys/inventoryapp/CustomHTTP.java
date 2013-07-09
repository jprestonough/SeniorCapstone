package com.lossboys.inventoryapp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

public class CustomHTTP {
	private static CookieStore cookieStore = new BasicCookieStore();
	private static HttpContext localContext = new BasicHttpContext();

	private static boolean debugHTTP = true;

	public static JSONObject makePOST(String url, List<NameValuePair> nameValuePair) {
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		// Creating HTTP client
		HttpClient httpClient = new DefaultHttpClient();
		// Creating HTTP Post
		HttpPost httpPost = new HttpPost(url);

		if (debugHTTP) {
			Log.d("CustomHTTP URL", url);
			for (NameValuePair pair : nameValuePair) {
				Log.d("CustomHTTP Param", pair.getName() + " = " + pair.getValue());
			}
		}

		// Url Encoding the POST parameters
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
		} catch (UnsupportedEncodingException e) {
			// writing error to Log
			e.printStackTrace();
		}

		// Making HTTP Request
		try {
			HttpResponse response = httpClient.execute(httpPost, localContext);
			String result = EntityUtils.toString(response.getEntity());

			// Writing response to log
			if(debugHTTP)
				Log.d("Http Response: ", result);
			try {
				JSONObject resultJSON = (JSONObject) new JSONTokener(result).nextValue();
				return resultJSON;
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		} catch (ClientProtocolException e) {
			// writing exception to log
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// writing exception to log
			e.printStackTrace();
			return null;
		}
	}
}
