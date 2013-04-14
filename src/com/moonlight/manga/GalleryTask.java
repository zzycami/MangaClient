package com.moonlight.manga;

import java.io.File;
import java.io.IOException;

import com.moonlight.manga.adt.*;
import com.moonlight.manga.util.*;



public class GalleryTask {

	public static void main(String[] args) {
		loadGallery("http://lofi.e-hentai.org/g/583742/31d6e31585/");
	}
	
	public static void loadGallery(String startPage){
		try {
			HTMLReader reader = new HTMLReader(Web.get(startPage));
			Gallery gallery = new Gallery();
			gallery.title = reader.getGalleryTitle();
			gallery.originTitle = reader.getGalleryOriginTitle();
			// create the fold to save gallery
			String fileName = checkFileName(gallery.title);
			File file = new File("image/" + fileName);
			if(!file.exists()){
				file.mkdir();
			}
			
			ImageBat bat = new ImageBat();
			bat.FileSaveRoot = "image/" + fileName;
			gallery.imageUrlList = reader.getImageUrlList();
			bat.loadGallery(gallery.imageUrlList.get(0), gallery.imageUrlList.size());
			new Compress().zip("image/" + fileName, "compress/" + fileName + ".zip");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public static String checkFileName(String fileName){
		char[] illegalChars = "|<>;~!@#$%^&*()?\\/:\"\'".toCharArray();
		for(char singleChar : illegalChars){
			fileName = fileName.replace(singleChar + "", "");
		}
		return fileName;
	}
}
