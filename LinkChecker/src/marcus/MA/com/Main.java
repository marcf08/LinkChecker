package marcus.MA.com;

import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
	
    	System.out.print("Enter directory of HTML files: ");
		Scanner scan = new Scanner(System.in);
    	String directory = scan.next();
    	LinkChecker link = new LinkChecker(directory);
    	link.extractLinks();
    	scan.close();
		
		Pairs p = new Pairs(directory);
		p.go();
	}
	
	
	
}
