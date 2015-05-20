package org.peg4d.editorplugin.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.peg4d.ParsingRule;
import org.peg4d.UList;

public class OutlinePage extends ContentOutlinePage {
	protected RootData root = new RootData();
	protected PegEditor editor;

	public OutlinePage(PegEditor editor) {
		this.editor = editor;
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);

		TreeViewer viewer = getTreeViewer();
		viewer.setContentProvider(new PegContentProvider());
		viewer.setLabelProvider(new PegLabelProvider());
		viewer.addSelectionChangedListener(new OutlineSelectionChangedListener(editor));
		viewer.setInput(root);

		IDocument document = editor.getDocumentProvider().getDocument(editor.getEditorInput());
		PegSimpleParser parser = new PegSimpleParser(document.get());
		UList<ParsingRule> ruleList = parser.getRuleList();
		refresh(ruleList);
	}

	public void refresh(UList<ParsingRule> ruleList) {
		root.ruleList = ruleList;
		getTreeViewer().refresh();
	}
}

class RootData {
	public UList<ParsingRule> ruleList;
}
