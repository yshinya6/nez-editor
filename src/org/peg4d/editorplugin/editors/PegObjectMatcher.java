package org.peg4d.editorplugin.editors;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.source.ICharacterPairMatcher;

import peg4deditorplug_in.Activator;

public class PegObjectMatcher implements ICharacterPairMatcher {

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IRegion match(IDocument document, int offset) {
		int len = document.getLength();
		if (offset >= len) {
			return null;
		}
		try {
			if (offset > 0 && document.getChar(offset - 1) == '{') {
				// 対応する'}'の位置を返す
				int end = document.get().indexOf("}", offset);
				if (end >= 0) {
					return new Region(end, 1);
				}
			} else if (document.getChar(offset) == '}') {
				// 対応する'{'の位置を返す
				int start = document.get().lastIndexOf("{", offset);
				if (start >= 0) {
					return new Region(start, 1);
				}
			}
		} catch (BadLocationException e) {
			ILog log = Activator.getDefault().getLog();
			log.log(new Status(Status.WARNING, Activator.PLUGIN_ID, 0, "PegObjectMatcher#match() location error.", e));
		}
			return null;
	}

	@Override
	public int getAnchor() {
		// TODO Auto-generated method stub
		return 0;
	}

}
