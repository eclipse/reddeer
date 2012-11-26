package org.jboss.reddeer.eclipse.condition;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.eclipse.ui.problems.ProblemsView;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.WaitCondition;

/**
 * Returns true, if there is no problem marker in problems view
 * 
 * @author Jaroslav Jankovic
 *
 */
public class ProblemsNotExists implements WaitCondition {

	private ProblemsView problemsView;
	
	@Override
	public boolean test() {
		problemsView = new ProblemsView();
		problemsView.open();
		
		List<TreeItem> errors = new ArrayList<TreeItem>();
		errors.addAll(problemsView.getAllErrors());
		if (errors.size() > 0) return false;
		
		List<TreeItem> warning = new ArrayList<TreeItem>();
		warning.addAll(problemsView.getAllWarnings());
		if (warning.size() > 0) return false;
		
		return true;
	}
	
	@Override
	public String description() {
		StringBuilder msg = new StringBuilder("There is problem marker in Problems view \n");
		return msg.toString();
	}
	
}
