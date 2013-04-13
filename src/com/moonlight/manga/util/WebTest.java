package com.moonlight.manga.util;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.google.gson.Gson;
import com.moonlight.manga.adt.Page;
import com.moonlight.manga.adt.RequestKey;

public class WebTest {

	@Test
	public void testPost() {
		try {
			HTMLReader reader = new HTMLReader(Web.get("http://g.e-hentai.org/s/d5f675de02/583608-4"));
			RequestKey requestKey = reader.getRequestKey();
			requestKey.page ++;
			Gson gsons = new Gson();
			String request = gsons.toJson(requestKey);
			System.out.println(request);
			String returnData = Web.post("http://g.e-hentai.org/api.php", request);
			System.out.println(returnData);
			Page page = Page.parsePage(returnData);
			reader.setDocument(page.i3);
			reader.getLargeImageUrl();
			BufferedImage image = Web.getImageFromUrl(reader.getLargeImageUrl());
			File saveFile = new File("image/testGetImageFromUrl.jpg");
			ImageIO.write(image, "JPEG", saveFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetImageFromUrl(){
		try {
			BufferedImage image = Web.getImageFromUrl("http://78.62.44.3:8765/h/d5f675de0216d6c4c79c225669c32ccde00aaa8d-326556-1055-1500-jpg/keystamp=1365842901-eb93d5a8af/004.jpg");
			File saveFile = new File("image/testGetImageFromUrl.jpg");
			ImageIO.write(image, "JPEG", saveFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
