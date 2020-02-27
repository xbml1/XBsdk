package com.Mixo;


import android.os.AsyncTask;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public class Mixo
{
	public static HashMap<String, String> sHeader = new HashMap<String, String>();

	public static void setRequestProperty(String str1, String str2){
		sHeader.put(str1,str2);
	}

    public static void setHeader(HashMap<String, String> header) {
        sHeader = header;
    }

    public static HashMap<String, String> getHeader() {
        return sHeader;
    }

    public static H1 get(String url,cl callback) {
		
        H1 task = new H1(url, "GET", null, null, null, callback);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return task;
    }

    public static H1 get(String url, HashMap<String, String> header,cl callback) {
        H1 task = new H1(url, "GET", null, null, header, callback);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return task;
    }

    public static H1 get(String url, String cookie, HashMap<String, String> header, cl callback) {
        H1 task = cookie.matches("[\\w\\-\\.:]+") && Charset.isSupported(cookie) ? new H1(url, "GET", null, cookie, header, callback) : new H1(url, "GET", cookie, null, header, callback);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return task;
    }

    public static H1 get(String url, String cookie, cl callback) {
        H1 task = cookie.matches("[\\w\\-\\.:]+") && Charset.isSupported(cookie) ? new H1(url, "GET", null, cookie, null, callback) : new H1(url, "GET", cookie, null, null, callback);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return task;
    }

    public static H1 get(String url, String cookie, String charset, cl callback) {
        H1 task = new H1(url, "GET", cookie, charset, null, callback);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return task;
    }

    public static H1 get(String url, String cookie, String charset, HashMap<String, String> header, cl callback) {
        H1 task = new H1(url, "GET", cookie, charset, header, callback);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return task;
    }

    public static H1 download(String url, String path, cl callback) {
        H1 task = new H1(url, "GET", null, null, null, callback);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, path);
        return task;
    }

    public static H1 download(String url, String path, HashMap<String, String> header, cl callback) {
        H1 task = new H1(url, "GET", null, null, header, callback);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, path);
        return task;
    }

    public static H1 download(String url, String path, String cookie, cl callback) {
        H1 task = new H1(url, "GET", cookie, null, null, callback);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, path);
        return task;
    }

    public static H1 download(String url, String path, String cookie, HashMap<String, String> header, cl callback) {
        H1 task = new H1(url, "GET", cookie, null, header, callback);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, path);
        return task;
    }


    public static H1 delete(String url, cl callback) {
        H1 task = new H1(url, "DELETE", null, null, null, callback);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return task;
    }

    public static H1 delete(String url, HashMap<String, String> header, cl callback) {
        H1 task = new H1(url, "DELETE", null, null, header, callback);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return task;
    }

    public static H1 delete(String url, String cookie, HashMap<String, String> header, cl callback) {
        H1 task = cookie.matches("[\\w\\-\\.:]+") && Charset.isSupported(cookie) ? new H1(url, "DELETE", null, cookie, header, callback) : new H1(url, "DELETE", cookie, null, header, callback);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return task;
    }

    public static H1 delete(String url, String cookie, cl callback) {
        H1 task = cookie.matches("[\\w\\-\\.:]+") && Charset.isSupported(cookie) ? new H1(url, "DELETE", null, cookie, null, callback) : new H1(url, "DELETE", cookie, null, null, callback);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return task;
    }

    public static H1 delete(String url, String cookie, String charset, cl callback) {
        H1 task = new H1(url, "DELETE", cookie, charset, null, callback);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return task;
    }

    public static H1 delete(String url, String cookie, String charset, HashMap<String, String> header, cl callback) {
        H1 task = new H1(url, "DELETE", cookie, charset, header, callback);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return task;
    }


    public static H1 post(String url, String data, cl callback) {
        H1 task = new H1(url, "POST", null, null, null, callback);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, data);
        return task;
    }

    public static H1 post(String url, String data, HashMap<String, String> header, cl callback) {
        H1 task = new H1(url, "POST", null, null, header, callback);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, data);
        return task;
    }

    public static H1 post(String url, String data, String cookie, cl callback) {
        H1 task = cookie.matches("[\\w\\-\\.:]+") && Charset.isSupported(cookie) ? new H1(url, "POST", null, cookie, null, callback) : new H1(url, "POST", cookie, null, null, callback);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, data);
        return task;
    }

    public static H1 post(String url, String data, String cookie, HashMap<String, String> header, cl callback) {
        H1 task = cookie.matches("[\\w\\-\\.:]+") && Charset.isSupported(cookie) ? new H1(url, "POST", null, cookie, header, callback) : new H1(url, "POST", cookie, null, header, callback);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, data);
        return task;
    }

    public static H1 post(String url, String data, String cookie, String charset, cl callback) {
        H1 task = new H1(url, "POST", cookie, charset, null, callback);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, data);
        return task;
    }

    public static H1 post(String url, String data, String cookie, String charset, HashMap<String, String> header, cl callback) {
        H1 task = new H1(url, "POST", cookie, charset, header, callback);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, data);
        return task;
    }


    public static H1 put(String url, String data, cl callback) {
        H1 task = new H1(url, "PUT", null, null, null, callback);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, data);
        return task;
    }

    public static H1 put(String url, String data, HashMap<String, String> header, cl callback) {
        H1 task = new H1(url, "PUT", null, null, header, callback);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, data);
        return task;
    }

    public static H1 put(String url, String data, String cookie, cl callback) {
        H1 task = cookie.matches("[\\w\\-\\.:]+") && Charset.isSupported(cookie) ? new H1(url, "PUT", null, cookie, null, callback) : new H1(url, "PUT", cookie, null, null, callback);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, data);
        return task;
    }

    public static H1 put(String url, String data, String cookie, HashMap<String, String> header, cl callback) {
        H1 task = cookie.matches("[\\w\\-\\.:]+") && Charset.isSupported(cookie) ? new H1(url, "PUT", null, cookie, header, callback) : new H1(url, "PUT", cookie, null, header, callback);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, data);
        return task;
    }

    public static H1 put(String url, String data, String cookie, String charset, cl callback) {
        H1 task = new H1(url, "PUT", cookie, charset, null, callback);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, data);
        return task;
    }

    public static H1 put(String url, String data, String cookie, String charset, HashMap<String, String> header, cl callback) {
        H1 task = new H1(url, "PUT", cookie, charset, header, callback);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, data);
        return task;
    }

	public static void setSsl() {
        try {  
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
					public boolean verify(String hostname, SSLSession session) {
						return true;  
					}
				});  

            SSLContext context = SSLContext.getInstance("TLS");  
            context.init(null, new X509TrustManager[] { new X509TrustManager() {
								 public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
								 }

								 public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
								 }

								 public X509Certificate[] getAcceptedIssuers() {
									 return new X509Certificate[0];  
								 }
							 } }, new SecureRandom());  
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();  
        }
    }
}

