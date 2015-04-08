package org.jboss.reddeer.core.condition;

import org.eclipse.swt.widgets.Shell;
import org.hamcrest.core.IsEqual;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.condition.WaitCondition;
import org.jboss.reddeer.common.exception.RedDeerException;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.lookup.ShellLookup;
import org.jboss.reddeer.core.util.InstanceValidator;

/**
 * Condition is met when shell with specified text (title) is active.
 * 
 * @author Vlado Pakan
 * @author jniederm
 */

public class ShellWithTextIsActive implements WaitCondition {

	private static final Logger log = Logger.getLogger(ShellWithTextIsActive.class);
	private org.hamcrest.Matcher<String> matcher;

	/**
	 * Constructs ShellWithTextIsActive wait condition. Condition is met when
	 * shell with specified text is active.
	 * 
	 * @param text text/name of the shell
	 */
	public ShellWithTextIsActive(String text) {
		InstanceValidator.checkNotNull(text, "text");
		this.matcher = new IsEqual<String>(text);
	}

	/**
	 * Constructs ShellWithTextIsActive wait condition. Condition is met when
	 * shell matching matcher is active.
	 * 
	 * @param matcher matcher matching text of the shell
	 */
	public ShellWithTextIsActive(org.hamcrest.Matcher<String> matcher) {
		InstanceValidator.checkNotNull(matcher, "matcher");
		this.matcher = matcher;
	}

	@Override
	public boolean test() {
		Shell currentActiveShell = ShellLookup.getInstance()
				.getCurrentActiveShell();
		if (currentActiveShell == null) {
			log.debug("Current active shell is null");
			return false;
		}

		String activeText;
		try {
			activeText = WidgetHandler.getInstance()
					.getText(currentActiveShell);
		} catch (RedDeerException e) {
			log.debug("Unable to determine text of current active shell");
			return false;
		}

		log.debug("Active shell: " + "\"" + activeText + "\""
				+ " / Expected shell: " + matcher);
		boolean matches = matcher.matches(activeText);
		return matches;
	}

	@Override
	public String description() {
		return "shell with text matching" + matcher.toString() + " is active";
	}

}