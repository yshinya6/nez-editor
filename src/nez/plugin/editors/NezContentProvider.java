package nez.plugin.editors;

import java.util.ArrayList;

import nez.lang.Production;
import nez.util.UList;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

// This class manage the tree of contents in OutlineView.
// 
public class NezContentProvider implements ITreeContentProvider {

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
		UList<Production> newList = extractLiteralRules(rootData.ruleList);
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

	public UList<Production> extractLiteralRules(UList<Production> parentList) {
		ArrayList<Production> literals = new ArrayList<>();
		ArrayList<Production> nonTerminalRules = new ArrayList<>();
		UList<Production> newList = new UList<Production>(new Production[4]);

		for (Production rule : parentList) {
			if (rule.getLocalName().startsWith("\"")) {
				literals.add(rule);
			}
			else {
				nonTerminalRules.add(rule);
			}
		}
		RuleSet nonTerminalRuleSet = new RuleSet("RULES (" + nonTerminalRules.size() + ")",
				nonTerminalRules);
		RuleSet literalRuleSet = new RuleSet("LITERALS (" + literals.size() + ")", literals);
		newList.add(nonTerminalRuleSet);
		newList.add(literalRuleSet);

		return newList;
	}
}

class RuleSet extends Production {
	Object[] ruleList;
	String setName;

	public RuleSet(String setName, ArrayList<Production> literals) {
		super();
		this.ruleList = literals.toArray();
		this.setName = setName;
	}
	public Object[] getLiteralList() {
		return this.ruleList;
	}

	@Override
	public String toString() {
		return this.setName;
	}

}
