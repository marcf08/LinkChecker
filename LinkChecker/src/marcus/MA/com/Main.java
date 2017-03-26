package marcus.MA.com;

import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
    	System.out.print("Enter directory of HTML files: ");
		Scanner scan = new Scanner(System.in);
    	String directory = scan.next();
    	Links link = new Links(directory);
    	link.run();
    	scan.close();
	}
}
