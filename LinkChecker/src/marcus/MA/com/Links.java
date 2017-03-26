package marcus.MA.com;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
/*
 * This class does the heavy lifting. It both reads the csv file for the article name
 * and file pair. It searches the directory and finally pulls the links. Note that
 * this class extends thread in order to split the execution path away from the
 * event dispatch thread. This allows the GUI to update as this class runs.
 * TODO: Refactor the pull links method.
 */
public class Links extends Thread {

	private String directory;
	private ArrayList<File> files;
	private String[] extensions = { "html" };
	private File topLevelDir;
	private ArrayList<String> content;
	List<List<String>> nameLink;
	String[] ar;

	public Links() {
		files = new ArrayList<File>();
		topLevelDir = new File(this.directory);
		content = new ArrayList<String>();
		nameLink = new ArrayList<>();
	}

	public Links(String directory) {
		files = new ArrayList<File>();
		this.directory = directory;
		topLevelDir = new File(this.directory);
		content = new ArrayList<String>();
		nameLink = new ArrayList<>();
	}

	public void setDir(String dir) {
		this.directory = dir;
	}
	
	/*
	 * This method allows the class to run via thread start.
	 * (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		searchDir();
		pullLinks();
	}

	/*
	 * This method searches the directory passed to the Links class. It creates an
	 * iterator over the list of files and adds them to the files array.
	 */
	private void searchDir() {
		Pairs p = new Pairs(this.directory);
		p.go();
		Iterator<File> it = FileUtils.iterateFiles(topLevelDir, extensions, true);
		while (it.hasNext()) {
			File f = (File) it.next();
			if (!f.canRead()) {
				System.out.println("Cannot read file " + f.getName() + " . Skipping.");
			} else if (f.getName().endsWith(".html")) {
				files.add(f);
				try {
					//Enclose the filename in brackets for lookup
					@SuppressWarnings("deprecation")
					String toAdd = "{" + f.getName() + "}" + FileUtils.readFileToString(f);
					content.add(toAdd);
				} catch (IOException e) {
					it.next();
				}
			}
		}
	}
	
	/*
	 * This method pulls the links. It starts a timer to get the execution
	 * time. It also pauses in order to prevent firing the HTTP request too frequently.
	 */
	public void pullLinks() {
		System.out.println("STARTING EXECUTION");
		long startTime = System.nanoTime();
		int counter = 0;
		Pairs p = new Pairs(this.directory);
		p.go();
		//Get the map of the article name
		Map<String, String> linkArticleName = p.getMap();
		
		for (int i = 0; i < content.size(); i++) {
			String s = content.get(i);
			//Get the filename from the content string
			s = s.substring(s.indexOf("{") + 1);
			s = s.substring(0, s.indexOf("}"));
			
			//Parse the html for http web addresses
			Document doc = Jsoup.parse(content.get(i));
			Elements link = doc.select("a[href*=http]");
			
			//Write output
			System.out.println("----------NEXT ARTICLE----------");
			System.out.println("ARTICLE NAME = " + "'" + linkArticleName.get(s) + "'" + " HAS LINKS: ");
			if (link.size() == 0) {
				System.out.println("No Links in this article.");
			} else {
				for (int j = 0; j < link.size(); j++) {
					String absFinalurl = link.get(j).attr("abs:href");
					int code = ping(absFinalurl);
					System.out.println(absFinalurl);
					System.out.println("          " + "RESPONSE = " + code);

				}
			}

		}

		//Finally, print a closing message about what was found
		System.out.println("----------FINISHED EXECUTION----------");
		long endTime = System.nanoTime();
		long total = endTime - startTime;
		total = TimeUnit.SECONDS.convert(total, TimeUnit.NANOSECONDS);
		System.out.println("Total time was " + total + " seconds.");
		System.out.println("Found " + counter + " potential dead links");
		System.out.println("Save this output and review your files for 404 Responses.");
		
	}
	
	/*
	 * This method pings a web address and gets the response.
	 * @param webAddress the address to ping
	 * @return the response code or -1 if the operation was unsuccessful
	 */
	private int ping(String webAddress) {
		try {
			URL url = new URL (webAddress);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			return connection.getResponseCode();			
		} catch (IOException e) {
			return -1;
		}

	}
	

}
