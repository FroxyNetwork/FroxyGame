package com.froxynetwork.froxygame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Test;

import com.froxynetwork.froxygame.languages.LanguageManager;
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
public class LanguageManagerTest {

	@Test
	public void testDefault() {
		assertNotNull(LanguageManager.DEFAULT);
	}

	@Test
	public void testGetLanguage() {
		assertEquals(Languages.ENGLISH, LanguageManager.getLanguage(Languages.ENGLISH).getLanguage());
		assertEquals(Languages.FRENCH, LanguageManager.getLanguage(Languages.FRENCH).getLanguage());
	}

	@Test
	public void testRegisterDirectoryNotFound() {
		assertEquals(0, LanguageManager.register(new File("src/test/resources/lang/langs/notfound")));
	}

	@Test
	public void testRegister() {
		assertEquals(2, LanguageManager.register(new File("src/test/resources/lang/langs")));
	}

	@Test
	public void testLang() {
		// register has been called in testRegister() method but this is not a problem
		LanguageManager.register(new File("src/test/resources/lang/langs"));

		// $(id, params)
		// Id does not exist
		assertEquals("id.not.exist", LanguageManager.$("id.not.exist", "taratata"));
		// Id exists
		assertEquals("English", LanguageManager.$("lang.english"));
		// Id exists & one arg
		assertEquals("Title 1 - 0ddlyoko", LanguageManager.$("title", "0ddlyoko"));
		// Id exists & two args
		assertEquals("Resume 1 - 0ddlyoko - lol", LanguageManager.$("resume", "0ddlyoko", "lol"));

		// $(id, lang, params)
		// Id does not exist
		assertEquals("id.not.exist", LanguageManager.$("id.not.exist", Languages.FRENCH));
		// Id exists
		assertEquals("Français", LanguageManager.$("lang.french", Languages.FRENCH));
		// Id exists & one arg
		assertEquals("Titre 1 - 0ddlyoko", LanguageManager.$("title", Languages.FRENCH, "0ddlyoko"));
		// Id exists & two args
		assertEquals("Résumé 1 - 0ddlyoko - lol", LanguageManager.$("resume", Languages.FRENCH, "0ddlyoko", "lol"));
		// Id does not exist but exists in default language
		assertEquals("Only English", LanguageManager.$("lang.only.english", Languages.FRENCH));

		// $_(id, lang, params)
		// Id does not exist
		assertEquals("id.not.exist", LanguageManager.$_("id.not.exist", Languages.FRENCH));
		// Id exists
		assertEquals("Français", LanguageManager.$_("lang.french", Languages.FRENCH));
		// Id exists & one arg
		assertEquals("Titre 1 - 0ddlyoko", LanguageManager.$_("title", Languages.FRENCH, "0ddlyoko"));
		// Id exists & two args
		assertEquals("Résumé 1 - 0ddlyoko - lol", LanguageManager.$_("resume", Languages.FRENCH, "0ddlyoko", "lol"));
		// Id does not exist but exists in default language
		assertEquals("lang.only.english", LanguageManager.$_("lang.only.english", Languages.FRENCH));
	}
}
