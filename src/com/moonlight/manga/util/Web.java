package com.moonlight.manga.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Web {
	
	public static String get(String urlStr) throws IOException{
		URL url = new URL(urlStr);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  
		con.setDoInput(true);
		con.connect();

		InputStream in = con.getInputStream();
		return getString(in);
	}
	
	public static String post(String urlStr, String request) throws IOException{
		URL url = new URL(urlStr);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("charset", "UTF-8");
		con.setRequestProperty("Content-Type","application/json");  
		con.setDoInput(true);
		con.setDoOutput(true);
		con.connect();

		OutputStream out = con.getOutputStream();
		out.write((request).getBytes());
		out.flush();
		InputStream in = con.getInputStream();
		return getString(in);
		
	}
	
	public static String getString(InputStream in) throws IOException{
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		for(String temp = reader.readLine();temp != null;temp = reader.readLine()){
			builder.append(temp);
		}
		return builder.toString();
	}
	
	public static BufferedImage getImageFromUrl(String imageUrl) throws IOException{
		URL url = new URL(imageUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		DataInputStream in = new DataInputStream(con.getInputStream());
		return javax.imageio.ImageIO.read(in);
	}
	
	public void FastLoad(String urlStr, String fileName) throws IOException{
	}
}
