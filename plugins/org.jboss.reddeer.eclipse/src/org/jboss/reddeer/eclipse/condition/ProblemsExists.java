package org.jboss.reddeer.eclipse.condition;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.eclipse.ui.problems.ProblemsView;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.WaitCondition;

/**
 * Returns true, if there is at least one problem marker in problems view (warning or error)
 * 
 * @author Jaroslav Jankovic
 *
 */
public class ProblemsExists implements WaitCondition {

	private ProblemsView problemsView;
	
	private boolean bothProblemTypes;
	
	public ProblemsExists() {
		this(false);
	}
	
	public ProblemsExists(boolean bothProblemTypes) {
		this.bothProblemTypes = bothProblemTypes;
	}
	
	@Override
	public boolean test() {
		List<TreeItem> errors = new ArrayList<TreeItem>();
		List<TreeItem> warning = new ArrayList<TreeItem>();
		problemsView = new ProblemsView();
		problemsView.open();
		errors.addAll(problemsView.getAllErrors());
		warning.addAll(problemsView.getAllWarnings());
		if (bothProblemTypes) {
			return errors.size() > 0 && warning.size() > 0; 
		} else {
			return errors.size() > 0 || warning.size() > 0;
		}
	}
	
	@Override
	public String description() {
		return "there is problem marker in Problems view";
	}
	
}
