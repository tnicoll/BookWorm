package com.tnicoll.apps.bookworm.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import java.util.Scanner;
import java.util.StringTokenizer;

import com.tnicoll.apps.bookworm.model.Word;

public final class Dictionary {
	
	private static HashMap<String, Word>dict = readDictionary();
	private static final String filename = "/Dictionaries/Dictionary.txt";
	
	private Dictionary(){}

	private static HashMap<String, Word> readDictionary() {

		HashMap<String, Word>words = new HashMap<String, Word>();
		
		try(InputStream is = Dictionary.class.getResourceAsStream(filename);
				Scanner lineScanner = new Scanner(new InputStreamReader(is))){
			
			while(lineScanner.hasNextLine())
			{
				String temp = lineScanner.nextLine();
				StringTokenizer st = new StringTokenizer(temp, "|");
				while(st.hasMoreTokens()){
					
					Word w = new Word();
					
					w.setElement(st.nextToken());
					try{
					w.setSyllables(Integer.parseInt(st.nextToken()));
					}catch(NullPointerException | NumberFormatException n){
						w.setSyllables(-1);
					}
					try{
					w.setType(Word.WordType.valueOf(st.nextToken()));
					}catch(NullPointerException | IllegalArgumentException ill){
						w.setType(Word.WordType.U);
					}
					words.put(w.getElement(), w);
				}
			}
			return words;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	public static boolean isInDictionary(String word){
		return(dict.containsKey(word));
	}
	
	public static Word getWord(String word){
		return(dict.get(word));
		
	}

}
