package com.lightningstreaming.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import com.lightningstreaming.regex.Regex;

public class Connection {

	protected String urlst;
	protected URL url;
	protected BufferedReader in;
	protected ArrayList<String> name;
	protected ArrayList<String> urls;
	public Connection (URL u) throws IOException{
		url=u;
		urlst=url.toString();
		new BufferedReader(new InputStreamReader(url.openStream()));
	}
	
	public void Connect () throws IOException {
		name=new ArrayList<String>();
		urls=new ArrayList<String>();
		String st;
		while((st = in.readLine()) != null){
			//parse st
			if(Regex.count(st,".m3u8" )>0)
			{
				String n=Regex.extractString(st, "<a", "</a>");
				String url_m3u8=Regex.extractString(n," href=\"","\"");
				String name_m3u8=Regex.extractString(n,">",".m3u8");
				String total_url_m3u8=urlst+url_m3u8;
				name.add(name_m3u8);
				urls.add(total_url_m3u8);
			}
		}
		in.close();
		
	}
}
