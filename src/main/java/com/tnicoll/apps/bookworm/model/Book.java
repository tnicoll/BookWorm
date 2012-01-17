package com.tnicoll.apps.bookworm.model;

import java.util.Scanner;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;



public class Book {
	
	/*
	 * Contains all the information about a book
	 */
	//private Map<Word, MutableInt> words;
	private HashMultiset <String>words; 
	private int paragraph_count;

	
	public Multiset <String>readBook(String content)
	{
		words = HashMultiset.create();
		content = stripPunctuation(content);
		Scanner lineScanner = new Scanner(content);
		paragraph_count = 0;

		while(lineScanner.hasNextLine())
		{
			String temp = lineScanner.nextLine();
			if(!temp.equals(""))
			{
				paragraph_count++;
				Scanner wordScanner = new Scanner(temp);
				while(wordScanner.hasNext())
				{
					String s = stripPunctuation(wordScanner.next());
					words.add(s);
				}
			}
		}
		System.out.println("count: " + paragraph_count);
		return words;
	}
	
	public String stripPunctuation(String token)
	{
		if(token.substring(token.length()-1).matches("\\p{Punct}+"))
			return token.substring(0, token.length()-1);
		else
			return token;
	}

	public int getParagraph_count() {
		return paragraph_count;
	}

	public void setParagraph_count(int paragraph_count) {
		this.paragraph_count = paragraph_count;
	}
	
}
