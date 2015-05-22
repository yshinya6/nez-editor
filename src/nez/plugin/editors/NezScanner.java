package nez.plugin.editors;

import nez.plugin.preference.PreferenceConstants;

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
import org.eclipse.swt.SWT;

class NezScanner extends RuleBasedScanner {
	ColorManager manager;
	static final String[] NEZ_KEYWORD = {
			"public", "import", "inline", "type", "grammar", "format", "define", "example"
	};
	public NezScanner(ColorManager manager) {
		this.manager = manager;
		IToken token = new Token(new TextAttribute(manager.getColor(INezColorConstants.DEFAULT)));
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
		IToken keyword = new Token(new TextAttribute(
				manager.getColor(PreferenceConstants.COLOR_KEYWORD), null, SWT.BOLD));
		KeywordRule keywordRule = new KeywordRule(token);
		keywordRule.addWords(NEZ_KEYWORD, keyword);

		IRule[] rules = {
				keywordRule,
				new labelRule(label),
				new tagRule(tag),
				new ConnectorRule(connector),
				new MultiLineRule("'''", "'''", example),
				new SingleLineRule("`", "`", value),
				new SingleLineRule("\"", "\"", string), new SingleLineRule("'", "'", string),
				new MultiLineRule("<", ">", function),
				new SingleLineRule("[", "]", character),
				new WhitespaceRule(new NezWhitespaceDetector()),
		};

		setRules(rules);
	}

	public void init() {
		IToken token = new Token(new TextAttribute(manager.getColor(INezColorConstants.DEFAULT)));
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
		IToken keyword = new Token(new TextAttribute(
				manager.getColor(PreferenceConstants.COLOR_KEYWORD), null, SWT.BOLD));
		KeywordRule keywordRule = new KeywordRule(token);
		keywordRule.addWords(NEZ_KEYWORD, keyword);

		IRule[] rules = {
				keywordRule,
				new labelRule(label),
				new tagRule(tag),
				new ConnectorRule(connector),
				new MultiLineRule("'''", "'''", example),
				new SingleLineRule("`", "`", value),
				new SingleLineRule("\"", "\"", string), new SingleLineRule("'", "'", string),
				new funcRule(function),
				new SingleLineRule("[", "]", character),
				new WhitespaceRule(new NezWhitespaceDetector()),
		};
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
		return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z');
	}

	@Override
	public boolean isWordPart(char c) {
		return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z');
	}
}

class NezKeywordDetector implements IWordDetector {

	@Override
	public boolean isWordPart(char c) {
		return ('a' <= c && c <= 'z');
	}

	@Override
	public boolean isWordStart(char c) {
		return (c == 'p' || c == 'i' || c == 't' || c == 'g' || c == 'f' || c == 'd' || c == 'e');
	}

}

class KeywordRule extends WordRule {
	public KeywordRule(IToken defaultT) {
		super(new NezKeywordDetector(), defaultT);
	}

	public void addWords(String[] words, IToken token) {
		for (String keyword : words) {
			addWord(keyword, token);
		}
	}
}
