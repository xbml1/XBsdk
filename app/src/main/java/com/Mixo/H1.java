package com.Mixo;

import java.io.*;

import android.os.AsyncTask;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.net.ssl.HttpsURLConnection;

public class H1 extends AsyncTask
{
	private String mUrl;
	private cl mCallback;
	private byte[] mData;
	private String mCharset;
	private String mCookie;
	private HashMap<String,String> mHeader;
	private String mMethod;
	private String returnCode = null;
	private String returnContent = null;
	private String returnCookie = null;
	private Map<String,String> returnHeader = null;
	private int resultCode = 0;
	private long mFileLength;
	private long mTotalLength;

	public H1(String url, String method, String cookie, String charset, HashMap<String,String> header, cl callback){
		mUrl = url;
		mMethod = method;
		mCookie = cookie;
		mCharset = charset;
		mHeader = header;
		mCallback = callback;
	}

	@Override
	protected Object doInBackground(Object[] p1){
		try{
			URL url = new URL(mUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if(mUrl.startsWith("https://")){
				conn = (HttpsURLConnection) url.openConnection();
				Mixo.setSsl();
			}
			conn.setConnectTimeout(120000);
			conn.setFollowRedirects(true);
			conn.setDoInput(true);
			conn.setRequestProperty("Accept-Language","zh-cn,zh;q=0.5");
			if(mCharset==null)
				mCharset = "UTF-8";
			conn.setRequestProperty("Accept-Charset",mCharset);

			if(mCookie!=null)
				conn.setRequestProperty("Cookie",mCookie); 

			if(mHeader!=null){
				Set<Map.Entry<String, String>> entries=mHeader.entrySet();
				for(Map.Entry<String, String> entry:entries){
					conn.setRequestProperty(entry.getKey(),entry.getValue());
				}
			}

			if(!Mixo.sHeader.isEmpty()){
				Set<Map.Entry<String, String>> entries2=Mixo.sHeader.entrySet();
				for(Map.Entry<String, String> entry:entries2){
					conn.setRequestProperty(entry.getKey(),entry.getValue());
				}
			}

			if(mMethod!=null)
				conn.setRequestMethod(mMethod);

			if(!"GET".equals(mMethod)&&p1.length!=0){
				mData = formatData(p1);
				conn.setDoOutput(true);
				conn.setRequestProperty("Content-length",""+mData.length); 
			}

			conn.connect();

			//download
			if("GET".equals(mMethod)&&p1.length!=0){
				mFileLength = conn.getContentLengthLong();
				File f=new File((String)p1[0]);
				if(!f.getParentFile().exists())
					f.getParentFile().mkdirs();
				FileOutputStream os=new FileOutputStream(f);
				InputStream is = conn.getInputStream();
				copyFile(is,os);
				Map<String, List<String>> hs=conn.getHeaderFields();
				List<String> cs=hs.get("Set-Cookie");
				StringBuffer cok=new StringBuffer();
				if(cs!=null)
					for(String s:cs){
						cok.append(s+";");
					}
				returnCookie = cok.toString();
				returnCode = conn.getResponseCode()+"";
				returnHeader = (Map<String, String>) conn.getHeaderFields();
				resultCode = 1;
				return new Object[]{returnCode,returnHeader};
			}

			//post upload
			if(p1.length!=0){
				OutputStream os=conn.getOutputStream();
				os.write(mData);
			}

			int code=conn.getResponseCode();
			Map<String, List<String>> hs=conn.getHeaderFields();
			if(code>=200&&code<400){
				String encoding=conn.getContentEncoding();
				List<String> cs=hs.get("Set-Cookie");
				StringBuffer cok=new StringBuffer();
				if(cs!=null)
					for(String s:cs){
						cok.append(s+";");
					}

				InputStream is = conn.getInputStream();
				BufferedReader reader=new BufferedReader(new InputStreamReader(is,mCharset));
				StringBuffer buf=new StringBuffer();
				String line;
				while((line=reader.readLine())!=null&&!isCancelled())
					buf.append(line+'\n');
				is.close();
				returnCode = code+"";
				returnContent = new String(buf);
				returnCookie = cok.toString();
				returnHeader = (Map<String, String>) hs;
				resultCode = 1;
				return new Object[]{returnCode,returnContent,returnCookie,returnHeader};
			}else{
				returnCode = code+"";
				returnContent = new String(conn.getResponseMessage());
				//returnCookie=cok.toString();
				returnHeader = (Map<String, String>) hs;
				resultCode = 1;
				return new Object[]{returnCode,returnContent,null,returnHeader};
			}
		}
		catch(Exception e){
			e.printStackTrace();
			returnCode = "-1";
			returnContent = e.getMessage();
			resultCode = 0;
			return new Object[]{returnCode,returnContent};
		}

	}

	private byte[] formatData(Object[] p1) throws UnsupportedEncodingException, IOException{
		// TODO: Implement this method
		byte[] bs = null;
		if(p1.length==1){
			Object obj=p1[0];
			if(obj instanceof String)
				bs = ((String)obj).getBytes(mCharset);
			else if(obj.getClass().getComponentType()==byte.class)
				bs = (byte[])obj;
			else if(obj instanceof File)
				bs = readAll(new FileInputStream((File)obj));
			else if(obj instanceof File)
				bs = formatData((Map)obj);
		}
		return bs;
	}

	private byte[] formatData(Map obj) throws UnsupportedEncodingException{
		// TODO: Implement this method
		StringBuilder buf=new StringBuilder();
		Set<Map.Entry<String, String>> entries=mHeader.entrySet();
		for(Map.Entry<String, String> entry:entries){
			buf.append(entry.getKey()+"="+entry.getValue()+"&");
		}
		return buf.toString().getBytes(mCharset);
	}

	public boolean cancel(){
		// TODO: Implement this method
		return super.cancel(true);
	}

	@Override
	protected void onProgressUpdate(Object[] values){
		// TODO: Implement this method
		super.onProgressUpdate(values);
		mCallback.jd(values[0]);
	} 

	@Override
	protected void onPostExecute(Object result){
		// TODO: Implement this method
		super.onPostExecute(result);
		if(resultCode==1)
			mCallback.ok(returnCode,returnContent,returnCookie,returnHeader);
		else
			mCallback.no(returnCode,returnContent);
	}

	private byte[] readAll(InputStream input) throws IOException{
		ByteArrayOutputStream output = new ByteArrayOutputStream(4096);
		byte[] buffer = new byte[2^32];
		int n = 0;
		while(-1!=(n=input.read(buffer))){
			output.write(buffer,0,n);
		}
		byte[] ret= output.toByteArray();
		output.close();
		return ret;
	}

	private boolean copyFile(InputStream in, OutputStream out){ 
		try{ 
			int byteread = 0; 
			byte[] buffer = new byte[1024*1024]; 
			while((byteread=in.read(buffer))!=-1){ 
				mTotalLength += byteread;
				int value = (int) ((mTotalLength/(float)mFileLength)*100);
				publishProgress(value);
				out.write(buffer,0,byteread); 
			} 
			//in.close
			//out.close
		} 
		catch(Exception e){ 
			return false;
		} 
		return true;
	} 
}


