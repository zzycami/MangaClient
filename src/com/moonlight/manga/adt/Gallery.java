package com.moonlight.manga.adt;

import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Gallery {
	public ArrayList<String> imageUrlList;
	
	public BufferedImage cover;
	public String title;
	public String originTitle;
	public String posted;
	public String uploader;
	public String category;
	public String tags;
	public String rating;
	
	public Gallery(){
		this.imageUrlList = new ArrayList<String>();
	}
}
