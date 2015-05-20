package org.peg4d.editorplugin.editors;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

public class PegContentAssistProcessor implements IContentAssistProcessor {

	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
			int offset) {
		List<String> ruleNameList = new ArrayList<String>();
		String source = viewer.getDocument().get();
		Pattern p = Pattern.compile("^[ \t]*" + "([a-zA-Z][a-zA-Z0-9]*)"
				+ "(\\s*\\[[^\\]]*\\])?" + "\\s*=", Pattern.MULTILINE);

		// create ruleName list
		Matcher m = p.matcher(source);
		while (m.find()) {
			String ruleNamePart = m.group(1);
			ruleNameList.add(ruleNamePart);
		}

		// search for head of target word
		int n = offset - 1;
		for (; n >= 0; n--) {
			char c;
			try {
				c = viewer.getDocument().getChar(n);
			} catch (BadLocationException e) {
				break;
			}
			if ('A' <= c && c <= 'Z' || 'a' <= c && c <= 'z' || '0' <= c
					&& c <= '9') {
				continue;
			} else {
				break;
			}
		}
		// search for '=' or '/' to detect writing ruleName
		for (int i = n; i >= 0; i--) {
			char c;
			try {
				c = viewer.getDocument().getChar(i);
			} catch (BadLocationException e) {
				break;
			}
			if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
				continue;
			} else if (c == '=' || c == '/' || c == '!' || c == '?' || c == '{'
					|| c == '(' || c == '@') {
				int start = n + 1;
				int len = offset - start;
				String text;
				try {
					text = viewer.getDocument().get(start, len);
				} catch (BadLocationException e) {
					return null;
				}
				List<ICompletionProposal> list = new ArrayList<ICompletionProposal>();
				for (i = 0; i < ruleNameList.size(); i++) {
					String cand = ruleNameList.get(i);
					if (len == 1) {
						if (cand.startsWith(text)
								|| cand.startsWith(text.toUpperCase())) {
							list.add(new CompletionProposal(cand, start, len,
									cand.length()));
						}
					} else if (len > 1) {
						if (cand.startsWith(text)
								|| cand.startsWith(text.toUpperCase())
								|| cand.startsWith(text.substring(0, 1)
										.toUpperCase()
										+ text.substring(1).toLowerCase())) {
							list.add(new CompletionProposal(cand, start, len,
									cand.length()));
						}
					} else {
						break;
					}
				}
				return list.toArray(new ICompletionProposal[list.size()]);
			} else {
				break;
			}
		}
		return null;
	}

	@Override
	public IContextInformation[] computeContextInformation(ITextViewer viewer,
			int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		return new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
				'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
				'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
				'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
				'u', 'v', 'w', 'x', 'y', 'z' };
	}

	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IContextInformationValidator getContextInformationValidator() {
		// TODO Auto-generated method stub
		return null;
	}

}
