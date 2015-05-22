package nez.plugin.editors;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.Token;

public class Rule extends MultiLineRule {
	public Rule(String startSequence, String endSequence, IToken token) {
		super(startSequence, endSequence, token);
	}
}

class AngleRule extends Rule {
	public AngleRule(IToken token) {
		super("<", ">", token);
	}
}

class ObjectRule extends Rule {
	public ObjectRule(IToken token) {
		super("{", "}", token);
	}
}

class GroupRule extends Rule {
	public GroupRule(IToken token) {
		super("(", ")", token);
	}
}

class ConnectorRule implements IRule {
	private IToken token;

	public ConnectorRule(IToken token) {
		Assert.isNotNull(token);
		this.token = token;
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner) {
		int c = scanner.read();
		if (c == '@') {
			return token;
		}
		scanner.unread();
		return Token.UNDEFINED;
	}

	protected Boolean isWordPart(char c) {
		return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z')
				|| ('0' <= c && c <= '9');
	}
}

class tagRule implements IRule {
	private IToken token;
	public tagRule(IToken token) {
		Assert.isNotNull(token);
		this.token = token;
	}
	@Override
	public IToken evaluate(ICharacterScanner scanner) {
		int c = scanner.read();
		if (c == '#') {
			do {
				c = scanner.read();
			} while (isWordPart((char) c) || c == '.' || c == '_');
			scanner.unread();
			return token;
		}
		scanner.unread();
		return Token.UNDEFINED;
	}

	protected Boolean isWordPart(char c) {
		return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z')
				|| ('0' <= c && c <= '9');
	}
}

class labelRule implements IRule {
	private IToken token;
	public labelRule(IToken token) {
		Assert.isNotNull(token);
		this.token = token;
	}
	@Override
	public IToken evaluate(ICharacterScanner scanner) {
		int c = scanner.read();
		if (isLetterPart((char) c)) {
			//ルール名を読み進める
			do {
				c = scanner.read();
			} while ((isWordPart((char) c)));
			// "="か"[e"を見つけられればルール名と判断する．
			scanner.unread();
			do {
				c = scanner.read();
				if (c == '=') {
					scanner.unread();
					return token;
				}
			} while (isWhiteSpace((char) c));
			scanner.unread();
			scanner.unread();
			return Token.UNDEFINED;
		} else {
			scanner.unread();
			return Token.UNDEFINED;
		}
	}

	protected Boolean isWordPart(char c) {
		return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z')
				|| ('0' <= c && c <= '9') || (c == '_');
	}

	protected Boolean isLetterPart(char c) {
		return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z') || (c == '_');
	}

	protected Boolean isWhiteSpace(char c) {
		return (c == '\n') || (c == '\r') || (c == ' ') || (c == '\t');
	}
}

class tokenRule implements IRule {
	private IToken token;

	public tokenRule(IToken token) {
		Assert.isNotNull(token);
		this.token = token;
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner) {
		int c = scanner.read();
		if (isWordPart((char) c)) {
			do {
				c = scanner.read();
			} while ((isWordPart((char) c)));
			scanner.unread();
			return token;
		}
		scanner.unread();
		return Token.UNDEFINED;
	}

	protected Boolean isWordPart(char c) {
		return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z')
				|| ('0' <= c && c <= '9');
	}
}

class funcRule implements IRule {
	private IToken token;

	public funcRule(IToken token) {
		Assert.isNotNull(token);
		this.token = token;
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner) {
		int c = scanner.read();
		if (c == '<') {
			c = scanner.read();
			if (isWordPart((char) c)) {
				do {
					c = scanner.read();
				} while (c != '>');
				c = scanner.read();
				scanner.unread();
				return token;
			}
			scanner.unread();
			return Token.UNDEFINED;
		}
		scanner.unread();
		return Token.UNDEFINED;
	}

	protected Boolean isWordPart(char c) {
		return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z');
	}
}

//class publicRule implements IRule {
//	IToken token;
//
//	public publicRule(IToken token) {
//		Assert.isNotNull(token);
//		this.token = token;
//	}
//
//	@Override
//	public IToken evaluate(ICharacterScanner scanner) {
//		int c = scanner.read();
//		if (c == 'p') {
//			int[] word = new int[6];
//			word[0] = 'p';
//			for (int i = 0; i < 6; i++) {
//				word[i] = scanner.read();
//			}
//			if (word.equals("public")) {
//				scanner.unread();
//				return Token.UNDEFINED;
//			}
//		}
//		scanner.unread();
//		return token;
//	}
//
// }
