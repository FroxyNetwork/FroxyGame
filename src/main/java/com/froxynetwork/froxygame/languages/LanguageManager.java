package com.froxynetwork.froxygame.languages;

import java.io.File;
import java.util.EnumMap;
import java.util.Map;

/**
 * MIT License
 *
 * Copyright (c) 2019 FroxyNetwork
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
/**
 * Language Manager<br />
 * This interface is used to manage languages, to get and register translates, etc
 */
public class LanguageManager {

	/**
	 * Default language, set to English
	 */
	public static final Languages DEFAULT = Languages.ENGLISH;

	private static Map<Languages, Language> languages;

	/**
	 * Private constructor
	 */
	private LanguageManager() {
	}

	static {
		languages = new EnumMap<>(Languages.class);
		for (Languages l : Languages.values())
			languages.put(l, new Language.Impl(l));
		//		register(new File(pathname));
	}

	/**
	 * Register a path as a language directory.<br />
	 * All language files MUST have the correct name to be loaded.<br />
	 * Files name MUST be of this form: "{name}.lang".<br />
	 * Example: <code>fr_FR.lang or en_US.lang</code>
	 * 
	 * @param path The directory
	 */
	public static void register(File path) {

	}

	/**
	 * Return specific Language class from specific language
	 * 
	 * @param lang The language
	 * @return Specific Language
	 * @see Languages
	 */
	public static Language getLanguage(Languages lang) {
		return languages.get(lang);
	}

	/**
	 * Get the default translate of specific id.<br />
	 * Same as <code>get(id, DEFAULT, params)</code>
	 * @param id The id of the message
	 * @param params The parameters
	 * @return The message translated by default language, or the id if message id doesn't exist
	 */
	public static String $(String id, String... params) {
		return $(id, DEFAULT, params);
	}

	/**
	 * Get the translate of specific id with specific language.<br />
	 * @param id The id of the message
	 * @param lang The specific language
	 * @param params The parameters
	 * @return The message translated by specific language, or the id if message id doesn't exist
	 */
	public static String $(String id, Languages lang, String... params) {
		Language l = getLanguage(lang);
		if (l == null) {
			// Impossible
			throw new IllegalStateException("Language not found !");
		}
		return l.get(id, params);
	}
}
