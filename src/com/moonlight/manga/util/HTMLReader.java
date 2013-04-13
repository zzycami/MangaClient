package com.moonlight.manga.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.moonlight.manga.adt.RequestKey;


public class HTMLReader {
	private Document doc;
	
	public HTMLReader(String html){
		this.doc = Jsoup.parse(html);
	}
	
	public HTMLReader() {
	}

	public void setDocument(String html) {
		this.doc = Jsoup.parse(html);
	}
	
	/**
	 * Get the image url of current page
	 * @return String image url
	 */
	public String getImageUrl(){
		Elements image = this.doc.select("img#sm");
		return image.attr("src");
	}
	
	public String getImageUrl(String html){
		Document localDoc = Jsoup.parse(html);
		Elements image = localDoc.select("img#sm");
		return image.attr("src");
	}
	
	/**
	 * Parse like 
	 * 	<a onclick=\"return load_image(3, '753d75af73')\" href=\"http://g.e-hentai.org/s/753d75af73/583207-3\">
	 * 		<img id=\"img\" src=\"http://67.159.28.218:21526/h/0139900da1272efb4337e3210a6fb9bbeaaef3a5-328615-1200-1722-jpg/keystamp=1365838136-fdd8953b78/off_03_.jpg\" style=\"width:1200px;height:1722px\" />
	 * </a>
	 * to get the image url
	 * this page can find in g.e-hentai.org
	 * @return String hight quality image url
	 */
	public String getLargeImageUrl(){
		Elements image = this.doc.select("img#img");
		return image.attr("src");
	}
	
	/**
	 * To get the next image, we need to parse the image key
	 * @return String image key
	 */
	public String getImageKey(){
		Elements a = this.doc.select("div #i3 a");
		String onclick = a.attr("onclick");
		Pattern pattern = Pattern.compile("load_image[(](\\d+), '(.*)'[)]");
		Matcher matcher = pattern.matcher(onclick);
		if(matcher.find()){
			return matcher.group(2);
		}else {
			return "";
		}
	}
	
	/**
	 * To get next page number
	 * @return int page number
	 */
	public int getPageNumber(){
		Elements a = this.doc.select("a");
		String onclick = a.attr("onclick");
		Pattern pattern = Pattern.compile("load_image[(](\\d+), '(.*)'[)]");
		Matcher matcher = pattern.matcher(onclick);
		if(matcher.find()){
			return Integer.parseInt(matcher.group(1));
		}else {
			return 0;
		}
	}
	
	/**
	 *Get the url of next page, if do not have next page return over
	 * @return String the url string of next page
	 */
	public String getNextImagePage(){
		Elements a = this.doc.select("div#ia a");
		if(a.size() == 2){
			return "over";
		}else if(a.size() == 3){
			if(a.get(0).html().equals("Back")){
				return a.get(1).attr("href");
			}else {
				return "over";
			}
		}else {
			return a.get(2).attr("href");
		}
	}
	
	public String getNextImagePage(Document document){
		Elements a = document.select("div#ia a");
		if(a.size() == 2){
			if(a.get(0).html().equals("Next Page &gt;")){
				return a.get(0).attr("href");
			}else {
				return "over";
			}
		}else {
			return a.get(1).attr("href");
		}
	}
	
	
	public RequestKey getRequestKey(){
		RequestKey key = new RequestKey();
		Elements script = this.doc.select("script");
		String contents = script.get(1).html();
		
		// Use march to get the single data
		Pattern pattern = Pattern.compile("gid.*?=.*?(\\d+)");
		Matcher matcher = pattern.matcher(contents);
		if(matcher.find()){
			key.gid = Integer.parseInt(matcher.group(1));
		}
		
		pattern = Pattern.compile("showkey.*?=.*?\"(.*?)\"");
		matcher = pattern.matcher(contents);
		if(matcher.find()){
			key.showkey = matcher.group(1);
		}
		
		pattern = Pattern.compile("startpage.*?=.*?(\\d+)");
		matcher = pattern.matcher(contents);
		if(matcher.find()){
			key.page = Integer.parseInt(matcher.group(1));
		}
		
		key.imgkey = this.getImageKey();
		
		return key;
	}
	
	/**
	 * get the hight quality image page url list
	 * @return ArrayList<String> the url list
	 * @throws IOException
	 */
	public ArrayList<String>  getImagePageUrlList() throws IOException{
		ArrayList<String>  imageUrlList = new ArrayList<String>();
		
		// get the page counts
		Elements links = this.doc.select("table.ptt td");
		int count = Integer.parseInt(links.get(links.size() - 2).select("a").html());
		String baseUrl = links.get(1).select("a").attr("href");
		
		// get the first pages
		System.out.print("Start load page 0");
		Elements gdtms = this.doc.select("div.gdtm");
		for(Element gdtm : gdtms){
			Elements a = gdtm.select("a");
			imageUrlList.add(a.attr("href"));
			System.out.print(".");
		}
		System.out.println("");
		// get the last
		for(int i= 1;i< (count-1);i++){
			String url = baseUrl + "?p=" + i;
			this.setDocument(Web.get(url));
			System.out.print("Start load page " + i);
			// get pages
			gdtms = this.doc.select("div.gdtm");
			for(Element gdtm : gdtms){
				Elements a = gdtm.select("a");
				imageUrlList.add(a.attr("href"));
				System.out.print(".");
			}
			System.out.println("");
		}
		return imageUrlList;
	}
	
	public ArrayList<String> getImageUrlList() throws IOException{
		ArrayList<String> imageUrlList = new ArrayList<String>();
		int page = 1;
		// get the first page's image url
		Elements links = this.doc.select("div#gh div.gi");
		System.out.print("start load page 0:");
		for(Element link : links){
			imageUrlList.add(link.select("a").attr("href"));
			System.out.print(".");
		}
		System.out.println("");
		
		// get the last image url
		String next = this.getNextImagePage(doc);
		if(next.equals("over")){
			return imageUrlList;
		}
		
		while(true){
			// load the next page
			System.out.print("start load page "+(page ++)+":");
			Document document = Jsoup.parse(Web.get(next));
			// load the next page's image url
			links = document.select("div#gh div.gi");
			for(Element link : links){
				imageUrlList.add(link.select("a").attr("href"));
				System.out.print(".");
			}
			System.out.println("");
			
			// check if has over
			next = this.getNextImagePage(document);
			if(next.equals("over")){
				break;
			}
		}
		
		return imageUrlList;
	}
	
	public String getGalleryTitle(){
		Elements galleryName = this.doc.select("h1#gn");
		return galleryName.html();
	}
	
	public String getGalleryOriginTitle(){
		Elements galleryName = this.doc.select("h1#gj");
		return galleryName.html();
	}
	
}
