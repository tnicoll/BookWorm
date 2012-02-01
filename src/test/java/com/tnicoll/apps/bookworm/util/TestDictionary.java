package com.tnicoll.apps.bookworm.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestDictionary {

	@Test
	public void testValidWord() {
		
		assertTrue(Dictionary.isInDictionary("aa"));
	}

}
