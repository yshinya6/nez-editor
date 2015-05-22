package nez.plugin.editors;

import java.util.ArrayList;
import java.util.List;

import nez.SourceContext;
import nez.ast.CommonTree;
import nez.lang.Grammar;
import nez.lang.GrammarChecker;
import nez.lang.NameSpace;
import nez.lang.NezParser;
import nez.lang.Production;
import nez.lang.ReportSet;
import nez.main.CommandConfigure;
import nez.util.ConsoleUtils;
import nez.util.UList;

public class NezEParser extends NezParser {
	private String sourceText;
	private Grammar nezGrammar;
	CommandConfigure config;
	NameSpace ns;
	GrammarChecker checker;

	public NezEParser(String sourceText) {
		super();
		this.sourceText = sourceText;
		this.config = new CommandConfigure();
		this.ns = config.getNameSpace(true);
		this.checker = new GrammarChecker();
	}

	public CommonTree parse(SourceContext sc) {
		CommonTree node = nezGrammar.parse(sc);
		if (node == null) {
			ConsoleUtils.println(sc.getSyntaxErrorMessage());
		}
		if(sc.hasUnconsumed()) {
			ConsoleUtils.println(sc.getUnconsumedMessage());
			node = null;
		}
		return node;
	}

	public void parse() {
		this.eval(ns, "<stdio>", 0, this.sourceText);
	}

	public UList<Production> getProductionList() {
		return ns.getRuleList();
	}

	public List<String> getNonterminalList() {
		return ns.getNonterminalList();
	}

	public ArrayList<ReportSet> getReportList() {
		checker.verify(ns);
		return checker.reportList;
	}


}
