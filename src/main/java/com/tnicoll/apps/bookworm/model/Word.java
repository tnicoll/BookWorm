package com.tnicoll.apps.bookworm.model;

public class Word implements Comparable<Object>{

	private String element;
	private boolean isRecognised;
	private int syllables;
	private WordType type;
	
	public enum WordType {
	    N, V, ADJ, ADV, U 
	}
	
	public boolean isRecognised() {
		return isRecognised;
	}

	public void setRecognised(boolean isRecognised) {
		this.isRecognised = isRecognised;
	}

	public Word(){}

	public Word(String element) {
		super();
		this.element = element;
		this.isRecognised = true;
	}
	public Word(String element, int syllables, WordType type) {
		super();
		this.element = element;
		this.isRecognised = true;
		this.syllables = syllables;
		this.type = type;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}
	
	

	public int getSyllables() {
		return syllables;
	}

	public void setSyllables(int syllables) {
		this.syllables = syllables;
	}

	public WordType getType() {
		return type;
	}

	public void setType(WordType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return getElement();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((element == null) ? 0 : element.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		if (element == null) {
			if (other.element != null)
				return false;
		} else if (!element.equals(other.element))
			return false;
		return true;
	}

//	@Override
//	public int compareTo(Word o) {
//		return this.getElement().compareTo(o.getElement());
//	}

	@Override
	public int compareTo(Object o) {
		return(this.getElement().compareTo(((Word)o).getElement()));
	}
	
	
}
