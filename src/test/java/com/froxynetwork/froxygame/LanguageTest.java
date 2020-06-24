package com.froxynetwork.froxygame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Test;

import com.froxynetwork.froxygame.languages.Language.LanguageImpl;
import com.froxynetwork.froxygame.languages.Languages;

/**
 * MIT License
 *
 * Copyright (c) 2020 FroxyNetwork
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 * @author 0ddlyoko
 */
public class LanguageTest {

	@Test
	public void testVars() {
		LanguageImpl languageImpl = new LanguageImpl(Languages.ENGLISH);

		// Test vars
		assertEquals(Languages.ENGLISH, languageImpl.getLanguage());
		assertNotNull(languageImpl.getTranslates());
		assertEquals(0, languageImpl.getTranslates().size());
	}

	@Test(expected = NullPointerException.class)
	public void testRegisterNull() {
		LanguageImpl languageImpl = new LanguageImpl(Languages.ENGLISH);
		languageImpl.register(null);
	}

	@Test
	public void testRegisterFileNotFound() {
		LanguageImpl languageImpl = new LanguageImpl(Languages.ENGLISH);

		// Import file
		languageImpl.register(new File("src/test/resources/lang/not_found.lang"));
		assertEquals(0, languageImpl.getTranslates().size());
	}

	@Test
	public void testRegisterFileEmpty() {
		LanguageImpl languageImpl = new LanguageImpl(Languages.ENGLISH);

		// Import file
		languageImpl.register(new File("src/test/resources/lang/empty.lang"));
		assertEquals(0, languageImpl.getTranslates().size());
	}

	@Test
	public void testRegisterSpace() {
		LanguageImpl languageImpl = new LanguageImpl(Languages.ENGLISH);

		// Import file
		languageImpl.register(new File("src/test/resources/lang/lang_space.lang"));
		assertEquals(5, languageImpl.getTranslates().size());
	}

	@Test
	public void testNoSpace1() {
		LanguageImpl languageImpl = new LanguageImpl(Languages.ENGLISH);

		// Import file
		languageImpl.register(new File("src/test/resources/lang/lang_no_space_1.lang"));
		assertEquals(5, languageImpl.getTranslates().size());
	}

	@Test
	public void testNoSpace2() {
		LanguageImpl languageImpl = new LanguageImpl(Languages.ENGLISH);

		// Import file
		languageImpl.register(new File("src/test/resources/lang/lang_no_space_2.lang"));
		assertEquals(5, languageImpl.getTranslates().size());
	}

	@Test
	public void testNoSpace3() {
		LanguageImpl languageImpl = new LanguageImpl(Languages.ENGLISH);

		// Import file
		languageImpl.register(new File("src/test/resources/lang/lang_no_space_3.lang"));
		assertEquals(5, languageImpl.getTranslates().size());
	}

	@Test
	public void testGet() {
		LanguageImpl languageImpl = new LanguageImpl(Languages.ENGLISH);
		languageImpl.register(new File("src/test/resources/lang/lang_space.lang"));

		// Test id not found
		assertEquals("id.not.found", languageImpl.get("id.not.found"));

		// Normal
		assertEquals("1", languageImpl.get("test1"));
		// One arg at string, one arg passed
		assertEquals("2 (a)", languageImpl.get("test2", "a"));
		// One arg at string, zero args passed
		assertEquals("3 ({})", languageImpl.get("test3"));
		// One arg at string, two args passed
		assertEquals("4 (a)", languageImpl.get("test4", "a", "b"));
		// Two args at string, two args passed
		assertEquals("5 (a - b)", languageImpl.get("test5", "a", "b"));
	}

	@Test
	public void testGetMultipleColon() {
		LanguageImpl languageImpl = new LanguageImpl(Languages.ENGLISH);

		// Import file
		languageImpl.register(new File("src/test/resources/lang/lang_multiple_colon.lang"));
		assertEquals(5, languageImpl.getTranslates().size());
		assertEquals("1", languageImpl.get("test1"));
		assertEquals("2:2", languageImpl.get("test2"));
		assertEquals("3:3 : 3", languageImpl.get("test3"));
		assertEquals("4:4 : 4 :4", languageImpl.get("test4"));
		assertEquals("5:5 : 5 :5: 5", languageImpl.get("test5"));
	}
}
