package com.eptd.dsaver.dbo;

import java.util.List;

public class ExtractDataProcessor {
	private static final String SEPERATOR = ",";
	
	
	public static String writeLine(List<String> cells){
		StringBuilder line = new StringBuilder();
		for(String s : cells){
			line.append(s);
			line.append(SEPERATOR);
		}
		return line.toString();
	}
	
	public static void main(String args[]){
		StringBuilder line = new StringBuilder();
		line.append("aaa")
			.append(",");
		line.replace(line.length()-1, line.length(),"bbb");
		System.out.println(line.toString());
	}
}
