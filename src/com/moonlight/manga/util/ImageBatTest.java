package com.moonlight.manga.util;


import org.junit.Before;
import org.junit.Test;

public class ImageBatTest {
	private ImageBat bat;

	@Before
	public void setUp() throws Exception {
		bat = new ImageBat();
	}

	@Test
	public void testLoadGallery() {
		try {
			bat.FileSaveRoot = "image/test";
			bat.loadGallery(bat.loadImageUrlList("http://lofi.e-hentai.org/s/f2db144f930a8d56bc508ed03f5900f1c121005e-71181-240-350-jpg/583657-1"));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
