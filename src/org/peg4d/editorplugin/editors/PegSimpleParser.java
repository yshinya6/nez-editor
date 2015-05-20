package org.peg4d.editorplugin.editors;

import org.peg4d.Grammar;
import org.peg4d.GrammarFactory;
import org.peg4d.ParsingContext;
import org.peg4d.ParsingRule;
import org.peg4d.ParsingSource;
import org.peg4d.UList;
import org.peg4d.editorplugin.parser.EditorSource;
import org.peg4d.editorplugin.parser.PegGrammar;

public class PegSimpleParser {
	private String sourceText;
	private GrammarFactory factory;

	public PegSimpleParser(String sourceText) {
		this.sourceText = sourceText;
		this.factory = new GrammarFactory();
	}

	public UList<ParsingRule> getRuleList() {
		ParsingSource source = EditorSource.loadTargetSource(sourceText);
		PegGrammar peg = new PegGrammar(factory, "target");
		peg.loadGrammar(source);
		UList<ParsingRule> ruleList = peg.getRuleList();
		return ruleList;
	}

	public ParsingContext parse() {
		ParsingSource source = EditorSource.loadTargetSource(sourceText);
		Grammar peg4d = GrammarFactory.Grammar;
		ParsingContext context = new ParsingContext(source);
		while (context.hasByteChar()) {
			context.parse(peg4d, "Chunk");
			if (context.isFailure()) {
				break;
			}
		}
		return context;
	}
}
