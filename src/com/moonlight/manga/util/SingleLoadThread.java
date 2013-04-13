package com.moonlight.manga.util;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CountDownLatch;


public class SingleLoadThread extends Thread{
	private String fileName;
	private String url;
	private CountDownLatch countDownLatch;

	public SingleLoadThread(String url, String fileName, CountDownLatch countDownLatch){
		this.fileName = fileName;
		this.url = url;
		this.countDownLatch = countDownLatch;
	}
	
	
	@Override
	public void run() {
		try {
			System.out.println("start load :"+this.url);
			URL url = new URL(this.url);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			setHeader(con);
			FileOutputStream fileStream = new FileOutputStream(new File(this.fileName));
			InputStream inputStream = con.getInputStream();
			byte[] buffer = new byte[8 * 1024];
			int length = 0;
			while((length = inputStream.read(buffer)) != -1){
				fileStream.write(buffer, 0, length);
			}
			inputStream.close();
			fileStream.close();
			System.out.println("finish load :"+this.fileName);
			this.countDownLatch.countDown();
		} catch (IOException e) {
			this.countDownLatch.countDown();
			e.printStackTrace();
		}
		super.run();
	}
	
    public void setHeader(URLConnection conn) {
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.3) Gecko/2008092510 Ubuntu/8.04 (hardy) Firefox/3.0.3");
        conn.setRequestProperty("Accept-Language", "en-us,en;q=0.7,zh-cn;q=0.3");
        conn.setRequestProperty("Accept-Encoding", "utf-8");
        conn.setRequestProperty("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
        conn.setRequestProperty("Keep-Alive", "300");
        conn.setRequestProperty("connnection", "keep-alive");
        conn.setRequestProperty("If-Modified-Since", "Fri, 02 Jan 2009 17:00:05 GMT");
        conn.setRequestProperty("If-None-Match", "\"1261d8-4290-df64d224\"");
        conn.setRequestProperty("Cache-conntrol", "max-age=0");
        conn.setRequestProperty("Referer", "http://www.baidu.com");
    }

}
