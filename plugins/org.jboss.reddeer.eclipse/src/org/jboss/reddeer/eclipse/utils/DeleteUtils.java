package org.jboss.reddeer.eclipse.utils;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.condition.JobIsRunning;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.AbstractShell;
import org.jboss.reddeer.swt.lookup.ShellLookup;
import org.jboss.reddeer.swt.matcher.AndMatcher;
import org.jboss.reddeer.swt.matcher.WithTextMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitWhile;

public class DeleteUtils {
	private static final Logger log = Logger.getLogger(DeleteUtils.class);
	/**
	 * Handles specific cases during project deletion
	 * @param deleteShellText text of shell
	 */
	public static void handleDeletion(String deleteShellText) {
		try {
			new WaitWhile(new ShellWithTextIsActive(deleteShellText),
					TimePeriod.VERY_LONG);
		} catch (WaitTimeoutExpiredException e) {
			new ShellWithButton(deleteShellText, "Continue");
			new PushButton("Continue").click();
			new WaitWhile(new ShellWithTextIsActive(deleteShellText),
					TimePeriod.VERY_LONG);
		}
		new WaitWhile(new JobIsRunning(), TimePeriod.VERY_LONG);
	}
	
	private static class ShellWithButton extends AbstractShell {

		public ShellWithButton(String title, String buttonLabel) {
			super(lookForShellWIthButton(title, buttonLabel));
			setFocus();
			log.debug("Shell with title '" + title + "' and button '"
					+ buttonLabel + "' found");
		}

	}

	private static Shell lookForShellWIthButton(final String title,
			final String buttonLabel) {
		Matcher<String> titleMatcher = new WithTextMatcher(title);
		Matcher<String> buttonMatcher = new BaseMatcher<String>() {
			@Override
			public boolean matches(Object obj) {
				if (obj instanceof Control) {
					final Control control = (Control) obj;
					ReferencedComposite ref = new ReferencedComposite() {
						@Override
						public Control getControl() {
							return control;
						}
					};
					try {
						new PushButton(ref, buttonLabel);
						return true;
					} catch (SWTLayerException e) {
						// ok, this control doesn't contain the button
					}
				}
				return false;
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("containing button '" + buttonLabel
						+ "'");
			}
		};
		@SuppressWarnings("unchecked")
		Matcher<String> matcher = new AndMatcher(titleMatcher, buttonMatcher);
		return ShellLookup.getInstance().getShell(matcher);
	}
	
}
