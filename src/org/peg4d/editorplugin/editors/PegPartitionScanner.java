package org.peg4d.editorplugin.editors;

import org.eclipse.jface.text.rules.*;

public class PegPartitionScanner extends RuleBasedPartitionScanner {
	public final static String PEG_COMMENT = "__peg_comment";
	public final static String PEG_GROUP = "__peg_group";

	public PegPartitionScanner() {

		IToken pegComment = new Token(PEG_COMMENT);
		IToken pegGroup = new Token(PEG_GROUP);
		
		IPredicateRule[] rules = new IPredicateRule[3];

		rules[0] = new EndOfLineRule("//",pegComment);
		rules[1] = new MultiLineRule("/*", "*/", pegComment);
		rules[2] = new GroupRule(pegGroup);
		setPredicateRules(rules);
	}
}
