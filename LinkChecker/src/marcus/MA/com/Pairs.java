package marcus.MA.com;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import com.opencsv.CSVReader;
/*
 * This class searches a csv file for the article title name and the filename.
 * It matches the title name to the filename. Without this method, the user would
 * simply receive a list of filenames with the its links and no way to identify it.
 */
public class Pairs {
	
	private static final int TITLE = 0;
	private static final int FILE = 5;
	private File topLevelDir;
	
	private String[] extensions = {"csv"};
	private ArrayList<File> csvs;
	List<List<String>> pair;
	private Map<String, String> map;
	
	/*
	 * Default constructor.
	 */
	public Pairs() {
		csvs = new ArrayList<File>();
		map = new HashMap<String, String>();
	}
	
	/*
	 * This constructor allows you to instantiate the method given a directory.
	 * @param directory a directory used to instantiate this method.
	 */
	public Pairs(String directory) {
		csvs = new ArrayList<File>();
		topLevelDir = new File(directory);
		map = new HashMap<String, String>();
	}
	
	/*
	 * This is a simple setter for setting the directory.
	 * @param directory the directory to operate on
	 */
	public void setDirectory(String directory) {
		topLevelDir = new File(directory);
	}
	
	/*
	 * This high level method pulls gets the csv files and scans them for the pairs needed.
	 * If you change the directory via setDirectory(), you will want to recall this method.
	 */
	public void go() {
		searchDirForCsv();
		getPairs();
	}
	
	/*
	 * This method is a simple getting for the map from this method.
	 * @return the map of titles and filenames from the document
	 */
	public Map<String, String> getMap() {
		return this.map;
	}
	
	/*
	 * This method recursively searches the directory so the user does not have to manually create a
	 * one level directory. It adds all csv files to the appropriate data strcuture.
	 */
	private void searchDirForCsv() {
		   Iterator<File> it = FileUtils.iterateFiles(topLevelDir, extensions, true);
		   while (it.hasNext()) {
			   File f = (File) it.next();
			   csvs.add(f);
		   }
	}
	
	/*
	 * This method creates a list of pairs of the article title and filename. The method
	 * reads a line of the csv file and extracts the title and filename fields. This method adds the 
	 */
	private void getPairs() {
		CSVReader reader;
		for (int i = 0; i < csvs.size(); i++) {
			try {
				reader = new CSVReader(new FileReader(csvs.get(i)));
				//Reader gets a line as an array
				String[] nextLine;
				while ((nextLine = reader.readNext()) != null) {
					String title = nextLine[TITLE];
					String name = nextLine[FILE];
					//The filename has some unnecessary junk to filter out.
					if (name.endsWith(".html")) {
						String nameFix = name.replace("data/", "");
						map.put(nameFix, title);
					}
				}
			} catch (IOException e) {
				continue;
			}

		}
	}
}
	
	
	
	
	

