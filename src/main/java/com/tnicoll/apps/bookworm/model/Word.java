package com.tnicoll.apps.bookworm.model;

public class Word{

	String element;
	boolean isRecognised;
	
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

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
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
	
	
}
