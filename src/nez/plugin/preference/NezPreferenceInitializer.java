package nez.plugin.preference;

import nez.plugin.editors.INezColorConstants;
import nez_editor.Activator;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.StringConverter;

public class NezPreferenceInitializer extends AbstractPreferenceInitializer {

	public static String COMPOSER_NAME = "Shinya Yamagichi";

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.COLOR_TAG,
				StringConverter.asString(INezColorConstants.TAG));
		store.setDefault(PreferenceConstants.COLOR_COMMENT,
				StringConverter.asString(INezColorConstants.COMMENT));
		store.setDefault(PreferenceConstants.COLOR_STRING,
				StringConverter.asString(INezColorConstants.STRING));
		store.setDefault(PreferenceConstants.COLOR_CONNECTOR,
				StringConverter.asString(INezColorConstants.CONNECTOR));
		store.setDefault(PreferenceConstants.COLOR_DEFAULT,
				StringConverter.asString(INezColorConstants.DEFAULT));
		store.setDefault(PreferenceConstants.COLOR_EXAMPLE,
				StringConverter.asString(INezColorConstants.EXAMPLE));
		store.setDefault(PreferenceConstants.COLOR_CHARACTER,
				StringConverter.asString(INezColorConstants.CHARACTER));
		store.setDefault(PreferenceConstants.COLOR_VALUE,
				StringConverter.asString(INezColorConstants.VALUE));
		store.setDefault(PreferenceConstants.COLOR_LABEL,
				StringConverter.asString(INezColorConstants.LABEL));
		store.setDefault(PreferenceConstants.COLOR_FUNCTION,
				StringConverter.asString(INezColorConstants.FUNCTION));
		store.setDefault(PreferenceConstants.COLOR_KEYWORD,
				StringConverter.asString(INezColorConstants.KEYWORD));
	}

}
