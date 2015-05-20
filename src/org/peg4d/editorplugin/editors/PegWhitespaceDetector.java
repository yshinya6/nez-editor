package org.peg4d.editorplugin.editors;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

public class PegWhitespaceDetector implements IWhitespaceDetector {

	public boolean isWhitespace(char c) {
		return (c == ' ' || c == '\t' || c == '\n' || c == '\r');
	}
}
