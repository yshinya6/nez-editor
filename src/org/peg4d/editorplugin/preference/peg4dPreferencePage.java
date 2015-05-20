package org.peg4d.editorplugin.preference;

import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import peg4deditorplug_in.Activator;

public class Peg4dPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	private Text text;

	public Peg4dPreferencePage() {
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
		addField(new ColorFieldEditor(PreferenceConstants.COLOR_CHARACTER, "Pattern ([ABC])",
				getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceConstants.COLOR_FUNCTION, "Function (<ABC >)",
				getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceConstants.COLOR_EXAMPLE, "Example ([example: ])",
				getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceConstants.COLOR_LABEL, "Rule Name (ABC = ...)",
				getFieldEditorParent()));
	}

	// public peg4dPreferencePage(String title) {
	// super();
	// // TODO 自動生成されたコンストラクター・スタブ
	// }

	// public peg4dPreferencePage(String title, ImageDescriptor image) {
	// super();
	// // TODO 自動生成されたコンストラクター・スタブ
	// }

	@Override
	public void init(IWorkbench workbench) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected Control createContents(Composite parent) {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		Composite composite = new Composite(parent, SWT.NONE);

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);

		Label label = new Label(composite, SWT.NONE);
		label.setText("Composer:");

		text = new Text(composite, SWT.SINGLE | SWT.BORDER);
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		text.setText(store.getString(Peg4dPreferenceInitializer.COMPOSER_NAME));

		label.setText("comment");

		// backupColor = PreferenceConverter.getColor(store,
		// PreferenceConstants.COLOR_COMMENT);
		// commentColor = new ColorSelector(composite);
		// commentColor.addListener(new ColorListener(store, COLOR_COMMENT));
		// commentColor.setColorValue(backupColor);
		//
		// backupStyle = store.getInt(STYLE_COMMENT);
		// commentCheck = new Button(composite, SWT.CHECK | SWT.CENTER);
		// commentCheck.addSelectionListener(new CheckListener(store,
		// STYLE_COMMENT));
		// commentCheck.setSelection((backupStyle & SWT.BOLD) != 0);

		return composite;
	}

	// @Override
	// public boolean performOk() {
	// IPreferenceStore store = Activator.getDefault().getPreferenceStore();
	// store.setValue(Peg4dPreferenceInitializer.COMPOSER_NAME, text.getText());
	// return true;
	// }
	//
	// @Override
	// protected void performDefaults() {
	// IPreferenceStore store = Activator.getDefault().getPreferenceStore();
	// text.setText(store
	// .getDefaultString(Peg4dPreferenceInitializer.COMPOSER_NAME));
	// }

	// @Override
	// public Point computeSize() {
	// // TODO 自動生成されたメソッド・スタブ
	// return null;
	// }

	// @Override
	// public boolean isValid() {
	// // TODO 自動生成されたメソッド・スタブ
	// return false;
	// }
	//
	// @Override
	// public boolean okToLeave() {
	// // TODO 自動生成されたメソッド・スタブ
	// return false;
	// }
	//
	// @Override
	// public boolean performCancel() {
	// // TODO 自動生成されたメソッド・スタブ
	// return false;
	// }

	// @Override
	// public void setContainer(IPreferencePageContainer
	// preferencePageContainer) {
	// // TODO 自動生成されたメソッド・スタブ
	//
	// }

	// @Override
	// public void setSize(Point size) {
	// // TODO 自動生成されたメソッド・スタブ
	//
	// }

	// @Override
	// public void createControl(Composite parent) {
	// // TODO 自動生成されたメソッド・スタブ
	//
	// }

	// @Override
	// public Control getControl() {
	// // TODO 自動生成されたメソッド・スタブ
	// return null;
	// }

	// @Override
	// public String getDescription() {
	// // TODO 自動生成されたメソッド・スタブ
	// return null;
	// }

	// @Override
	// public String getErrorMessage() {
	// // TODO 自動生成されたメソッド・スタブ
	// return null;
	// }

	// @Override
	// public Image getImage() {
	// // TODO 自動生成されたメソッド・スタブ
	// return null;
	// }

	// @Override
	// public String getMessage() {
	// // TODO 自動生成されたメソッド・スタブ
	// return null;
	// }

	// @Override
	// public String getTitle() {
	// // TODO 自動生成されたメソッド・スタブ
	// return null;
	// }

	// @Override
	// public void setImageDescriptor(ImageDescriptor image) {
	// // TODO 自動生成されたメソッド・スタブ
	//
	// }

}
