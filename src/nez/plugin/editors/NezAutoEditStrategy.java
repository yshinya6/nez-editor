package nez.plugin.editors;

import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;

// this class has auto insertion command
public class NezAutoEditStrategy implements IAutoEditStrategy {

	@Override
	public void customizeDocumentCommand(IDocument document, DocumentCommand command) {
		try {
			if (command.text.equals("{")) {
				command.text += "}";
				command.caretOffset = command.offset + 1;
				command.shiftsCaret = false;
				command.doit = false;
			} else if (command.text.equals("(")) {
				command.text += ")";
				command.caretOffset = command.offset + 1;
				command.shiftsCaret = false;
				command.doit = false;
			} else if (command.text.equals("<")) {
				command.text += ">";
				command.caretOffset = command.offset + 1;
				command.shiftsCaret = false;
				command.doit = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
