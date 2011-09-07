package com.tnicoll.apps.bookworm;

import java.util.NavigableSet;
import java.util.Scanner;
import java.util.TreeSet;

public class Book {
	private NavigableSet<Word> words;
	private int paragraph_count;

	public NavigableSet <Word>readBook(String content)
	{
		words = new TreeSet<Word>();
		
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
					Word w = new Word(wordScanner.next());
					words.add(w);
				}
			}
		}
		System.out.println("count: " + paragraph_count);
		return words;
	}
	
}
