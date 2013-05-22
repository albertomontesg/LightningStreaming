package com.lightningstreaming.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

import com.lightningstreaming.regex.Regex;

public class Connection {

	protected Socket s;
	protected URL url;
	protected PrintWriter out;
	protected BufferedReader in;
	protected ArrayList<String> name;
	protected ArrayList<String> urls;
	public Connection (URL u) throws IOException{
		url=u;
		String st=url.toString();
		s=new Socket(st,80);
		in=new BufferedReader(new InputStreamReader(s.getInputStream()));
		out=new PrintWriter(s.getOutputStream(),true);
		name=new ArrayList<String>();
		urls=new ArrayList<String>();
	}
	
	public void Connect () throws IOException {
		String st=in.readLine();
		while(st!=null){
			//parse st
			if(Regex.count(st,"<h" )>0)
			{
				String n=Regex.extractString(st, ">", "<");
				name.add(n);
			}
			else if(Regex.count(st, "<p>")>0){
				String u=Regex.extractString(st, "<p>", ".</p>");
				urls.add(u);
			}
			st=in.readLine();
		}
		s.close();
		
	}
}
