package com.lightningstreaming.regex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

	public static String extractString (String input, String beg, String end) {
		
		String mydata = "some string with 'the data i want' inside";
		Pattern pattern = Pattern.compile(beg+"(.*?)"+end);
		Matcher matcher = pattern.matcher(mydata);
		if (matcher.find())
		    return matcher.group(1);
		else
			return null;
		
		/*
		Vector<String> v = new Vector<String>(Arrays.asList(input.split(beg)));
		Vector<String> w = new Vector<String>(Arrays.asList(v.get(1).split(end)));
		String output = w.get(0);
		return output; */
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
		
		return null;
	}
	
}
