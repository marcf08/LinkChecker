package marcus.MA.com;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.opencsv.CSVReader;

public class Pairs {
	private String[] extensions = {"csv"};
	private File topLevelDir;
	private ArrayList<File> csvs;
	List<List<String>> pair;
	private Map<String, String> map;
	
	
	public Pairs() {
		csvs = new ArrayList<File>();
		map = new HashMap<String, String>();
		pair = new ArrayList(); 
		
	}
	
	public Pairs(String directory) {
		csvs = new ArrayList<File>();
		topLevelDir = new File(directory);
		map = new HashMap<String, String>();
		pair = new ArrayList(); 
	}
	
	public void setDirectory(String directory) {
		topLevelDir = new File(directory);
	}
	
	public void go() {
		searchDirForCsv();
		try {
			getPairs();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void searchDirForCsv() {
		   Iterator<File> it = FileUtils.iterateFiles(topLevelDir, extensions, true);
		   while (it.hasNext()) {
			   File f = (File) it.next();
			   csvs.add(f);
		   }
	
	}
	
	public Map<String, String> getMap() {
		return this.map;
	}
	
	private void getPairs() throws IOException {
		for (int i = 0; i < csvs.size(); i++) {
			CSVReader reader = new CSVReader(new FileReader(csvs.get(i)));
			String [] nextLine;
			     while ((nextLine = reader.readNext()) != null) {
			    	 String title = nextLine[0];
			    	 String name = nextLine[5];
			    	 if (name.endsWith(".html")) {
			    		 String nameFix = name.replace("data/", "");
			    		 map.put(nameFix, title);
			    		 
			    	 }
			} 
		}
	}
		
}
	
	
	
	
	

