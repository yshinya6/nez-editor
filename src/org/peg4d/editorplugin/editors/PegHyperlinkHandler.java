package org.peg4d.editorplugin.editors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.Region;
import org.eclipse.ui.handlers.HandlerUtil;

public class PegHyperlinkHandler extends AbstractHandler {

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		PegEditor editor = (PegEditor) HandlerUtil.getActiveEditor(event);
		IDocument document = editor.getDocumentProvider().getDocument(
				editor.getEditorInput());
		String source = document.get();
		ITextSelection selection = (ITextSelection) editor
				.getSelectionProvider().getSelection();
		int offset = selection.getOffset();
		IRegion wordRegion = getWordRegion(source, offset);
		String word = source.substring(wordRegion.getOffset(),
				wordRegion.getOffset() + wordRegion.getLength());
		PegHyperlink link = new PegHyperlink(null, new Region(getLabelOffset(
				source, word), word.length()), word, editor);
		link.open();
		return null;
	}

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

	private int getLabelOffset(String source, String word) {
		// d + "[ \n\t\r]*="
		Pattern p = Pattern.compile("^[ \t]*" + word + "(\\s*\\[[^\\]]*\\])?"
				+ "\\s*=", Pattern.MULTILINE);
		Matcher m = p.matcher(source);
		m.find();
		return m.start();
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isHandled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

}
