package com.moonlight.manga.util;

import java.io.IOException;

import org.junit.Test;

public class CompressTest {

	@Test
	public void test() {
		Compress compress = new Compress();
		try {
			compress.zip("image", "image.zip");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
