package com.moonlight.manga.util;


import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class HTMLReaderTest {
	
	private HTMLReader reader;

	@Before
	public void setUp() throws Exception {
		reader = new HTMLReader(Web.get("http://g.e-hentai.org/g/583608/3aeb0e91aa/"));
	}

	@Test
	public void testGetImageUrl() {
		String imageUrl = this.reader.getImageUrl();
		System.out.println(imageUrl);
	}
	
	@Test
	public void testGetNextImagePage(){
		String imageUrl = this.reader.getNextImagePage();
		System.out.println(imageUrl);
	}
	
	@Test
	public void testGetLargeImageUrl(){
		String image = this.reader.getLargeImageUrl();
		System.out.println(image);
	}
	
	@Test
	public void testGetImageKey(){
		String imageUrl = "<a onclick=\"return load_image(323454435, '753d75af73')\" href=\"http://g.e-hentai.org/s/753d75af73/583207-3\"><img id=\"img\" src=\"http://67.159.28.218:21526/h/0139900da1272efb4337e3210a6fb9bbeaaef3a5-328615-1200-1722-jpg/keystamp=1365838136-fdd8953b78/off_03_.jpg\" style=\"width:1200px;height:1722px\" /></a>";
		this.reader.setDocument(imageUrl);
		String imageKey = this.reader.getImageKey();
		System.out.println(imageKey);
	}
	
	@Test
	public void testGetPageNumber(){
		String imageUrl = "<a onclick=\"return load_image(312343, '753d75af73')\" href=\"http://g.e-hentai.org/s/753d75af73/583207-3\"><img id=\"img\" src=\"http://67.159.28.218:21526/h/0139900da1272efb4337e3210a6fb9bbeaaef3a5-328615-1200-1722-jpg/keystamp=1365838136-fdd8953b78/off_03_.jpg\" style=\"width:1200px;height:1722px\" /></a>";
		this.reader.setDocument(imageUrl);
		int pageNumber = this.reader.getPageNumber();
		System.out.println(pageNumber);
	}
	
	@Test
	public void testGetRequestKey(){
		this.reader.getRequestKey();
	}
	
	@Test
	public void testGetImagePageUrlList(){
		try {
			this.reader.getImagePageUrlList();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetImageUrlList(){
		try {
			this.reader.setDocument(Web.get("http://lofi.e-hentai.org/g/583658/5967bd8633/"));
			ArrayList<String> imageList = this.reader.getImageUrlList();
			for(String imageUrl : imageList){
				System.out.println(imageUrl);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetGalleryTitle(){
		try {
			this.reader.setDocument(Web.get("http://lofi.e-hentai.org/g/583658/5967bd8633/"));
			System.out.println(reader.getGalleryTitle());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
