package org.jboss.reddeer.eclipse.condition;

import org.jboss.reddeer.eclipse.ui.problems.ProblemsView;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;

/**
 * Wait condition for a empty problems view. If there are no errors nor warnings, condition is met.
 * 
 * @author mlabuda@redhat.com
 * since 0.7
 *
 */
public class ProblemsViewIsEmpty implements WaitCondition {

	private ProblemsView problemsView;
	
	/**
	 * Creates a new wait condition to wait for empty problems view.
	 */
	public ProblemsViewIsEmpty() {
		problemsView = new ProblemsView();
		problemsView.open();
	}
	
	@Override
	public boolean test() {
		problemsView.activate();
		// using this should ensure atomicity, because getProblems method of Problems view 
		// does not get warnings and errors at once
		return new DefaultTree().getItems().isEmpty();
	}

	@Override
	public String description() {
		return " Problems view is empty.";
	}

}
