package org.jboss.reddeer.logparser.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.jboss.reddeer.logparser.editors.LogParserEditor;

public class ShowInOriginalLogHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEditorPart editorPart = HandlerUtil.getActiveEditor(event);
		if (editorPart instanceof LogParserEditor){
			((LogParserEditor) editorPart).showSelectedParsedLineInOriginalLog();
		}		
		return null;
	}

}
