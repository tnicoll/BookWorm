package com.tnicoll.apps.bookworm.util;


public class BookStats {

	private int paragraph_count;
	private int word_count;
	private int avg_paragraph_word_count;
	private int sentence_count;
	private int speech_count;
	private int filesize;
	private int character_count;
	private int spelling_error_count;
	private int lines;
	
	public int getLines() {
		return lines;
	}

	public void setLines(int lines) {
		this.lines = lines;
	}

	public int getParagraph_count() {
		return paragraph_count;
	}

	public void setParagraph_count(int paragraph_count) {
		this.paragraph_count = paragraph_count;
	}

	public int getWord_count() {
		return word_count;
	}

	public void setWord_count(int word_count) {
		this.word_count = word_count;
	}

	public int getAvg_paragraph_word_count() {
		return avg_paragraph_word_count;
	}

	public void setAvg_paragraph_word_count(int avg_paragraph_word_count) {
		this.avg_paragraph_word_count = avg_paragraph_word_count;
	}

	public int getSentence_count() {
		return sentence_count;
	}

	public void setSentence_count(int sentence_count) {
		this.sentence_count = sentence_count;
	}

	public int getSpeech_count() {
		return speech_count;
	}

	public void setSpeech_count(int speech_count) {
		this.speech_count = speech_count;
	}

	public int getFilesize() {
		return filesize;
	}

	public void setFilesize(int filesize) {
		this.filesize = filesize;
	}

	public int getCharacter_count() {
		return character_count;
	}

	public void setCharacter_count(int character_count) {
		this.character_count = character_count;
	}

	public int getSpelling_error_count() {
		return spelling_error_count;
	}

	public void setSpelling_error_count(int spelling_error_count) {
		this.spelling_error_count = spelling_error_count;
	}

	public static int getSpeechCount(String token)
	{
		//to be implemented
		return 0;
	}
	
}
