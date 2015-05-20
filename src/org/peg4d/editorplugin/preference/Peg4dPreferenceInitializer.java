package org.peg4d.editorplugin.preference;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.StringConverter;
import org.peg4d.editorplugin.editors.IPegColorConstants;

import peg4deditorplug_in.Activator;

public class Peg4dPreferenceInitializer extends AbstractPreferenceInitializer {

	public static String COMPOSER_NAME = "yshinya6";

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.COLOR_TAG,
				StringConverter.asString(IPegColorConstants.TAG));
		store.setDefault(PreferenceConstants.COLOR_COMMENT,
				StringConverter.asString(IPegColorConstants.PEG_COMMENT));
		store.setDefault(PreferenceConstants.COLOR_STRING,
				StringConverter.asString(IPegColorConstants.STRING));
		store.setDefault(PreferenceConstants.COLOR_CONNECTOR,
				StringConverter.asString(IPegColorConstants.PEG_CONNECTOR));
		store.setDefault(PreferenceConstants.COLOR_DEFAULT,
				StringConverter.asString(IPegColorConstants.DEFAULT));
		store.setDefault(PreferenceConstants.COLOR_EXAMPLE,
				StringConverter.asString(IPegColorConstants.PEG_EXAMPLE));
		store.setDefault(PreferenceConstants.COLOR_CHARACTER,
				StringConverter.asString(IPegColorConstants.PEG_CHARACTER));
		store.setDefault(PreferenceConstants.COLOR_VALUE,
				StringConverter.asString(IPegColorConstants.VALUE));
		store.setDefault(PreferenceConstants.COLOR_LABEL,
				StringConverter.asString(IPegColorConstants.LABEL));
		store.setDefault(PreferenceConstants.COLOR_FUNCTION,
				StringConverter.asString(IPegColorConstants.PEG_FUNCTION));
	}

}
