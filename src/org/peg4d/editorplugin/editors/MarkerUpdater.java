package org.peg4d.editorplugin.editors;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.ui.texteditor.IMarkerUpdater;

public class MarkerUpdater implements IMarkerUpdater {

	public MarkerUpdater() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getMarkerType() {
		return "org.peg4d.editroplugin.markers";
	}

	@Override
	public String[] getAttribute() {
		return null;
	}

	@Override
	public boolean updateMarker(IMarker marker, IDocument document,
			Position position) {
		try {
			int start = position.getOffset();
			int end = position.getOffset() + position.getLength();
			marker.setAttribute(IMarker.CHAR_START, start);
			marker.setAttribute(IMarker.CHAR_END, end);
			return true;
		} catch (CoreException e) {
			return false;
		}
	}
}
