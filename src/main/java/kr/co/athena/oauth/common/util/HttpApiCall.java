package kr.co.athena.oauth.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class HttpApiCall {
        
	public static String methodGet(String strUrl) {
		StringBuilder sb = new StringBuilder();
		
		try {
			if(strUrl.indexOf("https") > -1) {
			   TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				   public java.security.cert.X509Certificate[] getAcceptedIssuers() {return null;}
				   public void checkClientTrusted(X509Certificate[] certs, String authType) {}
				   public void checkServerTrusted(X509Certificate[] certs,String authType) {}
				} };
	
				SSLContext sc = SSLContext.getInstance("SSL");
				sc.init(null, trustAllCerts, new java.security.SecureRandom());
				HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());			  				  
			}
			
			URL url = new URL(strUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection(); 
			con.setConnectTimeout(30000); 
			con.setReadTimeout(30000); 
			con.setRequestMethod("GET");
			
			con.setDoOutput(false); 
						
			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(con.getInputStream(), "utf-8"));
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				br.close();
				//System.out.println("" + sb.toString());
			} else {
				//System.out.println(con.getResponseMessage());
				sb.append("ResponseCode : "+con.getResponseCode());
			}

		} catch (Exception e) {
			System.err.println("http url Connection error: " + e.toString());
			return sb.toString();
		}
		return sb.toString();
	}
	
	public static String methodGet(String strUrl, String token) {
		StringBuilder sb = new StringBuilder();
		
		try {
			if(strUrl.indexOf("https") > -1) {
			   TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				   public java.security.cert.X509Certificate[] getAcceptedIssuers() {return null;}
				   public void checkClientTrusted(X509Certificate[] certs, String authType) {}
				   public void checkServerTrusted(X509Certificate[] certs,String authType) {}
				} };
	
				SSLContext sc = SSLContext.getInstance("SSL");
				sc.init(null, trustAllCerts, new java.security.SecureRandom());
				HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());			  				  
			}
			
			URL url = new URL(strUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection(); 
			con.setConnectTimeout(30000); 
			con.setReadTimeout(30000); 
			con.setRequestMethod("GET");
			con.setRequestProperty("AuthToken", token);
			
			con.setDoOutput(false); 
						
			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(con.getInputStream(), "utf-8"));
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				br.close();
				//System.out.println("" + sb.toString());
			} else {
				//System.out.println(con.getResponseMessage());
				sb.append("ResponseCode : "+con.getResponseCode());
			}

		} catch (Exception e) {
			System.err.println("http url Connection error: " + e.toString());
			return sb.toString();
		}
		return sb.toString();
	}
	
	
	public static String methodPost(String sendUrl, String jsonValue) throws IllegalStateException {

		String inputLine = null;
		StringBuffer outResult = new StringBuffer();

		  try{
		    if(sendUrl.indexOf("https") > -1) {
			   TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				   public java.security.cert.X509Certificate[] getAcceptedIssuers() {return null;}
				   public void checkClientTrusted(X509Certificate[] certs, String authType) {}
				   public void checkServerTrusted(X509Certificate[] certs,String authType) {}
				} };
	
				SSLContext sc = SSLContext.getInstance("SSL");
				sc.init(null, trustAllCerts, new java.security.SecureRandom());
				HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());			  				  
			}
			  
			URL url = new URL(sendUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			conn.setRequestProperty("Accept-Charset", "UTF-8"); 
			conn.setConnectTimeout(20000);
			conn.setReadTimeout(20000);			
			
			OutputStream os = conn.getOutputStream();
			os.write(jsonValue.getBytes("UTF-8"));
			os.flush();

			// 리턴된 결과 읽기
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while ((inputLine = in.readLine()) != null) {
				outResult.append(inputLine);
			}
		    
			conn.disconnect();    
		  }catch(Exception e){
		      e.printStackTrace();
		  }	
		  
		  return outResult.toString();
		}
	
	public static String methodPost(String sendUrl, String jsonValue, String token) throws IllegalStateException {

		String inputLine = null;
		StringBuffer outResult = new StringBuffer();

		  try{
		    if(sendUrl.indexOf("https") > -1) {
			   TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				   public java.security.cert.X509Certificate[] getAcceptedIssuers() {return null;}
				   public void checkClientTrusted(X509Certificate[] certs, String authType) {}
				   public void checkServerTrusted(X509Certificate[] certs,String authType) {}
				} };
	
				SSLContext sc = SSLContext.getInstance("SSL");
				sc.init(null, trustAllCerts, new java.security.SecureRandom());
				HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());			  				  
			}
			  
			URL url = new URL(sendUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			conn.setRequestProperty("Accept-Charset", "UTF-8"); 
			conn.setConnectTimeout(20000);
			conn.setReadTimeout(20000);
			conn.setRequestProperty("AuthToken", token); 
			
			OutputStream os = conn.getOutputStream();
			os.write(jsonValue.getBytes("UTF-8"));
			os.flush();

			// 리턴된 결과 읽기
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while ((inputLine = in.readLine()) != null) {
				outResult.append(inputLine);
			}
		    
			conn.disconnect();    
		  }catch(Exception e){
		      e.printStackTrace();
		  }	
		  
		  return outResult.toString();
		}
}
