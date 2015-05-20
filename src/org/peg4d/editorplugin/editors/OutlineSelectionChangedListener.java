package org.peg4d.editorplugin.editors;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.peg4d.ParsingObject;
import org.peg4d.ParsingRule;

//アウトライン上でデータ（要素・ノード）がクリックされたときに呼ばれるリスナークラス
public class OutlineSelectionChangedListener implements ISelectionChangedListener {
	protected PegEditor editor;

	public OutlineSelectionChangedListener(PegEditor editor) {
		this.editor = editor;
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		Object element = selection.getFirstElement();
		//ルールの位置を特定し，カーソルを移動させる
		if (element instanceof ParsingRule) {
			ParsingRule rule = (ParsingRule) element;
			ParsingObject po = rule.po;
			if (po != null) {
				int offset = (int) po.getSourcePosition();
				int length = po.getLength();
				editor.selectAndReveal(offset, length);
			}
		}
	}
}
