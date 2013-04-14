package com.moonlight.manga;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.moonlight.manga.util.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String testUrl = "http://lofi.e-hentai.org/g/583842/6d746f66ae/";
			HTMLReader reader = new HTMLReader(Web.get(testUrl));
			ArrayList<String> pages = reader.getImagePageUrlList();
			BufferedImage image = null;
			int index =0;
			for(String page : pages){
				reader.setDocument(Web.get(page));
				String imageUrl = reader.getLargeImageUrl();
				System.out.println("start load image:" + imageUrl);
				image = Web.getImageFromUrl(imageUrl);
				ImageIO.write(image, "JPEG", new File("image/"+ index++ +".jpg"));
				System.out.println("save image at image/"+ index++ +".jpg");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
