package nez.plugin.editors;

import nez.lang.Production;
import nez.util.UList;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;


// This class control OutlinePage view.
// The view is composed of information from Nez Parser.
// So you have to update the version of Nez Parser with changing Nez Syntax. 

public class OutlinePage extends ContentOutlinePage {
	protected RootData root = new RootData();
	protected NezEditor editor;

	public OutlinePage(NezEditor editor) {
		this.editor = editor;
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);

		TreeViewer viewer = getTreeViewer();
		viewer.setContentProvider(new NezContentProvider());
		viewer.setLabelProvider(new NezLabelProvider());
		viewer.addSelectionChangedListener(new OutlineSelectionChangedListener(editor));
		viewer.setInput(root);

		IDocument document = editor.getDocumentProvider().getDocument(editor.getEditorInput());
		NezEParser parser = new NezEParser(document.get());
		UList<Production> ruleList = parser.getProductionList();
		refresh(ruleList);
	}

	public void refresh(UList<Production> ruleList) {
		root.ruleList = ruleList;
		getTreeViewer().refresh();
	}
}

class RootData {
	public UList<Production> ruleList;
}
