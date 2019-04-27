package com.froxynetwork.froxygame;

import static com.froxynetwork.froxygame.languages.LanguageManager.$;
import static com.froxynetwork.froxygame.languages.LanguageManager.$_;

import java.io.File;

import com.froxynetwork.froxygame.languages.LanguageManager;
import com.froxynetwork.froxygame.languages.Languages;

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
public class FroxyGame {

	public FroxyGame() {
		File lang = new File(getClass().getClassLoader().getResource("lang").getFile());
		LanguageManager.register(lang);
		// test.test in DEFAULT language
		System.out.println($("test.test"));
		// test.test2 in DEFAULT language
		System.out.println($("test.test2"));
		// test.test in French language
		System.out.println($("test.test", Languages.FRENCH));
		// test.test3 in French language. This message id doesn't exist in french, so the english message is returned
		System.out.println($("test.test3", Languages.FRENCH));
		// test.test3 in French language. This message id doesn't exist in french neither in english, so the id is returned
		System.out.println($_("test.test3", Languages.FRENCH));

		System.out.println($("test.test4", "ah", "0ddlyoko"));
		System.out.println($("test.test4", Languages.FRENCH, "bh", "0ddlyoko"));
	}

	public static void main(String[] args) {
		new FroxyGame();
	}
}
