package org.jboss.reddeer.swt.condition;

import org.eclipse.swt.widgets.Shell;
import org.hamcrest.core.IsEqual;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.lookup.ShellLookup;
import org.jboss.reddeer.swt.util.internal.InstanceValidator;

/**
 * Condition is fulfilled when shell with text is active
 * 
 * @author Vlado Pakan
 * @author jniederm
 * 
 */
public class ShellWithTextIsActive implements WaitCondition {

	private final Logger log = Logger.getLogger(this.getClass());
	private org.hamcrest.Matcher<String> matcher;

	/**
	 * @throws IllegalArgumentException if {@code text} is {@code null}
	 */
	public ShellWithTextIsActive(String text) {
		InstanceValidator.checkNotNull(text, "text");
		this.matcher = new IsEqual<String>(text);
	}
	
	/**
	 * @throws IllegalArgumentException if {@code matcher} is {@code null}
	 */
	public ShellWithTextIsActive(org.hamcrest.Matcher<String> matcher) {
		InstanceValidator.checkNotNull(matcher, "matcher");
		this.matcher = matcher;
	}

	@Override
	public boolean test() {
		Shell currentActiveShell = ShellLookup.getInstance().getCurrentActiveShell();
		if (currentActiveShell == null) {
			return false;
		}
		String activeText = WidgetHandler.getInstance().getText(currentActiveShell);
		log.debug("Active shell: " + "\"" + activeText + "\"" + " / Expected shell: " + matcher);
		boolean matches = matcher.matches(activeText);
		return matches;
	}

	@Override
	public String description() {
		return "shell with text matching" + matcher.toString() + " is active";
	}

}
