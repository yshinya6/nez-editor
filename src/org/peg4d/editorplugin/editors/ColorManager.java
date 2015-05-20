package org.peg4d.editorplugin.editors;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

import peg4deditorplug_in.Activator;

public class ColorManager {

	protected Map fColorTable = new HashMap(10);
	protected IPreferenceStore store;

	public ColorManager() {
		this.store = Activator.getDefault().getPreferenceStore();
	}

	public void dispose() {
		Iterator e = fColorTable.values().iterator();
		while (e.hasNext())
			((Color) e.next()).dispose();
	}

	public Color getColor(RGB rgb) {
		Color color = (Color) fColorTable.get(rgb);
		if (color == null) {
			color = new Color(Display.getCurrent(), rgb);
			fColorTable.put(rgb, color);
		}
		return color;
	}

	public Color getColor(String prefKey) {
		String colorName = store.getString(prefKey);
		RGB rgb = StringConverter.asRGB(colorName);
		return getColor(rgb);
	}
}
