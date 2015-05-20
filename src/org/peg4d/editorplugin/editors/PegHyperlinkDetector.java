package org.peg4d.editorplugin.editors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;

public class PegHyperlinkDetector implements IHyperlinkDetector {
	PegEditor editor;

	public void init(PegEditor editor) {
		this.editor = editor;
	}

	private int getLabelOffset(String source, String word) {
		// d + "[ \n\t\r]*="
		Pattern p = Pattern.compile("^[ \t]*" + word + "(\\s*\\[[^\\]]*\\])?"
				+ "\\s*=", Pattern.MULTILINE);
		Matcher m = p.matcher(source);
		boolean matched = m.find();
		return m.start();
	}

	@Override
	public IHyperlink[] detectHyperlinks(ITextViewer textViewer,
			IRegion region, boolean canShowMultipleHyperlinks) {
		IProject project = PegUtil.getProject();
		if (project == null)
			return null;
		String source = textViewer.getDocument().get();

		int offset = region.getOffset();
		IRegion wordRegion = getWordRegion(source, offset);
		String word = source.substring(wordRegion.getOffset(),
				wordRegion.getOffset() + wordRegion.getLength());
		return new IHyperlink[]{new PegHyperlink(region, new Region(
				getLabelOffset(source, word), word.length()), word, editor)};
	}

	// private boolean findlabel(String source, String word) {
	// return Pattern.compile(word).matcher(source).find();
	// }

	private IRegion getWordRegion(String source, int offset) {
		int start = offset;
		for (; start >= 0 && isWordChar(source, start); start--);
		start++;
		int end = offset;
		for (; end < source.length(); end++) {
			if (!isWordChar(source, end)) {
				break;
			}
		}
		return new Region(start, end - start);
	}

	private boolean isWordChar(String s, int i) {
		char c = s.charAt(i);
		return Character.isLetterOrDigit(c) || c == '_';
	}

}
