package nez.plugin.editors;

import nez.plugin.preference.PreferenceConstants;

import org.eclipse.jface.text.DefaultTextHover;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.ui.texteditor.MarkerAnnotation;

public class NezEditorConfiguration extends SourceViewerConfiguration {
	private NezDoubleClickStrategy doubleClickStrategy;
	private NezHyperlinkDetector pegHyperlinkDetector;
	private NezScanner scanner;
	private ColorManager colorManager;

	public NezEditorConfiguration(ColorManager colorManager) {
		this.colorManager = colorManager;
	}

	@Override
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[]{IDocument.DEFAULT_CONTENT_TYPE, NezPartitionScanner.PEG_COMMENT};
	}

	@Override
	public ITextDoubleClickStrategy getDoubleClickStrategy(ISourceViewer sourceViewer,
			String contentType) {
		if (doubleClickStrategy == null)
			doubleClickStrategy = new NezDoubleClickStrategy();
		return doubleClickStrategy;
	}

	protected NezScanner getNezScanner() {
		if (scanner == null) {
			scanner = new NezScanner(colorManager);
			scanner.setDefaultReturnToken(new Token(new TextAttribute(colorManager
					.getColor(PreferenceConstants.COLOR_DEFAULT))));
		}
		return scanner;
	}

	public void updatePreferences() {
		getNezScanner().init();
		PresentationReconciler reconciler = new PresentationReconciler();
		NonRuleBasedDamagerRepairer ndr = new NonRuleBasedDamagerRepairer(new TextAttribute(
				colorManager.getColor(PreferenceConstants.COLOR_COMMENT)));
		reconciler.setDamager(ndr, NezPartitionScanner.PEG_COMMENT);
		reconciler.setRepairer(ndr, NezPartitionScanner.PEG_COMMENT);
	}

	@Override
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();
		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(getNezScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
		NonRuleBasedDamagerRepairer ndr = new NonRuleBasedDamagerRepairer(new TextAttribute(
				colorManager.getColor(PreferenceConstants.COLOR_COMMENT)));
		reconciler.setDamager(ndr, NezPartitionScanner.PEG_COMMENT);
		reconciler.setRepairer(ndr, NezPartitionScanner.PEG_COMMENT);
		return reconciler;
	}

	public NezHyperlinkDetector getPegHyperlinkDetector() {
		if (pegHyperlinkDetector == null) {
			pegHyperlinkDetector = new NezHyperlinkDetector();
		}
		return pegHyperlinkDetector;
	}

	@Override
	public IHyperlinkDetector[] getHyperlinkDetectors(ISourceViewer sourceViewer) {
		IHyperlinkDetector[] parentDetecors = super.getHyperlinkDetectors(sourceViewer);
		IHyperlinkDetector[] myDetectors = new IHyperlinkDetector[parentDetecors.length + 1];
		System.arraycopy(parentDetecors, 0, myDetectors, 0, parentDetecors.length);
		myDetectors[myDetectors.length - 1] = getPegHyperlinkDetector();
		return myDetectors;
	}

	@Override
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		ContentAssistant assistant = new ContentAssistant();

		NezContentAssistProcessor processor = new NezContentAssistProcessor();
		assistant.setContentAssistProcessor(processor, IDocument.DEFAULT_CONTENT_TYPE);
		assistant.enableAutoActivation(true);
		assistant.setAutoActivationDelay(350);
		assistant.enablePrefixCompletion(true);
		assistant.install(sourceViewer);
		return assistant;
	}

	@Override
	public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType) {
		return new DefaultTextHover(sourceViewer) {
			@Override
			protected boolean isIncluded(Annotation annotation) {
				if (annotation instanceof MarkerAnnotation) {
					return true;
				}
				return false;
			}
		};
	}
}