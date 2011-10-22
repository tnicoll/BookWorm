package com.tnicoll.apps.bookworm;

import static org.junit.Assert.*;

import org.junit.Test;

import com.tnicoll.apps.bookworm.model.Book;

public class TestBook {

	@Test
	public void testStripPunctuationWithPunctuation() {
		Book tBook = new Book();
		assertEquals(tBook.stripPunctuation("test&"), "test");
	}
	@Test
	public void testStripPunctuationWithoutPunctuation() {
		Book tBook = new Book();
		assertEquals(tBook.stripPunctuation("test"), "test");
	}

}
