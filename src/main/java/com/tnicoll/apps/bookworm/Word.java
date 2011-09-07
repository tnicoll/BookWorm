package com.tnicoll.apps.bookworm;

public class Word implements Comparable{

	String token;
	int count;
	public Word(String word)
	{
		token = word;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "Word [token=" + token + ", count=" + count + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((token == null) ? 0 : token.hashCode());
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
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}
	@Override
	public int compareTo(Object o) {
	    
	    if ( this == o ) 
	    {	
	    	this.setCount(this.getCount()+1);
	    	return 0;
	    
	    }
	    else if(this.token.compareTo(((Word) o).getToken()) ==0)
	    {
	    	this.setCount(this.getCount()+1);
	    	return 0;
	    }
	    
	    else return -1;
	}
	
	
}
