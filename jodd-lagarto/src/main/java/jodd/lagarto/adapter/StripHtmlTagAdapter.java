// Copyright (c) 2003-present, Jodd Team (jodd.org). All Rights Reserved.

package jodd.lagarto.adapter;

import jodd.lagarto.Tag;
import jodd.lagarto.TagAdapter;
import jodd.lagarto.TagType;
import jodd.lagarto.TagVisitor;
import jodd.util.CharUtil;

import java.nio.CharBuffer;

/**
 * Strips all non-important characters from HTML.
 * Script and style blocks are not stripped, just HTML text blocks
 * and comments.
 */
public class StripHtmlTagAdapter extends TagAdapter {

	public StripHtmlTagAdapter(TagVisitor target) {
		super(target);
	}

	protected int strippedCharsCount;
	protected boolean strip;

	@Override
	public void start() {
		strippedCharsCount = 0;
		strip = true;
		super.start();
	}

	/**
	 * Skips HTML comments.
	 */
	@Override
	public void comment(CharSequence comment) {
		strippedCharsCount += comment.length() + 7;
	}

	private static char[] PRE = new char[] {'p', 'r', 'e'};

	@Override
	public void tag(Tag tag) {
		if (tag.getType() == TagType.START && tag.nameEquals(PRE)) {
			strip = false;
		}

		if (tag.getType() == TagType.END && tag.nameEquals(PRE)) {
			strip = true;
		}

		super.tag(tag);
	}

	/**
	 * Cleans unnecessary whitespaces.
	 */
	@Override
	public void text(CharSequence text) {
		if (strip == false) {
			super.text(text);
			return;
		}

		int textLength = text.length();

		char[] dest = new char[textLength];

		int ndx = 0;
		boolean regularChar = true;
		for (int i = 0; i < textLength; i++) {
			char c = text.charAt(i);

			if (CharUtil.isWhitespace(c)) {
				if (regularChar) {
					regularChar = false;
					c = ' ';
				} else {
					continue;
				}
			} else {
				regularChar = true;
			}

			dest[ndx] = c;
			ndx++;
		}

		if (regularChar || (ndx != 1)) {
			super.text(CharBuffer.wrap(dest, 0, ndx));
			strippedCharsCount += textLength - ndx;
		} else {
			strippedCharsCount += textLength;
		}
	}

	/**
	 * Returns total number of stripped chars.
	 */
	public int getStrippedCharsCount() {
		return strippedCharsCount;
	}
}
