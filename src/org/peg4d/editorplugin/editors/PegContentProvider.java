package org.peg4d.editorplugin.editors;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.peg4d.ParsingRule;
import org.peg4d.UList;

//アウトラインツリーの内容を管理するクラス
public class PegContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}

	@Override
	public Object[] getElements(Object inputElement) {
		RootData rootData = (RootData) inputElement;
		if (rootData.ruleList == null) {
			return new Object[]{};
		}
		UList<ParsingRule> newList = extractLiteralRules(rootData.ruleList);
		return newList.toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof RuleSet) {
			RuleSet ruleSet = (RuleSet) parentElement;
			return ruleSet.getLiteralList();
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof RuleSet) {
			return true;
		}
		return false;
	}

	public UList<ParsingRule> extractLiteralRules(UList<ParsingRule> parentList) {
		ArrayList<ParsingRule> literals = new ArrayList<>();
		ArrayList<ParsingRule> nonTerminalRules = new ArrayList<>();
		UList<ParsingRule> newList = new UList<ParsingRule>(new ParsingRule[4]);

		//		for (ParsingRule parsingRule : parentList) {
		//			switch (parsingRule.type) {
		//				case ParsingRule.LexicalRule :
		//					lexicalRules.add(parsingRule);
		//					break;
		//				case ParsingRule.ObjectRule :
		//					objectRules.add(parsingRule);
		//					break;
		//				case ParsingRule.OperationRule :
		//					operationRules.add(parsingRule);
		//					break;
		//			}
		//		}
		for (ParsingRule parsingRule : parentList) {
			if (parsingRule.ruleName.startsWith("\"")) {
				literals.add(parsingRule);
			}
			else {
				nonTerminalRules.add(parsingRule);
			}
		}
		//		for (int i = 0; i < parentList.size(); i++) {
		//			if (parentList.get(i).ruleName.startsWith("\"")) {
		//				literals.add(parentList.get(i));
		//			} else {
		//				nonTerminalRules.add(parentList.get(i));
		//			}
		//		}
		RuleSet nonTerminalRuleSet = new RuleSet("RULES (" + nonTerminalRules.size() + ")",
				nonTerminalRules);
		RuleSet literalRuleSet = new RuleSet("LITERALS (" + literals.size() + ")", literals);
		newList.add(nonTerminalRuleSet);
		newList.add(literalRuleSet);
		//		RuleSet lexicalRuleSet = new RuleSet("LexicalRules (" + lexicalRules.size() + ")",
		//				lexicalRules);
		//		newList.add(lexicalRuleSet);
		//		RuleSet objectRuleSet = new RuleSet("ObjectRules (" + objectRules.size() + ")", objectRules);
		//		newList.add(objectRuleSet);
		//		RuleSet operationRuleSet = new RuleSet("OperationRules (" + operationRules.size() + ")",
		//				operationRules);
		//		newList.add(operationRuleSet);
		return newList;
	}
}

class RuleSet extends ParsingRule {
	Object[] ruleList;

	public RuleSet(String ruleName, ArrayList<ParsingRule> literals) {
		super(null, ruleName, null, null);
		this.ruleList = literals.toArray();
	}

	public Object[] getLiteralList() {
		return this.ruleList;
	}

}
