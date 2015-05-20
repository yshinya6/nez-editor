package org.peg4d.editorplugin.editors;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;
import org.peg4d.editorplugin.preference.PreferenceConstants;

class PEGScanner extends RuleBasedScanner {
	ColorManager manager;

	public PEGScanner(ColorManager manager) {
		this.manager = manager;
		IToken token = new Token(new TextAttribute(manager.getColor(IPegColorConstants.DEFAULT)));
		IToken connector = new Token(new TextAttribute(
				manager.getColor(PreferenceConstants.COLOR_CONNECTOR)));
		IToken tag = new Token(new TextAttribute(manager.getColor(PreferenceConstants.COLOR_TAG)));
		IToken string = new Token(new TextAttribute(
				manager.getColor(PreferenceConstants.COLOR_STRING)));
		IToken label = new Token(new TextAttribute(
				manager.getColor(PreferenceConstants.COLOR_LABEL)));
		IToken value = new Token(new TextAttribute(
				manager.getColor(PreferenceConstants.COLOR_VALUE)));
		IToken character = new Token(new TextAttribute(
				manager.getColor(PreferenceConstants.COLOR_CHARACTER)));
		IToken example = new Token(new TextAttribute(
				manager.getColor(PreferenceConstants.COLOR_EXAMPLE)));
		IToken function = new Token(new TextAttribute(
				manager.getColor(PreferenceConstants.COLOR_FUNCTION)));

		IRule[] rules = {new tagRule(tag), new ConnectorRule(connector), new labelRule(label),
				new SingleLineRule("`", "`", value),
				new SingleLineRule("\"", "\"", string), new SingleLineRule("'", "'", string),
				new MultiLineRule("<", ">", function),
				new MultiLineRule("[example:", "]", example),
				new SingleLineRule("[", "]", character),
				new WhitespaceRule(new PegWhitespaceDetector()),
				/* new PegWordRule("@", connecter) */};

		setRules(rules);
	}

	public void init() {
		IToken connector = new Token(new TextAttribute(
				manager.getColor(PreferenceConstants.COLOR_CONNECTOR)));
		IToken tag = new Token(new TextAttribute(manager.getColor(PreferenceConstants.COLOR_TAG)));
		IToken string = new Token(new TextAttribute(
				manager.getColor(PreferenceConstants.COLOR_STRING)));
		IToken label = new Token(new TextAttribute(
				manager.getColor(PreferenceConstants.COLOR_LABEL)));
		IToken value = new Token(new TextAttribute(
				manager.getColor(PreferenceConstants.COLOR_VALUE)));
		IToken character = new Token(new TextAttribute(
				manager.getColor(PreferenceConstants.COLOR_CHARACTER)));
		IToken example = new Token(new TextAttribute(
				manager.getColor(PreferenceConstants.COLOR_EXAMPLE)));
		IToken function = new Token(new TextAttribute(
				manager.getColor(PreferenceConstants.COLOR_FUNCTION)));

		IRule[] rules = {new tagRule(tag), new ConnectorRule(connector), new labelRule(label),
				new SingleLineRule("`", "`", value),
				new SingleLineRule("\"", "\"", string), new SingleLineRule("'", "'", string),
				new funcRule(function),
				new MultiLineRule("[example:", "]", example),
				new SingleLineRule("[", "]", character),
				new WhitespaceRule(new PegWhitespaceDetector()),};
		setRules(rules);
	}
}

class PegWordRule extends WordRule {
	public PegWordRule(String word, IToken token) {
		super(new PegWordDetector());
		addWord(word, token);
	}
}

class PegWordDetector implements IWordDetector {
	@Override
	public boolean isWordStart(char c) {
		return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z') || (c == '@');
	}

	@Override
	public boolean isWordPart(char c) {
		return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z') || (c == '@');
	}
}
