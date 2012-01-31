package com.tnicoll.apps.bookworm.model;

import java.util.Scanner;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.tnicoll.apps.bookworm.util.BookStats;



public class Book {
	
	/*
	 * Contains all the information about a book
	 */
	//private Map<Word, MutableInt> words;
	private HashMultiset <String>words; 
	
	
	public BookStats stats;

	
	public BookStats getStats() {
		return stats;
	}

	public void setStats(BookStats stats) {
		this.stats = stats;
	}

	public Multiset <String>readBook(String content)
	{
		stats = new BookStats();
		words = HashMultiset.create();
		stats.setSentence_count(countSentences(content));
		content = stripPunctuation(content);
		Scanner lineScanner = new Scanner(content);
		int paragraph_count = 0, word_count=0;

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
					word_count++;
				}
			}
		}
		stats.setParagraph_count(paragraph_count);
		stats.setWord_count(word_count);
		stats.setAvg_paragraph_word_count(word_count/paragraph_count);
		
		return words;
	}
	
	private int countSentences(String content) {

		if (content == null) {
	        return -1;
	    }
		else{
		    int count = 0;
		    
		    content = content.replaceAll("[...]", ".");
		    
		    for (int i = 0; i < content.length(); i++) {
		        char c = content.charAt(i);
		        if (c == '.') {
		            count++;
		        }
		    }
		    
		    return count;
		}
	}

	public String stripPunctuation(String token)
	{
		if(token.substring(token.length()-1).matches("\\p{Punct}+"))
			return token.substring(0, token.length()-1);
		else
			return token;
	}
	
}
