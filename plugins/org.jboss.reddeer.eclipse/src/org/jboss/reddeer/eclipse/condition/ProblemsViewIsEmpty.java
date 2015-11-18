package org.jboss.reddeer.eclipse.condition;

import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.eclipse.ui.problems.ProblemsView;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;

/**
 * Wait condition for a empty problems view. If there are no errors nor warnings, condition is met.
 * 
 * @author mlabuda@redhat.com
 * since 0.7
 *
 */
public class ProblemsViewIsEmpty extends AbstractWaitCondition {

	private ProblemsView problemsView;
	
	/**
	 * Creates a new wait condition to wait for empty problems view.
	 */
	public ProblemsViewIsEmpty() {
		problemsView = new ProblemsView();
		problemsView.open();
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.WaitCondition#test()
	 */
	@Override
	public boolean test() {
		problemsView.activate();
		// using this should ensure atomicity, because getProblems method of Problems view 
		// does not get warnings and errors at once
		return new DefaultTree().getItems().isEmpty();
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return " Problems view is empty.";
	}

}
