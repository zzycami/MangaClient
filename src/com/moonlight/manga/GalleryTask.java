package com.moonlight.manga;

import java.io.File;
import java.io.IOException;

import com.moonlight.manga.adt.*;
import com.moonlight.manga.util.*;



public class GalleryTask {

	public static void main(String[] args) {
		loadGallery("http://lofi.e-hentai.org/g/583608/3aeb0e91aa/");
	}
	
	public static void loadGallery(String startPage){
		try {
			HTMLReader reader = new HTMLReader(Web.get(startPage));
			Gallery gallery = new Gallery();
			gallery.title = reader.getGalleryTitle();
			
			// create the fold to save gallery
			File file = new File("image/" + gallery.title);
			if(!file.exists()){
				file.mkdir();
			}
			
			ImageBat bat = new ImageBat();
			bat.FileSaveRoot = "image/" + gallery.title;
			gallery.imageUrlList = reader.getImageUrlList();
			bat.loadGallery(gallery.imageUrlList.get(0), gallery.imageUrlList.size());
			new Compress().zip("image/" + gallery.title, "compress/" + gallery.title + ".zip");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
