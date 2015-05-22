package nez.plugin.editors;

import java.util.ArrayList;

import nez.SourceContext;
import nez.lang.Production;
import nez.lang.ReportSet;
import nez.util.UList;
import nez_editor.Activator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITextViewerExtension2;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.MatchingCharacterPainter;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.IDocumentProviderExtension;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.texteditor.SimpleMarkerAnnotation;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

public class NezEditor extends TextEditor implements IPropertyChangeListener {
	private ColorManager colorManager;
	public static final String ASSIST_ACTION_ID = "Peg4dEditor.Assist";
	protected OutlinePage outlinePage;

	public NezEditor() {
		super();
		colorManager = new ColorManager();
		setDocumentProvider(new NezDocumentProvider());
		setSourceViewerConfiguration(new NezEditorConfiguration(colorManager));
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.addPropertyChangeListener(this);
		// // add preference listener
		// Activator.getDefault().getPreferenceStore()
		// .addPropertyChangeListener(this);
	}

	@Override
	public void dispose() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.removePropertyChangeListener(this);
		colorManager.dispose();
		super.dispose();
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		NezEditorConfiguration config = (NezEditorConfiguration) getSourceViewerConfiguration();
		config.updatePreferences();
		getSourceViewer().invalidateTextPresentation();
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);

		// for brace match
		MatchingCharacterPainter painter = new MatchingCharacterPainter(getSourceViewer(),
				new NezBraceMatcher());
		painter.setColor(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
		ITextViewerExtension2 extension = (ITextViewerExtension2) getSourceViewer();
		extension.addPainter(painter);
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		NezEditorConfiguration configuration = (NezEditorConfiguration) getSourceViewerConfiguration();
		NezHyperlinkDetector detector = configuration.getPegHyperlinkDetector();
		detector.init(this);
	}

	@Override
	protected void initializeKeyBindingScopes() {
		setKeyBindingScopes(new String[]{"org.eclipse.ui.textEditorScope",
				"peg4d-editor-plugin.context"});
	}

	@Override
	protected void createActions() {
		super.createActions();
		IAction contentAssistAction = new Action("Contents Assist") {
			@Override
			public void run() {
				ITextOperationTarget target = (ITextOperationTarget) NezEditor.this
						.getAdapter(ITextOperationTarget.class);
				target.doOperation(ISourceViewer.CONTENTASSIST_PROPOSALS);
			}
		};
		contentAssistAction
				.setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS);
		setAction(ASSIST_ACTION_ID, contentAssistAction);
	}

	public static void addAnnotation(IMarker marker, ITextSelection selection, ITextEditor editor) {
		// The DocumentProvider enables to get the document currently loaded in
		// the editor
		IDocumentProvider idp = editor.getDocumentProvider();

		// This is the document we want to connect to. This is taken from
		// the current editor input.
		IDocument document = idp.getDocument(editor.getEditorInput());

		// The IannotationModel enables to add/remove/change annotation to a
		// Document
		// loaded in an Editor
		IAnnotationModel iamf = idp.getAnnotationModel(editor.getEditorInput());

		// Note: The annotation type id specify that you want to create one of
		// your
		// annotations
		SimpleMarkerAnnotation ma = new SimpleMarkerAnnotation("nez.plugin.occurences",
				marker);

		// Finally add the new annotation to the model
		iamf.connect(document);
		iamf.addAnnotation(ma, new Position(selection.getOffset(), selection.getLength()));
		iamf.disconnect(document);
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		if (IContentOutlinePage.class.equals(adapter)) {
			if (outlinePage == null) {
				outlinePage = new OutlinePage(this);
			}
			return outlinePage;
		}
		return super.getAdapter(adapter);
	}

	@Override
	public void doSaveAs() {
		super.doSaveAs();
		update();
	}

	@Override
	public void doSave(IProgressMonitor progressMonitor) {
		super.doSave(progressMonitor);
		update();
	}

	private void update() {
		getSourceViewer().invalidateTextPresentation();
		IDocument document = getDocumentProvider().getDocument(getEditorInput());
		NezEParser parser = new NezEParser(document.get());
		parser.parse();
		UList<Production> ruleList = parser.getProductionList();

		// update outline page
		if (outlinePage != null) {
			outlinePage.refresh(ruleList);
		}

		// update marker
		IFileEditorInput input = (IFileEditorInput) getEditorInput();
		IResource resource = input.getFile();
		try {
			resource.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		validate(input.getFile(), document);
		getSourceViewer().invalidateTextPresentation();
	}

	private void validate(IFile file, IDocument document) {
		String sourceText = document.get();
		NezEParser parser = new NezEParser(sourceText);
		parser.parse();
		ArrayList<ReportSet> reportList = parser.getReportList();
		try {
			createReportMarker(reportList, file, document);
		} catch (BadLocationException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	private void createReportMarker(ArrayList<ReportSet>  reportList, IFile file, IDocument document)
			throws BadLocationException, CoreException {
		for (ReportSet report : reportList) {
			int begin = (int)report.getSourcePosition().getSourcePosition();
			int lineNum = document.getLineOfOffset(begin);
			int eol = begin + document.getLineLength(lineNum);
			int end = eol;

			// detect right end position
			for (int i = begin + 1; i < eol; i++) {
				if (document.getChar(i) == ' ') {
					end = i;
					break;
				}
			}

			IMarker marker = file.createMarker(IMarker.PROBLEM);
			switch (report.getReportType()) {
				case ERROR:
					marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
				case WARNING:
					marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
				case NOTICE:
					marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
				default :
					break;
			}
			marker.setAttribute(IMarker.MESSAGE, report.getMessage());
			marker.setAttribute(IMarker.LINE_NUMBER, lineNum + 1);
			marker.setAttribute(IMarker.CHAR_START, begin);
			marker.setAttribute(IMarker.CHAR_END, end);
		}
	}

	// create error marker when fail parsing
	private void createErrorMarker(SourceContext context, IFile file, IDocument document)
			throws CoreException, BadLocationException {

		int lineNum = document.getLineOfOffset((int) context.getHeadPosition());
		int begin = document.getLineOffset(lineNum - 1);
		int end = begin + document.getLineLength(lineNum - 1);
		IMarker marker = file.createMarker(IMarker.PROBLEM);
		marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
		marker.setAttribute(IMarker.MESSAGE, context.getSyntaxErrorMessage());
		marker.setAttribute(IMarker.LINE_NUMBER, lineNum);
		marker.setAttribute(IMarker.CHAR_START, begin);
		marker.setAttribute(IMarker.CHAR_END, end);
	}

	public void refresh() {
		IDocumentProvider provider = getDocumentProvider();
		IDocumentProviderExtension extension = (IDocumentProviderExtension) provider;
		try {
			extension.synchronize(getEditorInput());
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
}
