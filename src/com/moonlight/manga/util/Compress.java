package com.moonlight.manga.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Compress {
	
	public void zip(String inputFileName, String zipFileName) throws IOException{
		System.out.println("start compress file " + inputFileName);
		zip(zipFileName, new File(inputFileName));
	}
	
	private void zip(String zipFileName, File inputFile) throws IOException{
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
		zip(out, inputFile, "");
		System.out.println("zip done");
		out.close();
	}

	private void zip(ZipOutputStream out, File file, String base) throws IOException{
		if(file.isDirectory()){
			File[] fileList = file.listFiles();
			out.putNextEntry(new ZipEntry(base + "/"));
			base = base.length() == 0 ? "" : base + "/";
			for(int i=0;i<fileList.length;i++){
				zip(out, fileList[i], base + fileList[i].getName());
			}
		}else if(file.isFile()){
			out.putNextEntry(new ZipEntry(base));
			FileInputStream in = new FileInputStream(file);
			System.out.println("compress "+base);
			byte[] buffer = new byte[8* 1024];
			int length;
			while((length = in.read(buffer)) != -1){
				out.write(buffer, 0, length);
			}
			in.close();
		}
	}
}
