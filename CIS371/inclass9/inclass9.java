package inclass9;

import java.io.BufferedReader;
import java.io.FileReader;

public class inclass9 {

	public static void main(String[] args) {

		String fileName = "links.html";

		// This will reference one line at a time
		String line = "";

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileName);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				if (line.contains("href") && line.contains("http")) {
					int start = line.indexOf("href=") + 6;
					int end = line.indexOf("\">");
					
					if (line.contains(".edu")) {
						end = line.indexOf(".edu") + 4;
					}
					
					if (line.contains(".com")) {
						end = line.indexOf(".com") + 4;
					}
					
					System.out.println(line.substring(start, end));
					
					line = line.substring(end);
					
					if (line.contains("href") && line.contains("http")) {
						start = line.indexOf("href=") + 6;
						end = line.indexOf("\">");
						
						if (line.contains(".edu")) {
							end = line.indexOf(".edu") + 4;
						}
						
						if (line.contains(".com")) {
							end = line.indexOf(".com") + 4;
						}
						
						System.out.println(line.substring(start, end));
					}
				}
			}

			// Always close files.
			bufferedReader.close();
		} catch (Exception e) {
			System.out.println("Error" + e);
		}
	}
}