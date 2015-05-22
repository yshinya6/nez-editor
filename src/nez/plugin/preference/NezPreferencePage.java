package nez.plugin.preference;

import nez_editor.Activator;

import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class NezPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	private Text text;

	public NezPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	@Override
	protected void createFieldEditors() {

		addField(new ColorFieldEditor(PreferenceConstants.COLOR_DEFAULT, "Default",
				getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceConstants.COLOR_COMMENT, "Comment",
				getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceConstants.COLOR_TAG, "Tag (#ABC)",
				getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceConstants.COLOR_CONNECTOR, "Connector (@ABC)",
				getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceConstants.COLOR_VALUE, "Value (`ABC`)",
				getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceConstants.COLOR_STRING, "Strings (\"ABC\")",
				getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceConstants.COLOR_CHARACTER, "CharSet ([ABC])",
				getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceConstants.COLOR_FUNCTION, "Function (<ABC >)",
				getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceConstants.COLOR_EXAMPLE, "Example",
				getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceConstants.COLOR_LABEL, "Production (ABC = ...)",
				getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceConstants.COLOR_KEYWORD, "Keyword",
				getFieldEditorParent()));

	}

	@Override
	public void init(IWorkbench workbench) {
	}

//	protected Control createContents(Composite parent) {
//		IPreferenceStore store = getPreferenceStore();
//
//		Composite composite = new Composite(parent, SWT.NONE);
//
//		GridLayout layout = new GridLayout();
//		layout.numColumns = 2;
//		composite.setLayout(layout);
//
//		// Label label = new Label(composite, SWT.NONE);
//		// label.setText("Composer:");
//
//		text = new Text(composite, SWT.SINGLE | SWT.BORDER);
//		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
//		text.setText(store.getString(NezPreferenceInitializer.COMPOSER_NAME));
//
//		// label.setText("comment");
//
//		return composite;
//	}

}
