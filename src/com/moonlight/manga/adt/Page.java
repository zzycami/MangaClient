package com.moonlight.manga.adt;

import java.io.IOException;
import java.io.StringReader;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class Page {
	public int p;
	public String s;
	public String n;
	public String i;
	public String k;
	public String i3;
	public String i5;
	public String i6;
	public String i7;
	public int si;
	public String x;
	public String y;
	
	@SuppressWarnings("resource")
	public static Page parsePage(String pageStr) throws IOException{
		Page page = new Page();
		JsonReader reader = new JsonReader(new StringReader(pageStr));
		reader.beginObject();
		while(reader.hasNext()) {
			String name = reader.nextName();
			if(name.equals("s") && reader.peek() != JsonToken.NULL) {
				page.s = reader.nextString();
			}else if(name.equals("p") && reader.peek() != JsonToken.NULL){
				page.p = reader.nextInt();
			}else if(name.equals("n") && reader.peek() != JsonToken.NULL){
				page.n = reader.nextString();
			}else if(name.equals("i") && reader.peek() != JsonToken.NULL) {
				page.i = reader.nextString();
			}else if(name.equals("k") && reader.peek() != JsonToken.NULL) {
				page.k = reader.nextString();
			}else if(name.equals("i3") && reader.peek() != JsonToken.NULL) {
				page.i3 = reader.nextString();
			}else if(name.equals("i5") && reader.peek() != JsonToken.NULL) {
				page.i5 = reader.nextString();
			}else if(name.equals("i6") && reader.peek() != JsonToken.NULL){
				page.i6 = reader.nextString();
			}else if(name.equals("i7") && reader.peek() != JsonToken.NULL) {
				page.i7 = reader.nextString();
			}else if(name.equals("si") && reader.peek() != JsonToken.NULL) {
				page.si = reader.nextInt();
			}else if(name.equals("x") && reader.peek() != JsonToken.NULL){
				page.x = reader.nextString();
			}else if(name.equals("y") && reader.peek() != JsonToken.NULL) {
				page.y = reader.nextString();
			}else {
				reader.skipValue();
			}
		}
		reader.endObject();
		
		return page;
	}
}
