package nez.plugin.editors;

import nez.ast.CommonTree;
import nez.ast.SourcePosition;
import nez.lang.Production;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;

// This is listener class that is invoked when click the production label on outline page  

public class OutlineSelectionChangedListener implements ISelectionChangedListener {
	protected NezEditor editor;

	public OutlineSelectionChangedListener(NezEditor editor) {
		this.editor = editor;
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		Object element = selection.getFirstElement();
		if (element != null) {
			// detect the production's position, and move pointer.
			Production rule = (Production) element;
			SourcePosition sp = rule.getSourcePosition();
			if (sp instanceof CommonTree && sp != null) {
				CommonTree node = (CommonTree) sp;
				int offset = (int) node.getSourcePosition();
				int length = node.getLength();
				editor.selectAndReveal(offset, length);
			}
		}
	}
}
