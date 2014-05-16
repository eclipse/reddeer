package org.jboss.reddeer.swt.condition;

/**
 * Condition is fulfilled when progress information shell is active
 * 
 * @author Lucia Jelinkova
 *
 */
public class ProgressInformationShellIsActive extends ShellWithTextIsActive {

	public ProgressInformationShellIsActive() {
		super("Progress Information");
	}
}
