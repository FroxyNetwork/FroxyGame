package com.froxynetwork.froxygame.languages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
 * A language
 */
public interface Language {

	/**
	 * @return The language associated to this language
	 */
	public Languages getLanguage();

	/**
	 * Return specific language associated to specific id with parameters
	 * 
	 * @param id     The id of the message
	 * @param params The parameters. Can be empty
	 * @return Specific message if found, or the id
	 */
	public String get(String id, String... params);

	/**
	 * Register messages in specific file
	 * 
	 * @param file Specific file with messages inside
	 */
	public void register(File file);

	@AllArgsConstructor
	@Getter
	public class LanguageImpl implements Language {

		private final Logger LOG = LoggerFactory.getLogger(getClass());

		private Languages language;

		private HashMap<String, String> translates;

		public LanguageImpl(Languages language) {
			this.language = language;
			this.translates = new HashMap<>();
		}

		@Override
		public String get(String id, String... params) {
			String msg = translates.get(id);
			if (msg == null)
				return id;
			for (String str : params)
				msg = msg.replaceFirst("\\{\\}", str);
			return msg;
		}

		private Pattern pattern = Pattern.compile(" : ");

		@Override
		public void register(File file) {
			LOG.info("Initializing file {}", file.getName());
			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				String line;
				int lines = 0;
				int success = 0;
				int empty = 0;
				int error = 0;
				while ((line = br.readLine()) != null) {
					LOG.debug("Reading line {}", line);
					lines++;
					if ("".equalsIgnoreCase(line.trim())) {
						LOG.warn("Line {} is empty !", lines);
						empty++;
						continue;
					}
					String[] strs = pattern.split(line);
					if (strs.length != 2) {
						LOG.warn("Error while parsing line {}, found {}", lines, line);
						error++;
						continue;
					}
					String oldValue = translates.put(strs[0], strs[1]);
					if (oldValue != null)
						LOG.warn("Warning : key {} is aleady registered as value = {}", strs[0], oldValue);
					success++;
				}
				LOG.info("Reading {} lines, {} successfully imported, {} empty and {} errors", lines, success, empty,
						error);
			} catch (FileNotFoundException ex) {
				LOG.error("File {} not found", file.getName());
				LOG.error("", ex);
				return;
			} catch (IOException ex) {
				LOG.error("IOException with file {}", file.getName());
				LOG.error("", ex);
				return;
			}
			LOG.info("File {} initialized !", file.getName());
		}
	}
}
