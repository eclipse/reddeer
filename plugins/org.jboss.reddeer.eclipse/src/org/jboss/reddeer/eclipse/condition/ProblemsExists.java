package org.jboss.reddeer.eclipse.condition;

import java.util.List;

import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.ui.problems.ProblemsView;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.common.condition.WaitCondition;

/**
 * Returns true, if there is at least one problem marker in problems view (warning or error)
 * 
 * @author Jaroslav Jankovic
 * @deprecated since 0.7. Use ProblemExists and ProblemsViewIsEmpty instead of this one.
 */
@Deprecated
public class ProblemsExists implements WaitCondition {

	public enum ProblemType {
		NONE, BOTH, ANY, ERROR, WARNING;
	}

	private ProblemsView problemsView;

	private ProblemType problemType;

	public ProblemsExists() {
		this(false);
	}

	/**
	 * Construct the condition with a given problem type checking.
	 * 
	 * @param bothProblemTypes If true is specified then there must be at least one error and at least one warning.
	 */
	public ProblemsExists(boolean bothProblemTypes) {
		if (bothProblemTypes) {
			problemType = ProblemType.BOTH;
		} else {
			problemType = ProblemType.ANY;
		}
	}

	/**
	 * Construct the condition with a given problem type checking.
	 * 
	 * @param problemType Problem type
	 */
	public ProblemsExists(ProblemType problemType) {
		this.problemType = problemType;
	}

	@Override
	public boolean test() {
		problemsView = new ProblemsView();
		problemsView.open();

		List<TreeItem> errors = problemsView.getAllErrors();
		List<TreeItem> warning = problemsView.getAllWarnings();

		switch (problemType) {
		case ANY: return !errors.isEmpty() || !warning.isEmpty();
		case BOTH: return !errors.isEmpty() && !warning.isEmpty();
		case NONE: return errors.isEmpty() && warning.isEmpty(); 
		case WARNING: return !warning.isEmpty(); 
		case ERROR: return !errors.isEmpty(); 
		default: throw new EclipseLayerException("Unknown Problem type: " + problemType);
		}
	}

	@Override
	public String description() {
		switch (problemType) {
		case ANY: return "there is at least one error or warning in Problems view";
		case BOTH: return "there is at least one error and one warning in Problems view"; 
		case WARNING: return "there is at least one warning in Problems view";
		case ERROR: return "there is at least one error in Problems view";
		case NONE: return "there is no error and no warning in Problems view";
		default: throw new EclipseLayerException("Unknown Problem type: " + problemType);
		}
	}
}
