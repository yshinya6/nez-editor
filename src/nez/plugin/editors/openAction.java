package nez.plugin.editors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

public class openAction implements IEditorActionDelegate {
	private IEditorPart targetEditor = null;

	@Override
	public void run(IAction action) {
		NezEditor editor = (NezEditor) targetEditor;
		IDocument document = editor.getDocumentProvider().getDocument(
				editor.getEditorInput());
		String source = document.get();
		ITextSelection selection = (ITextSelection) editor
				.getSelectionProvider().getSelection();
		int offset = selection.getOffset();
		IRegion wordRegion = getWordRegion(source, offset);
		String word = source.substring(wordRegion.getOffset(),
				wordRegion.getOffset() + wordRegion.getLength());
		NezHyperlink link = new NezHyperlink(null, new Region(getLabelOffset(
				source, word), word.length()), word, editor);
		link.open();
	}

	private IRegion getWordRegion(String source, int offset) {
		int start = offset;
		for (; start >= 0 && isWordChar(source, start); start--)
			;
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

	private int getLabelOffset(String source, String word) {
		// d + "[ \n\t\r]*="
		Pattern p = Pattern.compile("^[ \t]*" + word + "(\\s*\\[[^\\]]*\\])?"
				+ "\\s*=", Pattern.MULTILINE);
		Matcher m = p.matcher(source);
		m.find();
		return m.start();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		this.targetEditor = targetEditor;
	}

}
