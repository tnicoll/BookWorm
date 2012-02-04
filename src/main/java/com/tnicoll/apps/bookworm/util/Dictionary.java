package com.tnicoll.apps.bookworm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;

public final class Dictionary {
	
	private static HashSet<String>dict = readDictionary();
	private static final String filename = "Dictionary2.txt";
	
	private Dictionary(){}

	private static HashSet<String> readDictionary() {

		HashSet<String>d = new HashSet<String>();
		
		File file = new File(filename);

		try(Scanner lineScanner = new Scanner(new FileReader(file));){
			
			while(lineScanner.hasNextLine())
			{
				String temp = lineScanner.nextLine();
				d.add(temp);
			}
			return d;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	public static boolean isInDictionary(String word){
		return(dict.contains(word.toLowerCase()));
	}

}
