package com.froxynetwork.froxygame.languages;

import java.io.File;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private static final Logger LOG = LoggerFactory.getLogger(LanguageManager.class);

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
		LOG.info("Initializing LanguageManager. Default language = {}", DEFAULT);
		languages = new EnumMap<>(Languages.class);
		for (Languages l : Languages.values())
			languages.put(l, new Language.LanguageImpl(l));
		LOG.info("LanguageManager initialized, {} languages imported !", Languages.values().length);
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
		LOG.info("Registering directory {}", path.getPath());
		if (!path.exists() || !path.isDirectory()) {
			LOG.error("File {} doesn't exist or is not a directory !", path.getPath());
			// We don't throw a new Exception
			return;
		}
		int nbrFiles = 0;
		for (Entry<Languages, Language> langs : languages.entrySet()) {
			String fileName = langs.getKey().getLang() + ".lang";
			File f = new File(path, fileName);
			if (!f.exists() || !f.isFile()) {
				LOG.warn("File {} not found, or is not a file !", fileName);
				continue;
			}
			LOG.info("Parsing file {}", fileName);
			langs.getValue().register(f);
			nbrFiles++;
		}
		LOG.info("Directory {} registered, {} files imported", path.getPath(), nbrFiles);
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
	 * Get the default translate of specific message id.<br />
	 * Same as <code>$(id, DEFAULT, params)</code>
	 * 
	 * @param id The id of the message
	 * @param params The parameters
	 * @return The message translated by default language, or the id if message id doesn't exist
	 */
	public static String $(String id, String... params) {
		return $(id, DEFAULT, params);
	}

	/**
	 * Get the translation of specific message id with specific language. If message id not found, return the translation with DEFAULT language.<br />
	 * 
	 * @param id The id of the message
	 * @param lang The specific language
	 * @param params The parameters
	 * @return The message translated by specific language, or the message translated by default language, or the id if message id doesn't exist
	 */
	public static String $(String id, Languages lang, String... params) {
		String msg = $_(id, lang, params);
		// If message is not found in specific language, we'll return the message in DEFAULT language.
		if (msg.equals(id) && lang != DEFAULT) {
			LOG.warn("Message {} not found in language {}, trying to get message in DEFAULT language", id, lang);
			msg = $_(id, DEFAULT, params);
			if (msg.equals(id))
				LOG.warn("Message {} not found in DEFAULT language", id);
		}
		return msg;
	}

	/**
	 * Get the translate of specific id with specific language.<br />
	 * 
	 * @param id The id of the message
	 * @param lang The specific language
	 * @param params The parameters
	 * @return The message translated by specific language, or the id if message id doesn't exist
	 */
	public static String $_(String id, Languages lang, String... params) {
		Language l = getLanguage(lang);
		if (l == null) {
			// Impossible
			throw new IllegalStateException("Language not found !");
		}
		return l.get(id, params);
	}
}
