package File_format;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class convert single csv file to kml file.
 * @author Bar Genish
 * @author Elyashiv Deri
 */

public class Csv2kml {
	/**
	 * This help function write all the lines in new kml.
	 * @param a ArrayList<String[]> with all the lines that we copy from the csv.
	 * @param output the name of the new kml.
	 */
	private static void writeFileKML(ArrayList<String[]> a, String output) {
		ArrayList<String> content = new ArrayList<String>();
		String kmlstart = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				"<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n<Document><Style id=\"red\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/red-dot.png</href></Icon></IconStyle></Style><Style id=\"yellow\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/yellow-dot.png</href></Icon></IconStyle></Style><Style id=\"green\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/green-dot.png</href></Icon></IconStyle></Style><Folder><name>Wifi Networks</name>\n";
		content.add(kmlstart);

		String kmlend = "\n</Folder></Document></kml>";
		try{
			FileWriter fw = new FileWriter(output);
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 1; i < a.size(); i++) {
				String[] s = a.get(i);
				String kmlelement ="<Placemark>\n" +
						"<name><![CDATA["+s[1]+"]]></name>\n" +
						"<description><![CDATA[BSSID: <b>"+s[0]+"</b><br/>Capabilities: <b>"+s[2]+"</b><br/>Frequency: <b>"+s[4]+"</b><br/>Timestamp: <b>+"+s[5]+"+</b><br/>Date: <b>"+s[3]+"</b>]]></description>\n" +
						"<styleUrl>"+"green"+"</styleUrl>"+"<Point>\n" +
						"<coordinates>"+s[7]+","+s[6]+"</coordinates>" +
						"</Point>\n" +
						"</Placemark>";
				content.add(kmlelement);
			}
			content.add(kmlend);
			String csv = content.toString().replaceAll("</Placemark>, <Placemark>", "</Placemark><Placemark>").replaceAll("</Placemark>, ", "</Placemark>").replaceAll(", <Placemark>", "<Placemark>");
			csv = csv.substring(1, csv.length()-1);
			bw.write(csv);
			bw.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * This help function copy all the lines in the csv to a new arraylist of strings[].
	 * @param csvPath the path of the csv that we want to convert.
	 * @return a ArrayList<String[]> with all the lines that we copy from the csv.
	 */
	private static ArrayList<String[]> getCSVList(String csvPath){
		ArrayList<String[]> a = new ArrayList<String[]>();
		String line = "";
		String cvsSplitBy = ",";
		try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) 
		{
			line=br.readLine();
			line=br.readLine();
			while ((line = br.readLine()) != null) 
			{
				a.add(line.split(","));
			}

		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		return a;
	}
	/**
	 * This function convert csv file to kml.
	 * @param csvPath the path of the csv that we want to convert.
	 * @param kmlPath the path of new kml that we create.
	 */
	public static void convertCsv2Kml(String csvPath, String kmlPath) {
		ArrayList<String[]> a = getCSVList(csvPath);
		writeFileKML(a, kmlPath);
	}
}
