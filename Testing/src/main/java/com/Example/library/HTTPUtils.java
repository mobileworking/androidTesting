package com.Example.library;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultHttpRoutePlanner;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.util.Log;

@SuppressWarnings("deprecation")
public class HTTPUtils {
	
	public static String HTTPPostWithToken(String urlAddress, String lan, String token, String... postDataPair) {
		
		HttpParams myParams = new BasicHttpParams();

		HttpConnectionParams.setConnectionTimeout(myParams, 30000);
        HttpConnectionParams.setSoTimeout(myParams, 30000);
        
        HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
        
        DefaultHttpClient client = new DefaultHttpClient(myParams);

        SchemeRegistry registry = new SchemeRegistry();
        SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
        socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
        registry.register(new Scheme("https", socketFactory, 443));
        SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);
        DefaultHttpClient httpClient = new DefaultHttpClient(mgr, client.getParams());
        
        httpClient.setRoutePlanner(new DefaultHttpRoutePlanner(registry));
        // Set verifier     
        HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);

		ResponseHandler <String> res=new BasicResponseHandler();
		HttpPost postMethod=new HttpPost(urlAddress);
		
		postMethod.addHeader("Accept", "application/json");
		postMethod.addHeader("Accept-language", lan);
		postMethod.setHeader("Content-Type", "application/x-www-form-urlencoded");
		postMethod.setHeader("Token", token);
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(postDataPair.length / 2);
		
		for (int i = 0; i < postDataPair.length; i += 2) {
			nameValuePairs.add(new BasicNameValuePair(postDataPair[i], postDataPair[i + 1]));
		}
		
		String response = "";
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			response = httpClient.execute(postMethod,res);
		} catch (UnsupportedEncodingException e) {
			Log.d("HTTP Post", "HTTP Post : UnsupportedEncodingException, " + e.toString());
			response = "";
		} catch (ClientProtocolException e) {
			Log.d("HTTP Post", "HTTP Post : ClientProtocolException, " + e.toString());
			response = "";
		} catch (IOException e) {
			Log.d("HTTP Post", "HTTP Post : IOException, " + e.toString());
			response = "";
		}
		return response;
	}
	
	
	public static String HTTPPost(String urlAddress, String lan, String... postDataPair) {
		
		HttpParams myParams = new BasicHttpParams();

		HttpConnectionParams.setConnectionTimeout(myParams, 30000);
        HttpConnectionParams.setSoTimeout(myParams, 30000);
      
		DefaultHttpClient hc=new DefaultHttpClient(myParams);   
		ResponseHandler <String> res=new BasicResponseHandler();
		
		HttpPost postMethod=new HttpPost(urlAddress);
		
		postMethod.addHeader("Accept", "application/json");
		postMethod.addHeader("Accept-language", lan);
		postMethod.setHeader("Content-Type", "application/x-www-form-urlencoded");
			
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(postDataPair.length / 2);
		
		for (int i = 0; i < postDataPair.length; i += 2) {
			nameValuePairs.add(new BasicNameValuePair(postDataPair[i], postDataPair[i + 1]));
		}
		
		String response = "";
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			response = hc.execute(postMethod,res);
		} catch (UnsupportedEncodingException e) {
			Log.d("HTTP Post", "HTTP Post : UnsupportedEncodingException, " + e.toString());
			response = "";
		} catch (ClientProtocolException e) {
			Log.d("HTTP Post", "HTTP Post : ClientProtocolException, " + e.toString());
			response = "";
		} catch (IOException e) {
			Log.d("HTTP Post", "HTTP Post : IOException, " + e.toString());
			response = "";
		}
		return response;
	}

	
}
