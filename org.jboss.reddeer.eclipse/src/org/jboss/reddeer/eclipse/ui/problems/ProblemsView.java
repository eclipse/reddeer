package org.jboss.reddeer.eclipse.ui.problems;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.jboss.reddeer.swt.util.Bot;
import org.jboss.reddeer.workbench.view.impl.WorkbenchView;

public class ProblemsView extends WorkbenchView{

	public ProblemsView() {
		super("Problems");
	}

	/**
	 * Returns array of all errors
	 * 
	 * @return
	 */
	
	public List<String> getAllErrors(){
		if (!viewObject.isActive()){
			viewObject.setFocus();
		}
		SWTBot problemsBot = viewObject.bot();
		SWTBotTree tree = problemsBot.tree();
		if (!tree.hasItems() || !tree.getAllItems()[0].getText().contains("Errors")){
			log.info("No errors found");
			return new ArrayList<String>();
		}
		return tree.expandNode("Errors").getNodes();
	}
	
}
