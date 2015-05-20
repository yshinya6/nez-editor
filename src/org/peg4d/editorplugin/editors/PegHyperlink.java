package org.peg4d.editorplugin.editors;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;

public class PegHyperlink implements IHyperlink {
	private PegEditor editor;
	private IRegion region, target;
	String linkText;

	public PegHyperlink(IRegion region, IRegion target, String linktext,
			PegEditor editor) {
		this.region = region;
		this.target = target;
		this.linkText = linktext;
		this.editor = editor;
	}

	@Override
	public String getHyperlinkText() {
		return linkText;
	}

	@Override
	public void open() {
		if (target != null) {
			int offset = target.getOffset();
			int length = target.getLength();
			editor.selectAndReveal(offset, length);
		}
	}

	@Override
	public IRegion getHyperlinkRegion() {
		// TODO Auto-generated method stub
		return region;
	}

	@Override
	public String getTypeLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
