package com.tnicoll.apps.bookworm.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;



public class Book {
	
	/*
	 * Contains all the information about a book
	 */
	private Map<Word, MutableInt> words;
	private int paragraph_count;

	
	public Map <Word, MutableInt>readBook(String content)
	{
		words = new HashMap<Word, MutableInt>();
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
					Word w = new Word(s);
					MutableInt value = words.get(w);
					if (value == null) 
					{
					  value = new MutableInt ();
					  words.put (w, value);
					} 
					else 
					{
					  value.inc();
					}
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
