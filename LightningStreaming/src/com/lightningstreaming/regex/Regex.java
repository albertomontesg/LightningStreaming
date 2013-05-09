package com.lightningstreaming.regex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

	public static String extractString (String input, String beg, String end) {
		
		Pattern pattern = Pattern.compile(beg+"(.*?)"+end);
		Matcher matcher = pattern.matcher(input);
		if (matcher.find())
		    return matcher.group(1);
		else
			return null;
	}
	
	public static int count (String input, String find) {
		int lastIndex = 0;
		int count =0;

		while(lastIndex != -1){
		       lastIndex = input.indexOf(find,lastIndex);
		       if (lastIndex != -1){
		             count ++;
		             lastIndex+=find.length();
		      }
		}
		return count;
	}
	
	public static String fileToString(File file) {
		// Array of the String lines of the file
		String data = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			while (in.ready()) {
				  String line = in.readLine();
				  if (!line.equals("")) data += line + "\n";
				}
			in.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return data;
	}

	public static String extractString(String input, String extract) {
		Pattern pattern = Pattern.compile("(.*?)"+extract+"(.*?)");
		Matcher matcher = pattern.matcher(input);
		if (matcher.find())
		    return matcher.group(1);
		else
			return null;
		
	}
	
	public static String extractFileName(File file) {
		String path = file.getName();
		String[] t = path.split("/");
		return t[t.length -1];
	}
	
	public static String extractFileName(String path) {
		String[] t = path.split("/");	
		return t[t.length -1];
	}

	public static String getUrlPath(URL url) {
		String u = url.toString();
		return Regex.extractString(u, Regex.extractFileName(url.toString()));
	}
}
