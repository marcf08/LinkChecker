package marcus.MA.com;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.opencsv.CSVReader;


public class LinkChecker {
		
	   private String directory;
	   private ArrayList<String> links;
	   private ArrayList<File> files;
	   private ArrayList<File> csvfiles;
	   
	   private ArrayList<File> htmlFiles;
	   private String[] extensions = {"html"};
	   private File topLevelDir;
	   private ArrayList<String> content;
	   List<List<String>> nameLink;
	   
	   
	   
	   
	   private File[] toplist;
	   String[] ar; 
	   
	   public LinkChecker() {
		   links = new ArrayList <String>();
		   files = new ArrayList<File>();
		   htmlFiles = new ArrayList<File>();
		   topLevelDir = new File(this.directory);
		   content = new ArrayList<String>();
		   nameLink = new ArrayList<>();


		   
	   }
	   
	   public LinkChecker(String directory) {
		   links = new ArrayList <String>();
		   files = new ArrayList<File>();
		   this.directory = directory;
		   htmlFiles = new ArrayList<File>();
		   topLevelDir = new File(this.directory);
		   content = new ArrayList<String>();
		   nameLink = new ArrayList<>();

	   }
	   
	   public void setDir(String dir) {
		   this.directory = dir;
	   }
	   
	   private void setDirectory(String directory) {
		   this.directory = directory;
	   }
	   
	   public void extractLinks() {
		   searchDir();
		   pullLinks();
		   
	   }
	   
	   
	   
	   private void searchDir() {
		   Pairs p = new Pairs(this.directory);
		   p.go();
		   Map<String, String> linkArticleName = p.getMap();
		   Iterator<File> it = FileUtils.iterateFiles(topLevelDir, extensions, true);
		   while (it.hasNext()) {
			   File f = (File) it.next();
			   if (f.getName().endsWith(".html")) {
				   files.add(f);
				   try {
					   String toAdd = "{" + f.getName() + "}" + FileUtils.readFileToString(f);
					   String artName = linkArticleName.get(f.getName());
					   content.add(toAdd);
				   } catch (IOException e) {
					   // TODO Auto-generated catch block
					   e.printStackTrace();
				   }
			   
			   }
		   }
		   
		   
	   }
	   
	   
	   
	   public void pullLinks() {
		   int code;
		   Pairs p = new Pairs(this.directory);
		   p.go();
		   Map<String, String> linkArticleName = p.getMap();
		   //Replace 1 with content.size();
		   for (int i = 0; i < content.size(); i++) {
			   String s = content.get(i);
			   s = s.substring(s.indexOf("{") + 1);
			   s = s.substring(0, s.indexOf("}"));
			   //System.out.println(s);
     		   Document doc = Jsoup.parse(content.get(i));
			   Elements link = doc.select("a[href*=http]");
			   System.out.println("Article Name = " + linkArticleName.get(s) + " has links: ");
			   for (int j = 0; j < link.size(); j++) {
				   String absFinalurl = link.get(j).attr("abs:href");
				   //Post request here
				   
				   URL url;
				try {
					url = new URL(absFinalurl);
				
				   HttpURLConnection connection = (HttpURLConnection)url.openConnection();
				   connection.setRequestMethod("GET");
				   connection.connect();

				   code = connection.getResponseCode();
				   System.out.println(j + " " + absFinalurl + " " + "Response: " + code);
				   
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				   //Post
				   
				   
			   }

			   
		   }
	   }
	   
	  
	   
	   
	    

}
