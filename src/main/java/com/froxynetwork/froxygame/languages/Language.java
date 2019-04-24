package com.froxynetwork.froxygame.languages;

import java.util.HashMap;

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
	 * @param id The id of the message
	 * @param params The parameters. Can be empty
	 * @return Specific message if found, or the id
	 */
	public String get(String id, String... params);

	@AllArgsConstructor
	@Getter
	public class Impl implements Language {

		private Languages language;

		private HashMap<String, String> translates;

		public Impl() {
			this.translates = new HashMap<>();
		}

		@Override
		public String get(String id, String... params) {
			String msg = translates.get(id);
			if (msg == null)
				return id;
			for (String str : params) {
				msg = msg.replaceFirst("{}", str);
			}
			return msg;
		}
	}
}
