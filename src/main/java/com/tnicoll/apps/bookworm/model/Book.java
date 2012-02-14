package com.tnicoll.apps.bookworm.model;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.tnicoll.apps.bookworm.util.BookStats;
import com.tnicoll.apps.bookworm.util.Dictionary;



public class Book {
	
	/*
	 * Contains all the information about a book
	 */
	//private Map<Word, MutableInt> words;
	private HashMultiset <Word>words; 
	
	
	public BookStats stats;

	
	public BookStats getStats() {
		return stats;
	}

	public void setStats(BookStats stats) {
		this.stats = stats;
	}

	public Multiset <Word>readBook(String content)
	{
		stats = new BookStats();
		words = HashMultiset.create();
		stats.setSentence_count(countSentences(content));
		stats.setCharacter_count(content.length());
		stats.setSpeech_count(countQuotes(content));
		content = stripPunctuation(content);
		Scanner lineScanner = new Scanner(content);
		int paragraph_count = 0, word_count=0, linecount=0, spelling_error_count=0, no_of_nouns=0,
				no_of_verbs=0, no_of_adverbs=0, no_of_adjectives=0, no_of_unknown=0;

		StringBuilder sb = new StringBuilder();
		while(lineScanner.hasNextLine())
		{
			sb = new StringBuilder(lineScanner.nextLine());
			linecount++;
			if(!sb.toString().equals(""))
			{
				paragraph_count++;
				Scanner wordScanner = new Scanner(sb.toString());
				while(wordScanner.hasNext())
				{
					
					Word s = new Word(stripPunctuation(wordScanner.next()));
					Word lookup = Dictionary.getWord(s.getElement());
					if(lookup!=null){
						s.setRecognised(true);
						s.setSyllables(lookup.getSyllables());
						s.setType(lookup.getType());
						
					}
					else{
						spelling_error_count++;
						s.setRecognised(false);
						s.setType(Word.WordType.U);
						s.setSyllables(-1);
					}
					
					switch(s.getType()){
					case N:
						no_of_nouns++;
						break;
					case V:
						no_of_verbs++;
						break;
					case ADJ:
						no_of_adjectives++;
						break;
					case ADV:
						no_of_adverbs++;
						break;
					default:
						no_of_unknown++;
						break;
					}
//					if(!Dictionary.isInDictionary(s.getElement())){
//						spelling_error_count++;
//						s.setRecognised(false);
//					}
					words.add(s);
					word_count++;
				}
			}
		}
		stats.setParagraph_count(paragraph_count);
		stats.setWord_count(word_count);
		stats.setAvg_paragraph_word_count(word_count/paragraph_count);
		stats.setLines(linecount);
		stats.setSpelling_error_count(spelling_error_count);
		stats.setNo_of_adjectives(no_of_adjectives);
		stats.setNo_of_adverbs(no_of_adverbs);
		stats.setNo_of_nouns(no_of_nouns);
		stats.setNo_of_verbs(no_of_verbs);
		stats.setNo_of_unknown(no_of_unknown);
		
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
		Pattern p = Pattern.compile("^\\p{P}*(.*?)\\p{P}*$");
	    Matcher m = p.matcher(token);
	    
	    if(m.matches())
	    	return m.group(1);
	    else
	    	return token;
	    
	}
	
	public int countQuotes(String content){
		int count = 0;
		Pattern p = Pattern.compile("['’\"]\\s");
		Matcher m = p.matcher(content);
		while(m.find()){
			count++;
		}
		return count;
	}
	
	 
}
