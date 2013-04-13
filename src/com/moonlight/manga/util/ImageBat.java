package com.moonlight.manga.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;


public class ImageBat {
	static final BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(1);
	public String FileSaveRoot = "image";
	public String FileExtension = "jpg";
	
	public ArrayList<String> loadImageUrlList(String start){
		HTMLReader reader = new HTMLReader();
		String page = start;
		ArrayList<String> imageUrlList = new ArrayList<String>();
		try {
			System.out.print("start load image urls: ");
			while(true) {
				String html = Web.get(page);
				reader.setDocument(html);
				String imageUrl = reader.getImageUrl();
				System.out.print(".");
				imageUrlList.add(imageUrl);
				page = reader.getNextImagePage();
				if(page.equals("over")){
					break;
				}
			}
			System.out.println("");
			return imageUrlList;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void loadGallery(ArrayList<String> imageUrlList) throws InterruptedException{
		CountDownLatch countDownLatch = new CountDownLatch(imageUrlList.size());
		
		for(int i=0;i<imageUrlList.size();i++){
			// start a new thread download the image
			new SingleLoadThread(imageUrlList.get(i), setFileName(i), countDownLatch).start();
		}
		
		// wait sub thread finish work
		countDownLatch.await();
		System.out.println("all finished");
	}
	
	public void loadGallery(String startPage, int count) throws InterruptedException{
		CountDownLatch countDownLatch = new CountDownLatch(count);
		
		HTMLReader reader = new HTMLReader();
		String page = startPage;
		int index = 0;
		try {
			System.out.print("start load image urls: ");
			while(true) {
				String html = Web.get(page);
				reader.setDocument(html);
				String imageUrl = reader.getImageUrl();
				new SingleLoadThread(imageUrl, setFileName(index++), countDownLatch).start();
				page = reader.getNextImagePage();
				if(page.equals("over")){
					break;
				}
			}
			System.out.println("");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// wait sub thread finish work
		countDownLatch.await();
		System.out.println("all finished");
	}
	
	private String setFileName(int index){
		if(index < 10){
			return this.FileSaveRoot + "/00" + index + ".jpg";
		}else if(index < 100){
			return this.FileSaveRoot + "/0" + index + ".jpg";
		}else{
			return this.FileSaveRoot + "/" +index + ".jpg";
		}
	}
}
